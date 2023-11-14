package com.TeamProject.TeamProject.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String membername; //아이디

    private String password; // 비번

    @Column(unique = true)
    private String email;

    @CreatedDate
    private LocalDateTime createDate; //가입일시

}
