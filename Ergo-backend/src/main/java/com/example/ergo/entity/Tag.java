package com.example.ergo.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 标签管理表(Tag)表实体类
 *
 * @author makejava
 * @since 2023-12-31 22:07:35
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tag")
public class Tag  {
    //主键ID@TableId
    private Integer id;

    //标签名称
    private String tagName;
    //标签类型：1-系统标签，2-自定义标签
    private Integer tagType;
    //类目ID
    private Integer categoryId;
    //状态：0-未发布，1-已发布
    private Integer status;
    //是否删除
    private Integer deleted;
    //创建时间
    private Date createTime;
    //最后更新时间
    private Date updateTime;



}
