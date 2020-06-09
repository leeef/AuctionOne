package com.hlnwl.auction.message;

/**
 * 版权：XXX公司 版权所有
 * 作者：yougui
 * 版本：1.0
 * 创建日期：2018/12/25
 * 描述：获取登录信息message
 */
public class PayMessage {

    private String payPwd;

    public PayMessage(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }
}
