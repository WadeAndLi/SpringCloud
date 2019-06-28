package com.wade.po;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_spu_detail")
public class SpuDetailPO {
    @Id
    private Long spuId;
    private String description;
    private String specifications;
    private String specTemplate;
    private String packingList;
    private String afterService;

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getSpecTemplate() {
        return specTemplate;
    }

    public void setSpecTemplate(String specTemplate) {
        this.specTemplate = specTemplate;
    }

    public String getPackingList() {
        return packingList;
    }

    public void setPackingList(String packingList) {
        this.packingList = packingList;
    }

    public String getAfterService() {
        return afterService;
    }

    public void setAfterService(String afterService) {
        this.afterService = afterService;
    }
}
