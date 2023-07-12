package com.example.web.gpt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.Completions;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.FluxSink;

/**
 * @author niuxiangqian
 * @version 1.0
 * @date 2023/3/21 20:15
 **/
@Slf4j
public class OpenAISubscriber implements Subscriber<String>, Disposable {
    private final FluxSink<String> emitter;
    private Subscription subscription;
    private final CompletedCallBack completedCallBack;
    private final StringBuilder sb;

    public OpenAISubscriber(FluxSink<String> emitter,  CompletedCallBack completedCallBack) {
        this.emitter = emitter;
        this.completedCallBack = completedCallBack;
        this.sb = new StringBuilder();
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(String data) {
        log.info("返回数据：{}", data);
        if ("[DONE]".equals(data)) {
            log.info("返回数据结束了");
            subscription.request(1);
            emitter.next("");
            completedCallBack.completed();
            emitter.complete();
        } else {
            emitter.next(data);
            sb.append(data);
            subscription.request(1);
        }

    }

    @Override
    public void onError(Throwable t) {
        log.error("返回数据异常：{}", t.getMessage());
        emitter.error(t);
        completedCallBack.fail();
    }

    @Override
    public void onComplete() {
        log.info("返回数据完成");
//        log.info("完整结果：{}", sb.toString());
        emitter.complete();
    }

    @Override
    public void dispose() {
        log.warn("返回数据关闭");
        emitter.complete();
    }
}