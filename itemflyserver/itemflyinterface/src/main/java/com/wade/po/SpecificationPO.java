package com.wade.po;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "tb_specification")
public class SpecificationPO {

    @Column(name = "category_id")
    private Long categoryId;
    
    @Column(name = "specifications")
    private String specifications;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
}
