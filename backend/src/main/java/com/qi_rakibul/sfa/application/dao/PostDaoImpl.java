package com.qi_rakibul.sfa.application.dao;

import com.qi_rakibul.sfa.application.domain.PostEntity;
import com.qi_rakibul.sfa.application.enums.Visibility;
import com.qi_rakibul.sfa.application.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostDaoImpl implements PostDao {

    private final PostRepository repository;

    @Override
    public PostEntity save(PostEntity post) {
        return repository.save(post);
    }

    @Override
    public Page<PostEntity> findFeedPosts(UUID currentUserId, Visibility visibility, int page, int size) {
        Pageable pageable = PageRequest.of(
                page, size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return repository.findFeedPosts(
                currentUserId,
                Visibility.PUBLIC,
                pageable
        );
    }
}