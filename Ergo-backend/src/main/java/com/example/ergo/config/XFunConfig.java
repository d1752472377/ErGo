package com.example.ergo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *   星火大模型AI接口配置
 */
@Configuration
@ConfigurationProperties(prefix = "xfun.open")
@Data
public class XFunConfig {
    private String appid;
    private String apiSecret;
    private String hostUrl;
    private String apiKey;
}
