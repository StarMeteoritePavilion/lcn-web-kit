package io.github.lcn29.web.kit.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <pre>
 * 响应结果转 Response 处理器
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 15:39
 */
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    private final static Logger logger = LoggerFactory.getLogger(ResponseResultHandler.class);

    static {
        logger.info("ResponseResultHandler Init ...");
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        // springboot 内部会添加一个 StringHttpMessageConverter 的转换器
        // 如果返回结果为 string, StringHttpMessageConverter 会对 string 进行处理
        // 会导致 ResponseResultAdvice 异常, 移除这个转换器
        // 使用时, 需要将 StringHttpMessageConverter 从 HttpMessageConverter 中移除

        // Content-Type 不是 JSON 格式 或者返回结果已经是 Response, 直接返回结果
        if (!MediaType.APPLICATION_JSON.equals(selectedContentType) || body instanceof Response<?>) {
            return body;
        }
        return ResponseBuilder.success(body);
    }
}
