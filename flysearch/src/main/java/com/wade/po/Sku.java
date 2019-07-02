package com.wade.po;

public class Sku {
    private Long id;
    private String image;
    private String title;
    private Long price;
    private String ownSpec;

    public Sku() {}

    public Sku(Long id, String image, String title, Long price, String ownSpec) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.price = price;
        this.ownSpec = ownSpec;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getOwnSpec() {
        return ownSpec;
    }

    public void setOwnSpec(String ownSpec) {
        this.ownSpec = ownSpec;
    }
}
