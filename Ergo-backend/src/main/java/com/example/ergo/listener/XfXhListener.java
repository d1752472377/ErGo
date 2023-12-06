package com.example.ergo.listener;

import com.alibaba.fastjson.JSONObject;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import top.hualuo.dto.MsgDTO;
import top.hualuo.dto.ResponseDTO;

import java.util.List;

/**
 * @author 17524
 */
public class XfXhListener extends WebSocketListener {

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);

    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);
        System.out.println("text:\n" + text);
        ResponseDTO responseData = JSONObject.parseObject(text,ResponseDTO.class);
        if(0 == responseData.getHeader().getCode()){
            ResponseDTO.PayloadDTO pl = responseData.getPayload();
            List<MsgDTO> tests = pl.getChoices().getText();
            MsgDTO textDTO = tests.stream().findFirst().orElse(new MsgDTO());

            System.out.println(textDTO.toString());

            if(2 == responseData.getHeader().getStatus()){
                ResponseDTO.PayloadDTO.UsageDTO.TextDTO testDto = pl.getUsage().getText();
                Integer totalTokens = testDto.getTotalTokens();
                System.out.println("本次花费："+totalTokens + " tokens");


                webSocket.close(3,"客户端主动断开链接");
            }


        }else{
            System.out.println("返回结果错误：\n" + responseData.getHeader().getCode()+  responseData.getHeader().getMessage() );
        }
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
    }
}
