package com.xuecheng.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.xuecheng.commons.model.vo.AuthInfo;
import com.xuecheng.commons.utils.AuthInfoHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 自定义拦截器
 */
@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    //进入controller方法之前执行。
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1、获取网关转发来的json数据，
        String payload = request.getHeader("payload");
        if (StringUtils.isEmpty(payload)) {
            return true;
        }

        //2、转化成AuthInfo对象，
        String  json = URLDecoder.decode(payload, "UTF-8");
        AuthInfo info = JSON.parseObject(json, AuthInfo.class);

        //3、存入threadlocal
        AuthInfoHolder.setAuthInfo(info);
        return true;
    }
}
