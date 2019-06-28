package com.wade.po;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_stock")
public class StockPO {

    @Id
    private Long skuId;
    private Long seckillStock;
    private Long seckillTotal;
    private Long stock;


    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSeckillStock() {
        return seckillStock;
    }

    public void setSeckillStock(Long seckillStock) {
        this.seckillStock = seckillStock;
    }

    public Long getSeckillTotal() {
        return seckillTotal;
    }

    public void setSeckillTotal(Long seckillTotal) {
        this.seckillTotal = seckillTotal;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
