package com.webProject.webProject.Tag;

import com.mysql.cj.protocol.ColumnDefinition;
import com.webProject.webProject.Review.Review;
import com.webProject.webProject.Review_tag.Review_tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String tageName;

    @OneToMany(mappedBy = "tag")
    private List<Review_tag> reviewTags;

}
