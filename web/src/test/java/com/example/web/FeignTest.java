package com.example.web;

import com.example.web.feign.OpenAiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zyc
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={WebApplication.class})
public class FeignTest {

    @Autowired
    private OpenAiService openAiService;

    @Test
    public void testGetChatCompletions() {
        String requestBody = "{\"messages\":[{\"role\": \"system\", \"content\": \"You are an AI assistant that helps people find information.\"},{\"role\": \"user\", \"content\": \"黄斑裂孔是什么\"}]}";
        String apiKey = "e394e4bf48674c89bd8f37240002f86e";
        String chatCompletions = openAiService.getChatCompletions( apiKey, requestBody);
        System.out.println(chatCompletions);

    }
}
