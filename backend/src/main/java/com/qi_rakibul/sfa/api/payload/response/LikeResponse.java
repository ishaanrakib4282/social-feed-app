package com.qi_rakibul.sfa.api.payload.response;

public record LikeResponse(
        long likeCount,
        boolean liked
) {
}