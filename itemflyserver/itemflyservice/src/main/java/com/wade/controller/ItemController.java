package com.wade.controller;

import com.wade.common.ExceptionEnum;
import com.wade.common.FlyException;
import com.wade.po.Item;
import com.wade.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> saveItem(Item item) {
        Item resultItem = itemService.saveItem(item);
        if (resultItem.getName() == null) {
            throw new FlyException(ExceptionEnum.NAME_CANNOT_BE_NULL);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(resultItem);
    }
}
