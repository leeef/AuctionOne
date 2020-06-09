package com.hlnwl.auction.bean;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/3 17:50
 * 描述：
 */
public class NoDataBean {


    /**
     * status : 1
     * msg : 下单成功
     * data : []
     */

    private String status;
    private String msg;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
