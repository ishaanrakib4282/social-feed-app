package com.qi_rakibul.sfa.api.payload.response;

import java.time.Instant;
import java.util.UUID;

public record CommentResponse(
        UUID id,
        UUID authorId,
        String authorName,
        UUID parentCommentId,
        String content,
        Instant createdAt,
        long likeCount,
        boolean likedByCurrentUser
) {
}