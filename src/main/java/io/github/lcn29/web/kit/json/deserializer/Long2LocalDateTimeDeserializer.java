package io.github.lcn29.web.kit.json.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * <pre>
 * 反序列化器: 将 long 转为 localDateTime
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 17:56
 */
public class Long2LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        if (jsonParser == null) {
            return null;
        }
        return long2LocalDateTime(jsonParser.getValueAsLong());
    }

    /**
     * long 转 localDateTime
     *
     * @param longTime long 时间
     * @return localDateTime
     */
    private LocalDateTime long2LocalDateTime(Long longTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(longTime), ZoneId.systemDefault());
    }
}
