package com.example.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.annotation.Resource;

@FeignClient(name = "openai", url = "${openai.api.base}")
public interface OpenAiApi {
    @PostMapping(value = "/openai/deployments/airdoc-gpt-35-turbo/chat/completions?api-version=2023-03-15-preview", consumes = "application/json")
    String getChatCompletions(@RequestHeader("api-key") String apiKey, @RequestBody String requestBody);
}
