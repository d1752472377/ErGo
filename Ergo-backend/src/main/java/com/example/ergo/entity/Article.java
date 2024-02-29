package com.example.ergo.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.w3c.dom.Text;


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

public class Article  implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;//主键ID@TableId
    private Integer userId;//用户ID
    private Integer articleType;//文章类型：1-博文，2-问答
    private String title;//文章标题
    private String shortTitle; //短标题
    private String picture;//文章头图
    private String summary; //文章摘要
    private Integer categoryId;//类目ID
    private Integer source;//来源：1-转载，2-原创，3-翻译
    private String sourceUrl; //原文链接
    private Integer officalStat;//官方状态：0-非官方，1-官方
    private Boolean toppingStat;//置顶状态：0-不置顶，1-置顶
    private Boolean creamStat;//加精状态：0-不加精，1-加精
    private Integer status;//状态：0-未发布，1-已发布
    private Integer deleted;//是否删除
    private Integer level;//推荐层级
    private Integer sort;//是否排序
    private String content; //正文
    private Date createTime;//创建时间
    private Date updateTime; //最后更新时间
}
