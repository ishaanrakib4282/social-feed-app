package com.qi_rakibul.sfa.application.dao;

import com.qi_rakibul.sfa.application.domain.CommentEntity;
import com.qi_rakibul.sfa.application.domain.PostEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface CommentDao {

    CommentEntity save(CommentEntity comment);

    Optional<CommentEntity> findById(UUID commentId);

    Page<CommentEntity> findByPost(PostEntity post, int page, int size);
}
