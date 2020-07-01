package com.hlnwl.auction.bean.user.info;

import java.io.Serializable;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/25 15:23
 * 描述：
 */
public class AddressData implements Serializable {

    /**
     * id : 25
     * theme : 黑土
     * phone : 15812345678
     * city : 河南省 郑州市 二七区
     * address : 你猜猜
     * cold : 0
     * xiangxi : 河南省 郑州市 二七区 你猜猜
     */

    private String id;
    private String theme;
    private String phone;
    private String city;
    private String address;
    private String cold;
    private String xiangxi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCold() {
        return cold;
    }

    public void setCold(String cold) {
        this.cold = cold;
    }

    public String getXiangxi() {
        return xiangxi;
    }

    public void setXiangxi(String xiangxi) {
        this.xiangxi = xiangxi;
    }
}
