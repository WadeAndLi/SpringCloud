package com.wade.service;

import com.wade.po.Item;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ItemService {

    public Item saveItem(Item item) {
        item.setId(new Random().nextInt(100));
        return item;
    }
}
