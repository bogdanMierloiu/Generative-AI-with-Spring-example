package com.bogdanmierloiu.SpringAI.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record ContentResponse(

        @JsonPropertyDescription("The content of the response")
        String content
) {
}
