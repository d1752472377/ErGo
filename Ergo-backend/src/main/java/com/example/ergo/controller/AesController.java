package com.example.ergo.controller;

import com.example.ergo.enums.Constant;
import com.example.ergo.util.AESUtil;
import com.example.ergo.vo.AesVo;
import com.example.ergo.vo.BaseVO;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "AES密钥集合")
@RestController
@Log4j2
@CrossOrigin
public class AesController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @PostMapping("/web/aes/key")
    public BaseVO getKey() {
        AesVo aesVo = new AesVo();
        String aesKeypair = Constant.AES_KEYPAIR;
        Constant.AES_KEYPAIR = AESUtil.genEncodeRule();
        //设置密钥到redis中
        redisTemplate.opsForValue().set(Constant.AES_KEY, aesKeypair);
        //获取密钥
        String keypair = redisTemplate.opsForValue().get(Constant.AES_KEY);
        log.info("写入redis-key [{}]", keypair);
        aesVo.setKey(keypair);
        return aesVo;
    }
}
