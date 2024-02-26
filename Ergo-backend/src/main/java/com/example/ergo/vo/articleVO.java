package com.example.ergo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Date;

/**
 * @date 2023/10/14
 * 描述：
 */
@Data
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
public class articleVO {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 作者
     */
    private String userName;
    /**
     * 图片
     */
    private String picture;
    /**
     * 官方
     */
    private Boolean officalStat;
    private String shortTitle;
    private String tagName;
    private String summary;

    /**
     * 标题
     */
    private String title;
    /**
     * 分类
     */
    private String categoryName;
    /**
     * 置顶
     */
    private Boolean toppingStat;
    /**
     * 推荐
     */
    private Boolean creamStat;
    /**
     * 可见性
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;


}
