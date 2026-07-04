package com.pokemon.blog.repository;

import com.pokemon.blog.entity.Post;
import com.pokemon.blog.entity.PostLike;
import com.pokemon.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    // Tìm xem user đã like post này chưa
    Optional<PostLike> findByPostAndUser(Post post, User user);

    // Đếm số like của post
    Long countByPost(Post post);
}