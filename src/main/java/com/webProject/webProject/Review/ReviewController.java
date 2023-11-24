package com.webProject.webProject.Review;

import com.webProject.webProject.Photo.Photo;
import com.webProject.webProject.Photo.PhotoRepository;
import com.webProject.webProject.Photo.PhotoService;
import com.webProject.webProject.Review_tag.Review_tag;
import com.webProject.webProject.Review_tag.Review_tagRepository;
import com.webProject.webProject.Review_tag.Review_tagService;
import com.webProject.webProject.Store.Store;
import com.webProject.webProject.Store.StoreService;
import com.webProject.webProject.Tag.Tag;
import com.webProject.webProject.Tag.TagRepository;
import com.webProject.webProject.Tag.TagService;
import com.webProject.webProject.User.User;
import com.webProject.webProject.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/review")
@RequiredArgsConstructor
@Controller
public class ReviewController {
    private final StoreService storeService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final TagService tagService;
    private final Review_tagService reviewTagService;
    private final PhotoService photoService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/{id}")
    private String reviewCreate(Model model, ReviewForm reviewForm) {
        List<Tag> allTags = this.tagService.getAllTags();
        model.addAttribute("allTags", allTags);
        return "review_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    private String reviewCreate(Model model, ReviewForm reviewForm, @PathVariable("id") Integer id,
                                BindingResult bindingResult, Principal principal, List<MultipartFile> fileList) throws Exception {
        Store store = this.storeService.getStore(id);
        User user = this.userService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("store", store);
            return "store_list";
        }
        Review review = this.reviewService.create(user, store, reviewForm.getContent(), reviewForm.getRating());

        if (review != null) {
            reviewTagService.saveTagsForReview(review, reviewForm.getTagList());
        }
        if (review != null) {
            photoService.saveImgsForReview(review, fileList);
        }

        return String.format("redirect:/store/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String reviewModify(Model model, ReviewForm reviewForm, @PathVariable("id") Integer id, Principal principal) {
        Review review = this.reviewService.getReview(id);
        List<Tag> allTags = this.tagService.getAllTags();
        model.addAttribute("allTags", allTags);
        if (!review.getAuthor().getUserId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        reviewForm.setContent(review.getContent());
        reviewForm.setRating(review.getRating());
        List<Integer> tagIds = this.reviewService.getTagIdsForReview(id);
        reviewForm.setTagList(tagIds);
        return "review_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String reviewModify(@Valid ReviewForm reviewForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) throws Exception {
        if (bindingResult.hasErrors()) {
            return "review_form";
        }
        Review review = this.reviewService.getReview(id);
        if (!review.getAuthor().getUserId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        List<Integer> newTagIds = reviewForm.getTagList();
        this.reviewTagService.deleteTagsForReview(review);
        List<MultipartFile> newPhotos = reviewForm.getFileList();
        this.photoService.deletePhotosByReview(review);

        for (Integer tagId : newTagIds) {
            Tag tag = tagService.getTagById(tagId);
            if (tag != null) {
                reviewService.addTagToReview(review, tag);
            }
        }
        if (newPhotos != null && !newPhotos.isEmpty()) {
            this.photoService.saveImgsForReview(review, newPhotos);
        }
        this.reviewService.modify(review, reviewForm.getContent(), reviewForm.getRating());

        return String.format("redirect:/store/detail/%s", review.getStore().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String reviewDelete(Principal principal, @PathVariable("id") Integer id) {
        Review review = this.reviewService.getReview(id);
        if (!review.getAuthor().getUserId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        this.photoService.deletePhotosByReview(review);
        this.reviewTagService.deleteTagsByReviewId(id);
        this.reviewService.delete(review);

        return String.format("redirect:/store/detail/%s", review.getStore().getId());
    }



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
        Review review  = this.reviewService.getReview(id);
        User user = this.userService.getUser(principal.getName());
        this.reviewService.vote(review, user);
        return String.format("redirect:/store/detail/%s", review.getStore().getId());
    }
}
