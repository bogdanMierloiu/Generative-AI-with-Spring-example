package com.bogdanmierloiu.SpringAI.service;

import com.bogdanmierloiu.SpringAI.dto.Answer;
import com.bogdanmierloiu.SpringAI.dto.GetCapitalRequest;
import com.bogdanmierloiu.SpringAI.dto.GetCapitalResponse;
import com.bogdanmierloiu.SpringAI.dto.Question;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public OpenAIServiceImpl(ChatClient chatClient, ObjectMapper objectMapper) {
        this.chatClient = chatClient;
        this.objectMapper = objectMapper;
    }

    @Value("classpath:prompts/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:prompts/get-capital-with-info-prompt.st")
    private Resource getCapitalWithInfoPrompt;


    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.call(prompt);

        return response.getResult().getOutput().getContent();
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }


    @Override
    public GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest) {
        // create a json schema for the response
        BeanOutputParser<GetCapitalResponse> parser = new BeanOutputParser<>(GetCapitalResponse.class);
        String format = parser.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of(
                "stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format));

        ChatResponse response = chatClient.call(prompt);

        return parser.parse(response.getResult().getOutput().getContent());
    }
}
