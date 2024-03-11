package com.example.ergo.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.ergo.entity.Article;
import com.example.ergo.entity.ReadCount;
import com.example.ergo.mapper.ReadCountMapper;
import com.example.ergo.service.ArticleService;
import com.example.ergo.service.ReadCountService;
import com.example.ergo.util.RedisUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ListenHandler {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ReadCountService readCountService;
    @Autowired
    private ReadCountMapper readCountMapper;

    @Autowired
    private RedisUtil redisUtil;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ListenHandler(){
        this.logger.info("开始数据初始化");
    }

    @PostConstruct
    public void init() throws Exception {
        this.logger.info("数据库 初始化开始");
        //将数据库中的数据写入redis
        List<ReadCount> list = readCountService.list();
        for (ReadCount readCount: list){
            HashMap<String, Object> h1 = new HashMap<>();
            h1.put(readCount.getDocumentId().toString(), readCount.getCnt());
            redisUtil.zhAdd("readCount",h1);
        }
        this.logger.info("已写入redis");

    }
    //关闭时操作
    @PreDestroy
    public void afterDestroy(){
        System.out.println("关闭====================================");
        //将redis中的数据一次性写入数据库
        Map<String, Integer> readCount = redisUtil.getAllValuesByKey("readCount");
        this.writeNum(readCount);

        this.logger.info("数据库拿出到redis完毕");
        System.out.println("系统关闭===========reids->数据库更新完毕=================");
    }


    public void writeNum(Map<String,Integer> map){
        for (Map.Entry<String, Integer> entry : map.entrySet()){
            String documentId = entry.getKey();
            Integer cnt = entry.getValue();
            LambdaQueryWrapper<ReadCount> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ReadCount::getDocumentId,documentId);
            ReadCount one = readCountService.getOne(queryWrapper);
            if (one == null){
                ReadCount readCount = new ReadCount();
                readCount.setCnt(cnt);
                readCount.setDocumentId(Integer.valueOf(documentId));
                readCount.setDocumentType(1);
                readCountMapper.insert(readCount);
                log.info("插入完毕");
            }else {
                LambdaUpdateWrapper<ReadCount> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(ReadCount::getDocumentId,documentId)
                        .set(ReadCount::getCnt,cnt);
                //更新数据库
                readCountService.update(updateWrapper);
                this.logger.info("阅读量更新完毕");
            }
        }
    }
}
