package com.hlnwl.auction.ui.store;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/6/25 13:46
 */
public class BaseHolderBean implements MultiItemEntity {
    public int viewType;

    public BaseHolderBean() {
    }

    public BaseHolderBean(int viewType) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemType() {
        return viewType;
    }
}
