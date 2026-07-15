package com.qi_rakibul.sfa.api.endpoint;

import com.qi_rakibul.sfa.api.payload.request.CreateCommentRequest;
import com.qi_rakibul.sfa.api.payload.request.CreatePostRequest;
import com.qi_rakibul.sfa.api.payload.response.*;
import com.qi_rakibul.sfa.application.service.PostService;
import com.qi_rakibul.sfa.util.ServiceEndpoints;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ServiceEndpoints.Controllers.POST_CONTROLLER)
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(ServiceEndpoints.POST_CONTROLLER.CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePostResponse createPost(
            @Valid @RequestBody CreatePostRequest request
    ) {
        return postService.createPost(request);
    }

    @GetMapping(ServiceEndpoints.POST_CONTROLLER.SEARCH_ALL_POST)
    public Page<FeedPostResponse> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return postService.getFeed(
                page,
                size
        );
    }

    @PostMapping(ServiceEndpoints.POST_CONTROLLER.LIKE)
    public LikeResponse likePost(
            @PathVariable(value = "id") UUID postId
    ) throws IllegalAccessException {
        return postService.likePost(postId);
    }

    @DeleteMapping(ServiceEndpoints.POST_CONTROLLER.UNLIKE)
    public LikeResponse unlikePost(
            @PathVariable(value = "id") UUID postId
    ) throws IllegalAccessException {
        return postService.unlikePost(postId);
    }

    @GetMapping(ServiceEndpoints.POST_CONTROLLER.LIKERS)
    public Page<LikeUserResponse> getLikes(
            @PathVariable(value = "id") UUID postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return postService.getPostLikes(postId, page, size);
    }

    @PostMapping(ServiceEndpoints.POST_CONTROLLER.COMMENT)
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse createComment(
            @PathVariable(value = "id") UUID postId,
            @Valid @RequestBody CreateCommentRequest request
    ) {
        return postService.createComment(postId, request);
    }

    @GetMapping(ServiceEndpoints.POST_CONTROLLER.COMMENTS)
    public Page<CommentResponse> getComments(
            @PathVariable(value = "id") UUID postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return postService.getComments(postId, page, size);
    }
}