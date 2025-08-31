package com.shashankc7.linkedin.posts_service.repository;

import com.shashankc7.linkedin.posts_service.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long>
{
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    @Transactional    // we need transactional annotation for delete functionlity or else we will get runtime exceptions
    void deleteByUserIdAndPostId(Long userId, Long postId);
}
