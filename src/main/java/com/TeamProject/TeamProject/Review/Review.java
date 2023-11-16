package com.TeamProject.TeamProject.Review;

import com.TeamProject.TeamProject.Member.Member;
import com.TeamProject.TeamProject.Restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(columnDefinition = "TEXT")
  private String content;

  private double rating; //평점

  private Integer thumbsup; // 해당 리뷰가 받은 추천수

  @ManyToOne
  private Member author;

  private LocalDateTime createDate;

  @ManyToOne
  private Restaurant restaurant;

}
