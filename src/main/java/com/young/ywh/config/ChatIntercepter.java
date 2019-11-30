package com.young.ywh.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * websocket握手的拦截器，检查握手请求和相应，对websockethandler传递属性，用于区别websocket
 */
@Slf4j
public class ChatIntercepter extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        //为了区分链接，通过名字区分
        //需获取到用户名字，地址定义的最后一位是名字，只要截取地址的最后一位就可以
        log.info("握手");
        String url = request.getURI().toString();
        String name = url.substring(url.lastIndexOf("/") + 1);
        //给当前链接设置名字
        attributes.put("name", name);
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }


    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        log.info("握手之后");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
