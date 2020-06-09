package com.hlnwl.auction.bean.shop;

import com.hlnwl.auction.bean.goods.GoodsData;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/28 16:20
 * 描述：
 */
public class ShopGoodsBean {


    /**
     * status : 1
     * msg : 成功
     * data : [{"sinfo":{"id":"1","name":"嘻嘻嘻嘻嘻","pic":"http://192.168.0.9:8060/public/uploads/2019925/1569406061_346857.jpg","genre":"1","status":"1"},"glist":[{"id":"1","name":"欧阳中石原稿手写真迹 并 权威机构鉴定","low_price":"20.00","pic":"http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg","genre":"1","put_count":"8"}],"ment":[]}]
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
         * sinfo : {"id":"1","name":"嘻嘻嘻嘻嘻","pic":"http://192.168.0.9:8060/public/uploads/2019925/1569406061_346857.jpg","genre":"1","status":"1"}
         * glist : [{"id":"1","name":"欧阳中石原稿手写真迹 并 权威机构鉴定","low_price":"20.00","pic":"http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg","genre":"1","put_count":"8"}]
         * ment : []
         */

        private List<GoodsData> glist;
        private List<ShopComment> mentlist;


        public List<GoodsData> getGlist() {
            return glist;
        }

        public void setGlist(List<GoodsData> glist) {
            this.glist = glist;
        }


        public List<ShopComment> getMentlist() {
            return mentlist;
        }

        public void setMentlist(List<ShopComment> mentlist) {
            this.mentlist = mentlist;
        }
    }
}
