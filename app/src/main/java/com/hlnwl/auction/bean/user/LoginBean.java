package com.hlnwl.auction.bean.user;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/21 08:54
 * 描述：
 */
public class LoginBean {

    /**
     * status : 1
     * msg : 登录成功
     * data : [{"id":"1","mobile":"15812345678","theme":"黑土","face_img":"http://192.168.0.9:8060/uploads/20190822/3d153943002f4848674d6c26be33b2bf.jpg","oid":"0","realname":"黑土","identity":"","sex":"1","address":"河南省 郑州市","u_money":"4551.79","status":"1","addtime":"1551160534","pro_url":"http://192.168.0.9:8060/index/entran/reg?promo_code=15812345678","chant":"2","token":"0013ae8390a6060ab2d5f15da5e8a981"}]
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
        /**
         * id : 1
         * mobile : 15812345678
         * theme : 黑土
         * face_img : http://192.168.0.9:8060/uploads/20190822/3d153943002f4848674d6c26be33b2bf.jpg
         * oid : 0
         * realname : 黑土
         * identity :
         * sex : 1
         * address : 河南省 郑州市
         * u_money : 4551.79
         * status : 1
         * addtime : 1551160534
         * pro_url : http://192.168.0.9:8060/index/entran/reg?promo_code=15812345678
         * chant : 2
         * token : 0013ae8390a6060ab2d5f15da5e8a981
         */

        private String id;
        private String mobile;
        private String theme;
        private String face_img;
        private String oid;
        private String realname;
        private String identity;
        private String sex;
        private String address;
        private String u_money;
        private String status;
        private String addtime;
        private String chant;
        private String token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getFace_img() {
            return face_img;
        }

        public void setFace_img(String face_img) {
            this.face_img = face_img;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getU_money() {
            return u_money;
        }

        public void setU_money(String u_money) {
            this.u_money = u_money;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getChant() {
            return chant;
        }

        public void setChant(String chant) {
            this.chant = chant;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
