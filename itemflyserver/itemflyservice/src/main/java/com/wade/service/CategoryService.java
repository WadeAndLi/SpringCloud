package com.wade.service;

import com.wade.common.ExceptionEnum;
import com.wade.common.FlyException;
import com.wade.mapper.CategoryMapper;
import com.wade.po.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> getCategoryList(long id) {
        Category category = new Category();
        category.setParentId(id);
        if (CollectionUtils.isEmpty(categoryMapper.select(category))) {
            throw new FlyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return categoryMapper.select(category);
    }

    List<Category> getCategoryById(List<Long> ids) {
        List<Category> resultList = categoryMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(resultList)) {
            throw new FlyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return resultList;
    }

    public List<Category> getCategoryListByIds(List<Long> ids) {
        List<Category> resultList = categoryMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(resultList)) {
            throw new FlyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return resultList;
    }
}
