package com.hlnwl.auction.message;

/**
 * 版权：XXX公司 版权所有
 * 作者：yougui
 * 版本：1.0
 * 创建日期：2018/12/25
 * 描述：获取登录信息message
 */
public class PayBidMessage {
    private String upDate;

    public PayBidMessage(String upDate) {
        this.upDate = upDate;
    }

    public String getUpDate() {
        return upDate;
    }

    public void setUpDate(String upDate) {
        this.upDate = upDate;
    }
}
