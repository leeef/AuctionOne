package com.hlnwl.auction.bean.user.info;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/25 15:22
 * 描述：
 */
public class AddressBean {


    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"25","theme":"黑土","phone":"15812345678","city":"河南省 郑州市 二七区","address":"你猜猜","cold":"0","xiangxi":"河南省 郑州市 二七区 你猜猜"}]
     */

    private String status;
    private String msg;
    private List<AddressData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<AddressData> getData() {
        return data;
    }

    public void setData(List<AddressData> data) {
        this.data = data;
    }

}
