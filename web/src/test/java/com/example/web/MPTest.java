package com.example.web;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.example.web.entity.WxChatRecords;
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
public class MPTest {
    @Autowired
    private WxChatRecordsMapper recordsMapper;


    @Test
    public void query(){
        QueryWrapper<WxChatRecords> queryWrapper = new QueryWrapper<>();
        QueryWrapper<WxChatRecords> eq = queryWrapper.select("open_id", "context")
                .eq("open_id", "otLtL5vnCPNmqyCCAkbpnklr6z8Q");
        List<WxChatRecords> wxChatRecords = recordsMapper.selectList(eq);

        wxChatRecords.forEach(System.out::println);
    }


}
