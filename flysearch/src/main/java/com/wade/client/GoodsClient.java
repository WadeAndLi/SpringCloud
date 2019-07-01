package com.wade.client;

import com.wade.api.GoodsAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface GoodsClient extends GoodsAPI{
}
