package com.pokemon.blog.repository;

import com.pokemon.blog.entity.Comment;
import com.pokemon.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Tìm comments của post (phân trang).
     *
     * @param post post object
     * @param pageable pagination info
     * @return Page chứa comments + metadata
     */
    Page<Comment> findByPost(Post post, Pageable pageable);

    /**
     * Tìm comments của post (không phân trang).
     * Giữ lại cho backward compatibility.
     *
     * @param post post object
     * @return List comments
     */
    List<Comment> findByPost(Post post);
}