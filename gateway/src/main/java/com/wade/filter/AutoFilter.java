package com.wade.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.wade.common.CookieUtils;
import com.wade.config.FilterAllowProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@EnableConfigurationProperties(FilterAllowProperties.class)
public class AutoFilter extends ZuulFilter{

    @Value("${fly.jwt.cookieName}")
    private String cookieName;

    @Autowired
    private FilterAllowProperties allowProperties;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; //过滤器类型是前置过滤
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1; //过滤器顺序
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //获取上下文
        String url = request.getRequestURI();
        //获取请求路径
        //判断是否需要拦截
        return !isAllowPath(url);
    }

    private boolean isAllowPath(String url) {
        List<String> paths = allowProperties.getAllowPaths();
        for (String item : paths) {
            if (url.startsWith(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        // 获取上下文
        String cookie = CookieUtils.getCookieValue(request, cookieName);
        // 获取cookie
        if (StringUtils.isBlank(cookie)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(403);
        }
        // 解析token
        // 校验权限
        return null;
    }
}
