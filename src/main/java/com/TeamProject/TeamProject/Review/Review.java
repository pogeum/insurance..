package com.TeamProject.TeamProject.Review;

import com.TeamProject.TeamProject.Model.Restaurant;
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

  @Column(length = 200)
  public String title;

  @Column(columnDefinition = "TEXT")
  public String content;

  public int rating;

  public String category;

  @CreatedDate
  public LocalDateTime createDate;

  @ManyToOne
  public Restaurant restaurant;
}
