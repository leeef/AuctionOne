package com.hlnwl.auction.bean.user.bid;

import com.hlnwl.auction.bean.user.info.AddressData;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 09:42
 * 描述：
 */
public class BidOrderBean {


    /**
     * status : 1
     * msg : 成功
     * data : [{"addinfo":[{"id":"15","theme":"张礼","phone":"15212345678","city":"天津市天津市河西区","address":"进不挂号14","xiangxi":"天津市天津市河西区 进不挂号14"}],"orinfo":[{"id":"5","gname":"欧阳中石原稿手写真迹 并 权威机构鉴定","gpic":"http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg","price":"140.00"}]}]
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
        private List<AddressData> addinfo;
        private List<OrinfoBean> orinfo;

        public List<AddressData> getAddinfo() {
            return addinfo;
        }

        public void setAddinfo(List<AddressData> addinfo) {
            this.addinfo = addinfo;
        }

        public List<OrinfoBean> getOrinfo() {
            return orinfo;
        }

        public void setOrinfo(List<OrinfoBean> orinfo) {
            this.orinfo = orinfo;
        }


        public static class OrinfoBean {
            /**
             * id : 5
             * gname : 欧阳中石原稿手写真迹 并 权威机构鉴定
             * gpic : http://192.168.0.9:8060/upload/20190919/a66665155cbc3f12eb3d4202d1e0b5e5.jpg
             * price : 140.00
             */

            private String id;
            private String gname;
            private String gpic;
            private String price;

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
        }
    }
}
