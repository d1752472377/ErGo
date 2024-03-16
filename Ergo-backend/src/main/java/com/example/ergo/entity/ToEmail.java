package com.example.ergo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author leilei
 * @version 1.0
 * @date 2020/3/12 14:28
 * @desc: 邮件发送封装实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToEmail implements Serializable {

  /**
   * 邮件接收方，可多人
   */
  private String[] tos;
  /**
   * 邮件主题
   */
  private String subject;
  /**
   * 邮件内容
   */
  private String content;
}