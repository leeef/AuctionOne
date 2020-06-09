package com.hlnwl.auction.bean.home;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/30 15:34
 * 描述：
 */
public class NewsBean {
    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"3","title":"公告：京东方","add_time":"2019-05-14 18:08:59","link":"http://192.168.0.9:8060/index/index/news?id=3"},{"id":"2","title":"内测","add_time":"2019-01-28 17:00:38","link":"http://192.168.0.9:8060/index/index/news?id=2"}]
     */

    private String status;
    private String msg;
    private List<NewsData> data;

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

    public List<NewsData> getData() {
        return data;
    }

    public void setData(List<NewsData> data) {
        this.data = data;
    }




}
