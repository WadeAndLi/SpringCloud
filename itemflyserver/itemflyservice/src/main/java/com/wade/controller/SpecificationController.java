package com.wade.controller;

import com.wade.po.SpecificationPO;
import com.wade.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<SpecificationPO> getSpecification(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(specificationService.getSpecifications(cid));
    }
}
