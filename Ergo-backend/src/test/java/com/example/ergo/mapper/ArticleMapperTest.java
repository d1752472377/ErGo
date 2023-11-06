package com.example.ergo.mapper;

import com.example.ergo.vo.articleVO;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 董冠男
 * @date 2023/10/14
 * 描述：
 */

class ArticleMapperTest {
    @Resource
    private ArticleMapper articleMapper;
    @Test
    public void test(){
        int pageSize = 10;
        int pageNum =1;
        List<articleVO> article = articleMapper.getArticle(pageNum, pageSize);
        System.out.println(article);
    }

}