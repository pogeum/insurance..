package com.webProject.webProject.Review;

import com.webProject.webProject.Review_tag.Review_tag;
import com.webProject.webProject.Store.Store;
import com.webProject.webProject.Tag.Tag;
import com.webProject.webProject.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;   //리뷰내용

    private double rating; //글쓴이가 등록한 별점

    @OneToMany(mappedBy = "review")
    private List<Review_tag> reviewTags;

    @ManyToOne
    private Store store; // 음식점 이름

    @ManyToOne
    private User author; //작성자

    private LocalDateTime createDate;
}
