package com.wade.api;

import com.wade.po.SpecificationPO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface SpecificationAPI {

    @GetMapping("groups/{cid}")
    SpecificationPO getSpecification(@PathVariable("cid") Long cid);
}
