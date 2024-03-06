package com.example.ergo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ergo.entity.Tag;

import java.util.Map;


/**
 * 标签管理表(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-12-31 22:07:35
 */
public interface TagService extends IService<Tag> {

    Map<String, Object> getListByPage(int pagesize, int currentPage);
}

