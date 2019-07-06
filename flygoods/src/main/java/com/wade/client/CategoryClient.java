package com.wade.client;

import com.wade.api.CategoryAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface CategoryClient extends CategoryAPI{
}
