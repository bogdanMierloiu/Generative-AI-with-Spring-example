package com.bogdanmierloiu.SpringAI.service;

import com.bogdanmierloiu.SpringAI.dto.Answer;
import com.bogdanmierloiu.SpringAI.dto.GetCapitalRequest;
import com.bogdanmierloiu.SpringAI.dto.GetCapitalResponse;
import com.bogdanmierloiu.SpringAI.dto.Question;

public interface OpenAIService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest);

}
