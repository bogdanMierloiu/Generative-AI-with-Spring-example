package com.bogdanmierloiu.SpringAI.controller;

import com.bogdanmierloiu.SpringAI.dto.Answer;
import com.bogdanmierloiu.SpringAI.dto.GetCapitalRequest;
import com.bogdanmierloiu.SpringAI.dto.GetCapitalResponse;
import com.bogdanmierloiu.SpringAI.dto.Question;
import com.bogdanmierloiu.SpringAI.service.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

    @PostMapping("/capital")
    public GetCapitalResponse askAboutCapital(@RequestBody GetCapitalRequest stateOrCountry) {
        return openAIService.getCapital(stateOrCountry);
    }
}
