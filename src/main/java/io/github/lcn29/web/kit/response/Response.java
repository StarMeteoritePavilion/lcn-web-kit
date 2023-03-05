package io.github.lcn29.web.kit.response;

import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * <pre>
 * 请求响应体
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 15:16
 */
public class Response<T> implements Serializable {

    /**
     * 响应的状态码
     */
    private final int statusCode;

    /**
     * 响应的描述信息
     */
    private final String message;

    /**
     * 响应的内容
     */
    @Nullable
    private T content;

    public Response(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public Response(int statusCode, String message, @Nullable T content) {
        this.statusCode = statusCode;
        this.message = message;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    @Nullable
    public T getContent() {
        return content;
    }
}
