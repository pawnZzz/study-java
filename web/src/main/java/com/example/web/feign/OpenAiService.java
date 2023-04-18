package com.example.web.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zyc
 */
@Service
public class OpenAiService {

    @Autowired
    private OpenAiApi openAiApi;

    public OpenAiService(OpenAiApi openAiApi) {
        this.openAiApi = openAiApi;
    }

    public String getChatCompletions(String openaiApiKey, String requestBody) {

        return openAiApi.getChatCompletions(openaiApiKey, requestBody);
    }
}