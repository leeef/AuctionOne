package com.hlnwl.auction.bean.user.shop;

import com.hlnwl.auction.bean.goods.SpecBean;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/10/11 11:25
 * 描述：
 */
public class GoodMsgBean {


    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"1","cid":"7","cname":"书法","name":"李良东 十一届国展状元 一二五六届兰亭奖 江西省书协评审委员","price":"10.00","low_price":"10.00","bid_price":"0.00","content":"只是测试","pic":["http://192.168.0.9:8060/goods/2019109/1570612640_622998.jpg"],"is_bid":"2","endtime":"2020-10-09 17:17:20","speci":[{"sname":"颜色","sprice":"白色"},{"sname":"重量","sprice":"0.2"}]}]
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
         * cid : 7
         * cname : 书法
         * name : 李良东 十一届国展状元 一二五六届兰亭奖 江西省书协评审委员
         * price : 10.00
         * low_price : 10.00
         * bid_price : 0.00
         * content : 只是测试
         * pic : ["http://192.168.0.9:8060/goods/2019109/1570612640_622998.jpg"]
         * is_bid : 2
         * endtime : 2020-10-09 17:17:20
         * speci : [{"sname":"颜色","sprice":"白色"},{"sname":"重量","sprice":"0.2"}]
         */

        private String id;
        private String cid;
        private String cname;
        private String name;
        private String price;
        private String low_price;
        private String bid_price;
        private String content;
        private String is_bid;
        private String endtime;
        private List<String> pic;
        private List<SpecBean> speci;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
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

        public String getLow_price() {
            return low_price;
        }

        public void setLow_price(String low_price) {
            this.low_price = low_price;
        }

        public String getBid_price() {
            return bid_price;
        }

        public void setBid_price(String bid_price) {
            this.bid_price = bid_price;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIs_bid() {
            return is_bid;
        }

        public void setIs_bid(String is_bid) {
            this.is_bid = is_bid;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }

        public List<SpecBean> getSpeci() {
            return speci;
        }

        public void setSpeci(List<SpecBean> speci) {
            this.speci = speci;
        }


    }
}
