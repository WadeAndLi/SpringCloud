package com.wade.po;

import javax.persistence.Id;

public class SpuDetailPO {
    @Id
    private Long spuId;
    private String description;
    private String specifications;
    private String speTemplate;
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

    public String getSpeTemplate() {
        return speTemplate;
    }

    public void setSpeTemplate(String speTemplate) {
        this.speTemplate = speTemplate;
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
