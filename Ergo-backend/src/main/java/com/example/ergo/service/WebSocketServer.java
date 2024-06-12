package com.example.ergo.service;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author autor
 * @date 2024/4/11
 * 描述：
 */
@Component
@ServerEndpoint(value = "/ws/{userName}")
public class WebSocketServer {
    /**
     * 存储系统用户信息
     */
    static Map<Session,String> map = new HashMap<Session,String>();

    /**
     * @Description: WebSocket服务端代码，包含接收消息，推送消息等接口
     * userName是接收的参数
     */
    @OnOpen
    public void OnOpen(@PathParam("userName") String userName, Session session){
        System.out.println(userName);
        map.put(session,userName);
        System.out.println("OnOpen()方法被执行");
        System.out.println("websocket连接建立成功");
    }
    /**
     * 连接关闭的方法
     */
    @OnClose
    public void OnClose(Session session){
        if (map.containsKey(session)){
            map.remove(session);
        }
        System.out.println("OnClose()方法被执行...");
        System.out.println("websocket连接已经关闭...");
    }
    /**
     * 接收消息的方法
     * @param msg
     * @param session
     * @throws InterruptedException
     */
    @OnMessage
    public void OnMessage(String msg,Session session) throws  InterruptedException{
        System.out.println("已从客户端接收消息：" + msg);

        System.out.println("向客户端发送数据完毕...");
    }
    /**
     * 出错的方法，注意参数不能错
     * @param session
     * @param error
     */
    @OnError
    public void OnError(Session session,Throwable error) {
        if (map.containsKey(session)) {
            map.remove(session);
        }
        System.out.println("OnError()方法dewd被执行...");
        System.out.println("websocket出错...");
    }
    /**
     * 推送数据的方法
     * @param session map里存的登录信息
     * @param message 推送数据
     */
    public void sendMessage(Session session,String message){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



}
