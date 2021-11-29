package com.tfb.log.common.utils;

/**
 * @Author: lipan
 * @Date: 2021/9/26
 * @Description： 简单描述下：「 数据样本-工具 」
 */
public class DataUtil {

    // 异常规则
    public static final String warn_Rule_Result_YC = "{\n" +
            "    \"success\": \"true\",\n" +
            "    \"message\": \"请求成功\",\n" +
            "    \"result\": [\n" +
            "        {\n" +
            "            \"name\": \"001\",\n" +
            "            \"type\" : \"CONTAIN\",\n" +
            "            \"matchStr\": \"exception\",\n" +
            "            \"descrip\": \"程序异常\",\n" +
            "            \"enable\": \"true\",\n" +
            "            \"system\": \"12301\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"0012\",\n" +
            "            \"type\" : \"CONTAIN\",\n" +
            "            \"matchStr\": \"timeout\",\n" +
            "            \"descrip\": \"超时\",\n" +
            "            \"enable\": \"true\",\n" +
            "            \"system\": \"12301\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    // 预警规则
    public static final String warn_Rule_Result_YJ = "";

}
