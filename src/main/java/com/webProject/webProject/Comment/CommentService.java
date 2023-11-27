package com.webProject.webProject.Comment;

import com.webProject.webProject.DataNotFoundException;
import com.webProject.webProject.Review.Review;
import com.webProject.webProject.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment create(Review review, String content, User author) {
        Comment c = new Comment();
        c.setContent(content);
        c.setCreateDate(LocalDateTime.now());
        c.setReview(review);
        c.setAuthor(author);
        c = this.commentRepository.save(c);
        return c;
    }

    public Comment getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFoundException("코멘트를 찾을 수 없습니다.");
        }
    }

    public Comment modify(Comment cmt, String content) {
        cmt.setContent(content);
        cmt.setModifyDate(LocalDateTime.now());
        cmt = this.commentRepository.save(cmt);
        return cmt;
    }

    public void delete(Comment c) {
        this.commentRepository.delete(c);
    }
}