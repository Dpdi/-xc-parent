package com.xuecheng.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.sun.deploy.net.URLEncoder;
import com.xuecheng.commons.enums.ErrorCode;
import com.xuecheng.commons.model.vo.AuthInfo;
import com.xuecheng.commons.utils.JwtUtils;
import lombok.SneakyThrows;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关-全局过滤器
 * 1、实现两个接口和两个方法
 * 2、在filter方法中，完成过滤逻辑
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    /**
     * 核心过滤方法：业务处理
     * @param exchange：请求上下文（获取request和response）
     * @param chain：过滤器链（控制程序放行）
     * @return
     */
    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //1、获取当前的请求连接
        String path = request.getURI().getPath();

        //2、判断，此请求地址是否需要进行token校验，如果不需要，直接放行，进入微服务
        if (path.contains("coursePub/preview") ||
                path.contains("login") ||
                path.contains("basic/dictionary") ||
                path.contains("category/tree-nodes")||
                path.contains("course/upload")||
                path.contains("search/")) {
            return chain.filter(exchange);
        }

        //3、获取请求头参数token
        String token = request.getHeaders().getFirst("Authorization");

        //4、验证token是否合法，如果不合法，响应状态码401
        Boolean verifyToken = JwtUtils.verifyToken(token);
        if (!verifyToken) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete(); //直接返回
        }

        //5、解析token，获取登录时存入的数据（userId，companyId，companyName）
        AuthInfo info = JwtUtils.getInfoFromToken(token);

        //6、设置一个新的请求头参数（解析后的明文数据）
        String json = URLEncoder.encode(JSON.toJSONString(info),"UTF-8");
        //创建一个httpRequest的请求对象
        ServerHttpRequest httpRequest = request.mutate().headers(httpHeaders -> {
            httpHeaders.set("payload", json);
        }).build();
        //将此请求对象，写入到微服务转发的上下文中
        exchange.mutate().request(httpRequest);
        return chain.filter(exchange);
    }

    /**
     * 指定多个过滤器时，此过滤器的执行顺序
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
