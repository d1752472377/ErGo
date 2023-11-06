package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.config.SnowFlakeUtil;
import com.example.ergo.entity.Category;
import com.example.ergo.mapper.CategoryMapper;
import com.example.ergo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类目管理表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-10-16 23:30:48
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public Map getCagetoryList(Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getId);
        Page page =new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        IPage iPage = categoryMapper.selectPage(page,queryWrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("total",iPage.getTotal());
        List<Category> category = iPage.getRecords();
        map.put("list",category);
        return map;
    }

    @Override
    public Integer addCagetory(Category category) {
        SnowFlakeUtil snowFlakeUtil =new SnowFlakeUtil(12,13);
        Long id = snowFlakeUtil.getNextId();
//        category.setId(id);
        Boolean category1 = save(category);


        if (category1 == true){
            return 1;
        }return 0;
    }

    @Override
    public Integer deleteCategory(Long id) {
        int i = categoryMapper.deleteById(id);
        if (i == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer updateCategory(Category category) {
        LambdaUpdateWrapper<Category> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Category::getId,category.getId())
                .set(Category::getCategoryName,category.getCategoryName())
                .set(Category::getRank,category.getRank())
                .set(Category::getStatus,category.getStatus())
                .set(Category::getDeleted,category.getDeleted());

        int update = categoryMapper.update(category,updateWrapper);
        if (update == 1){

            return 1;
        }
        return 0;
    }


}

