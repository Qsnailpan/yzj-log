package com.tfb.log.common.schemas;

import com.google.gson.Gson;
import com.tfb.log.common.model.NormalEvent;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.util.serialization.DeserializationSchema;
import org.apache.flink.streaming.util.serialization.SerializationSchema;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * User Schema ，支持序列化和反序列化
 * <p>
 */
public class NormalEventSchema implements DeserializationSchema<NormalEvent>, SerializationSchema<NormalEvent> {

    private static final Gson gson = new Gson();

    @Override
    public NormalEvent deserialize(byte[] bytes) throws IOException {
        return gson.fromJson(new String(bytes), NormalEvent.class);
    }

    @Override
    public boolean isEndOfStream(NormalEvent normalEvent) {
        return false;
    }

    @Override
    public byte[] serialize(NormalEvent normalEvent) {
        return gson.toJson(normalEvent).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public TypeInformation<NormalEvent> getProducedType() {
        return TypeInformation.of(NormalEvent.class);
    }
}
