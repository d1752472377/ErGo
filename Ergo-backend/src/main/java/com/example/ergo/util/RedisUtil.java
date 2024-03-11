package com.example.ergo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author autor
 * @date 2024/3/12
 * 描述：
 */
@Component
@Slf4j
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 增加操作
     * @param key
     * @param map
     * @return
     */
    public Boolean zhAdd(String key, HashMap<String, Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    /**
     * 给某个key某个属性增值操作
     * @param key
     * @param field1  articleId
     * @param field2  cnt
     * @return
     */
    public Boolean incrementHashValue(String key, String field1, int field2) {
        Boolean exists = redisTemplate.hasKey(key);
        if (exists) {
            // 如果字段存在，对字段的值进行自增操作
            redisTemplate.opsForHash().increment(key, field1, 1); // 对指定字段的值自增1
            log.info("自增完成");
            return true;
        } else {
            // 如果字段不存在，添加到redis中
            redisTemplate.opsForHash().put(key, field1, field2); // 将字段和值添加到Hash结构中
            log.info("添加完成");
            return true;
        }
    }
    public Map<String,Integer> getAllValuesByKey(String key) {
        Map readCount1 = redisTemplate.opsForHash().entries(key);
        // 通过key获取存储的值
        return readCount1;
    }



}
