package com.wade.api;

import com.wade.po.BrandPO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BrandAPI {

    @GetMapping("/brand/{id}")
    BrandPO getBrand(@PathVariable("id")Long id);
}
