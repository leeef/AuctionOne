package com.hlnwl.auction.bean.category;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/24 15:31
 * 描述：
 */
public class CategoryBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"5","name":"玻尿酸"}]
     */

    private String status;
    private String msg;
    private List<CategoryData> data;

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

    public List<CategoryData> getData() {
        return data;
    }

    public void setData(List<CategoryData> data) {
        this.data = data;
    }


}
