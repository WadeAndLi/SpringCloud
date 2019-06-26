package com.wade.common;


import java.util.List;

public class PageResult<T> {

    private int total;
    private int totalPage;
    private List<T> items;

    public PageResult(int total, int totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

    public PageResult(int total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult() {}

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
