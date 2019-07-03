package com.wade.po;

public class SearchRequest {

    private static final Long DEFAULT_ROWS = 20L;
    private static final Long DEFAULT_PAGE = 1L;

    private String key;
    private Long page;
    private Long size;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getPage() {
        if(this.page == null) {
            page = DEFAULT_PAGE;
        }
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getSize() {
        return DEFAULT_ROWS;
    }
}
