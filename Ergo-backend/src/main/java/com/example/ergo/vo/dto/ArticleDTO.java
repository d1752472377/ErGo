package com.example.ergo.vo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2023/12/6
 * 描述：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private Integer id;//主键ID@TableId
    private String content; //文章内容
    private String title;//文章标题
    private String shortTitle; //短标题
}
