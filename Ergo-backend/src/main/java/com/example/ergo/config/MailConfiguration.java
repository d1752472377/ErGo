package com.example.ergo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
 
@Configuration
public class MailConfiguration {

    @Bean
    public JavaMailSenderImpl JavaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.163.com");
        javaMailSender.setUsername("dongguannan212@163.com");
        javaMailSender.setPassword("UVFWWVOHLMSLBTLK");
        return javaMailSender;
    }
}