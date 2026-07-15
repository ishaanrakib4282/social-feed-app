package com.qi_rakibul.sfa.application.service;

import com.qi_rakibul.sfa.api.payload.request.CreatePostRequest;
import com.qi_rakibul.sfa.api.payload.response.CreatePostResponse;
import com.qi_rakibul.sfa.api.payload.response.FeedPostResponse;
import com.qi_rakibul.sfa.api.payload.response.LikeResponse;
import com.qi_rakibul.sfa.api.payload.response.LikeUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface PostService {

    CreatePostResponse createPost(CreatePostRequest request);

    @Transactional(readOnly = true)
    Page<FeedPostResponse> getFeed(
            int page,
            int size
    );

    LikeResponse likePost(UUID postId) throws IllegalAccessException;

    LikeResponse unlikePost(UUID postId) throws IllegalAccessException;

    Page<LikeUserResponse> getPostLikes(
            UUID postId,
            int page,
            int size
    );
}
