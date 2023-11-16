package com.TeamProject.TeamProject.Restaurant;

import com.TeamProject.TeamProject.Review.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //pk

    @Column(columnDefinition = "VARCHAR(100) NOT NULL")
    private String title; //식당이름

    @Column(columnDefinition = "VARCHAR(100) NOT NULL")
    private String category; //식당이름

    @Column(columnDefinition = "TEXT NOT NULL")
    private String address;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String roadAddress;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.REMOVE)
    private List<Review> reviewList;
}
