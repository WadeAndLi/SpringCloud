package com.wade.dto;

import com.wade.po.SkuPO;
import com.wade.po.SpuDetailPO;
import com.wade.po.SpuPO;

import java.util.List;

public class SpuDTO {
    private SpuPO spu;
    private List<SkuPO> skus;
    private SpuDetailPO spuDetail;

    public SpuPO getSpu() {
        return spu;
    }

    public void setSpu(SpuPO spu) {
        this.spu = spu;
    }

    public List<SkuPO> getSkus() {
        return skus;
    }

    public void setSkus(List<SkuPO> skus) {
        this.skus = skus;
    }

    public SpuDetailPO getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetailPO spuDetail) {
        this.spuDetail = spuDetail;
    }
}
