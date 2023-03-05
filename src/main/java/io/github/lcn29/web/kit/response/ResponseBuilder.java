package io.github.lcn29.web.kit.response;

import io.github.lcn29.web.kit.code.WebStatusCode;
import io.github.lcn29.web.kit.code.StatusCodeInterface;

/**
 * <pre>
 *  Response 构建器
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 15:24
 */
public class ResponseBuilder {

    /**
     * 构建不带响应内容的成功 Response
     *
     * @return Response
     */
    public static Response<Void> success() {
        return success(null);
    }

    /**
     * 构建带响应内容的成功 Response
     *
     * @param content 返回内容
     * @param <T>     返回内容的数据类型
     * @return Response
     */
    public static <T> Response<T> success(T content) {
        return buildResponse(WebStatusCode.SUCCESS.getCode(), WebStatusCode.SUCCESS.getMessage(), content);
    }

    /**
     * 构建不带响应内容的失败 Response
     *
     * @param statusCodeInterface 状态码接口
     * @return Response
     */
    public static Response<Void> fail(StatusCodeInterface statusCodeInterface) {
        return fail(statusCodeInterface.getCode(), statusCodeInterface.getMessage(), null);
    }

    /**
     * 构建不带响应内容的失败 Response
     *
     * @param code    状态码
     * @param message 状态信息
     * @return Response
     */
    public static Response<Void> fail(int code, String message) {
        return fail(code, message, null);
    }

    /**
     * 构建带响应内容的失败 Response
     *
     * @param code    状态码
     * @param message 状态信息
     * @param content 返回内容
     * @param <T>     返回内容的数据类型
     * @return Response
     */
    public static <T> Response<T> fail(int code, String message, T content) {
        return buildResponse(code, message, content);
    }

    /**
     * 构建带响应内容的失败 Response
     *
     * @param statusCodeInterface 状态码接口
     * @param content             返回内容
     * @param <T>                 返回内容的数据类型
     * @return Response
     */
    public static <T> Response<T> fail(StatusCodeInterface statusCodeInterface, T content) {
        return buildResponse(statusCodeInterface.getCode(), statusCodeInterface.getMessage(), content);
    }

    /**
     * 构建 Response
     *
     * @param code    状态码
     * @param message 状态信息
     * @param content 返回内容
     * @param <T>     返回内容的数据类型
     * @return Response
     */
    private static <T> Response<T> buildResponse(int code, String message, T content) {
        return content == null ? new Response<T>(code, message) : new Response<T>(code, message, content);
    }

}
