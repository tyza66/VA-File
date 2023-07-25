package com.tyza66.vfm_core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: tyza66
 * Date: 2023/7/24 14:11
 * Github: https://github.com/tyza66
 **/

@ServerEndpoint("/ws/chatRoom/{userName}") //WebSocket客户端建立连接的地址
@Component
@Slf4j
public class ChatRoomServerEndpoint {

    //存活的session集合（使用线程安全的map保存）
    private static Map<String, Session> livingSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName) {
        livingSessions.put(session.getId(), session);
        log.info("用户 {} 已连接", userName);
        sendMessageToAll(userName + " 加入内部聊天室");
    }

    //收到客户端消息的回调方法
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userName") String userName) {
        sendMessageToAll(userName + " : " + message);
    }


    //发生错误的回调方法
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("用户 {} 发生错误", session.getId());
        error.printStackTrace();
    }

    //关闭连接的回调方法
    @OnClose
    public void onClose(Session session, @PathParam("userName") String userName) {
        livingSessions.remove(session.getId());
        log.info("用户 {} 已断开连接", userName);
        sendMessageToAll(userName + " 退出内部聊天室");
    }


    //单独发送消息
    public void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //群发消息
    public void sendMessageToAll(String message) {
        livingSessions.forEach((sessionId, session) -> {
            sendMessage(session, message);
        });
    }

}
