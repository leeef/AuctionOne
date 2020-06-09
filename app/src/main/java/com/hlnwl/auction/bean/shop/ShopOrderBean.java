package com.hlnwl.auction.bean.shop;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 16:14
 * 描述：
 */
public class ShopOrderBean {


    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"5","gid":"1","gname":"欧阳中石原稿手写真迹 并 权威机构鉴定","gpic":"http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg","price":"140.00","margin":"20.00","order_sn":"20190928102821801312","status":"0","genre":"1"}]
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
         * id : 5
         * gid : 1
         * gname : 欧阳中石原稿手写真迹 并 权威机构鉴定
         * gpic : http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg
         * price : 140.00
         * margin : 20.00
         * order_sn : 20190928102821801312
         * status : 0
         * genre : 1
         */

        private String id;
        private String gid;
        private String gname;
        private String gpic;
        private String price;
        private String margin;
        private String order_sn;
        private String status;
        private String genre;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getGpic() {
            return gpic;
        }

        public void setGpic(String gpic) {
            this.gpic = gpic;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMargin() {
            return margin;
        }

        public void setMargin(String margin) {
            this.margin = margin;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }
    }
}
