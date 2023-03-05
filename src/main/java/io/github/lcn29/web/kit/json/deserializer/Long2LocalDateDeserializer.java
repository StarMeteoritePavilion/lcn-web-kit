package io.github.lcn29.web.kit.json.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * <pre>
 * 反序列化器: 将 long 转为 localDateTime
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 17:53
 */
public class Long2LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        if (jsonParser == null) {
            return null;
        }
        return long2LocalDate(jsonParser.getValueAsLong());
    }

    /**
     * long 转 localDate
     *
     * @param longTime long 时间
     * @return localDate
     */
    private LocalDate long2LocalDate(Long longTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(longTime), ZoneId.systemDefault()).toLocalDate();
    }
}
