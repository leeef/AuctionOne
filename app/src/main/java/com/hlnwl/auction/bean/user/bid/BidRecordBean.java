package com.hlnwl.auction.bean.user.bid;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/28 17:14
 * 描述：
 */
public class BidRecordBean {


    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"7","gid":"9","gname":"华为 Mate 30 Pro（8GB/256GB / 全网通 / 5G 版 / 玻璃版）","gpic":"http://192.168.0.9:8060/upload/20190921/733d7d0916d5a550fc6ac187be6e7d6b.jpg","price":"6110.00","margin":"10000.00","order_sn":"20190928163403466339","status":"1","name":"嘻嘻嘻嘻嘻","pic":"http://192.168.0.9:8060/uploads/2019925/1569406061_346857.jpg","genre":"1"}]
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
         * id : 7
         * gid : 9
         * gname : 华为 Mate 30 Pro（8GB/256GB / 全网通 / 5G 版 / 玻璃版）
         * gpic : http://192.168.0.9:8060/upload/20190921/733d7d0916d5a550fc6ac187be6e7d6b.jpg
         * price : 6110.00
         * margin : 10000.00
         * order_sn : 20190928163403466339
         * status : 1
         * name : 嘻嘻嘻嘻嘻
         * pic : http://192.168.0.9:8060/uploads/2019925/1569406061_346857.jpg
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
        private String name;
        private String pic;
        private String genre;
        private String endtime;

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    }
}
