package com.qi_rakibul.sfa.api.payload.request;

import com.qi_rakibul.sfa.application.enums.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePostRequest(

        @NotBlank
        String content,

        @NotNull
        Visibility visibility
) {
}