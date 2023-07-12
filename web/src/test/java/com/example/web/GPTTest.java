package com.example.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.web.entity.WxChatRecords;
import com.example.web.gpt.UserChatService;
import com.example.web.mapper.WxChatRecordsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zyc
 */
@SpringBootTest(classes = WebApplication.class)
@RunWith(SpringRunner.class)
public class GPTTest {
   @Autowired
    private UserChatService chatService;

   @Test
    public void test(){
       chatService.send("讲个笑话");
   }

}
