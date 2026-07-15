package com.qi_rakibul.sfa.application.service;

import com.qi_rakibul.sfa.api.payload.request.CreateCommentRequest;
import com.qi_rakibul.sfa.api.payload.request.CreatePostRequest;
import com.qi_rakibul.sfa.api.payload.response.*;
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

    CommentResponse createComment(UUID postId, CreateCommentRequest request);

    Page<CommentResponse> getComments(
            UUID postId,
            int page,
            int size
    );
}
