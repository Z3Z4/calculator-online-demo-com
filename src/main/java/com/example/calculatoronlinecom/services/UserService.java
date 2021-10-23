package com.example.calculatoronlinecom.services;


import com.example.calculatoronlinecom.dto.UserDTO;
import com.example.calculatoronlinecom.entity.User;
import com.example.calculatoronlinecom.entity.enums.ERole;
import com.example.calculatoronlinecom.exceptions.UserExistException;
import com.example.calculatoronlinecom.payload.request.SignupRequest;
import com.example.calculatoronlinecom.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserService(IUserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRole().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving user {}", userIn.getEmail());
            return userRepository.save(user);


        } catch (Exception e) {
            LOG.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + "already exist. Please check credential");

        }
    }


        public User updateUser(UserDTO userDTO, Principal principal) {
            User user = getUserByPrincipal(principal);
            user.setName(userDTO.getFirstname());
            user.setLastname(user.getLastname());
            user.setBio(userDTO.getBio());

            return userRepository.save(user);
        }

        public User getCurrentUser (Principal principal) {
            return getUserByPrincipal(principal);

        }


        private User getUserByPrincipal(Principal principal) {
            String username = principal.getName();
            return userRepository.findUserByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

        }

    }

