package io.github.lcn29.web.kit.interceptor;

import io.github.lcn29.web.kit.constants.WebConstants;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * <pre>
 * TraceId 拦截器
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 15:45
 */
public class TraceIdInterceptor implements HandlerInterceptor {

    /**
     * 日志文件中, traceId 的标识
     */
    private final static String LOG_TRACK_ID_MARK = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 从请求头中尝试获取 traceId, 获取不到自动生成
        String trackId = request.getHeader(LOG_TRACK_ID_MARK);
        trackId = StringUtils.hasLength(trackId) ? trackId : getUuid();
        // 填充到日志上下文
        MDC.put(LOG_TRACK_ID_MARK, trackId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 从日志上下文中移除
        MDC.remove(LOG_TRACK_ID_MARK);
    }

    /**
     * 获取 UUID 字符串
     *
     * @return 替换掉 - 的 UUID 字符串
     */
    private String getUuid() {
        return UUID.randomUUID().toString().replace(WebConstants.CROSS_BAR, WebConstants.EMPTY_STR);
    }
}
