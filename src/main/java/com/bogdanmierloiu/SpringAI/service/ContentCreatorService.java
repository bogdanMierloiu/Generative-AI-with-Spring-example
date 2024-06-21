package com.bogdanmierloiu.SpringAI.service;

import com.bogdanmierloiu.SpringAI.dto.ContentResponse;
import com.bogdanmierloiu.SpringAI.entity.Agent;
import com.bogdanmierloiu.SpringAI.prompts.DefaultPrompts;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.autoconfigure.openai.OpenAiChatProperties;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContentCreatorService {

    private final ChatClient chatClient;

    private final OpenAiChatProperties openAiChatProperties;

    public ContentResponse generateContent(String promptRequest) {
        BeanOutputParser<ContentResponse> parser = new BeanOutputParser<>(ContentResponse.class);
        String format = parser.getFormat();

        OpenAiChatOptions openAiChatOptions = new OpenAiChatOptions.Builder(openAiChatProperties.getOptions())
//                .withTemperature(0.7f)
                .withModel("gpt-4o")
                .build();

        Agent agent = AgentService.getDefaultAgent();
//        Agent agent = AgentService.getGigiAgent();
        String contentCreatorPrompt = DefaultPrompts.getContentCreatorPrompt(agent, promptRequest);
        PromptTemplate promptTemplate = new PromptTemplate(contentCreatorPrompt);

        Prompt prompt = promptTemplate.create(Map.of("format", format));

        Prompt promptToSend = new Prompt(prompt.toString(), openAiChatOptions);

        ChatResponse response = chatClient.call(promptToSend);


        return parser.parse(response.getResult().getOutput().getContent());
    }

}
