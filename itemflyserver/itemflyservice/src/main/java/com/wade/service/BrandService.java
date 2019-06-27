package com.wade.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wade.common.ExceptionEnum;
import com.wade.common.FlyException;
import com.wade.common.PageResult;
import com.wade.mapper.BrandMapper;
import com.wade.po.BrandPO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class  BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public PageResult<BrandPO> queryBrand(int page, String key, int rows, String sortBy, boolean desc) {
        PageHelper.startPage(page, rows);

        Example example = new Example(BrandPO.class);
        if(StringUtils.isNotBlank(key)) {
            example.createCriteria().orLike("name", "%" + key + "%")
                .orEqualTo("letter", key);
        }

        if(StringUtils.isNotBlank(sortBy)) {
            StringBuilder builder = new StringBuilder(sortBy);
            builder.append(desc ? " DESC" : " ASC");
            example.setOrderByClause(String.valueOf(builder));
        }

        List<BrandPO> resultList = brandMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(resultList)) {
            throw new FlyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        PageInfo<BrandPO> result = new PageInfo<>(resultList);
        return new PageResult<>((int) result.getTotal(), resultList);
    }

    BrandPO getBrand(Long id) {
        BrandPO result = brandMapper.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(result)) {
            throw new FlyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return result;
    }

    public List<BrandPO> queryBrandById(Long cid) {
        List<BrandPO> resultList = brandMapper.getBrandById(cid);
        if (CollectionUtils.isEmpty(resultList)) {
            throw new FlyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return resultList;
    }
}
