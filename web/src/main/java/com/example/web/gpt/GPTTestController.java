package com.example.web.gpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author zyc
 */
@RestController
@RequestMapping("/openapi")
public class GPTTestController {

    @Autowired
    private UserChatService chatService;

    @PostMapping(value = "/getChatStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getChatStream(String prompt)  {
       return chatService.send(prompt);
    }
}
