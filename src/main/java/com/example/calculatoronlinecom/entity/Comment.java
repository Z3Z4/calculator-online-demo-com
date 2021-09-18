package com.example.calculatoronlinecom.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data                                                                           //анотация которая содержит в себе много всяких фич и сокращает количество написанного кода, она содержит в себе другие аннотации
                                                                                // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@Entity                                                                          //Эта аннотация указывает Hibernate, что данный класс является сущностью (entity bean).
                                                                                // Такой класс должен иметь конструктор по-умолчанию (пустой конструктор).
public class Comment {

    @Id                                                                         //С помощью аннотации @Id мы указываем первичный ключ (Primary Key) данного класса.
    @GeneratedValue(strategy = GenerationType.IDENTITY)                         //Эта аннотация используется вместе с аннотацией @Id и определяет такие паметры, как strategy и generator.
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)                                         //Аннотация необходимая для корректной подгрузки данных из разных связанных между собою таблиц, в данном случае постов от одного
                                                                                // пользователя может быть много, а пользователь может быть только один, если поставить - lazy, будет показан только 1 пост, например.
    private Post post;
    @Column(nullable = false)
    /*Аннотация @Column определяет к какому столбцу в таблице БД относится конкретное поле класса (аттрибут класса).

    Наиболее часто используемые аттрибуты аннотации @Column такие:

    name
    Указывает имя столбца в таблице
            unique
    Определяет, должно ли быть данноезначение уникальным
            nullable
    Определяет, может ли данное поле быть NULL, или нет.
            length
    Указывает, какой размер столбца (например колчиство символов, при использовании String).*/
    private String username;
    @Column(nullable = false)
    private Long userId;
    @Column(columnDefinition = "text", nullable = false)
    private String message;
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}
