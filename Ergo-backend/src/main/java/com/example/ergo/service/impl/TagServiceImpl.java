package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.entity.Tag;
import com.example.ergo.mapper.TagMapper;
import com.example.ergo.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签管理表(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-12-31 22:07:36
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

