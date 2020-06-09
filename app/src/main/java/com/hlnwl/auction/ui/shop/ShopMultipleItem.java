package com.hlnwl.auction.ui.shop;

import com.hlnwl.auction.bean.shop.ShopListBean;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 14:20
 * 描述：
 */
public class ShopMultipleItem {
    public static final int TOP = 1;
    public static final int IMG = 2;

    public int itemType;
    private String url;
    private ShopListBean.DataBean data;

    public ShopMultipleItem(int itemType, String url) {
        this.itemType = itemType;
        this.url = url;
    }

    public ShopMultipleItem(int itemType, ShopListBean.DataBean data) {
        this.itemType = itemType;
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ShopListBean.DataBean getData() {
        return data;
    }

    public void setData(ShopListBean.DataBean data) {
        this.data = data;
    }
}
