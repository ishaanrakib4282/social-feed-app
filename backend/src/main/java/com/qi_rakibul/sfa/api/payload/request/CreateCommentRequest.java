package com.qi_rakibul.sfa.api.payload.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateCommentRequest(
        @NotBlank
        String content,
        UUID parentCommentId
) {
}