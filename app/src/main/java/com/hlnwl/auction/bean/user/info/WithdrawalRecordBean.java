package com.hlnwl.auction.bean.user.info;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 09:16
 * 描述：
 */
public class WithdrawalRecordBean {

    /**
     * status : 1
     * msg : 成功
     * data : [{"id":"8","amount":"20.00","paytype":"银行卡提现","status":"0","addtime":"2019-09-27 15:01:16","text_status":"未处理"}]
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
         * id : 8
         * amount : 20.00
         * paytype : 银行卡提现
         * status : 0
         * addtime : 2019-09-27 15:01:16
         * text_status : 未处理
         */

        private String id;
        private String amount;
        private String paytype;
        private String status;
        private String addtime;
        private String text_status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
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

        public String getText_status() {
            return text_status;
        }

        public void setText_status(String text_status) {
            this.text_status = text_status;
        }
    }
}
