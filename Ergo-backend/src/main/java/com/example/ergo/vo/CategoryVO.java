package com.example.ergo.vo;

import java.util.Date;

public class CategoryVO {
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
