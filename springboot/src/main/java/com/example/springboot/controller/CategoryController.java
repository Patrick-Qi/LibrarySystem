package com.example.springboot.controller;

import com.example.springboot.common.Result;

import com.example.springboot.controller.request.CategoryPageRequest;
import com.example.springboot.entity.Category;

import com.example.springboot.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {



    @Autowired
    ICategoryService categoryService;


    @PostMapping("/save")
    public Result save(@RequestBody Category category) {

        categoryService.save(category);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Category category){
        categoryService.updateById(category);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    @GetMapping("/list")
    public Result list(){
        List<Category> users = categoryService.list();
        return Result.success(users);
    }
    @GetMapping("/page")
    public Result page(CategoryPageRequest categoryPageRequest){

        return Result.success(categoryService.page(categoryPageRequest));
    }
}
