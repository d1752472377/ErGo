package com.example.ergo.service.impl;

import com.example.ergo.config.Result;
import com.example.ergo.entity.ToEmail;
import com.example.ergo.service.ToEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


/**
 * @desc: 邮件发送支持 实现类
 */
@Service
@Slf4j
public class ToEmailServiceImpl implements ToEmailService {
  @Autowired
  private JavaMailSender mailSender;


  private String from = "dongguannan212@163.com";



  /**
   * 发送Html 有机胺
   *
   * @param toEmail 发送对象
   */
  @Override
  public Result htmlEmail(ToEmail toEmail) throws MessagingException, jakarta.mail.MessagingException {
    //创建一个MINE消息
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper minehelper = new MimeMessageHelper(message, true);
    //谁发
    minehelper.setFrom(from);
    //谁要接收
    minehelper.setTo(toEmail.getTos());
    //邮件主题
    minehelper.setSubject(toEmail.getSubject());//TODO update

    //邮件内容   true 表示带有附件或html
    minehelper.setText(toEmail.getContent(), true);
    try {
      mailSender.send(message);
      return Result.success(toEmail.getTos() + toEmail.getContent(), "HTML邮件成功");
    } catch (MailException e) {
      e.printStackTrace();
      return Result.success("HTML邮件失败");
    }
  }
}