package com.example.ergo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ergo.entity.Article;
import com.example.ergo.vo.articleInfoVo;
import com.example.ergo.vo.articleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 文章表(Article)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-27 00:23:05
 */

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     *  获取文章列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<articleVO> getArticle(Integer pageNum , Integer pageSize);
    List<articleInfoVo> getArticleInfo(Integer id);
    Integer deleteArticleById(Integer id);
    Integer getTotal();
}
