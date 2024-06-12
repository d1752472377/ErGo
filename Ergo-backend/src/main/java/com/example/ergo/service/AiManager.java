package com.example.ergo.service;

import com.example.ergo.util.XfunListener;
import com.example.ergo.vo.dto.Xfun.MsgDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author autor
 * @date 2024/3/2
 * 描述：
 */
@Service
public class AiManager {
    @Resource
    private XfunListener xfunListener;

    public String testChat(String question){
        //8位随机数
        String random = String.valueOf((int)((Math.random()*9+1)*10000000));
        List<MsgDTO> msgs = new ArrayList<>();
        MsgDTO msgDTO = new MsgDTO( );
        msgDTO.setRole("user");
        msgDTO.setContent(question);
        msgDTO.setIndex(0);
        msgs.add(msgDTO);

        xfunListener.init_chat();
        try {
            // 获取接受消息的WebSocket
            XfunListener webSocket = xfunListener.sendMsg(random, msgs, xfunListener);
            //等待weSocked返回消息 , 这是一个笨笨的处理方法。
            int cnt = 3;
            //最长等待30S
            while (!webSocket.isFinished() && cnt > 0){
                Thread.sleep(1000);  //休息1S
                cnt--;
            }
            if(cnt == 0){
                return null;
            }

            String answer = webSocket.getAnswer();
            //返回答案
            return answer;
        } catch (Exception  e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        AiManager ai = new AiManager();
        ai.xfunListener =  XfunListener.builder()
                .apiKey("3554aa0a17812c93448c245fb9a6228d")
                .apiSecret("N2NkMjg4M2JlMzBhYTg2MDMyNTllNzlh")
                .appid("e0c1f212")
                .hostUrl("https://spark-api.xf-yun.com/v2.1/chat")
                .build();

        System.out.println(ai.testChat("你好啊！"));
    }
}
