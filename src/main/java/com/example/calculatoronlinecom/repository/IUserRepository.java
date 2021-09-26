package com.example.calculatoronlinecom.repository;


import com.example.calculatoronlinecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
Optional<User> findUserByUsername(String username);
Optional<User> findUserByEmail(String email);
Optional<User> findUserById(Long id);


}
