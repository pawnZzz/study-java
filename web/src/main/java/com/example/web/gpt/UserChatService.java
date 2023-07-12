package com.example.web.gpt;

import reactor.core.publisher.Flux;

/**
 * @author niuxiangqian
 * @version 1.0
 * @date 2023/3/23 14:47
 **/
public interface UserChatService extends CompletedCallBack {

    /**
     * 发送消息
     *
     */
    Flux<String> send( String content);



}
