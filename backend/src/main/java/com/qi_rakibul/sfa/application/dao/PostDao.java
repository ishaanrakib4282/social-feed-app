package com.qi_rakibul.sfa.application.dao;

import com.qi_rakibul.sfa.application.domain.PostEntity;
import com.qi_rakibul.sfa.application.enums.Visibility;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface PostDao {

    PostEntity save(PostEntity post);

    Page<PostEntity> findFeedPosts(UUID currentUserId, Visibility visibility, int page, int size);

    Optional<PostEntity> findById(UUID postId);
}
