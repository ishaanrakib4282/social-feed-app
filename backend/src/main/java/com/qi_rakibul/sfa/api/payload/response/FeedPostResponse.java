package com.qi_rakibul.sfa.api.payload.response;

import java.time.Instant;
import java.util.UUID;

public record FeedPostResponse(

        UUID id,

        UUID authorId,

        String authorName,

        String content,

        String imageUrl,

        String visibility,

        Instant createdAt,

        long likeCount,

        long commentCount,

        boolean likedByCurrentUser
) {
}