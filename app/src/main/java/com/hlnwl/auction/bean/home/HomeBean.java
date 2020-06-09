package com.hlnwl.auction.bean.home;

import com.hlnwl.auction.bean.goods.GoodsData;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 09:54
 * 描述：
 */
public class HomeBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"news":[{"id":"3","title":"公告：京东方"},{"id":"2","title":"内测"}],"lunbo":[{"icon":"http://192.168.0.9:8060/upload/20190821/d33a42d5d9f6989de724b309b711fac4.jpg"},{"icon":"http://192.168.0.9:8060/upload/20190821/c645d5fa00a1d335c7141a56e5bfd26c.jpg"},{"icon":"http://192.168.0.9:8060/upload/20190821/35976df985902baee54dfa5f84e2718d.jpg"}],"reclist":[{"id":"1","name":"欧阳中石原稿手写真迹 并 权威机构鉴定","low_price":"20.00","pic":"http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg","genre":"1","put_count":"7"}]}]
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
        private List<NewsData> news;
        private List<LunboBean> lunbo;
        private List<GoodsData> reclist;

        public List<NewsData> getNews() {
            return news;
        }

        public void setNews(List<NewsData> news) {
            this.news = news;
        }

        public List<LunboBean> getLunbo() {
            return lunbo;
        }

        public void setLunbo(List<LunboBean> lunbo) {
            this.lunbo = lunbo;
        }

        public List<GoodsData> getReclist() {
            return reclist;
        }

        public void setReclist(List<GoodsData> reclist) {
            this.reclist = reclist;
        }


        public static class LunboBean {
            /**
             * icon : http://192.168.0.9:8060/upload/20190821/d33a42d5d9f6989de724b309b711fac4.jpg
             */

            private String icon;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }


    }
}
