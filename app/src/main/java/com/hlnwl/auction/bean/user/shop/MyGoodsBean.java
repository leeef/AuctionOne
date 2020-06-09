package com.hlnwl.auction.bean.user.shop;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/10/11 09:52
 * 描述：
 */
public class MyGoodsBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"1","name":"李良东 十一届国展状元 一二五六届兰亭奖 江西省书协评审委员","low_price":"10.00","pic":"http://192.168.0.9:8060/goods/2019109/1570612640_622998.jpg","genre":"1","status":"0","put_count":"0"}]
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
         * id : 1
         * name : 李良东 十一届国展状元 一二五六届兰亭奖 江西省书协评审委员
         * low_price : 10.00
         * pic : http://192.168.0.9:8060/goods/2019109/1570612640_622998.jpg
         * genre : 1
         * status : 0
         * put_count : 0
         */

        private String id;
        private String name;
        private String low_price;
        private String pic;
        private String genre;
        private String status;
        private String put_count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLow_price() {
            return low_price;
        }

        public void setLow_price(String low_price) {
            this.low_price = low_price;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPut_count() {
            return put_count;
        }

        public void setPut_count(String put_count) {
            this.put_count = put_count;
        }
    }
}
