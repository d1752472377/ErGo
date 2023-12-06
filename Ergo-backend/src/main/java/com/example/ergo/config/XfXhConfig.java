package com.example.ergo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "xfxh")
@Data
public class XfXhConfig {
    private String apiHost;
    private String apiPath;
    private String appId;
    private String apiKey;
    private String apiSecret;
}
