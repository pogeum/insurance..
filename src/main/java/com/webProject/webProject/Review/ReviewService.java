package com.webProject.webProject.Review;

import com.webProject.webProject.DataNotFoundException;
import com.webProject.webProject.Review_tag.Review_tag;
import com.webProject.webProject.Review_tag.Review_tagRepository;
import com.webProject.webProject.Review_tag.Review_tagService;
import com.webProject.webProject.Store.Store;
import com.webProject.webProject.Tag.Tag;
import com.webProject.webProject.Tag.TagService;
import com.webProject.webProject.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final TagService tagService;
    private final Review_tagRepository reviewTagRepository;

    public List<Review> getreviewList(Store store) {
        return this.reviewRepository.findAllByStore(store);
    }
    
    public Review findById(Integer reviewid) {
        return this.reviewRepository.findById(reviewid).get();
    }
    
    public Review getReview(Integer id) {
        Optional<Review> review = this.reviewRepository.findById(id);
        if (review.isPresent()) {
            return review.get();
        } else {
            throw new DataNotFoundException("review not found");
        }
    }
    
    public Review   create(User user, Store store, String content, Double rating) {
        Review review = new Review();
        review.setStore(store);
        review.setAuthor(user);
        review.setContent(content);
        review.setRating(rating);
        review.setCreateDate(LocalDateTime.now());

        this.reviewRepository.save(review);
        return review;
    }

    public void modify(Review review, String content, Double rating) {
        review.setContent(content);
        review.setRating(rating);
        review.setModifyDate(LocalDateTime.now());
        this.reviewRepository.save(review);
    }


    public void delete(Review review) {
        this.reviewRepository.delete(review);
    }

    // 리뷰에 해당하는 태그 ID 목록 가져오기
    public List<Integer> getTagIdsForReview(Integer reviewId) {
        List<Review_tag> reviewTags = reviewTagRepository.findByReviewId(reviewId);
        List<Integer> tagIds = new ArrayList<>();

        for (Review_tag reviewTag : reviewTags) {
            tagIds.add(reviewTag.getTag().getId());
        }

        return tagIds;
    }
    
    // 수정하기 -> 새로운 태그 저장
    public void addTagToReview(Review review, Tag tag) {
        Review_tag reviewTag = new Review_tag();
        reviewTag.setReview(review);
        reviewTag.setTag(tag);

        reviewTagRepository.save(reviewTag);
    }

    public void vote(Review review, User User) {
        review.getVoter().add(User);
        this.reviewRepository.save(review);
    }
}
