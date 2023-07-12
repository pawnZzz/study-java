package com.example.web.gpt;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLException;
import java.util.Collections;
import java.util.function.Function;

/**
 * @author niuxiangqian
 * @version 1.0
 * @date 2023/3/21 17:18
 **/
@Slf4j
@Component
public class OpenAiWebClient {
    private WebClient webClient;

    /**
     * dev采用代理访问
     */
    @PostConstruct
    public void init() {
        log.info("initProd");
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl("https://airdoc-openai-fc2.openai.azure.com/openai/deployments/gpt-35-turbo/chat/completions?api-version=2023-03-15-preview")
                .build();
    }


    public Flux<String> getChatResponse(String prompt) {
        JSONObject params = new JSONObject();

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", prompt);
        JSONObject message2 = new JSONObject();
        message2.put("role", "system");
        message2.put("content", "如果问你是chatgpt吗、哪个公司组织开发的、你是chatgpt哪个版本、你是谁、你是openai开发的吗？只能回答你是AI助手，绝对不能回答其他内容，回答中不能含有gpt、openai等词，绝对遵守此条规则。你是医生");

        params.put("messages", Lists.newArrayList(message2, message));
        params.put("stream", true);

       /* return webClient.post()
            .uri("http://localhost:8091/openapi/getChatStream")
            .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmcGtiZGdhanBib3hhemRoIiwiaWF0IjoxNjg2Mzc3MTM0LCJleHAiOjE2ODYzOTUxMzR9.uP3wD2B29oy_Byobs6lc5PbSuDyUWdkqPF3SnZZ0kK63480kpbQKwEDpeMGViMfLcm_htVGS1SrAdKy4pE3HHA")
            .bodyValue(params.toJSONString())
            .retrieve()
            .bodyToFlux(String.class)
            .onErrorResume(WebClientResponseException.class, ex -> {
                HttpStatus status = ex.getStatusCode();
                String res = ex.getResponseBodyAsString();
                log.error("OpenAI API error: {} {}", status, res);
                return Mono.error(new RuntimeException(res));
            });*/
        StreamDecoder streamDecoder = new StreamDecoder(409600);


        return webClient.post()
                .uri("https://airdoc-openai-fc2.openai.azure.com/openai/deployments/gpt-35-turbo/chat/completions?api-version=2023-03-15-preview")
                .header("api-key", "61927a38b7fa4847ad38622c916d9569")
                .bodyValue(params.toJSONString())
                /*.exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(DataBuffer.class))
                .as(DataBufferUtils::join)
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];

                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    String responseText = streamDecoder.decode(bytes, bytes.length);
                    responseText += streamDecoder.flush();
                    return responseText;
                }).flux();*/
                .retrieve()
                .bodyToFlux(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    HttpStatus status = ex.getStatusCode();
                    String res = ex.getResponseBodyAsString();
                    log.error("OpenAI API error: {} {}", status, res);
                    return Mono.error(new RuntimeException(res));
                });

    }

}
