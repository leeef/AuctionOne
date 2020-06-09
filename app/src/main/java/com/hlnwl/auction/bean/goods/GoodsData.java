package com.hlnwl.auction.bean.goods;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/21 14:03
 * 描述：
 */
public class GoodsData {
    /**
     * id : 1
     * name : 欧阳中石原稿手写真迹 并 权威机构鉴定
     * low_price : 20.00
     * pic : http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg
     * genre : 1
     * put_count : 4
     */

    private String id;
    private String name;
    private String low_price;
    private String pic;
    private String genre;
    private String put_count;

    private String barg;

    public String getBarg() {
        return barg;
    }

    public void setBarg(String barg) {
        this.barg = barg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLow_price() {
        return low_price;
    }

    public void setLow_price(String low_price) {
        this.low_price = low_price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPut_count() {
        return put_count;
    }

    public void setPut_count(String put_count) {
        this.put_count = put_count;
    }
}
