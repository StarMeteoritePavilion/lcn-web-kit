package io.github.lcn29.web.kit.code;

/**
 * <pre>
 * 状态码格式接口
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 15:03
 */
public interface StatusCodeInterface {

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    int getCode();

    /**
     * 获取状态描述
     *
     * @return 状态描述
     */
    String getMessage();
}
