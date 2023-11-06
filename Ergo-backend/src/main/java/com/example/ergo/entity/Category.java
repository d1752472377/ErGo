package com.example.ergo.entity;

import java.util.Date;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类目管理表(Category)表实体类
 *
 * @author makejava
 * @since 2023-10-16 23:30:48
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("category")
public class Category  {
    //主键ID@TableId
    @TableId(type = IdType.AUTO)
    private Long id;

    //类目名称
    private String categoryName;
    //状态：0-未发布，1-已发布
    private Integer status;
    //排序
    private Integer rank;
    //是否删除
    private Integer deleted;
    //创建时间
    private Date createTime;
    //最后更新时间
    private Date updateTime;



}
