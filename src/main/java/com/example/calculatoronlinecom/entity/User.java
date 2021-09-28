package com.example.calculatoronlinecom.entity;

import com.example.calculatoronlinecom.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, updatable = false)
    private String username;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true)
    private String email;
    @Column(columnDefinition = "text")
    private String bio;
    @Column(length = 3000)
    private String password;

    @ElementCollection(targetClass = ERole.class) //@ElementCollection предназначен для отображения не-сущностей (встраиваемых или базовых), а @OneToMany используется для сопоставления объектов.
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))  //Аннотация @CollectionTable используется для указания имени таблицы, в которой хранятся все записи коллекции
    private Set<ERole> role = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)  //Аннотация для прогрузки одного поста
    private List<Post> posts = new ArrayList<>();
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")        //формат даты и времени для создания постов на сайте
    @Column(updatable = false)
    private LocalDateTime createData;
    @Transient
    /*    Сериализация - это преобразование экземпляра класса в форму, пригодную для его сохранения (например в файл, в БД или для передачи по сети).
    Сериализованные объекты можно затем восстановить (десериализовать).
    Свойства класса, помеченные модификатором transient, не сериализуются. */
    private Collection<? extends GrantedAuthority> authorities;

    public User(){
    }

    public User(Long id,
                String username,
                String email,
                String password,
                Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    @PrePersist
    protected void onCreate(){
        this.createData = LocalDateTime.now();
    }

    /*
    *SECURITY
     */

   /*
    @Override

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();  ЭТОТ ЕБЛАН НЕ НАЖАЛ ОК ПОЭТОМУ ТУТ КОММЕНТАРИЙ
    }*/
    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
