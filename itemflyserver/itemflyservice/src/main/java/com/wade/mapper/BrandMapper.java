package com.wade.mapper;

import com.wade.po.BrandPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<BrandPO>{


    @Select("SELECT tb.* FROM tb_brand tb " +
            "LEFT JOIN tb_category_brand tcb " +
            "ON tb.id = tcb.brand_id " +
            "WHERE tcb.category_id = #{cid}")
    List<BrandPO> getBrandById(@Param("cid")Long id);
}
