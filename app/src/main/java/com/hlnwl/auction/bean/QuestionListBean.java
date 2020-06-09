package com.hlnwl.auction.bean;

import java.util.List;

public class QuestionListBean {


    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"2","title":"汽车完全没有必要购买商业险？","eng_title":"There is no need to buy commercial insurance for cars.","type":"0"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
         * id : 2
         * title : 汽车完全没有必要购买商业险？
         * eng_title : There is no need to buy commercial insurance for cars.
         * type : 0
         */

        private String id;
        private String title;
        private String eng_title;
        private String type;

        private boolean isCheck;
        private int check;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEng_title() {
            return eng_title;
        }

        public void setEng_title(String eng_title) {
            this.eng_title = eng_title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
