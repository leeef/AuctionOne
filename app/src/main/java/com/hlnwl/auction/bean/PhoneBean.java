package com.hlnwl.auction.bean;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/30 11:47
 * 描述：
 */
public class PhoneBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"kfphone":"15812345678"}]
     */

    private String status;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * kfphone : 15812345678
         */

        private String kfphone;

        public String getKfphone() {
            return kfphone;
        }

        public void setKfphone(String kfphone) {
            this.kfphone = kfphone;
        }
    }
}
