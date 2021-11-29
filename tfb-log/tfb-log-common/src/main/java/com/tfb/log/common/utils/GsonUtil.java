package com.tfb.log.common.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * gson 数据转换工具包
 */
public class GsonUtil {
    private final static Gson gson = new Gson();

    private final static JsonParser jsonParser = new JsonParser();

    public static <T> T fromJson(String value, Class<T> type) {
        return gson.fromJson(value, type);
    }

    public static String toJson(Object value) {
        return gson.toJson(value);
    }

    public static byte[] toJSONBytes(Object value) {
        return gson.toJson(value).getBytes(Charset.forName("UTF-8"));
    }

    public static <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    public static JsonParser jsonParser() {
        return jsonParser;
    }
}
