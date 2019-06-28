package com.wade.controller;

import com.wade.common.PageResult;
import com.wade.po.SpuPO;
import com.wade.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("spu")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/page")
    public ResponseEntity<PageResult<SpuPO>> getSpu(
            @RequestParam(value = "page", defaultValue = "1")int page,
            @RequestParam(value = "key", defaultValue = "")String key,
            @RequestParam(value = "rows", defaultValue = "5")int rows,
            @RequestParam(value = "saleable", defaultValue = "true")Boolean saleable
    ) {
        return ResponseEntity.ok(goodsService.getSpu(page, key, rows, saleable));
    }

    @PostMapping("/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuPO spuPO) {
        goodsService.insertGoods(spuPO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
