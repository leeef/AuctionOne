package com.hlnwl.auction.bean.category;

import com.hlnwl.auction.bean.SelectedBean;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/24 15:42
 * 描述：
 */
public class CategoryErji extends SelectedBean {
    /**
     * id : 7
     * name : 书法
     * icon : http://192.168.0.9:8060/upload/20190919/8f3c0c3aed95cafa46685eeda8bccb96.jpg
     */

    private String id;
    private String name;
    private String icon;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
