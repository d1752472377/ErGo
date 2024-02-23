package com.example.ergo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ergo.config.Result;
import com.example.ergo.entity.UserInfo;
import com.example.ergo.entity.UserRelation;
import com.example.ergo.service.UserInfoService;
import com.example.ergo.service.UserRelationService;
import com.example.ergo.vo.UserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 董冠男
 * @date 2024/2/23
 * 描述：
 */
@RestController
@CrossOrigin
@Log4j2
@RequestMapping("relation")
public class UserRelationController {

    @Autowired
    private UserRelationService userRelationService;
    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "查询用户关系")
    @GetMapping("/queryRelation")
    public Result queryUserRelation(@RequestParam(name = "userId") int userId){
        //粉丝
        LambdaQueryWrapper<UserRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRelation::getUserId,userId);
        List<UserRelation> fans = userRelationService.list(queryWrapper);
        long countFans = userRelationService.count(queryWrapper);
        List<UserInfo> collectedUserInfoList = new ArrayList<>();
        for (UserRelation userRelation:fans){
            Integer userIdForInfo = userRelation.getFollowUserId();
            LambdaQueryWrapper<UserInfo> queryWrapper1 =new LambdaQueryWrapper<>();
            queryWrapper1.eq(UserInfo::getUserId,userIdForInfo);
            List<UserInfo> userInfoList = userInfoService.list(queryWrapper1);
            collectedUserInfoList.addAll(userInfoList);
        }
        List<UserInfoVo> listForFans = new ArrayList<>();
        for (UserInfo userInfo : collectedUserInfoList) {
            UserInfoVo userInfoVo = new UserInfoVo();
            BeanUtils.copyProperties(userInfo, userInfoVo);
            listForFans.add(userInfoVo);
        }

        //关注
        LambdaQueryWrapper<UserRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRelation::getFollowUserId,userId);
        List<UserRelation> follow = userRelationService.list(wrapper);
        long countFollow = userRelationService.count(wrapper);
        List<UserInfo> collectedUser = new ArrayList<>();
        for(UserRelation userRelation:follow){
            Integer id = userRelation.getUserId();
            LambdaQueryWrapper<UserInfo> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(UserInfo::getUserId,id);
            List<UserInfo> userInfoList = userInfoService.list(queryWrapper1);
            collectedUser.addAll(userInfoList);
        }
        List<UserInfoVo> listForFollow = new ArrayList<>();
        for (UserInfo userInfo : collectedUser) {
            UserInfoVo userInfoVo = new UserInfoVo();
            BeanUtils.copyProperties(userInfo, userInfoVo);
            listForFollow.add(userInfoVo);
        }

        //整合
        Map<String,Object> map = new HashMap<>();
        map.put("fans",listForFans);
        map.put("follow",listForFollow);
        map.put("countFans",countFans);
        map.put("countFollow",countFollow);
        return Result.success(map);
    }

    @Operation(summary = "查询用户关系总数")
    @GetMapping("/queryRelationCount")
    public Result queryUserRelationCount(@RequestParam(name = "userId") int userId){
        //粉丝
        LambdaQueryWrapper<UserRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRelation::getUserId,userId);
        long countFans = userRelationService.count(queryWrapper);
        //关注
        LambdaQueryWrapper<UserRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRelation::getFollowUserId,userId);
        long countFollow = userRelationService.count(wrapper);
        //整合
        Map<String,Object> map = new HashMap<>();;
        map.put("countFans",countFans);
        map.put("countFollow",countFollow);
        return Result.success(map);
    }


}
