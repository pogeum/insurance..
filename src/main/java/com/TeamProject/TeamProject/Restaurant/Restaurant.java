package com.TeamProject.TeamProject.Restaurant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //pk

    @Column(columnDefinition = "TEXT NOT NULL")
    private String title; //식당이름

    @Column(columnDefinition = "VARCHAR(100) NOT NULL")
    private String category; //카테고리. 예를들면 프랑스음식이면 French로 저장됨.

    @Column(columnDefinition = "TEXT NOT NULL")
    private String address; // 식당주소

    @Column(columnDefinition = "TEXT NOT NULL")
    private String roadAddress;
}
