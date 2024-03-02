package com.example.ergo.util;

import com.alibaba.fastjson.JSONObject;
import com.example.ergo.vo.dto.Xfun.MsgDTO;
import com.example.ergo.vo.dto.Xfun.XfunReceieveRequest;
import com.example.ergo.vo.dto.Xfun.XfunSendRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import okhttp3.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author autor
 * @date 2024/3/2
 * 描述：
 */
@Builder
public class XfunListener extends WebSocketListener {

    private String hostUrl;
    private String appid;
    private String apiSecret;
    private String apiKey;

    @Builder.Default
    public boolean is_finished = false;

    @Builder.Default
    public String answer = "";

    public String getAnswer(){
        return answer;
    }
    public boolean isFinished(){
        return is_finished;
    }
    public List<MsgDTO> getHistoryList(){
        return historyList;
    }
    @Builder.Default
    public List<MsgDTO> historyList = new ArrayList<>();

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response){
        super.onOpen(webSocket,response);
    }
    public  void deleteHistory(){
        historyList = new ArrayList<>();
    }
    public void init_chat(){
        is_finished = false;
    }

    // 接收到消息如何处理
    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket,text);

        // 消息格式处理
        XfunReceieveRequest xfunReceieveRequest = JSONObject.parseObject(text,XfunReceieveRequest.class);

        //状态判断
        if (xfunReceieveRequest.getHeader().getCode() ==0){
            // 0的话 ，获取状态成功
            XfunReceieveRequest.PayloadDTO payload = xfunReceieveRequest.getPayload();
            XfunReceieveRequest.PayloadDTO.ChoicesDTO choices = payload.getChoices();
            //处理得到的答案
            List<MsgDTO> msgs = choices.getText();
            //打上index
            for(int i = 0; i < msgs.size(); i++){
                MsgDTO msg =msgs.get(i);
                msg.setIndex(historyList.size()+i);
                historyList.add(msg);
            }
            if(xfunReceieveRequest.getHeader().getStatus() == 2){
                //表示会话来到最后一个结果
                XfunReceieveRequest.PayloadDTO.UsageDTO.TextDTO text1 = payload.getUsage().getText();
                System.out.println("PromptTokecn：" + text1.getPromptTokens());
                System.out.println("QuestionToken：" + text1.getQuestionTokens());
                System.out.println("CompletionToken：" + text1.getCompletionTokens());
                System.out.println("TotalToken"+text1.getTotalTokens());

                is_finished = true;

                // 消息整合
                StringBuilder message = new StringBuilder();
                for(MsgDTO msg: historyList){
                    message.append(msg.getContent());
                }
                deleteHistory();
                answer = message.toString();
                //断开连接
                // webSocket.close(3,"客户端断开连接");


            }
        }
    }
    // 获得鉴权地址
    public static String getAuthUrl(String hostUrl,String apiSecret,String apiKey) throws Exception{
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        System.err.println(preStr);
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8),"hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
         System.err.println(sha);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).
                addQueryParameter("date", date).
                addQueryParameter("host", url.getHost()).
                build();
        // System.err.println(httpUrl.toString());
        return httpUrl.toString();
    }
    // msgs和uid 转成 XfunSendRequest
    public XfunSendRequest getSendRequest(String uid, List<MsgDTO> msgs) {
        XfunSendRequest xfunSendRequest = new XfunSendRequest();
        XfunSendRequest.Header header = new XfunSendRequest.Header();
        header.setAppId(appid);
        header.setUid(uid);
        xfunSendRequest.setHeader(header);
        XfunSendRequest.ParameterDTO parameterDTO = new XfunSendRequest.ParameterDTO();
        XfunSendRequest.ParameterDTO.ChatDTO chatDTO = new XfunSendRequest.ParameterDTO.ChatDTO();
        parameterDTO.setChat(chatDTO);
        xfunSendRequest.setParameterDTO(parameterDTO);
        XfunSendRequest.PayloadDTO payloadDTO = new XfunSendRequest.PayloadDTO();
        XfunSendRequest.PayloadDTO.MessageDTO messageDTO = new XfunSendRequest.PayloadDTO.MessageDTO();
        messageDTO.setText(msgs);
        payloadDTO.setMessage(messageDTO);
        xfunSendRequest.setPayload(payloadDTO);
        return xfunSendRequest;

    }
    /**
     *  发送信息
     */
    public XfunListener sendMsg(String uid, List<MsgDTO> msgs, XfunListener webSocketListener) throws Exception {
        // 获取鉴权url
        String url = getAuthUrl(hostUrl,apiSecret,apiKey);
        //建立请求
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).build();

        WebSocket webSocket = okHttpClient.newWebSocket(request, webSocketListener);

        XfunSendRequest xfunSendRequest = this.getSendRequest(uid, msgs);
        System.out.println("params:" + JSONObject.toJSONString(xfunSendRequest));

        //发送消息
        webSocket.send(JSONObject.toJSONString(xfunSendRequest));

        return webSocketListener;
    }
}
