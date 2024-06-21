package com.bogdanmierloiu.SpringAI.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenAIServiceImplTest {

    @Autowired
    OpenAIServiceImpl openAIService;

    @Test
    void getAnswer() {
        String answer = openAIService.getAnswer("What is the capital of Romania?");
        System.out.println("Got the answer: \n" + answer);
    }

}