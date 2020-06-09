package com.hlnwl.auction.bean.home;

import java.util.List;

/**
 * 新手上路
 */
public class NewHand {


    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"6","title":"新手上路","content":"<p style=\"text-align: center;\"><strong>新手上路<\/strong><br/><\/p><p><strong>&nbsp;&nbsp;&nbsp;&nbsp;<\/strong>这是是新手上路。<strong><br/><\/strong><\/p><p style=\"text-align: center;\"><img src=\"/upload/image/20191129/1574990182.jpeg\" title=\"1574990182.jpeg\" alt=\"20150312181134_aLAWY.jpeg\" width=\"720\" height=\"1733\"/><\/p><p><br/><\/p><p><br/><\/p>"}]
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
         * id : 6
         * title : 新手上路
         * content : <p style="text-align: center;"><strong>新手上路</strong><br/></p><p><strong>&nbsp;&nbsp;&nbsp;&nbsp;</strong>这是是新手上路。<strong><br/></strong></p><p style="text-align: center;"><img src="/upload/image/20191129/1574990182.jpeg" title="1574990182.jpeg" alt="20150312181134_aLAWY.jpeg" width="720" height="1733"/></p><p><br/></p><p><br/></p>
         */

        private String id;
        private String title;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
