package com.example.web.gpt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/**
 * @author niuxiangqian
 * @version 1.0
 * @date 2023/3/23 14:52
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserChatServiceImpl implements UserChatService {
    private final OpenAiWebClient openAiWebClient;

    @Override
    public Flux<String> send(String content) {
        log.info("prompt:{}", content);
        return Flux.create(emitter -> {
            OpenAISubscriber subscriber = new OpenAISubscriber(emitter, this);
            Flux<String> openAiResponse =
                openAiWebClient.getChatResponse(content);
            openAiResponse.subscribe(subscriber);
            emitter.onDispose(subscriber);
        });
    }


    @Override
    public void completed() {
          }

    @Override
    public void fail() {

    }
}
