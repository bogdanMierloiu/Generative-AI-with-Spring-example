package com.bogdanmierloiu.SpringAI.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String message,
        String jwt,
        boolean status
) {
}
