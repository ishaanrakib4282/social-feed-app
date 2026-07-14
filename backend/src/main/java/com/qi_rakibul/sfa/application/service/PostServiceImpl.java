package com.qi_rakibul.sfa.application.service;

import com.qi_rakibul.sfa.api.payload.request.CreatePostRequest;
import com.qi_rakibul.sfa.api.payload.response.FeedPostResponse;
import com.qi_rakibul.sfa.api.payload.response.CreatePostResponse;
import com.qi_rakibul.sfa.application.dao.PostDao;
import com.qi_rakibul.sfa.application.dao.UserDao;
import com.qi_rakibul.sfa.application.domain.AuthenticatedUser;
import com.qi_rakibul.sfa.application.domain.PostEntity;
import com.qi_rakibul.sfa.application.domain.UserEntity;
import com.qi_rakibul.sfa.application.enums.Visibility;
import com.qi_rakibul.sfa.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserDao userDao;
    private final PostDao postDao;

    @Transactional
    @Override
    public CreatePostResponse createPost(CreatePostRequest request) {

        AuthenticatedUser authenticatedUser = SecurityUtils.currentUser();

        UserEntity author = userDao.findById(authenticatedUser.getId())
                .orElseThrow();

        PostEntity post = PostEntity.builder()
                .author(author)
                .content(request.content())
                .visibility(request.visibility())
                .build();

        postDao.save(post);

        return new CreatePostResponse(
                post.getId(),
                author.getId(),
                author.getFullName(),
                post.getContent(),
                post.getImageUrl(),
                post.getVisibility().name(),
                post.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Page<FeedPostResponse> getFeed(int page, int size) {

        UUID currentUserId = SecurityUtils.currentUser().getId();

        return postDao.findFeedPosts(
                        currentUserId,
                        Visibility.PUBLIC,
                        page,
                        size
                )
                .map(post ->
                        new FeedPostResponse(
                                post.getId(),
                                post.getAuthor().getId(),
                                post.getAuthor().getFullName(),
                                post.getContent(),
                                post.getImageUrl(),
                                post.getVisibility().name(),
                                post.getCreatedAt(),
                                0,
                                0,
                                false
                        )
                );
    }
}