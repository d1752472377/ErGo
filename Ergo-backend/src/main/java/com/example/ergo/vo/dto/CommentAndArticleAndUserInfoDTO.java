package com.example.ergo.vo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author autor
 * @date 2024/3/7
 * 描述：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentAndArticleAndUserInfoDTO {
    private Integer id;
    private String photo;
    private String content;
    private Date createTime;
    private Integer articleId;
    private String title;
    private Integer userId;
    private String userName;
}
