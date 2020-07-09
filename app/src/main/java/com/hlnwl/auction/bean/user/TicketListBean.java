package com.hlnwl.auction.bean.user;

import java.util.List;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/7/9 11:24
 */
public class TicketListBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"sumcoupon":"","coupon":[{"id":"6","uid":"1368","name":"新用户注册","price":"20","status":"0","type":"1","addtime":"1594196499","endtime":"1596788499"},{"id":"4","uid":"1368","name":"自营商城消费赠送","price":"18","status":"0","type":"1","addtime":"1594178366","endtime":"1596770366"}]}]
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
         * sumcoupon :
         * coupon : [{"id":"6","uid":"1368","name":"新用户注册","price":"20","status":"0","type":"1","addtime":"1594196499","endtime":"1596788499"},{"id":"4","uid":"1368","name":"自营商城消费赠送","price":"18","status":"0","type":"1","addtime":"1594178366","endtime":"1596770366"}]
         */

        private String sumcoupon;
        private List<CouponBean> coupon;

        public String getSumcoupon() {
            return sumcoupon;
        }

        public void setSumcoupon(String sumcoupon) {
            this.sumcoupon = sumcoupon;
        }

        public List<CouponBean> getCoupon() {
            return coupon;
        }

        public void setCoupon(List<CouponBean> coupon) {
            this.coupon = coupon;
        }

        public static class CouponBean {
            /**
             * id : 6
             * uid : 1368
             * name : 新用户注册
             * price : 20
             * status : 0
             * type : 1
             * addtime : 1594196499
             * endtime : 1596788499
             */

            private String id;
            private String uid;
            private String name;
            private String price;
            private String status;
            private String type;
            private String addtime;
            private String endtime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }
        }
    }
}
