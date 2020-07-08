package com.hlnwl.auction.bean.goods;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/21 08:46
 * 描述：
 */
public class GoodsDetailBean {


    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"1","name":"欧阳中石原稿手写真迹 并 权威机构鉴定","genre":"1","price":"20.00","low_price":"20.00","bid_price":"30.00","pic":["http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg","http://192.168.0.9:8060/upload/20190919/2ff7a585c784cf3cf784615591d498c0.jpg"],"content":"<p style=\"white-space: normal; text-align: center;\">《渔父》<\/p><p style=\"white-space: normal; text-align: center;\">浪花有意千里雪，桃花无言一队春。<br/><\/p><p style=\"white-space: normal; text-align: center;\">一壶酒，一竿纶，世上如侬有几人。<\/p>","endtime":"1569081600","sid":"1","sname":"五谷鱼粉","spic":"http://192.168.0.9:8060/upload/20190720/c1de769c9636e8e7aba52916ed710535.jpg","sphone":"15812345678","sgenre":"1","offer":"1"}]
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
         * name : 欧阳中石原稿手写真迹 并 权威机构鉴定
         * genre : 1
         * price : 20.00
         * low_price : 20.00
         * bid_price : 30.00
         * pic : ["http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg","http://192.168.0.9:8060/upload/20190919/2ff7a585c784cf3cf784615591d498c0.jpg"]
         * content : <p style="white-space: normal; text-align: center;">《渔父》</p><p style="white-space: normal; text-align: center;">浪花有意千里雪，桃花无言一队春。<br/></p><p style="white-space: normal; text-align: center;">一壶酒，一竿纶，世上如侬有几人。</p>
         * endtime : 1569081600
         * sid : 1
         * sname : 五谷鱼粉
         * spic : http://192.168.0.9:8060/upload/20190720/c1de769c9636e8e7aba52916ed710535.jpg
         * sphone : 15812345678
         * sgenre : 1
         * offer : 1
         */

        private String id;
        private String name;
        private String genre;
        private String price;
        private String low_price;
        private String bid_price;
        private String content;
        private long endtime;
        private String sid;
        private String sname;
        private String spic;
        private String sphone;
        private String sgenre;
        private String offer;
        private List<String> pic;
        private List<GoodsAttr> speci;
        private String views;
        private String bang;
        private String is_bid;
        private String status;
        private String give;
        /**
         * cid : 14
         * money : 588.00
         * keywords :
         * images :
         * sold_count : 0
         * is_on : 1
         * sort : 0
         * lang : 0
         * addtime : 1593517032
         * pertime : 0
         */

        private String cid;
        private String money;
        private String keywords;
        private String images;
        private String sold_count;
        private String is_on;
        private String sort;
        private String lang;
        private String addtime;
        private String pertime;


        public String getGive() {
            return give;
        }

        public void setGive(String give) {
            this.give = give;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
        }

        public String getBang() {
            return bang;
        }

        public void setBang(String bang) {
            this.bang = bang;
        }

        public String getIs_bid() {
            return is_bid;
        }

        public void setIs_bid(String is_bid) {
            this.is_bid = is_bid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

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

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
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

        public long getEndtime() {
            return endtime;
        }

        public void setEndtime(long endtime) {
            this.endtime = endtime;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getSpic() {
            return spic;
        }

        public void setSpic(String spic) {
            this.spic = spic;
        }

        public String getSphone() {
            return sphone;
        }

        public void setSphone(String sphone) {
            this.sphone = sphone;
        }

        public String getSgenre() {
            return sgenre;
        }

        public void setSgenre(String sgenre) {
            this.sgenre = sgenre;
        }

        public String getOffer() {
            return offer;
        }

        public void setOffer(String offer) {
            this.offer = offer;
        }

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }

        public List<GoodsAttr> getSpeci() {
            return speci;
        }

        public void setSpeci(List<GoodsAttr> speci) {
            this.speci = speci;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getSold_count() {
            return sold_count;
        }

        public void setSold_count(String sold_count) {
            this.sold_count = sold_count;
        }

        public String getIs_on() {
            return is_on;
        }

        public void setIs_on(String is_on) {
            this.is_on = is_on;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getPertime() {
            return pertime;
        }

        public void setPertime(String pertime) {
            this.pertime = pertime;
        }

        public static class GoodsAttr {
            private String sname;
            private String sprice;

            public String getSname() {
                return sname;
            }

            public void setSname(String sname) {
                this.sname = sname;
            }

            public String getSprice() {
                return sprice;
            }

            public void setSprice(String sprice) {
                this.sprice = sprice;
            }

            @Override
            public String toString() {
                return "GoodsAttr{" +
                        "sname='" + sname + '\'' +
                        ", sprice='" + sprice + '\'' +
                        '}';
            }
        }
    }


}
