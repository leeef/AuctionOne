package com.hlnwl.auction.bean.user.info;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/27 17:27
 * 描述：
 */
public class BankMsgBean {


    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"4","name":"中国建设银行","realname":"白云","number":"3456","bank_addr":"花园路支行"}]
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
         * id : 4
         * name : 中国建设银行
         * realname : 白云
         * number : 3456
         * bank_addr : 花园路支行
         */

        private String id;
        private String name;
        private String realname;
        private String number;
        private String bank_addr;

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

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getBank_addr() {
            return bank_addr;
        }

        public void setBank_addr(String bank_addr) {
            this.bank_addr = bank_addr;
        }
    }
}
