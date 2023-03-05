package io.github.lcn29.web.kit.exception;

import io.github.lcn29.web.kit.code.StatusCodeInterface;

/**
 * <pre>
 * 业务异常
 * 全局捕获后, 会打印 warn 日志级别, 除了打印 message 信息, 还会打印堆栈信息
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 15:10
 */
public class WarnException extends RuntimeException {

    private final int code;

    private final String message;

    public WarnException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public WarnException(StatusCodeInterface statusCodeInterface) {
        super(statusCodeInterface.getMessage());
        this.code = statusCodeInterface.getCode();
        this.message = statusCodeInterface.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
