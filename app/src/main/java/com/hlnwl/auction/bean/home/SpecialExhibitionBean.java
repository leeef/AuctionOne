package com.hlnwl.auction.bean.home;

import java.util.List;

public class SpecialExhibitionBean {


    /**
     * status : 1
     * msg : 成功
     * data : [{"lun":[{"icon":"http://www.yicang123.com/upload/20200103/42458a20e005aa813e466225cf321ec5.jpg"}],"cate":[{"id":"51","name":"瓷器专场","eng_name":"Special porcelain","icon":"http://www.yicang123.com/upload/20191231/56fa1e204ccbcee5d8c4ddb9846986bd.jpg","good_list":[{"id":"111","name":"清仿粉彩花卉人物盘","low_price":"0.00","pic":"http://www.yicang123.com/goods/20191019/1571442925_156485.jpg","genre":"3","put_count":"0"}]}]}]
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
        private List<LunBean> lun;
        private List<CateBean> cate;

        public List<LunBean> getLun() {
            return lun;
        }

        public void setLun(List<LunBean> lun) {
            this.lun = lun;
        }

        public List<CateBean> getCate() {
            return cate;
        }

        public void setCate(List<CateBean> cate) {
            this.cate = cate;
        }

        public static class LunBean {
            /**
             * icon : http://www.yicang123.com/upload/20200103/42458a20e005aa813e466225cf321ec5.jpg
             */

            private String icon;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }

        public static class CateBean {
            /**
             * id : 51
             * name : 瓷器专场
             * eng_name : Special porcelain
             * icon : http://www.yicang123.com/upload/20191231/56fa1e204ccbcee5d8c4ddb9846986bd.jpg
             * good_list : [{"id":"111","name":"清仿粉彩花卉人物盘","low_price":"0.00","pic":"http://www.yicang123.com/goods/20191019/1571442925_156485.jpg","genre":"3","put_count":"0"}]
             */

            private String id;
            private String name;
            private String eng_name;
            private String icon;
            private List<GoodListBean> good_list;

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

            public String getEng_name() {
                return eng_name;
            }

            public void setEng_name(String eng_name) {
                this.eng_name = eng_name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public List<GoodListBean> getGood_list() {
                return good_list;
            }

            public void setGood_list(List<GoodListBean> good_list) {
                this.good_list = good_list;
            }

            public static class GoodListBean {
                /**
                 * id : 111
                 * name : 清仿粉彩花卉人物盘
                 * low_price : 0.00
                 * pic : http://www.yicang123.com/goods/20191019/1571442925_156485.jpg
                 * genre : 3
                 * put_count : 0
                 */

                private String id;
                private String name;
                private String low_price;
                private String pic;
                private String genre;
                private String put_count;

                private String barg;

                public String getBarg() {
                    return barg;
                }

                public void setBarg(String barg) {
                    this.barg = barg;
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

                public String getLow_price() {
                    return low_price;
                }

                public void setLow_price(String low_price) {
                    this.low_price = low_price;
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

                public String getPut_count() {
                    return put_count;
                }

                public void setPut_count(String put_count) {
                    this.put_count = put_count;
                }
            }
        }
    }
}
