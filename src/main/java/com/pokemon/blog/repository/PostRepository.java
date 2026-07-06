package com.pokemon.blog.repository;

import com.pokemon.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Tìm posts theo title hoặc content chứa keyword (phân trang).
     *
     * @param title keyword tìm trong title
     * @param content keyword tìm trong content
     * @param pageable pagination info
     * @return Page chứa posts tìm thấy + metadata
     */
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title, String content, Pageable pageable);

    /**
     * Tìm posts theo title hoặc content chứa keyword (không phân trang).
     * Giữ lại cho backward compatibility.
     *
     * @param title keyword tìm trong title
     * @param content keyword tìm trong content
     * @return List posts tìm thấy
     */
    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title, String content);
}