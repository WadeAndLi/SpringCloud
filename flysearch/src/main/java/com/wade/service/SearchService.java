package com.wade.service;

import com.wade.client.BrandClient;
import com.wade.client.CategoryClient;
import com.wade.client.GoodsClient;
import com.wade.client.SpecificationClient;
import com.wade.common.ExceptionEnum;
import com.wade.common.FlyException;
import com.wade.common.JsonUtils;
import com.wade.common.PageResult;
import com.wade.dto.SpuDTO;
import com.wade.po.*;
import com.wade.repository.GoodsRepository;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    CategoryClient categoryClient;

    @Autowired
    BrandClient brandClient;

    @Autowired
    SpecificationClient specificationClient;

    @Autowired
    GoodsClient goodsClient;

    @Autowired
    GoodsRepository goodsRepository;

    @Transactional
    public SearchPO buildGoods(SpuPO spuPO) {

        Long id = spuPO.getId();
        StringBuilder content = new StringBuilder();
        content.append(spuPO.getTitle());

        List<Category> categories = categoryClient.getCategoryList(Arrays.asList(spuPO.getCid1(), spuPO.getCid2(), spuPO.getCid3()));
        if (CollectionUtils.isEmpty(categories)) {
            throw new FlyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());
        content.append(StringUtils.join(names, ""));

        BrandPO brand = brandClient.getBrand(spuPO.getBrandId());
        if (null == brand) {
            throw new FlyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        content.append(brand.getName());

        SpuDTO detailResult = goodsClient.getSpuDetailById(id);
        Set<Long> prices = new TreeSet<>();

        List<Sku> skuList = new ArrayList<>();
        for(SkuPO sku : detailResult.getSkus()) {
            prices.add(sku.getPrice());
            skuList.add(new Sku(sku.getId(), StringUtils.substringBefore(sku.getImages(), ","),
                    sku.getTitle(), sku.getPrice(), sku.getOwnSpec()));
        }

        SpuDetailPO spuDetailPO = detailResult.getSpuDetail();
        SearchPO result = new SearchPO();
        result.setBrandId(spuPO.getBrandId());
        result.setCid1(spuPO.getCid1());
        result.setCid2(spuPO.getCid2());
        result.setCid3(spuPO.getCid3());
        result.setCreateTime(spuPO.getCreateTime());
        result.setId(id);
        result.setSubTitle(spuPO.getSubTitle());
        result.setAll(content.toString());
        result.setSku(JsonUtils.serialize(skuList));
        result.setSpecs(null == spuDetailPO ? null : spuDetailPO.getSpecifications());
        result.setPrice(prices);
        return result;
    }

    public PageResult<SearchPO> getSearchResult(SearchRequest searchRequest) {
        int page = searchRequest.getPage().intValue() - 1;
        int size = searchRequest.getSize().intValue();
        String key = searchRequest.getKey();
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "subTitle", "sku"}, null));
        searchQueryBuilder.withPageable(PageRequest.of(page, size));
        searchQueryBuilder.withQuery(QueryBuilders.matchQuery("all", key));
        Page<SearchPO> result = goodsRepository.search(searchQueryBuilder.build());

        long total = result.getTotalElements();
        long totalPage = result.getTotalPages();
        List<SearchPO> searchResult = result.getContent();
        return new PageResult<>((int)total, (int)totalPage, searchResult);
    }

    public void reIndexGoods(Long spuId) {
        SpuPO spu = goodsClient.getOneSpu(spuId);
        SearchPO searchPO = buildGoods(spu);
        goodsRepository.save(searchPO);
    }
}
