package com.hlnwl.auction.bean.goods;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 17:37
 * 描述：
 */
public class GoodsListBean {
    private String status;
    private String msg;
    private List<GoodsData> data;

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

    public List<GoodsData> getData() {
        return data;
    }

    public void setData(List<GoodsData> data) {
        this.data = data;
    }
}
