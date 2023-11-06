package com.example.ergo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.ergo.vo.UserVO;
import com.example.ergo.vo.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 董冠男
 * @date 2023/9/24
 * 描述：一个JwtTokenUtil工具类，用于创建token、验证token
 */
public class JwtUtil {
    /**
     * 打印日志
     */
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    /**
     * 密钥
     */
    private static final String SECRET = "my_select";
    /**
     * 过期时间,设置成五分钟
     */
    private static final long EXPIRATION = 300L;
    /**
     * 生成用户token，设置过期时间
     */
    public static String creatToken(UserVO user){
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        Map<String,Object> map = new HashMap<>();
        map.put("alg","HS256");
        map.put("typ","JWT");
        String token = JWT.create()
                .withHeader(map)//添加头部
                .withClaim("id",user.getId())
                .withClaim("userName",user.getUserName())
//                .withClaim("password",user.getPassword())
                .withExpiresAt(expireDate)//超时设置,设置过期的日期
                .withIssuedAt(new Date())//签发日期
                .sign(Algorithm.HMAC256(SECRET));//SECRET加密
        return token;
    }

    /**
     * 校验解析token
     */
    public static Map<String, Claim> verifyToken(String token){
        //将令牌(token)解码为一个DecodedJWT对象
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("解码器异常");
            return null;
        }

        //返回JWT负载中的所有声明(Claims)，这些声明以Map<String, Claim>的形式返回。
        return jwt.getClaims();
    }
}
