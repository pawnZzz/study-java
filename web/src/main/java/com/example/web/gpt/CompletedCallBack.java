package com.example.web.gpt;

/**
 * @author niuxiangqian
 * @version 1.0
 * @date 2023/3/23 15:14
 **/
public interface CompletedCallBack {

    /**
     * 完成回掉
     *
     */
    void completed();

    void fail();

}
