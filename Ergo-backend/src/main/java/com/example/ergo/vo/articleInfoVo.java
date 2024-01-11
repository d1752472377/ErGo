package com.example.ergo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Date;

/**
 * @author 董冠男
 * @date 2024/1/10
 * 描述：
 */
@Data
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
public class articleInfoVo {
    private Integer id;
    /**
     * 作者
     */
    private String userName;
    /**
     * 内容
     */
    private String content;
    /**
     * 头像
     */
    private String photo;
    /**
     * 标题
     */
    private String title;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;
}
