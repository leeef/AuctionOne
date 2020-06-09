package com.hlnwl.auction.bean.user.shop;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/10/29 15:34
 * 描述：
 */
public class JoinBean {

    /**
     * status : 1
     * msg : 申请成功
     * data : [{"str":"appid=wxe9201dc7e3e49562&partnerid=1560749931&prepayid=wx29153454001681c1d86c9bf21814719800&package=Sign=WXPay&noncestr=8277&timestamp=1572334494&sign=4E5B725AB6D3F5FFA99D7E42647CD13F"}]
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
         * str : appid=wxe9201dc7e3e49562&partnerid=1560749931&prepayid=wx29153454001681c1d86c9bf21814719800&package=Sign=WXPay&noncestr=8277&timestamp=1572334494&sign=4E5B725AB6D3F5FFA99D7E42647CD13F
         */

        private String str;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }
}
