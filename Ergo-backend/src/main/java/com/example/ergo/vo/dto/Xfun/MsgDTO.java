package com.example.ergo.vo.dto.Xfun;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author autor
 * &#064;date  2024/3/2
 * 描述：消息对象
 */
@Data
public class MsgDTO {
    /**
     * 角色
     */
    private String role;
    /**
     * 消息内容
     */
    private String content;
    private Integer index;
    @Getter
    public static enum Role{
        SYSTEM("system"),
        USER("user"),
        ASSISTANT("assistant");
        private String name;
        private Role(String name) {
            this.name = name;
        }
    }
}
