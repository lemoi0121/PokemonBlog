package com.pokemon.blog.repository;

import com.pokemon.blog.entity.Comment;
import com.pokemon.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Lấy tất cả comment của một post
    List<Comment> findByPost(Post post);
}