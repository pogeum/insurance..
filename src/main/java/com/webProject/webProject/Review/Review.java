package com.webProject.webProject.Review;

import com.webProject.webProject.Store.Store;
import com.webProject.webProject.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;    // 음식점 이름

    private double rating;

    @Column(columnDefinition = "TEXT")
    private String tag;

    @ManyToOne
    private Store store;
    
    @ManyToOne
    private User author;
}
