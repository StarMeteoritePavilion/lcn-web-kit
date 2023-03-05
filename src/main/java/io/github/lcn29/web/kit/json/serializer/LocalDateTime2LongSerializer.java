package io.github.lcn29.web.kit.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * <pre>
 * 序列化器: 将 LocalDateTime 序列化为 Long
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 18:00
 */
public class LocalDateTime2LongSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null && gen != null) {
            gen.writeNumber(localDateTime2Long(value));
        }
    }

    /**
     * localDateTime 转 long
     *
     * @param localDateTime localDateTime 时间
     * @return 转换后的 long 类型时间戳
     */
    private Long localDateTime2Long(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
