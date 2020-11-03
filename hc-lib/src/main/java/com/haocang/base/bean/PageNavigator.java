package com.haocang.base.bean;

import com.haocang.base.base.IBasePageView;

/**
 * PageNavigator
 * Created by mingzhenli on 2017/12/11.
 */
public class PageNavigator {
    private Page page = new Page(1, 10, 1);

    public boolean doPage(boolean reload, IBasePageView iBaseView) {
        if (reload) {
            page.setPageNo(1);
        } else {
            page.setPageNo(page.getPageNo() + 1);
            if (page.getPageNo() > page.getPageCount()) {
                iBaseView.onAllPageLoaded();
                return false;
            }
        }
        return true;
    }

    public int getPageNo() {
        return page.getPageNo();
    }

    public int getPageSize() {
        return page.getPageSize();
    }

    public void setPage(Page page) {
        this.page = page;
    }
}

