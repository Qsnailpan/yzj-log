//package com.tfb.log.common.watermarks;
//
//
//import com.sun.istack.internal.Nullable;
//import org.apache.flink.streaming.api.watermark.Watermark;
//
///**
// * blog：http://www.54tianzhisheng.cn/
// * 微信公众号：zhisheng
// */
//public class MetricWatermark implements AssignerWithPeriodicWatermarks<MetricEvent> {
//
//    private long currentTimestamp = Long.MIN_VALUE;
//
//    @Override
//    public long extractTimestamp(MetricEvent metricEvent, long previousElementTimestamp) {
//        if (metricEvent.getTimestamp() > currentTimestamp) {
//            this.currentTimestamp = metricEvent.getTimestamp();
//        }
//        return currentTimestamp;
//    }
//
//    @Nullable
//    @Override
//    public Watermark getCurrentWatermark() {
//        long maxTimeLag = 5000;
//        return new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - maxTimeLag);
//    }
//}
