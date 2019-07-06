package com.wade.controller;

import com.wade.common.PageResult;
import com.wade.po.SearchPO;
import com.wade.po.SearchRequest;
import com.wade.po.SpuPO;
import com.wade.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/page")
    public ResponseEntity<PageResult<SearchPO>> searchGoods(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(searchService.getSearchResult(searchRequest));
    }
}
