package com.qi_rakibul.sfa.application.service;

import com.qi_rakibul.sfa.api.payload.request.CreatePostRequest;
import com.qi_rakibul.sfa.api.payload.response.CreatePostResponse;
import com.qi_rakibul.sfa.api.payload.response.FeedPostResponse;
import com.qi_rakibul.sfa.api.payload.response.LikeResponse;
import com.qi_rakibul.sfa.api.payload.response.LikeUserResponse;
import com.qi_rakibul.sfa.application.dao.PostDao;
import com.qi_rakibul.sfa.application.dao.PostLikeDao;
import com.qi_rakibul.sfa.application.dao.UserDao;
import com.qi_rakibul.sfa.application.domain.AuthenticatedUser;
import com.qi_rakibul.sfa.application.domain.PostEntity;
import com.qi_rakibul.sfa.application.domain.PostLikeEntity;
import com.qi_rakibul.sfa.application.domain.UserEntity;
import com.qi_rakibul.sfa.application.enums.Visibility;
import com.qi_rakibul.sfa.error.exception.AccessDeniedException;
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
    private final PostLikeDao postLikeDao;

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

    @Transactional
    @Override
    public LikeResponse likePost(UUID postId) {

        UUID currentUserId = SecurityUtils.currentUser().getId();

        PostEntity post = postDao.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        UserEntity user = userDao.findById(currentUserId)
                .orElseThrow();

        checkPostVisibility(post, user);

        boolean alreadyLiked = postLikeDao.existsByPostAndUser(post, user);

        if (!alreadyLiked) {

            PostLikeEntity postLike = PostLikeEntity.builder()
                    .post(post)
                    .user(user)
                    .build();

            postLikeDao.save(postLike);
        }

        long likeCount = postLikeDao.countByPost(post);

        return new LikeResponse(likeCount, true);
    }

    @Transactional
    @Override
    public LikeResponse unlikePost(UUID postId) {

        UUID currentUserId = SecurityUtils.currentUser().getId();

        PostEntity post = postDao.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        UserEntity user = userDao.findById(currentUserId)
                .orElseThrow();

        checkPostVisibility(post, user);

        postLikeDao.deleteByPostAndUser(post, user);

        long likeCount = postLikeDao.countByPost(post);

        return new LikeResponse(likeCount, false);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<LikeUserResponse> getPostLikes(UUID postId, int page, int size) {

        UUID currentUserId = SecurityUtils.currentUser().getId();

        UserEntity currentUser = userDao.findById(currentUserId)
                .orElseThrow();

        PostEntity post = postDao.findById(postId)
                .orElseThrow();

        if (Visibility.PRIVATE.equals(post.getVisibility())
                && !currentUser.getId().equals(post.getAuthor().getId())) {
            return Page.empty();
        }

        return postLikeDao.findByPost(post, page, size)
                .map(postLike ->
                        new LikeUserResponse(
                                postLike.getUser().getId(),
                                postLike.getUser().getFullName()
                        )
                );
    }

    private static void checkPostVisibility(PostEntity post, UserEntity user) {
        if (Visibility.PRIVATE.equals(post.getVisibility())
                && !user.getId().equals(post.getAuthor().getId())) {
            throw new AccessDeniedException("Post not visible to current user");
        }
    }
}