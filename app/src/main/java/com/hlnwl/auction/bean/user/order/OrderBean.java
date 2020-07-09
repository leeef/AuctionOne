package com.hlnwl.auction.bean.user.order;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 11:26
 * 描述：
 */
public class OrderBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"1","gid":"1","gname":"欧阳中石原稿手写真迹 并 权威机构鉴定","gpic":"http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg","price":"140.00","order_sn":"20190928102821801312","status":"1","name":"嘻嘻嘻嘻嘻","pic":"http://192.168.0.9:8060/uploads/2019925/1569406061_346857.jpg","genre":"1"}]
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
         * gid : 1
         * gname : 欧阳中石原稿手写真迹 并 权威机构鉴定
         * gpic : http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg
         * price : 140.00
         * order_sn : 20190928102821801312
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
        private String order_sn;
        private String status;
        private String name;
        private String pic;
        private String genre;
        /**
         * money : 588.00
         * total : 388.00
         * num : 1
         * addtime : 1594261699
         */

        private String money;
        private String total;
        private String num;
        private String addtime;

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

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
