package com.example.calculatoronlinecom.dto;

import lombok.Data;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String caption;
    private String location;
    private String username;
    private Integer likes;

}
