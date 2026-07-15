package com.qi_rakibul.sfa.application.dao;

import com.qi_rakibul.sfa.application.domain.CommentEntity;
import com.qi_rakibul.sfa.application.domain.PostEntity;
import com.qi_rakibul.sfa.application.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {

    private final CommentRepository repository;


    @Override
    public CommentEntity save(CommentEntity comment) {
        return repository.save(comment);
    }

    @Override
    public Optional<CommentEntity> findById(UUID commentId) {
        return repository.findById(commentId);
    }

    @Override
    public Page<CommentEntity> findByPost(PostEntity post, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.ASC, "createdAt"));
        return repository.findByPost(post, pageable);
    }
}
