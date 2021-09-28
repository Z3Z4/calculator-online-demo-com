package com.example.calculatoronlinecom.services;

import com.example.calculatoronlinecom.entity.User;
import com.example.calculatoronlinecom.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository USER_REPOSITORY;

    @Autowired
    public CustomUserDetailsService(IUserRepository USER_REPOSITORY) {
        this.USER_REPOSITORY = USER_REPOSITORY;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
User user = USER_REPOSITORY.findUserByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: "+ username)); //какого-то хуя мейл будем считать юзернеймом ну да ладно
        return buildUser(user);
    }

    public User loadUserById(Long id) {
        return USER_REPOSITORY.findUserById(id).orElse(null);
    }
    public static User buildUser(User user){
        List<GrantedAuthority> authorities = user.getRole().stream() //у него написано getRoles, у нас чомут не ищется такой
                .map(role-> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new User(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

}
