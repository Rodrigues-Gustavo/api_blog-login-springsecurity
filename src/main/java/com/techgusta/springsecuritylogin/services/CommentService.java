package com.techgusta.springsecuritylogin.services;

import com.techgusta.springsecuritylogin.controller.dto.CreateCommentDto;
import com.techgusta.springsecuritylogin.controller.dto.UpdateCommentDto;
import com.techgusta.springsecuritylogin.entities.Comment;

import java.util.UUID;

public interface CommentService {
    Comment createComment(CreateCommentDto dto, UUID userId);
    void deleteComment(Long commentId, UUID userId);
    Comment updateComment(Long commentId, UpdateCommentDto dto, UUID userId);
}
