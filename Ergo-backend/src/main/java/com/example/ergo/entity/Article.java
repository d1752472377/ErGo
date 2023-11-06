package com.example.ergo.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 文章表(Article)表实体类
 *
 * @author makejava
 * @since 2023-09-27 00:23:05
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article")
public class Article  {
    //主键ID@TableId
    private Integer id;

    //用户ID
    private Integer userId;
    //文章类型：1-博文，2-问答
    private Integer articleType;
    //文章标题
    private String title;
    //短标题
    private String shortTitle;
    //文章头图
    private String picture;
    //文章摘要
    private String summary;
    //类目ID
    private Integer categoryId;
    //来源：1-转载，2-原创，3-翻译
    private Integer source;
    //原文链接
    private String sourceUrl;
    //官方状态：0-非官方，1-官方
    private Integer officalStat;
    //置顶状态：0-不置顶，1-置顶
    private Boolean toppingStat;
    //加精状态：0-不加精，1-加精
    private Boolean creamStat;
    //状态：0-未发布，1-已发布
    private Integer status;
    //是否删除
    private Integer deleted;
    //推荐层级
    private Integer level;
    //是否排序
    private Integer sort;
    //创建时间
    private Date createTime;
    //最后更新时间
    private Date updateTime;



}
