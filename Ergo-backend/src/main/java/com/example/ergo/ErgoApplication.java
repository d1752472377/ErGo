package com.example.ergo;

import com.example.ergo.config.XFunConfig;
import com.example.ergo.util.XfunListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import top.hualuo.XhStreamClient;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
public class ErgoApplication {

    @Autowired
    private XFunConfig xFunConfig;

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ErgoApplication.class, args);
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("\n\n ============> 系统启动成功！后台地址：http://localhost:" + environment.getProperty("server.port"));
        System.out.println(" ============> 巴啦啦能量小魔仙乌卡拉全身变");
    }
    @Bean
    public XfunListener getXfunListener(){
        return XfunListener.builder()
                .apiKey(xFunConfig.getApiKey())
                .apiSecret(xFunConfig.getApiSecret())
                .hostUrl(xFunConfig.getHostUrl())
                .appid(xFunConfig.getAppid()).build();
    }


}
