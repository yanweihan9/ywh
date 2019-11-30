package com.young.ywh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket//启用websocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //消息处理器和拦截器
        webSocketHandlerRegistry.addHandler(getHandler(),"/websocket/*").addInterceptors(new ChatIntercepter());
    }

    @Bean
    public TextMessageHandler getHandler(){
        return new TextMessageHandler();
    }
}
