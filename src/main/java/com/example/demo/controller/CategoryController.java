package com.example.demo.controller;


import com.example.demo.pojo.Category;
import com.example.demo.pojo.Result;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){
        categoryService.add(category);
        return Result.success("导入成功");
    }

    @GetMapping // 获取所有文章的类目列表
    public Result<List<Category>> list(){
        List<Category> categories = categoryService.list();
        return Result.success(categories);
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id){
        Category category = categoryService.findById(id);
        return Result.success(category);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success("更新成功");
    }

    @DeleteMapping
    public Result delete(@RequestParam @Validated(Category.Delete.class) Integer id){
        categoryService.delete(id);
        return Result.success("删除成功");
    }
}
