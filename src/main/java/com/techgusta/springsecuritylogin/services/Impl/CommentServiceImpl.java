package com.techgusta.springsecuritylogin.services.Impl;

import com.techgusta.springsecuritylogin.controller.dto.CreateCommentDto;
import com.techgusta.springsecuritylogin.controller.dto.UpdateCommentDto;
import com.techgusta.springsecuritylogin.entities.Comment;
import com.techgusta.springsecuritylogin.entities.User;
import com.techgusta.springsecuritylogin.repositories.CommentRepository;
import com.techgusta.springsecuritylogin.repositories.UserRepository;
import com.techgusta.springsecuritylogin.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Comment createComment(CreateCommentDto dto, UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setContent(dto.content());

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId, UUID userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to delete this comment");
        }

        commentRepository.deleteById(commentId);

    }

    @Override
    public Comment updateComment(Long commentId, UpdateCommentDto dto, UUID userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to update this comment");
        }

        comment.setContent(dto.content());

        return commentRepository.save(comment);
    }
}
