package com.webProject.webProject.Review_tag;

import com.webProject.webProject.Review.Review;
import com.webProject.webProject.Tag.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Review_tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Tag tag;

    @ManyToOne
    private Review review;
}
