package com.example.ergo.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 计数表(ReadCount)表实体类
 *
 * @author makejava
 * @since 2024-03-11 15:15:36
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("read_count")
public class ReadCount  {
    //主键ID@TableId
    private Integer id;

    //文档ID（文章/评论）
    private Integer documentId;
    //文档类型：1-文章，2-评论
    private Integer documentType;
    //访问计数
    private Integer cnt;
    //创建时间
    private Date createTime;
    //最后更新时间
    private Date updateTime;



}
