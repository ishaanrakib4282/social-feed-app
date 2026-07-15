package com.qi_rakibul.sfa.application.service;

import com.qi_rakibul.sfa.api.payload.request.CreateCommentRequest;
import com.qi_rakibul.sfa.api.payload.request.CreatePostRequest;
import com.qi_rakibul.sfa.api.payload.response.*;
import com.qi_rakibul.sfa.application.dao.CommentDao;
import com.qi_rakibul.sfa.application.dao.PostDao;
import com.qi_rakibul.sfa.application.dao.PostLikeDao;
import com.qi_rakibul.sfa.application.dao.UserDao;
import com.qi_rakibul.sfa.application.domain.*;
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
    private final CommentDao commentDao;

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

        UserEntity currentUser = userDao.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PostEntity post = postDao.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        checkPostVisibility(post, currentUser);

        boolean alreadyLiked = postLikeDao.existsByPostAndUser(post, currentUser);

        if (!alreadyLiked) {

            PostLikeEntity postLike = PostLikeEntity.builder()
                    .post(post)
                    .user(currentUser)
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

        UserEntity currentUser = userDao.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PostEntity post = postDao.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        checkPostVisibility(post, currentUser);

        postLikeDao.deleteByPostAndUser(post, currentUser);

        long likeCount = postLikeDao.countByPost(post);

        return new LikeResponse(likeCount, false);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<LikeUserResponse> getPostLikes(UUID postId, int page, int size) {

        UUID currentUserId = SecurityUtils.currentUser().getId();

        UserEntity currentUser = userDao.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PostEntity post = postDao.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

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

    @Transactional
    @Override
    public CommentResponse createComment(
            UUID postId,
            CreateCommentRequest request
    ) {

        UUID currentUserId = SecurityUtils.currentUser().getId();

        UserEntity currentUser = userDao.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PostEntity post = postDao.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        checkPostVisibility(post, currentUser);

        CommentEntity parentComment = null;

        if (request.parentCommentId() != null) {
            parentComment = commentDao.findById(request.parentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        }

        CommentEntity comment = CommentEntity.builder()
                .post(post)
                .author(currentUser)
                .parentComment(parentComment)
                .content(request.content())
                .build();

        commentDao.save(comment);

        return new CommentResponse(
                comment.getId(),
                currentUser.getId(),
                currentUser.getFullName(),
                parentComment == null
                        ? null
                        : parentComment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                0,
                false
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CommentResponse> getComments(UUID postId, int page, int size) {

        UUID currentUserId = SecurityUtils.currentUser().getId();

        UserEntity currentUser = userDao.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PostEntity post = postDao.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (Visibility.PRIVATE.equals(post.getVisibility())
                && !currentUser.getId().equals(post.getAuthor().getId())) {
            return Page.empty();
        }

        return commentDao
                .findByPost(post, page, size)
                .map(comment ->
                        new CommentResponse(
                                comment.getId(),
                                comment.getAuthor().getId(),
                                comment.getAuthor().getFullName(),
                                comment.getParentComment() == null
                                        ? null
                                        : comment.getParentComment().getId(),
                                comment.getContent(),
                                comment.getCreatedAt(),
                                0,
                                false
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