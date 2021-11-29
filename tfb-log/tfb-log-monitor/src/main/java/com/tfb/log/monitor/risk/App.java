package com.tfb.log.monitor.risk;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.tfb.log.common.enums.ERuleType;
import com.tfb.log.common.model.NormalEvent;
import com.tfb.log.common.model.WarnEvent;
import com.tfb.log.common.model.WarnRule;
import com.tfb.log.common.utils.*;
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

import java.util.*;


/**
 * @Author: lipan
 * @Date: 2021/9/14
 * @Description： 简单描述下：「 风控预警 」
 */
public class App {
    public static void main(String[] args) throws Exception {
        Logger log = LoggerFactory.getLogger(App.class);
        // 窗口异常最大条数值
        Integer sizeMax = 10;
        // 窗口大小（分钟）
        Integer windowTime_m = 2;
        // 水位线（秒）
        Integer waterMarkTime_s = 10;
        final ParameterTool parameterTool = ExecutionEnvUtil.createParameterTool(args);
        StreamExecutionEnvironment env = ExecutionEnvUtil.prepare(parameterTool);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        DataStreamSource<NormalEvent> data = KafkaConfigUtil.buildSource(env);
        // {msgNum:"12", message:"55431", source:"sdsad/adas/das/xxx.log", eventTime:2147483645, filed= {app="app",system="adsad"}
        data.print();
        // 无效事件过滤
        data.filter(e -> !Objects.isNull(e)
                && StringUtils.isNotBlank(e.getEventTime())
                && !Objects.isNull(e.getFiled()) && StringUtils.isNotBlank(e.getFiled().getSysNum()))
                // 单个log事件预警识别
                .map(new WarnMapFunction())
                // 过滤得到异常事件且EventTime有效
                .filter(e -> e.getWarn())
                // 设定延迟数据规则
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<NormalEvent>(Time.seconds(waterMarkTime_s)) {
                    @Override
                    public long extractTimestamp(NormalEvent normalEvent) {
                        String eventTime = normalEvent.getEventTime();
                        return DateUtil.format(eventTime, DateUtil.YYYY_MM_DD_HH_MM_SS_0);
                    }
                })
                //  按事件渠道分组聚合窗口
                .keyBy(e -> e.getFiled().getSysNum())
                .window(TumblingEventTimeWindows.of(Time.seconds(windowTime_m)))
                .process(new ProcessWindowFunction<NormalEvent, WarnEvent, String, TimeWindow>() {
                    @Override
                    public void process(String key, Context context, Iterable<NormalEvent> iterableIn, Collector<WarnEvent> collectorOut) throws Exception {
                        int size = CollectionUtils.size(iterableIn);
                        log.info(String.format("当前window Key : s% , size:s%", key, size));
                        if (size > sizeMax) {
                            WarnEvent warnEvent = new WarnEvent();
                            warnEvent.setNumber(UUID.randomUUID().toString());
                            warnEvent.setSysNum(key);
                            warnEvent.setWarnTime(System.nanoTime() + "");

                            //  服务器 | 统计时间段（分钟内））| 交易名称 | 告警类型 | 错误笔数 | 告警时间 | 告警标题 | 告警详情
                            //  s% | s% | s% | s% | s% | s% | s% | s%
                            NormalEvent next = iterableIn.iterator().next();
                            String ip = next.getFiled().getIp();
                            String warnContent = "预警内容 ||||";
                            warnEvent.setWarnContent(warnContent);
                            collectorOut.collect(warnEvent);
                        }
                    }
                })
                // 数据下沉
                .addSink(new PrintSinkFunction<>());
        env.execute("test");
    }
}
