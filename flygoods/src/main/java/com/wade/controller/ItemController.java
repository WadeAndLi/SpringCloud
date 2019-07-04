package com.wade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {

    @GetMapping("/goods/{id}.html")
    public String getPageDetail(@PathVariable("id") Long id, Model model) {
        return "item";
    }
}
