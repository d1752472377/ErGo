package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.entity.Tag;
import com.example.ergo.mapper.TagMapper;
import com.example.ergo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 标签管理表(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-12-31 22:07:36
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Override
    public Map<String, Object> getListByPage(int pagesize, int currentPage) {
        Page<Tag> tagPage = new Page<>(currentPage , pagesize);
        IPage<Tag> tagIPage = tagMapper.selectPage(tagPage,null);
        Map<String,Object> map =new HashMap<>();
        map.put("list",tagIPage.getRecords());
        map.put("Total",tagIPage.getTotal());
        return map;
    }
}

