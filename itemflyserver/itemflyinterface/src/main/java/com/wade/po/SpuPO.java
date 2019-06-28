package com.wade.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.List;

@Table(name = "tb_spu")
public class SpuPO {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String title;
    private String subTitle;
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private Long brandId;
    private Boolean saleable;
    private Boolean valid;
    private LocalDate createTime;

    @JsonIgnore
    private LocalDate lastUpdateTime;

    @Transient
    private String cname;

    @Transient
    private String bname;

    @Transient
    private List<SkuPO> skus;

    @Transient
    private SpuDetailPO spuDetail;


    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Long getCid1() {
        return cid1;
    }

    public void setCid1(Long cid1) {
        this.cid1 = cid1;
    }

    public Long getCid2() {
        return cid2;
    }

    public void setCid2(Long cid2) {
        this.cid2 = cid2;
    }

    public Long getCid3() {
        return cid3;
    }

    public void setCid3(Long cid3) {
        this.cid3 = cid3;
    }

    public Boolean getSaleable() {
        return saleable;
    }

    public void setSaleable(Boolean saleable) {
        this.saleable = saleable;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDate lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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
