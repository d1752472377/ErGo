package com.example.ergo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ergo.entity.Category;

import java.util.Map;


/**
 * 类目管理表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-10-16 23:30:48
 */
public interface CategoryService extends IService<Category> {

    Map getCagetoryList(Integer pageSize, Integer pageNum);

    Integer addCagetory(Category category);

    Integer deleteCategory(Long id);

    Integer updateCategory(Category category);
}

