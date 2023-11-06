package com.example.ergo.controller;




import com.example.ergo.config.Result;
import com.example.ergo.entity.Category;

import com.example.ergo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 类目管理表(Category)表控制层
 *
 * @author makejava
 * @since 2023-10-16 23:30:48
 */
@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "分页查询分类")
    @GetMapping("/list")
    public Result getCagetoryList(Integer pageSize,Integer pageNum){
        Map cagetoryList = categoryService.getCagetoryList(pageSize, pageNum);

        return Result.success("查询成功",cagetoryList);
    }

    @Operation(summary = "添加分类")
    @PostMapping("/add")
    public Result addCagetory(@RequestBody Category category){
        Integer integer = categoryService.addCagetory(category);
        if (integer == 1){
            return Result.success(200,"添加成功");
        }
        return Result.fail("添加失败");
    }

    @Operation(summary = "删除分类")
    @DeleteMapping("/delete")
    public Result deleteCategory(Long id){
        Integer integer = categoryService.deleteCategory(id);
        if (integer == 1){
            return Result.success(200,"删除成功");
        }
        return Result.fail("删除失败");
    }
    @Operation(summary = "修改分类")
    @PutMapping("/update")
    public Result updateCategory(@RequestBody Category category){
//        Integer integer = categoryService.updateCategory(category);
        boolean b = categoryService.updateById(category);
        if (b == true){
            return Result.success(200,"修改成功");
        }
        return Result.fail("修改失败");
    }
}

