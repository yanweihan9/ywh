package com.young.ywh.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextMessageHandler extends TextWebSocketHandler {

    private Map<String,WebSocketSession> allClients = new HashMap<>();

    /**
     * 建立链接
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //获取在拦截器中设置的name
        String name = (String)session.getAttributes().get("name");
        if (name != null){
            allClients.put(name,session);
        }
    }

    /**
     *
     * @param session 当前发送消息的用户的链接
     * @param message 发送的消息是什么
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(new String(message.asBytes()));
        String toUser = jsonObject.getString("toUser");//找到接收者
        String toMessage = jsonObject.getString("toMessage");//找到发送的内容
        Object fromUser = session.getAttributes().get("name");
        String content = "收到来自"+fromUser+"的消息,内容是："+toMessage;
        TextMessage textMessage = new TextMessage(content);//创建消息对象
        sendMessage(toUser,textMessage);
    }

    public void sendMessage(String toUser,TextMessage message){
        //获取到对方的链接
        WebSocketSession session = allClients.get(toUser);//获取到对方的链接
        if (session != null && session.isOpen()){
            try {
                session.sendMessage(message);
            } catch (IOException e) {

            }
        }
    }

    /**
     * 关闭链接
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}
