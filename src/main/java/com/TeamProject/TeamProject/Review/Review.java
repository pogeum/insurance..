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
//
//  @Column(length = 200)
//  private String title;

  @Column(columnDefinition = "TEXT")
  private String content;

  private int rating; //평점
  private int thumbsup; // 해당 리뷰가 받은 추천수


  private Member author;

  @CreatedDate
  private LocalDateTime createDate;

  @ManyToOne
  private Restaurant restaurant;


}
