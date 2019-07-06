package com.wade.controller;

import com.wade.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.concurrent.ConcurrentMap;

@Controller
public class GoodsController {

    @Autowired
    private PageService pageService;

    @GetMapping("/goods/{id}.html")
    public String getPageDetail(@PathVariable("id") Long id, Model model) {
        ConcurrentMap<String, Object> results = pageService.queryPageDetail(id);
        model.addAllAttributes(results);
        return "item";
    }
}
