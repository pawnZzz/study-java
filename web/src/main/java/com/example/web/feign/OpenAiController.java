package com.example.web.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zyc
 */
@RestController
@RequestMapping("/openAi")
public class OpenAiController {
    @Autowired
    private OpenAiService openAiService;

    @GetMapping("/chat")
    public String getChatCompletions() {

        return openAiService.getChatCompletions("e394e4bf48674c89bd8f37240002f86e",
                "{\"messages\":[{\"role\": \"system\", \"content\": \"Assistant is an intelligent chatbot designed to help users answer ocular fundus diseases related questions. Instructions: Only answer questions related to ocular fundus diseases.\"}, {\"role\": \"user\", \"content\": \"中国房地产市场的发展\"}]}");
    }
}
