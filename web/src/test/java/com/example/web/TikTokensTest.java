package com.example.web;

import com.example.web.gpt.ChatCompletion;
import com.example.web.gpt.Completion;
import com.example.web.gpt.Message;
import com.example.web.gpt.TikTokensUtil;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author https:www.unfbx.com
 * @sine 2023-04-05
 */
@Slf4j
public class TikTokensTest {
    String text;
    List<Message> messages;

    @Before
    public void prepareData() {
        text = "关注微信公众号：程序员的黑洞。进入chatgpt-java交流群获取最新版本更新通知。";
        messages = new ArrayList<>(1);
//        messages.add(Message.builder().role(Message.Role.USER).content("关注微信公众号：程序员的黑洞。").build());
//        messages.add(Message.builder().role(Message.Role.USER).content("进入chatgpt-java交流群获取最新版本更新通知。").build());
        messages.add(Message.builder().role(Message.Role.ASSISTANT).content("有一个鸟儿又聪明又勇敢，它飞过了一个漆黑的山洞。在洞口，有两只蝙蝠正交叉地挂在洞壁上，两者之间隔着一块大石头。\\n\\n鸟儿：“两位大仙，请问这里是哪里？”\\n\\n一只蝙蝠：“这里是蝙蝠洞。”\\n\\n鸟儿：“哦，我还以为是鸟巢呢。”\\n\\n另一只蝙蝠：“你这个笨鸟，我们蝙蝠洞里怎么会有鸟巢？”\\n\\n鸟儿：“我还以为是鸟巢，因为中间有两个笨蝙蝠挂在鸟巢里，还缺一块石头呢。”").build());
    }

    /**
     * gpt-3.5和gpt4.0聊天模型接口计算推荐这种方法
     */
    @Test
    public void chatCompletionTokensTest() {
        ChatCompletion completion = ChatCompletion.builder().messages(messages).build();
        long tokens = completion.tokens();
        log.info("Message集合文本：【{}】", messages, tokens);
        log.info("总tokens数{}", tokens);
    }
    /**
     * Completion 接口计算推荐使用这种方法
     */
    @Test
    public void completionTokensTest() {
        Completion completion = Completion.builder().prompt(text).build();
        long tokens = completion.tokens();
        log.info("单句文本：【{}】", text);
        log.info("总tokens数{}", tokens);
    }
    /**
     * 通过模型模型名称计算
     */
    @Test
    public void byModelNameTest() {
        String modelName = ChatCompletion.Model.GPT_4.getName();
//        String modelName = ChatCompletion.Model.GPT_3_5_TURBO.getName();
        List<Integer> encode = TikTokensUtil.encode(modelName, text);
        log.info(encode.toString());
        long tokens = TikTokensUtil.tokens(modelName, text);
        log.info("单句文本：【{}】", text);
        log.info("总tokens数{}", tokens);
        log.info("--------------------------------------------------------------");
        tokens = TikTokensUtil.tokens(modelName, messages);
        log.info("Message集合文本：【{}】", messages, tokens);
        log.info("总tokens数{}", tokens);
    }


    /**
     * 通过Encoding计算
     */
    @Test
    public void byEncodingTest() {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.P50K_BASE);
        List<Integer> encode = TikTokensUtil.encode(enc, text);
        log.info(encode.toString());
        long tokens = TikTokensUtil.tokens(enc, text);
        log.info("单句文本：【{}】", text);
        log.info("总tokens数{}", tokens);
    }

    /**
     * 通过EncodingType计算
     */
    @Test
    public void byEncodingTypeTest() {
        List<Integer> encode = TikTokensUtil.encode(EncodingType.CL100K_BASE, text);
        log.info(encode.toString());
        long tokens = TikTokensUtil.tokens(EncodingType.CL100K_BASE, text);
        log.info("单句文本：【{}】", text);
        log.info("总tokens数{}", tokens);
    }

}
