package com.hlnwl.auction.bean.goods;

import java.util.UUID;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/25 17:35
 * 描述：
 */
public class SpecBean {
    private String name;
    private String content;


    public SpecBean(String name, String content) {
        this.name = name;
        this.content = content;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
