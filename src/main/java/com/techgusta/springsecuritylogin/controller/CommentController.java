package com.techgusta.springsecuritylogin.controller;

import com.techgusta.springsecuritylogin.controller.dto.CreateCommentDto;
import com.techgusta.springsecuritylogin.controller.dto.FeedDto;
import com.techgusta.springsecuritylogin.controller.dto.FeedItemDto;
import com.techgusta.springsecuritylogin.controller.dto.UpdateCommentDto;
import com.techgusta.springsecuritylogin.entities.Comment;
import com.techgusta.springsecuritylogin.entities.Role;
import com.techgusta.springsecuritylogin.repositories.CommentRepository;
import com.techgusta.springsecuritylogin.repositories.UserRepository;
import com.techgusta.springsecuritylogin.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Comment Management System", description = "APIs for Managing Comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;

    @Operation(summary = "View a list of available comments", description = "Provides a list of all comments with pagination support")
    @GetMapping("/feed")
    public ResponseEntity<FeedDto> feed(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        var comments = commentRepository.findAll(
                        PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
                .map(comment ->
                        new FeedItemDto(
                                comment.getCommentId(),
                                comment.getContent(),
                                comment.getUser().getUsername())
                );

        return ResponseEntity.ok(new FeedDto(
                comments.getContent(), page, pageSize, comments.getTotalPages(), comments.getTotalElements()));
    }

    @Operation(summary = "Create a new comment", description = "Allows the user to create a new comment")
    @PostMapping("/comments")
    public ResponseEntity<Void> createComment(@RequestBody CreateCommentDto dto,
                                            JwtAuthenticationToken token) {
        commentService.createComment(dto, UUID.fromString(token.getName()));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update a comment", description = "Allows the user to update a comment")
    @PutMapping("/comments/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable("id") Long commentId,
                                              @RequestBody UpdateCommentDto dto,
                                              JwtAuthenticationToken token) {
        commentService.updateComment(commentId, dto, UUID.fromString(token.getName()));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a comment", description = "Allows the user to delete a comment")
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long commentId,
                                            JwtAuthenticationToken token) {
        commentService.deleteComment(commentId, UUID.fromString(token.getName()));
        return ResponseEntity.ok().build();
    }
}
