package com.hlnwl.auction.bean.goods;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/21 10:15
 * 描述：
 */
public class GoodsCategoryBean {


    /**
     * status : 1
     * msg : 成功
     * data : [{"cate":[{"id":"6","name":"和田玉"},{"id":"7","name":"书法"}],"list":[{"id":"1","name":"欧阳中石原稿手写真迹 并 权威机构鉴定","low_price":"20.00","pic":"http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg","genre":"1","put_count":"4"}]}]
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
        private List<CateBean> cate;
        private List<GoodsData> list;
        private List<BannerData> lun;

        public List<BannerData> getLun() {
            return lun;
        }

        public void setLun(List<BannerData> lun) {
            this.lun = lun;
        }

        public List<CateBean> getCate() {
            return cate;
        }

        public void setCate(List<CateBean> cate) {
            this.cate = cate;
        }

        public List<GoodsData> getList() {
            return list;
        }

        public void setList(List<GoodsData> list) {
            this.list = list;
        }


        public static class CateBean {
            /**
             * id : 6
             * name : 和田玉
             */

            private String id;
            private String name;

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


        }

        public class BannerData{
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
