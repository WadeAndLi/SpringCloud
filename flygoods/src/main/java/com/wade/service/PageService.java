package com.wade.service;

import com.wade.client.BrandClient;
import com.wade.client.CategoryClient;
import com.wade.client.GoodsClient;
import com.wade.common.JsonUtils;
import com.wade.dto.SpuDTO;
import com.wade.po.SpuPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class PageService {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    public ConcurrentMap<String, Object> queryPageDetail(Long id) {
        ConcurrentMap<String, Object> results = new ConcurrentHashMap<>();
        SpuDTO spuDetail = goodsClient.getSpuDetailById(id);
        SpuPO spu = spuDetail.getSpu();
        List<Long> ids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        JsonUtils.serialize(spuDetail.getSpuDetail().getSpecTemplate());
        results.put("spu", spuDetail.getSpu());
        results.put("skus", spuDetail.getSkus());
        results.put("detail", spuDetail.getSpuDetail());
        results.put("brand", brandClient.getBrand(spu.getBrandId()));
        results.put("categories", categoryClient.getCategoryList(ids));
        return results;
    }
}
