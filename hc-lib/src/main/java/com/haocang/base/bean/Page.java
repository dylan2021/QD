package com.haocang.base.bean;

/**
 * Page
 * Created by mingzhenli on 2017/12/11.
 */
public class Page {
    private int pageNo;
    private int pageSize;
    private int pageCount;

    public Page(int pageNo, int pageSize, int pageCount) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
