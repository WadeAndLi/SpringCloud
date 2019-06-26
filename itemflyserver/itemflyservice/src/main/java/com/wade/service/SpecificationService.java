package com.wade.service;

import com.wade.common.ExceptionEnum;
import com.wade.common.FlyException;
import com.wade.mapper.SpecificationMapper;
import com.wade.po.SpecificationPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;


    public SpecificationPO getSpecifications(Long cid) {
        SpecificationPO specificationPO = new SpecificationPO();
        specificationPO.setCategoryId(cid);
        SpecificationPO result = specificationMapper.selectOne(specificationPO);
        if(ObjectUtils.isEmpty(result)) {
            throw new FlyException(ExceptionEnum.SPECIFICATION_NOT_FOUND);
        }
        return result;
    }
}
