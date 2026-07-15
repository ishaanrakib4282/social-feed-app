package com.qi_rakibul.sfa.application.dao;

import com.qi_rakibul.sfa.application.domain.PostEntity;
import com.qi_rakibul.sfa.application.domain.PostLikeEntity;
import com.qi_rakibul.sfa.application.domain.UserEntity;
import org.springframework.data.domain.Page;

public interface PostLikeDao {

    PostLikeEntity save(PostLikeEntity postLike);

    long countByPost(PostEntity post);

    boolean existsByPostAndUser(PostEntity post, UserEntity user);

    void deleteByPostAndUser(PostEntity post, UserEntity user);

    Page<PostLikeEntity> findByPost(PostEntity post, int page, int size);
}
