package com.wade.client;

import com.wade.api.BrandAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface BrandClient extends BrandAPI{
}
