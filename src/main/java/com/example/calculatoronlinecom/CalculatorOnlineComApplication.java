package com.example.calculatoronlinecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CalculatorOnlineComApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculatorOnlineComApplication.class, args);
    }
//hello world

    //TODO Это костыль, найти как можно исправить (UserService.java)
    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
