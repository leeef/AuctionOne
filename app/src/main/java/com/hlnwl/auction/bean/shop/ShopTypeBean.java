package com.hlnwl.auction.bean.shop;

import java.util.List;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/6/11 10:14
 */
public class ShopTypeBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"/index/merchant/shopselect":"","lang":"1","userid":"1334","shop":[{"id":"86","pid":"44","name":"全球区店铺","field":"global","value":"150"},{"id":"87","pid":"44","name":"中国区店铺","field":"china","value":"100"}]}]
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
        private String lang;
        private String userid;
        private String coupon;
        private List<ShopBean> shop;

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
            this.coupon = coupon;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public List<ShopBean> getShop() {
            return shop;
        }

        public void setShop(List<ShopBean> shop) {
            this.shop = shop;
        }

        public static class ShopBean {
            /**
             * id : 86
             * pid : 44
             * name : 全球区店铺
             * field : global
             * value : 150
             */

            private String id;
            private String pid;
            private String name;
            private String field;
            private String value;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getField() {
                return field;
            }

            public void setField(String field) {
                this.field = field;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
