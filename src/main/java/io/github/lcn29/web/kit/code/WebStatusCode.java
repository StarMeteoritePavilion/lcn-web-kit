package io.github.lcn29.web.kit.code;

/**
 * <pre>
 * web 基础的状态码
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 15:06
 */
public enum WebStatusCode implements StatusCodeInterface {

    // 基础的状态码列表
    SUCCESS(200, "success"),
    PARAM_ERROR(400, "param error"),
    HTTP_REQUEST_METHOD_ERROR(401, "http request method error"),
    HTTP_REQUEST_PATH_NOT_FOUND(404, "http request path not found"),
    INTERNAL_SERVER_ERROR(50000, "Internal Server Error or IllegalArgumentException");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态描述
     */
    private final String message;

    WebStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * 获取状态描述
     *
     * @return 状态描述
     */
    @Override
    public String getMessage() {
        return message;
    }
}
