package com.example.calculatoronlinecom.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {
    private Long id;
    @NotEmpty
    private String Firstname;
    @NotEmpty
    private String lastname;
  //  @NotEmpty
    private String username;
    private String bio;
}
