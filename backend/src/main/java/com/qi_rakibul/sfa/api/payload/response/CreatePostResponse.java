package com.qi_rakibul.sfa.api.payload.response;

import java.time.Instant;
import java.util.UUID;

public record CreatePostResponse(

        UUID id,

        UUID authorId,

        String authorName,

        String content,

        String imageUrl,

        String visibility,

        Instant createdAt
) {
}