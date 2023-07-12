package com.example.base.stream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class WordCount {
    public static void main(String[] args) throws Exception {
        // 开启本地web ui，或者使用localEnv
        Configuration configuration = new Configuration();
        configuration.setString("rest.port","8081");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(configuration);
        // AUTOMATIC: Let the system decide based on the boundedness of the sources
        // 最好在提交任务时设置：bin/flink run -Dexecution.runtime-mode=BATCH <jarFile>
//        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);

        DataStreamSource<String> source = env.socketTextStream("localhost", 9999);
        SingleOutputStreamOperator<Tuple2<String, Long>> flatMapDS =
                source.flatMap((FlatMapFunction<String, Tuple2<String, Long>>) (value, out) -> {
            String[] s = value.split(" ");
            for (String s1 : s) {
                out.collect(Tuple2.of(s1, 1L));
            }
        }).returns(Types.TUPLE(Types.STRING, Types.LONG));

        flatMapDS.keyBy(tp -> tp.f0)
                .sum(1)
                .print();



        // stream需要 execute阻塞
        env.execute();
    }
}
