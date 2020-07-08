package com.hlnwl.auction.bean.goods;

import java.util.List;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/7/8 16:54
 */
public class GoodListBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"3","name":"民国粉彩四系盖罐","price":"388.00","money":"588.00","pic":"http://www.yicang123.com/upload/20200630/636fc40c4068f91d73ce76568ac816d5.jpg"},{"id":"2","name":"清晚期粉彩赏瓶","price":"188.00","money":"388.00","pic":"http://www.yicang123.com/upload/20200630/913a06a696401cba6812c71e6edd2ca7.jpg"}]
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
         * id : 3
         * name : 民国粉彩四系盖罐
         * price : 388.00
         * money : 588.00
         * pic : http://www.yicang123.com/upload/20200630/636fc40c4068f91d73ce76568ac816d5.jpg
         */

        private String id;
        private String name;
        private String price;
        private String money;
        private String pic;

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
