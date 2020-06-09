package com.hlnwl.auction.bean.home;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/30 15:25
 * 描述：
 */
public class NewsData {
    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"3","title":"公告：京东方","add_time":"2019-05-14 18:08:59","link":"http://192.168.0.9:8060/index/index/news?id=3"},{"id":"2","title":"内测","add_time":"2019-01-28 17:00:38","link":"http://192.168.0.9:8060/index/index/news?id=2"}]
     */



        /**
         * id : 3
         * title : 公告：京东方
         * add_time : 2019-05-14 18:08:59
         * link : http://192.168.0.9:8060/index/index/news?id=3
         */

        private String id;
        private String title;
        private String add_time;
        private String link;

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

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }


}
