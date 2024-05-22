package com.techgusta.springsecuritylogin.repositories;

import com.techgusta.springsecuritylogin.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
