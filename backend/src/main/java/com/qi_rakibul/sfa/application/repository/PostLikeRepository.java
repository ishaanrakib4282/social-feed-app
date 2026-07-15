package com.qi_rakibul.sfa.application.repository;

import com.qi_rakibul.sfa.application.domain.PostEntity;
import com.qi_rakibul.sfa.application.domain.PostLikeEntity;
import com.qi_rakibul.sfa.application.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, UUID> {

    Optional<PostLikeEntity> findByPostAndUser(
            PostEntity post,
            UserEntity user
    );

    boolean existsByPostAndUser(
            PostEntity post,
            UserEntity user
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                delete from PostLikeEntity pl
                where pl.post = :post
                  and pl.user = :user
            """)
    void deleteByPostAndUser(@Param("post") PostEntity post,
                             @Param("user") UserEntity user);

    long countByPost(PostEntity post);

    Page<PostLikeEntity> findByPost(
            PostEntity post,
            Pageable pageable
    );
}