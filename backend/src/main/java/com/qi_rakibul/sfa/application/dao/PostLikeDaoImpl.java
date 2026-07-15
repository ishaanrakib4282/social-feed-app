package com.qi_rakibul.sfa.application.dao;

import com.qi_rakibul.sfa.application.domain.PostEntity;
import com.qi_rakibul.sfa.application.domain.PostLikeEntity;
import com.qi_rakibul.sfa.application.domain.UserEntity;
import com.qi_rakibul.sfa.application.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostLikeDaoImpl implements PostLikeDao {

    private final PostLikeRepository repository;

    @Override
    public PostLikeEntity save(PostLikeEntity postLike) {
        return repository.save(postLike);
    }

    @Override
    public long countByPost(PostEntity post) {
        return repository.countByPost(post);
    }

    @Override
    public boolean existsByPostAndUser(PostEntity post, UserEntity user) {
        return repository.existsByPostAndUser(post, user);
    }

    @Override
    public void deleteByPostAndUser(PostEntity post, UserEntity user) {
        repository.deleteByPostAndUser(post, user);
    }

    @Override
    public Page<PostLikeEntity> findByPost(PostEntity post, int page, int size) {
        return repository.findByPost(post, PageRequest.of(page, size));
    }
}