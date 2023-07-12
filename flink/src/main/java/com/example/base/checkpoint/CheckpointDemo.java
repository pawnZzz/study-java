package com.example.base.checkpoint;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.IOException;

public class CheckpointDemo {
    public static void main(String[] args) throws IOException {
        Configuration configuration = new Configuration();
        configuration.setString("rest.port", "8081");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(configuration);

        // cp间隔
        env.enableCheckpointing(1000L);
        // 设置状态后端
//        env.setStateBackend(new FsStateBackend(""));
        env.setStateBackend(new RocksDBStateBackend(""));

        // 最大失败次数
        env.getCheckpointConfig().setTolerableCheckpointFailureNumber(3);
        // 超时时间
        env.getCheckpointConfig().setCheckpointTimeout(5 * 60 * 1000);
        // checkpoint模式，
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        // 防止触发过于频繁的checkpoint，消耗资源，两个cp间隔时间
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(600);
        // 设置checkpoint最大并行度 checkpoint job， 设置了上面间隔时间即为串行 此项默认为1，
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        // flink任务取消后，是否删除checkpoint，如果调试时不想被取消，则保留
        env.getCheckpointConfig().setExternalizedCheckpointCleanup(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);






    }
}
