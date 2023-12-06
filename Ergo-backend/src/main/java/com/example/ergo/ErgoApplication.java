package com.example.ergo;

import com.example.ergo.config.XfXhConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import top.hualuo.XhStreamClient;

@SpringBootApplication
public class ErgoApplication {
    @Autowired
    private XfXhConfig xfXhConfig;

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ErgoApplication.class, args);
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("\n\n ============> 系统启动成功！后台地址：http://localhost:" + environment.getProperty("server.port"));
        System.out.println(" ============> 巴啦啦能量小魔仙乌卡拉全身变");
    }
    @Bean
    public XhStreamClient xhStreamClient (){
        return XhStreamClient.builder()
                .apiHost(xfXhConfig.getApiHost())
                .apiPath(xfXhConfig.getApiPath())
                .appId(xfXhConfig.getAppId())
                .apiKey(xfXhConfig.getApiKey())
                .apiSecret(xfXhConfig.getApiSecret())

                .build();
    }

}
