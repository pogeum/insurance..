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

    @Column(columnDefinition = "TEXT NOT NULL")
    private String title; //식당이름

    @Column(columnDefinition = "TEXT NOT NULL")
    private String roadAddress;

    @Column(columnDefinition = "VARCHAR(100) NOT NULL")
    private String one_info; // 식당 한줄 소개

    @Column(columnDefinition = "VARCHAR(100) NOT NULL")
    private String info; // 식당 소개

    @Column(columnDefinition = "TEXT NOT NULL")
    private String hoursDetails; // 식당 운영시간 정보

    @Column(columnDefinition = "TEXT NOT NULL")
    private String holidayInfo; // 식당 휴무 정보

    @Column(columnDefinition = "TEXT NOT NULL")
    private String menuInfo; // 식당 메뉴 정보

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.REMOVE)
    private List<Review> reviewList;
}
