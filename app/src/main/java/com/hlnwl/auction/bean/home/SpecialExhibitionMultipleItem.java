package com.hlnwl.auction.bean.home;

import com.hlnwl.auction.bean.shop.ShopListBean;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 14:20
 * 描述：
 */
public class SpecialExhibitionMultipleItem {
    public static final int TOP = 1;
    public static final int IMG = 2;
    public static final int BANNER = 3;

    public int itemType;

    private String url;


    private List<SpecialExhibitionBean.DataBean.LunBean> banner;

    private SpecialExhibitionBean.DataBean.CateBean cateBeanList;

    private SpecialExhibitionBean.DataBean.CateBean.GoodListBean goodListBeanList;



    public SpecialExhibitionMultipleItem(int itemType, String url) {
        this.itemType = itemType;
        this.url = url;
    }

    public SpecialExhibitionMultipleItem(int itemType,
                                         List<SpecialExhibitionBean.DataBean.LunBean> bannerList,String msg) {
        this.itemType = itemType;
        this.banner = bannerList;
    }

    public SpecialExhibitionMultipleItem(int itemType,
                                         SpecialExhibitionBean.DataBean.CateBean cateBeanList,int msg) {
        this.itemType = itemType;
        this.cateBeanList = cateBeanList;
    }
    public SpecialExhibitionMultipleItem(int itemType,
                                         SpecialExhibitionBean.DataBean.CateBean.GoodListBean
                                                 goodListBeanList) {
        this.itemType = itemType;
        this.goodListBeanList = goodListBeanList;
    }
    public List<SpecialExhibitionBean.DataBean.LunBean> getBanner() {
        return banner;
    }

    public void setBanner(List<SpecialExhibitionBean.DataBean.LunBean> banner) {
        this.banner = banner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SpecialExhibitionBean.DataBean.CateBean getCateBeanList() {
        return cateBeanList;
    }

    public void setCateBeanList(SpecialExhibitionBean.DataBean.CateBean cateBeanList) {
        this.cateBeanList = cateBeanList;
    }

    public SpecialExhibitionBean.DataBean.CateBean.GoodListBean getGoodListBeanList() {
        return goodListBeanList;
    }

    public void setGoodListBeanList(SpecialExhibitionBean.DataBean.CateBean.GoodListBean goodListBeanList) {
        this.goodListBeanList = goodListBeanList;
    }
}
