package com.qi_rakibul.sfa.application.service;

import com.qi_rakibul.sfa.api.payload.request.CreatePostRequest;
import com.qi_rakibul.sfa.api.payload.response.FeedPostResponse;
import com.qi_rakibul.sfa.api.payload.response.CreatePostResponse;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface PostService {

    CreatePostResponse createPost(CreatePostRequest request);

    @Transactional(readOnly = true)
    Page<FeedPostResponse> getFeed(
            int page,
            int size
    );
}
