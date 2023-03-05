package io.github.lcn29.web.kit.interceptor;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * Http 请求拦截器
 * </pre>
 *
 * @author lcn29
 * @date 2023-03-05 17:10
 */
public class RequestLogInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(RequestLogInterceptor.class);

    /**
     * 请求属性中的开始时间的标识
     */
    private final static String START_TIME = "startTime";

    /**
     * 请求头中的用户代理的标识
     */
    private final static String USER_AGENT = "User-Agent";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);

        logger.info("{}:{}:{}:{}-{}\nReqParam:{}", request.getHeader(USER_AGENT), request.getRemoteHost(), request.getMethod(),
                request.getRequestURI(), startTime, JSONObject.toJSONString(request.getParameterMap()));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        Long startTime = (Long) request.getAttribute(START_TIME);
        logger.info("RespStatus:{}, dur:{}", response.getStatus(), System.currentTimeMillis() - startTime);
    }
}
