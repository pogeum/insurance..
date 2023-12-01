package com.webProject.webProject.Store;

import com.webProject.webProject.Menu.Menu;
import com.webProject.webProject.Photo.Photo;
import com.webProject.webProject.Review.Review;
import com.webProject.webProject.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(100)")
    private String name;    // 음식점 이름

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "VARCHAR(100)")
    private String category;

    @Column(columnDefinition = "TEXT")
    private String postcode;

    @Column(columnDefinition = "TEXT")
    private String roadAddress;

    @Column(columnDefinition = "TEXT")
    private String jibunAddress;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Menu> menuList;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Photo> photoList;
}
