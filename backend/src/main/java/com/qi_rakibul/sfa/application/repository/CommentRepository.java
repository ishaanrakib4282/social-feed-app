package com.qi_rakibul.sfa.application.repository;

import com.qi_rakibul.sfa.application.domain.CommentEntity;
import com.qi_rakibul.sfa.application.domain.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {

    Page<CommentEntity> findByPost(
            PostEntity post,
            Pageable pageable
    );
}