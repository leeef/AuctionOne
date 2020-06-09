package com.hlnwl.auction.bean.shop;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 14:18
 * 描述：
 */
public class ShopListBean  {


    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"1","name":"嘻嘻嘻嘻嘻","pic":"http://192.168.0.9:8060/public/uploads/2019925/1569406061_346857.jpg","genre":"1","gdpic":["http://192.168.0.9:8060/goods/2019927/1569565958_749522.jpg","http://192.168.0.9:8060/goods/2019927/1569552087_356398.jpg","http://192.168.0.9:8060/goods/2019927/1569551957_400084.jpg"]}]
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
         * name : 嘻嘻嘻嘻嘻
         * pic : http://192.168.0.9:8060/public/uploads/2019925/1569406061_346857.jpg
         * genre : 1
         * gdpic : ["http://192.168.0.9:8060/goods/2019927/1569565958_749522.jpg","http://192.168.0.9:8060/goods/2019927/1569552087_356398.jpg","http://192.168.0.9:8060/goods/2019927/1569551957_400084.jpg"]
         */

        private String id;
        private String name;
        private String pic;
        private String genre;
        private List<String> gdpic;

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

        public List<String> getGdpic() {
            return gdpic;
        }

        public void setGdpic(List<String> gdpic) {
            this.gdpic = gdpic;
        }
    }
}
