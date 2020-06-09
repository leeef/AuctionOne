package com.hlnwl.auction.message;

/**
 * 版权：XXX公司 版权所有
 * 作者：yougui
 * 版本：1.0
 * 创建日期：2018/12/25
 * 描述：获取登录信息message
 */
public class StopRefreshMessage {
    private String stopRefresh;

    public StopRefreshMessage(String stopRefresh) {
        this.stopRefresh = stopRefresh;
    }

    public String getStopRefresh() {
        return stopRefresh;
    }

    public void setStopRefresh(String stopRefresh) {
        this.stopRefresh = stopRefresh;
    }
}
