package com.tfb.log.monitor.risk;

import com.tfb.log.common.model.NormalEvent;
import com.tfb.log.common.model.WarnEvent;
import com.tfb.log.common.utils.DateUtil;
import com.tfb.log.common.utils.ExecutionEnvUtil;
import com.tfb.log.common.utils.KafkaConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.UUID;


/**
 * @Author: lipan
 * @Date: 2021/9/14
 * @Description： 简单描述下：「test 」
 */
@Slf4j
public class test {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> dataStreamSource = env.socketTextStream("localhost", 9988);

        dataStreamSource.map(new MapFunction<String, Object>() {
            @Override
            public Object map(String s) throws Exception {
                log.info("数据：%s", s);
                return s;
            }
        }).print();

        env.execute("test");

    }
}
