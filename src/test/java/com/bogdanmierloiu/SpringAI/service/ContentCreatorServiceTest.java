package com.bogdanmierloiu.SpringAI.service;

import com.bogdanmierloiu.SpringAI.dto.ContentResponse;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.autoconfigure.openai.OpenAiChatProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;


@SpringBootTest
class ContentCreatorServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ContentCreatorServiceTest.class);

    @Autowired
    private ContentCreatorService contentCreatorService;

    @Autowired
    OpenAiChatProperties openAiChatProperties;

    @Value("classpath:articles/generative-ai.txt")
    private Resource generativeAiArticleResource;

    @Test
    void generateContent() {
        String promptRequest = "Spring Security username+password authentication from REACT APP";

        ContentResponse generatedContent = contentCreatorService.generateContent(promptRequest);
        log.info("Generated content: {}", generatedContent);
    }
}