package com.webProject.webProject.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String userId;

    private String password;

    @Column(unique = true)
    private String email;

    private String nickname;
}
