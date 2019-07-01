package com.wade.api;

import com.wade.po.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CategoryAPI {
    @GetMapping("/category/list/ids")
    List<Category> getCategoryList(@RequestParam("ids")List<Long> ids);
}
