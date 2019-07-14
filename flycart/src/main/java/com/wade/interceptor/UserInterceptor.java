package com.wade.interceptor;

import com.wade.common.CookieUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {

    private String cookieName;

    //使用线程上下文 将用户信息放入到里面
    private static final ThreadLocal<String> userInfo = new ThreadLocal<>();

    public UserInterceptor(String cookieName) {
        this.cookieName = cookieName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取cookie
        String token = CookieUtils.getCookieValue(request, cookieName);
        userInfo.set(token);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        userInfo.remove();
    }

    public static String getUser() {
        return userInfo.get();
    }
}
