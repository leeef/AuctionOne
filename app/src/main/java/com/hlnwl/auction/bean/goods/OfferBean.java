package com.hlnwl.auction.bean.goods;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/24 09:56
 * 描述：
 */
public class OfferBean {


    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"6","price":"5555.00","addtime":"2019-09-24 09:52:40","theme":"android"}]
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
         * id : 6
         * price : 5555.00
         * addtime : 2019-09-24 09:52:40
         * theme : android
         */

        private String id;
        private String price;
        private String addtime;
        private String theme;
        private String face_img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getFace_img() {
            return face_img;
        }

        public void setFace_img(String face_img) {
            this.face_img = face_img;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", price='" + price + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", theme='" + theme + '\'' +
                    '}';
        }
    }
}
