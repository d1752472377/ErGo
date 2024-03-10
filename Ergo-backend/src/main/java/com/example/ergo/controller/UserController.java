package com.example.ergo.controller;

import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.ergo.config.JwtUtil;
import com.example.ergo.config.Result;
import com.example.ergo.entity.User;
import com.example.ergo.entity.UserInfo;
import com.example.ergo.enums.Constant;
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

import java.util.Date;
import java.util.HashMap;
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
        UserInfo userInfo = userInfoService.getById(user.getId());
        String photo = userInfo.getPhoto();
        // TODO 账号不存在，是否注册账户
        // TODO 账户已被冻结
        if (user == null){
            return Result.fail("账号密码错误，请重新输入");
        }
        UserVO userVO =new UserVO();
        BeanUtils.copyProperties(user,userVO);
        userVO.setPhoto(photo);
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
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        String userName = stringClaimMap.get("userName").asString();
        String photo = stringClaimMap.get("photo").asString();

        HashMap<String, Object> responseInfo = new HashMap<>();
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("roles","?");
        responseData.put("name",userName);
        responseData.put("avatar",photo);
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

    @Operation(summary = "修改用户状态")
    @PutMapping("/user/updateUserInfoForStatus")
    public Result updateUserInfoForStatus(@RequestBody UserInfo userInfo){
        boolean b = userInfoService.updateById(userInfo);
        if (b){
            return Result.success("修改成功");
        }
        return Result.fail("修改失败");
    }
    @Operation(summary = "修改用户密码")
    @PutMapping("/user/updatePassword")
    public Result updatePassword(@RequestBody User user){
        String username = user.getUserName();
        String password = user.getPassword();
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getUserName,username);
        updateWrapper.set(User::getPassword,password);
        userService.update(updateWrapper);
        return Result.success("修改成功");
    }

    @Operation(summary = "查询用户天数")
    @GetMapping("/getDateForUser")
    public Result getDateForUser(@RequestParam(name = "userId") int userId){
        User user = userService.getById(userId);
        Date createTime = user.getCreateTime();
//        System.out.println(createTime);
        Date date = new Date();
//        System.out.println(date);
        long diffInMillis = date.getTime() - createTime.getTime();
        long day = diffInMillis/1000/60/60/24;
//        System.out.println("时间差（天）: " + day);
        return Result.success(day);
    }


}