package com.hlnwl.auction.bean.user.order;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 14:34
 * 描述：
 */
public class OrderDetailBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"1","gname":"欧阳中石原稿手写真迹 并 权威机构鉴定","gpic":"http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg","price":"140.00","addr_name":"张礼","addr_phone":"15212345678","address":"天津市天津市河西区进不挂号14","order_sn":"20190928102821801312","status":"4","paytype":"余额","addtime":"2019-09-28 14:29:25","paytime":"2019-09-28 14:29:25","sendtime":"1970-01-01 08:00:00","status_text":""}]
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
         * gname : 欧阳中石原稿手写真迹 并 权威机构鉴定
         * gpic : http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg
         * price : 140.00
         * addr_name : 张礼
         * addr_phone : 15212345678
         * address : 天津市天津市河西区进不挂号14
         * order_sn : 20190928102821801312
         * status : 4
         * paytype : 余额
         * addtime : 2019-09-28 14:29:25
         * paytime : 2019-09-28 14:29:25
         * sendtime : 1970-01-01 08:00:00
         * status_text :
         */

        private String id;
        private String gname;
        private String gpic;
        private String price;
        private String addr_name;
        private String addr_phone;
        private String address;
        private String order_sn;
        private String status;
        private String paytype;
        private String addtime;
        private String paytime;
        private String sendtime;
        private String status_text;
        private String aid;
        private String express;
        private String expressnum;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getExpress() {
            return express;
        }

        public void setExpress(String express) {
            this.express = express;
        }

        public String getExpressnum() {
            return expressnum;
        }

        public void setExpressnum(String expressnum) {
            this.expressnum = expressnum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getAddr_name() {
            return addr_name;
        }

        public void setAddr_name(String addr_name) {
            this.addr_name = addr_name;
        }

        public String getAddr_phone() {
            return addr_phone;
        }

        public void setAddr_phone(String addr_phone) {
            this.addr_phone = addr_phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getPaytime() {
            return paytime;
        }

        public void setPaytime(String paytime) {
            this.paytime = paytime;
        }

        public String getSendtime() {
            return sendtime;
        }

        public void setSendtime(String sendtime) {
            this.sendtime = sendtime;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }
    }
}
