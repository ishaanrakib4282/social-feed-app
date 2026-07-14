package com.qi_rakibul.sfa.application.repository;

import com.qi_rakibul.sfa.application.domain.PostEntity;
import com.qi_rakibul.sfa.application.enums.Visibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {

    @Query("""
            SELECT p
            FROM PostEntity p
            WHERE
                p.visibility = :publicVisibility
                OR p.author.id = :currentUserId
            """)
    Page<PostEntity> findFeedPosts(
            @Param("currentUserId") UUID currentUserId,
            @Param("publicVisibility") Visibility publicVisibility,
            Pageable pageable
    );
}