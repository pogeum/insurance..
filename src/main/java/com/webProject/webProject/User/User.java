package com.webProject.webProject.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

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

    private String role; // 권한 (admin, owner, user)
}
