package com.qi_rakibul.sfa.api.endpoint;

import com.qi_rakibul.sfa.api.payload.request.CreatePostRequest;
import com.qi_rakibul.sfa.api.payload.response.FeedPostResponse;
import com.qi_rakibul.sfa.api.payload.response.CreatePostResponse;
import com.qi_rakibul.sfa.application.service.PostService;
import com.qi_rakibul.sfa.util.ServiceEndpoints;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ServiceEndpoints.Controllers.POST_CONTROLLER)
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePostResponse createPost(
            @Valid @RequestBody CreatePostRequest request
    ) {

        return postService.createPost(request);
    }

    @GetMapping
    public Page<FeedPostResponse> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        return postService.getFeed(
                page,
                size
        );
    }
}