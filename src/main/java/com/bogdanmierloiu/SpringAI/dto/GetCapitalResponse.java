package com.bogdanmierloiu.SpringAI.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalResponse(

        @JsonPropertyDescription("The capital of the state or country")
        String answer,

        @JsonPropertyDescription("The population of the state or country")
        String population
) {
}
