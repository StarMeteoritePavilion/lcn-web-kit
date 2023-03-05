package io.github.lcn29.web.kit.json.convert;

import io.github.lcn29.web.kit.constants.WebConstants;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * <pre>
 * 字符串转 LocalDateTime 转化器
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 17:39
 */
public class String2LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    /**
     * 日期格式类型
     */
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(WebConstants.DATE_TIME_FORMAT);

    @Override
    public LocalDateTime convert(String source) {
        // 全都是数字, 尝试按照 long 进行处理
        if (isNumeric(source)) {
            return long2LocalDateTime(Long.parseLong(source));
        }
        // 不全都是数字, 尝试进行解析
        return string2LocalDateTime(source);
    }

    /**
     * 判断一个字符串是否为数字
     *
     * @param str 需要判断的字符串
     * @return true: 是数字, false: 不是
     */
    private Boolean isNumeric(String str) {

        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }

    /**
     * long 转 localDate
     *
     * @param longTime long 时间
     * @return localDate
     */
    private LocalDateTime long2LocalDateTime(long longTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(longTime), ZoneId.systemDefault());
    }

    /**
     * 字符串转 localDateTime
     *
     * @param stringDateTime 需要转化的字符串
     * @return localDateTime
     */
    private LocalDateTime string2LocalDateTime(String stringDateTime) {
        return LocalDateTime.parse(stringDateTime, DATE_TIME_FORMATTER);
    }
}
