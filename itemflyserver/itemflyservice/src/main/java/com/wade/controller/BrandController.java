package com.wade.controller;

import com.wade.common.PageResult;
import com.wade.po.BrandPO;
import com.wade.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<BrandPO>> getBrand(
            @RequestParam(value = "page", defaultValue = "1")int page,
            @RequestParam(value = "key", defaultValue = "")String key,
            @RequestParam(value = "rows", defaultValue = "5")int rows,
            @RequestParam(value = "sortBy", required = false)String sortBy,
            @RequestParam(value = "desc", defaultValue = "false")boolean desc
    ) {
        return ResponseEntity.ok(brandService.queryBrand(page, key, rows, sortBy, desc));
    }
}
