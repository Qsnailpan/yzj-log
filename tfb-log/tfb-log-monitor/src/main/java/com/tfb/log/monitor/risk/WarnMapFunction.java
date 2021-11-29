package com.tfb.log.monitor.risk;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tfb.log.common.enums.ERuleType;
import com.tfb.log.common.model.NormalEvent;
import com.tfb.log.common.model.WarnYcRule;
import com.tfb.log.common.utils.DataUtil;
import com.tfb.log.common.utils.DateUtil;
import com.tfb.log.common.utils.GsonUtil;
import com.tfb.log.common.utils.HttpUtil;
import org.apache.flink.api.common.functions.MapFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: lipan
 * @Date: 2021/9/26
 * @Description： 简单描述下：「 异常事件识别并标记 - 以渠道为异常单位 」
 */
public class WarnMapFunction implements MapFunction<NormalEvent, NormalEvent> {
    Logger log = LoggerFactory.getLogger(WarnMapFunction.class);

    @Override
    public NormalEvent map(NormalEvent normalEvent) throws Exception {
        String eventTime = normalEvent.getEventTime();
        try {
            long eventTime_long = DateUtil.format(normalEvent.getEventTime(), DateUtil.YYYY_MM_DD_T_HH_MM_SS_000Z);
            log.info("eventTime: %s-%s", eventTime, eventTime_long);
        } catch (Exception e) {
            log.error("eventTime: %s- error", eventTime);
            return normalEvent;
        }

        // 1. 根据渠道号去拿异常规则列表
        String system = normalEvent.getFiled().getSysNum();
        // TODO 服务调用链接 http
        String url = "http://localhost:8081/WarnYcRuleResult/system/" + system;
        String WarnYcRuleResult = "";
        String msg = "异常规则获取s%！url:s% ,s%";
        try {
            WarnYcRuleResult = HttpUtil.doGet(url);
            String infoMsg = String.format(msg, "成功", url, WarnYcRuleResult);
            log.info(infoMsg);
            normalEvent.addInfo(infoMsg);
        } catch (Exception e) {
            String errorMsg = String.format(msg, "失败", url, e);
            log.error(errorMsg);
            normalEvent.addError(errorMsg);
            return normalEvent;
        }
        /**
         *  WarnYcRuleResult:
         *  {
         *      success: true
         *      message: 请求成功
         *      result: [
         *          {
         *                WarnYcRule..
         *          }
         *      ]
         *  }
         */
        // TODO 解析返回数据拿到规则 List
        List<WarnYcRule> WarnYcRuleResultList = Collections.emptyList();
        try {
            JsonParser jsonParser = GsonUtil.jsonParser();
            JsonElement parse = jsonParser.parse(WarnYcRuleResult);
            if (parse.isJsonObject()) {
                JsonObject asJsonObject = parse.getAsJsonObject();
                JsonArray result = asJsonObject.getAsJsonArray("result");
                WarnYcRuleResultList = GsonUtil.fromJson(result, new TypeToken<List<WarnYcRule>>() {
                }.getType());
            }
        } catch (Exception e) {
            String errorMsg = String.format("异常规则提取失败！: s%", e);
            log.error(errorMsg);
            normalEvent.addError(errorMsg);
            return normalEvent;
        }

        // 没有设定异常规则就放弃
        if (WarnYcRuleResultList.isEmpty()) {
            return normalEvent;
        }
        // 2. 遍历规则校验当前事件是否异常
        msg = "规则识别成功匹配： s%";
        for (WarnYcRule WarnYcRule : WarnYcRuleResultList) {
            String message = normalEvent.getMessage();
            // TODO 校验预警
            if (WarnYcRule.getType() == ERuleType.CONTAIN) {
                if (message.contains(WarnYcRule.getMatchStr())) {
//                        3. normalEvent 是否异常标识为true
                    normalEvent.setWarn(true);
//                        4. normalEvent 当前数据 添加对应命中的预警规则
                    normalEvent.setWarnRuleList(Arrays.asList(WarnYcRule));
                    String infoMsg = String.format(msg, WarnYcRule);
                    log.info(infoMsg);
                    normalEvent.addInfo(infoMsg);
                }
            }
        }
        return normalEvent;
    }

    public static void main(String[] args) {
        JsonElement parse = GsonUtil.jsonParser().parse(DataUtil.warn_Rule_Result_YC);
        if (parse.isJsonObject()) {
            JsonObject asJsonObject = parse.getAsJsonObject();
            JsonArray result = asJsonObject.getAsJsonArray("result");
            List<WarnYcRule> WarnYcRuleResultList = GsonUtil.fromJson(result, new TypeToken<List<WarnYcRule>>() {
            }.getType());

            System.out.println(WarnYcRuleResultList);
        }

    }
}
