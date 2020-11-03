package com.haocang.waterlink.self.bean;

import java.util.List;

public class FollowEntity {
    private List<FolloContentEntity> items;
    private int deleteItemCount;

    public List<FolloContentEntity> getItems() {
        return items;
    }

    public void setItems(List<FolloContentEntity> items) {
        this.items = items;
    }

    public int getDeleteItemCount() {
        return deleteItemCount;
    }

    public void setDeleteItemCount(int deleteItemCount) {
        this.deleteItemCount = deleteItemCount;
    }
}
