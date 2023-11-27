package com.webProject.webProject.Review;

import com.webProject.webProject.Comment.Comment;
import com.webProject.webProject.Photo.Photo;
import com.webProject.webProject.Review_tag.Review_tag;
import com.webProject.webProject.Store.Store;
import com.webProject.webProject.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;   //리뷰내용

    private Double rating; //글쓴이가 등록한 별점

    @ManyToOne
    private Store store; // 음식점 이름

    @ManyToOne
    private User author; //작성자

    @OneToMany(mappedBy = "review")
    private List<Review_tag> reviewTagList;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "review")
    private List<Photo> photoList;

    @OneToMany(mappedBy = "review")
    private List<Comment> commentList;

    @ManyToMany
    Set<User> voter;
}
