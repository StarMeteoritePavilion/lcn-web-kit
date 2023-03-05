package io.github.lcn29.web.kit.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * <pre>
 * 序列化器: 将 LocalDate 序列化为 Long
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 17:58
 */
public class LocalDate2LongSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        if (value != null && gen != null) {
            gen.writeNumber(localDate2Long(value));
        }
    }

    /**
     * localDate  转 long
     *
     * @param localDate localDate  时间
     * @return 转换后的 long 类型时间戳
     */
    private Long localDate2Long(LocalDate localDate) {
        return localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
