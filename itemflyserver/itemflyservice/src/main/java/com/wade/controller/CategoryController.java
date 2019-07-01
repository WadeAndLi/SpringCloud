package com.wade.controller;

import com.wade.po.Category;
import com.wade.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> listCategory(@RequestParam("pid")long pid) {
        return ResponseEntity.ok(categoryService.getCategoryList(pid));
    }

    @GetMapping("list/ids")
    public ResponseEntity<List<Category>> getCategoryByIds(@RequestParam("ids")List<Long> ids) {
        return ResponseEntity.ok(categoryService.getCategoryListByIds(ids));
    }
}
