package com.hlnwl.auction.bean.category;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/24 15:32
 * 描述：
 */
public class CategoryErjiBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"7","name":"书法","icon":"http://192.168.0.9:8060/upload/20190919/8f3c0c3aed95cafa46685eeda8bccb96.jpg"}]
     */

    private String status;
    private String msg;
    private List<CategoryErji> data;

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

    public List<CategoryErji> getData() {
        return data;
    }

    public void setData(List<CategoryErji> data) {
        this.data = data;
    }


}
