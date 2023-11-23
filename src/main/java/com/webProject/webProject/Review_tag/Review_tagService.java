package com.webProject.webProject.Review_tag;

import com.webProject.webProject.Review.Review;
import com.webProject.webProject.Tag.Tag;
import com.webProject.webProject.Tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Review_tagService {
    private final TagService tagService;
    private final Review_tagRepository reviewTagRepository;

    public void saveTagsForReview(Review review, List<Integer> tagIds) {
        if (review != null && tagIds != null && !tagIds.isEmpty()) {
            for (Integer tagId : tagIds) {
                Tag tag = tagService.getTagById(tagId);
                if (tag != null) {
                    Review_tag reviewTag = new Review_tag();
                    reviewTag.setTag(tag);
                    reviewTag.setReview(review);
                    this.reviewTagRepository.save(reviewTag);
                }
            }
        }
    }
    // 해당 리뷰 기존의 태그 삭제
    public void deleteTagsForReview(Review review) {
        List<Review_tag> reviewTags = reviewTagRepository.findByReview(review);
        reviewTagRepository.deleteAll(reviewTags);
    }

    // 리뷰 삭제와 동시에 리뷰의 태그들 삭제
    public void deleteTagsByReviewId(Integer id) {
        List<Review_tag> reviewTags = reviewTagRepository.findByReviewId(id);
        reviewTagRepository.deleteAll(reviewTags);
    }
}
