package com.example.ergo.vo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 董冠男
 * @date 2024/1/28
 * 描述：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Integer id;
    private String photo;
    private String content;
    private Integer topCommentId;
    private Integer parentCommentId;
    private String parentName;
    private Date createTime;
    private Integer articleId;
    private Integer userId;
    private String userName;
    private List<CommentDto> replyComments = new ArrayList<>();

}
