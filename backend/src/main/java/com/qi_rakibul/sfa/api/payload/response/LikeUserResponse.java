package com.qi_rakibul.sfa.api.payload.response;

import java.util.UUID;

public record LikeUserResponse(
        UUID userId,
        String fullName
) {
}