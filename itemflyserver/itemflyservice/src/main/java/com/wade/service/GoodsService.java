package com.wade.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wade.common.ExceptionEnum;
import com.wade.common.FlyException;
import com.wade.common.PageResult;
import com.wade.mapper.SpuDetailMapper;
import com.wade.mapper.SpuMapper;
import com.wade.po.Category;
import com.wade.po.SpuPO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    public PageResult<SpuPO> getSpu(int page, String key, int rows, Boolean saleable) {

        //开启pageHelper (传入页面和每页行数)
        PageHelper.startPage(page, rows);

        //构造SQL (实例Example)
        Example example = new Example(SpuPO.class);

        //使用实例的example构造条件
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }

        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }

        //构建排序
        example.setOrderByClause("last_update_time DESC");

        //获取结果
        List<SpuPO> resultList = spuMapper.selectByExample(example);

        transferName(resultList);

        //抛出异常
        if (CollectionUtils.isEmpty(resultList)) {
            throw new FlyException(ExceptionEnum.GOODS_NOT_FOUND);
        }

        //实例化 pageInfo (获取总条数)
        PageInfo<SpuPO> info = new PageInfo<>(resultList);

        //返回一个page result类
        return new PageResult<>((int) info.getTotal(), resultList);
    }

    private void transferName(List<SpuPO> resultList) {
        for(SpuPO spuPO : resultList) {
            List<Category> categories = categoryService.getCategoryById(Arrays.asList(spuPO.getCid1(), spuPO.getCid2(), spuPO.getCid3()));
            List<String> nameList = categories.stream().map(Category::getName).collect(Collectors.toList());
            spuPO.setCname(StringUtils.join(nameList, "/"));

            spuPO.setBname(brandService.getBrand(spuPO.getBrandId()).getName());
        }
    }
}
