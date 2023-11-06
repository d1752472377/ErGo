package com.example.ergo.controller;

import com.example.ergo.config.JwtUtil;
import com.example.ergo.config.Result;
import com.example.ergo.entity.User;
import com.example.ergo.entity.UserInfo;
import com.example.ergo.enums.Constant;
import com.example.ergo.util.AESDecryptor;
import com.example.ergo.util.AESUtil;
import com.example.ergo.vo.UserVO;
import com.example.ergo.vo.dto.UserDTO;
import com.example.ergo.service.UserInfoService;
import com.example.ergo.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "用户管理")
@RestController
@Slf4j
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // TODO 登录 根据用户名密码 userName password
    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result login(HttpServletRequest request){
        String username =request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.passwordlogin(username,password);
        // TODO 账号不存在，是否注册账户
        // TODO 账户已被冻结
        if (user == null){
            return Result.fail("账号密码错误，请重新输入");
        }
        UserVO userVO =new UserVO();
        BeanUtils.copyProperties(user,userVO);
        String token = JwtUtil.creatToken(userVO);
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        return Result.success("登陆成功",map);
    }
    @Operation(summary = "前台邮箱登录")
    @PostMapping("/auth/user/login")
    public Result loginByEmail(@RequestBody String requestData) throws Exception {
        // TODO 抽象出处理JSON的代码作为一个工具类
        String s = java.net.URLDecoder.decode(requestData, "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(s);
        String str = jsonNode.get("requestData").textValue();
        String str1 = str.replaceAll(" +","+");
        System.out.println(str1);
        String keypair =redisTemplate.opsForValue().get(Constant.AES_KEY);
        System.out.println(keypair);
        String a = AESUtil.aesDecrypt(str1,keypair);
        System.out.println(a);
        JsonNode jsonNode1 = objectMapper.readTree(a);
        String username =jsonNode1.get("username").textValue();
        String password = jsonNode1.get("password").textValue();
        User user = userService.passwordlogin(username,password);
        if (user == null){
            return Result.fail("账号密码错误，请重新输入");
        }
        Integer id= Math.toIntExact(user.getId());
        UserInfo userInfo = userInfoService.getById(id);
        System.out.println(userInfo);
        UserVO userVo =new UserVO();
        BeanUtils.copyProperties(userInfo,userVo);
        String token = JwtUtil.creatToken(userVo);
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("userInfo",userVo);
        return Result.success("登陆成功",map);
    }

    /**
     * 获取登录信息
     * @param token
     * @return
     */
    @Operation(summary = "获取登录信息")
    @GetMapping("/info")
    public Map info(String token){
        HashMap<String, Object> responseInfo = new HashMap<>();
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("roles","?");
        responseData.put("name","666");
        responseData.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        responseInfo.put("code",200);
        responseInfo.put("msg","登录成功");
        responseInfo.put("data",responseData);
        return responseInfo;
    }
    // TODO 登出
    @Operation(summary = "登出")
    @PostMapping("/user/list")
    public Result logout(){
        return Result.success();
    }

    @Operation(summary = "注册")
    @PostMapping("register")
    public Result register(UserDTO userDTO){
        Integer num = userService.register(userDTO);
        if (num == 0){
            return Result.fail("账户已存在，请登录");
        }
        return Result.success(200,"注册成功，请登录");
    }

    @Operation(summary = "查询用户列表")
    @GetMapping("/user/list")
    public Result getUserList(Integer pageSize,Integer pageNum){
        if(pageNum ==null || pageSize ==null){
            return Result.fail("空值啦");
        }
        Map map = userInfoService.getUserList(pageSize,pageNum);

        return Result.success("查询成功",map);
    }

    @Operation(summary = "获取所有的白名单用户")
    @GetMapping("/666")
    public Result queryAllArticleWhiteListAuthors(){

        return null;
    }

}