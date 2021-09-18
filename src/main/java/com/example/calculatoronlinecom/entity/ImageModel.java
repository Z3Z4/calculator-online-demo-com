package com.example.calculatoronlinecom.entity;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Data
@Entity
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Lob //Обычно @Lob используется для аннотирования поля HashMap внутри вашей сущности для хранения некоторых свойств объекта,
    // которые не отображаются в столбцах БД. Таким образом, все несопоставленные значения могут храниться в БД в одном столбце в их двоичном представлении.
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageBytes;
    @JsonIgnore             //используется для того, чтобы не передавать какие-то данные куда-то, в данном случае чтобы логин и пароль не были отправлены пользователю и никто не смог их украсть
    private Long userId;
    @JsonIgnore
    private Long postId;
}
