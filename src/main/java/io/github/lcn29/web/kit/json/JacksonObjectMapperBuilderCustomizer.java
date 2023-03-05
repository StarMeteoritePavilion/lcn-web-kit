package io.github.lcn29.web.kit.json;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.github.lcn29.web.kit.constants.WebConstants;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 自定义的 json 序列化和反序列化配置
 * 如果有特殊场景可以通过 @JsonDeserialize @JsonSerialize 进行重写
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 18:24
 */
public class JacksonObjectMapperBuilderCustomizer {

    /**
     * 临时存放自定义的序列化器
     */
    private final Map<String, CustomerJsonSerializer> customerJsonSerializerMap = new HashMap<>();

    /**
     * 临时存放自定义的反序列化器
     */
    private final Map<String, CustomerJsonDeserializer> customerJsonDeserializerMap = new HashMap<>();

    /**
     * 添加自定义的 Json 序列化器
     * 相同类型的只取最后一个
     *
     * @param classType      数据类型
     * @param jsonSerializer 序列化器
     * @return 当前的配置类
     */
    public <T> JacksonObjectMapperBuilderCustomizer addJsonSerializer(Class<T> classType, JsonSerializer<T> jsonSerializer) {
        customerJsonSerializerMap.put(classType.getName(), new CustomerJsonSerializer(classType, jsonSerializer));
        return this;
    }

    /**
     * 添加自定义的 Json 反序列化器
     * 相同类型的只取最后一个
     *
     * @param classType        数据类型
     * @param jsonDeserializer 序列化器
     * @return 当前的配置类
     */
    public <T> JacksonObjectMapperBuilderCustomizer addJsonSerializer(Class<T> classType, JsonDeserializer<T> jsonDeserializer) {
        customerJsonDeserializerMap.put(classType.getName(), new CustomerJsonDeserializer(classType, jsonDeserializer));
        return this;
    }

    /**
     * 获取自定义的序列器和反序列化器的构造器
     */
    public Jackson2ObjectMapperBuilderCustomizer generateJackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {

            // 自定义的序列化器
            customerJsonSerializerMap.values().forEach(item ->
                    jacksonObjectMapperBuilder.serializerByType(item.classType, item.jsonSerializer)
            );

            // 自定义的反序列化器
            customerJsonDeserializerMap.values().forEach(item ->
                    jacksonObjectMapperBuilder.deserializerByType(item.classType, item.jsonDeserializer)
            );

            // 注册成功后进行清除, 减少内存
            customerJsonSerializerMap.clear();
            customerJsonDeserializerMap.clear();

            // localDateTime 默认的序列化和反序列化配置
            localDateTimeHandler(jacksonObjectMapperBuilder);
            // localDate 默认的序列化和反序列化配置
            localDateHandler(jacksonObjectMapperBuilder);
            // 其他数据类型的序列化和反序列化配置
            otherDataTypeHandler(jacksonObjectMapperBuilder);
        };
    }

    /**
     * 指定 localDateTime 默认的序列化和反序列化
     *
     * @param jacksonObjectMapperBuilder Jackson ObjectMapper 构造器
     */
    private void localDateTimeHandler(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {

        // 指定 localDateTime 默认的序列化和发序列化格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(WebConstants.DATE_TIME_FORMAT);

        // 如果没有指定 LocalDateTime 的序列化器, 添加默认的
        if (!customerJsonSerializerMap.containsKey(LocalDateTime.class.getName())) {
            jacksonObjectMapperBuilder.serializerByType(
                    LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        }
        // 如果没有指定 LocalDateTime 的反序列化器, 添加默认的
        if (!customerJsonDeserializerMap.containsKey(LocalDateTime.class.getName())) {
            jacksonObjectMapperBuilder.deserializerByType(
                    LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        }
    }

    /**
     * 指定 localDateTime 默认的序列化和反序列化
     *
     * @param jacksonObjectMapperBuilder Jackson ObjectMapper 构造器
     */
    private void localDateHandler(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {

        // 指定 localDate 默认的序列化和反序列化格式
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(WebConstants.DATE_FORMAT);
        if (!customerJsonSerializerMap.containsKey(LocalDate.class.getName())) {
            jacksonObjectMapperBuilder.serializerByType(LocalDate.class, new LocalDateSerializer(dateFormatter));
        }
        if (!customerJsonDeserializerMap.containsKey(LocalDate.class.getName())) {
            jacksonObjectMapperBuilder.deserializerByType(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        }
    }

    /**
     * 其他数据类型的处理
     *
     * @param jacksonObjectMapperBuilder Jackson ObjectMapper 构造器
     */
    private void otherDataTypeHandler(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {

        // long/bigInteger 直接转到前端存在丢精度问题, 这里转为序列化为字符串处理
        if (!customerJsonSerializerMap.containsKey(BigInteger.class.getName())) {
            jacksonObjectMapperBuilder.serializerByType(BigInteger.class, ToStringSerializer.instance);
        }
        if (!customerJsonSerializerMap.containsKey(Long.class.getName())) {
            jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
        }
    }

    /**
     * JsonSerializer 存储对象
     *
     * @param <T>
     */
    private static class CustomerJsonSerializer<T> {

        /**
         * 序列化的类型
         */
        private final Class<T> classType;

        /**
         * 序列化器
         */
        private final JsonSerializer<T> jsonSerializer;

        public CustomerJsonSerializer(Class<T> type, JsonSerializer<T> jsonSerializer) {
            this.classType = type;
            this.jsonSerializer = jsonSerializer;
        }
    }

    /**
     * JsonDeserializer 存储对象
     *
     * @param <T>
     */
    private static class CustomerJsonDeserializer<T> {

        /**
         * 反序列化的类型
         */
        private final Class<T> classType;

        /**
         * 反序列化器
         */
        private final JsonDeserializer<T> jsonDeserializer;

        public CustomerJsonDeserializer(Class<T> type, JsonDeserializer<T> jsonDeserializer) {
            this.classType = type;
            this.jsonDeserializer = jsonDeserializer;
        }
    }

}
