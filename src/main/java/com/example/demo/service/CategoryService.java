package com.example.demo.service;

import com.example.demo.pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> list();


    Category findById(Integer id);

    void update(Category category);

    void delete(Integer id);
}
