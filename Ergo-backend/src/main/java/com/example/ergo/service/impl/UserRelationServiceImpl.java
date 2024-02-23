package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.entity.UserRelation;
import com.example.ergo.mapper.UserRelationMapper;
import com.example.ergo.service.UserRelationService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户关系表(UserRelation)表服务实现类
 *
 * @author makejava
 * @since 2024-02-23 19:08:28
 */
@Service("userRelationService")
@Slf4j
@Log4j2
public class UserRelationServiceImpl extends ServiceImpl<UserRelationMapper, UserRelation> implements UserRelationService {

}

