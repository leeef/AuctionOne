package com.hlnwl.auction.bean.shop;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 15:58
 * 描述：
 */
public class ShopComment {

    /**
     * id : 1
     * des : 写的真棒
     * icon : ["http://192.168.0.9:8060/uploads/2019929/1569719718_256064.jpg","http://192.168.0.9:8060/uploads/2019929/1569719718_100690.jpg"]
     * score : 1
     * addtime : 2019-09-29 09:15:18
     * face_img : http://192.168.0.9:8060/uploads/20190822/3d153943002f4848674d6c26be33b2bf.jpg
     * theme : 黑土
     */

    private String id;
    private String des;
    private String score;
    private String addtime;
    private String face_img;
    private String theme;
    private List<String> icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getFace_img() {
        return face_img;
    }

    public void setFace_img(String face_img) {
        this.face_img = face_img;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<String> getIcon() {
        return icon;
    }

    public void setIcon(List<String> icon) {
        this.icon = icon;
    }
}
