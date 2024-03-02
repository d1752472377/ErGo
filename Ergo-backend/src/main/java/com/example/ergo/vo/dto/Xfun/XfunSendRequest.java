package com.example.ergo.vo.dto.Xfun;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


/**
 * @author autor
 * @date 2024/3/2
 * 描述：请求构成的数据对象类
 */
@Data
public class XfunSendRequest {
    @JsonProperty("header")
    private Header header;
    @JsonProperty("parameter")
    private ParameterDTO parameterDTO;
    @JsonProperty("payload")
    private PayloadDTO payload;
    @Data
    public static class Header{
        @JSONField(name = "app_id")
        private String appId;
        @JSONField(name = "uid")
        private String uid;
    }
    @Data
    public static class ParameterDTO{

        @JsonProperty("chat")
        private ChatDTO chat;
        @Data
        public static class ChatDTO {
            @JsonProperty("domain")
            private String domain = "generalv2";
            @JsonProperty("temperature")
            private Double temperature = 0.5;
            @JSONField(name = "max_tokens")
            private Integer maxTokens = 2048;
        }
    }
    @Data
    public static class PayloadDTO {
        @JsonProperty("message")
        private MessageDTO message;


        @Data
        public static class MessageDTO {
            @JsonProperty("text")
            private List<MsgDTO> text;


        }
    }

}
