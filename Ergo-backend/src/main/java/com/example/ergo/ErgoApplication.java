package com.example.ergo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ErgoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ErgoApplication.class, args);
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("\n\n ============> 系统启动成功！后台地址：http://localhost:" + environment.getProperty("server.port"));
        System.out.println(" ============> 巴啦啦能量小魔仙乌卡拉全身变");
    }

}
