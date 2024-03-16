package com.example.ergo.service;

import com.example.ergo.config.Result;
import com.example.ergo.entity.ToEmail;
import jakarta.mail.MessagingException;

/**
 * @desc: 邮件发送接口
 */
public interface ToEmailService {




  /**
   * @param toEmail 发送对象
   * @return 统一返回ajax
   * @Desc 发送html形式的邮件
   */
  Result htmlEmail(ToEmail toEmail) throws MessagingException, jakarta.mail.MessagingException;


}