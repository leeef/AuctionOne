package com.hlnwl.auction.bean.home;



import com.hlnwl.auction.bean.goods.GoodsData;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 *         版本：1.0
 *         创建日期：2018/12/22
 *         描述：主页分类
 */
public class HomeMultipleItem {
    public static final int BANNER = 1;
    public static final int SEARCH = 2;
    public static final int ICONS = 3;
    public static final int MESSAGE = 4;
    public static final int IMAGE = 5;
    public static final int CONTENT= 6;
    public static final int EMPTY= 7;

    public int itemType;
    private List<HomeBean.DataBean.LunboBean> banner;
    private List<NewsData> message;

    private GoodsData content;

    public HomeMultipleItem(int itemType) {
        this.itemType = itemType;
    }

    public HomeMultipleItem(int itemType, List<HomeBean.DataBean.LunboBean> banner) {
        this.itemType = itemType;
        this.banner = banner;
    }

    public HomeMultipleItem(int itemType, List<NewsData> message,String msg) {
        this.itemType = itemType;
        this.message = message;
    }


    public HomeMultipleItem(int itemType, GoodsData content) {
        this.itemType = itemType;
        this.content = content;
    }

    public List<HomeBean.DataBean.LunboBean> getBanner() {
        return banner;
    }

    public void setBanner(List<HomeBean.DataBean.LunboBean> banner) {
        this.banner = banner;
    }

    public List<NewsData> getMessage() {
        return message;
    }

    public void setMessage(List<NewsData> message) {
        this.message = message;
    }


    public GoodsData getContent() {
        return content;
    }

    public void setContent(GoodsData content) {
        this.content = content;
    }
}
