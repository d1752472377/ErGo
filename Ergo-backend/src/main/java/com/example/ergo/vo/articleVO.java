package com.example.ergo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 董冠男
 * @date 2023/10/14
 * 描述：
 */
@Data
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
