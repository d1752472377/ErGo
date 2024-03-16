package com.example.ergo.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author autor
 * @date 2024/3/12
 * 描述：
 */
@Component
@Slf4j
@AllArgsConstructor
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



    /**
     * 普通获取键对应值
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通设置键值
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 普通设置键值并设置过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 键
     * @return 是否成功
     */
    public boolean del(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 指定缓存的失效时间
     *
     * @param key  键值 @NotNull
     * @param time 时间(秒) @NotNull
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
