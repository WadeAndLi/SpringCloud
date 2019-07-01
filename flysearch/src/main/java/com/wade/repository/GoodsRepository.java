package com.wade.repository;

import com.wade.po.SearchPO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsRepository extends ElasticsearchRepository<SearchPO, Long> {
}
