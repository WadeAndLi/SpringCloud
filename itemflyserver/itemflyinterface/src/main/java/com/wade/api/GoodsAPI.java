package com.wade.api;

import com.wade.common.PageResult;
import com.wade.dto.SpuDTO;
import com.wade.po.SpuPO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface GoodsAPI {

    @GetMapping("/spu/detail/{spuId}")
    SpuDTO getSpuDetailById(@PathVariable("spuId")Long spuId);

    @GetMapping("/spu/page")
    PageResult<SpuPO> getSpu(
            @RequestParam(value = "page", defaultValue = "1")int page,
            @RequestParam(value = "key", defaultValue = "")String key,
            @RequestParam(value = "rows", defaultValue = "5")int rows,
            @RequestParam(value = "saleable", defaultValue = "true")Boolean saleable
    );

    @GetMapping("/spu/{id}")
    SpuPO getOneSpu(@PathVariable("id") Long id);
}
