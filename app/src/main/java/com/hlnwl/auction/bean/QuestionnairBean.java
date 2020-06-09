package com.hlnwl.auction.bean;

import java.util.List;

public class QuestionnairBean {
    /**
     * status : 1
     * msg : 成功
     * data : [{"kfphone":"15812345678"}]
     */

    private String status;
    private String msg;
    private List<PhoneBean.DataBean> data;

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

    public List<PhoneBean.DataBean> getData() {
        return data;
    }

    public void setData(List<PhoneBean.DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * kfphone : 15812345678
         */

        private int tag;
        private int check;

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public DataBean(int tag, int check) {
            this.tag = tag;
            this.check = check;
        }
    }
}
