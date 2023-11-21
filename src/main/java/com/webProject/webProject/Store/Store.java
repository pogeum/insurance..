package com.webProject.webProject.Store;

import com.webProject.webProject.Review.Review;
import com.webProject.webProject.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
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
    private String roadAddress;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "store")
    private List<Review> reviewList;

    @ManyToOne
    private User author;

    @Lob
    private List<byte[]> imagefiles; //가게 사장이 첨부한 사진 등록할 필드
}
