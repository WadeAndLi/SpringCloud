package com.wade.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wade.common.ExceptionEnum;
import com.wade.common.FlyException;
import com.wade.common.MQConstant;
import com.wade.common.PageResult;
import com.wade.dto.SpuDTO;
import com.wade.mapper.SkuMapper;
import com.wade.mapper.SpuDetailMapper;
import com.wade.mapper.SpuMapper;
import com.wade.mapper.StockMapper;
import com.wade.po.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.entity.Example;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

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

    @Transactional
    public void insertGoods(SpuPO spuPO) {
        // add spu
        spuPO.setCreateTime(LocalDate.now());
        spuPO.setId(null);
        spuPO.setLastUpdateTime(LocalDate.now());
        spuPO.setSaleable(true);
        spuPO.setValid(false);
        int count = spuMapper.insert(spuPO);
        if (count != 1) {
            throw new FlyException(ExceptionEnum.ERROR_TO_ADD_GOODS);
        }

        // add spu detail
        SpuDetailPO spuDetailPO = spuPO.getSpuDetail();
        spuDetailPO.setSpuId(spuPO.getId());
        spuDetailMapper.insert(spuDetailPO);

        List<StockPO> stockPOList = new ArrayList<>();
        // add sku list
        List<SkuPO> skuPOList = spuPO.getSkus();
        for (SkuPO skuPO : skuPOList) {
            skuPO.setSpuId(spuPO.getId());
            skuPO.setCreateTime(LocalDate.now());
            skuPO.setLastUpdateTime(LocalDate.now());
            if(skuMapper.insert(skuPO) != 1) {
                throw new FlyException(ExceptionEnum.ERROR_TO_ADD_GOODS);
            }
            StockPO stockPO = new StockPO();
            stockPO.setSkuId(skuPO.getId());
            stockPO.setStock(skuPO.getStock());
            stockPOList.add(stockPO);
        }

        stockMapper.insertList(stockPOList);

        //发送MQ消息
        amqpTemplate.convertAndSend(MQConstant.GOODS_INSERT, spuPO.getId());
    }

    public SpuDTO getSpuById(Long spuId) {
        SpuDTO spuDTO = new SpuDTO();
        SpuPO spuPO = spuMapper.selectByPrimaryKey(spuId);
        if (ObjectUtils.isEmpty(spuPO)) {
            throw new FlyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        spuDTO.setSpu(spuPO);
        spuDTO.setSpuDetail(spuDetailMapper.selectByPrimaryKey(spuId));
        SkuPO skuPO = new SkuPO();
        skuPO.setSpuId(spuId);
        List<SkuPO> skuList = skuMapper.select(skuPO);
        for(SkuPO item : skuList) {
            item.setStock(stockMapper.selectByPrimaryKey(item.getId()).getStock());
        }
        spuDTO.setSkus(skuList);
        return spuDTO;
    }

    public SpuPO getOneSpu(Long id) {
        SpuPO spu = spuMapper.selectByPrimaryKey(id);
        if (spu == null) {
            throw new FlyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        return spu;
    }
}