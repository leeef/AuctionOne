package com.hlnwl.auction.utils.http;

import android.util.Log;

import com.bakerj.rxretrohttp.bean.IApiResult;
import com.blankj.utilcode.util.ToastUtils;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/4 11:40
 * 描述：
 */
public class MyApiResult<T> implements IApiResult<T> {
    private int status;
    private T data;
    private String msg;

    @Override
    public boolean isSuccess() {
        return status == 1;
    }

    @Override
    public T getData() {
        Log.e("09201412",data.toString());
        return data;
    }

    @Override
    public String getResultMsg() {
        return msg;
    }

    @Override
    public String getResultCode() {
        return String.valueOf(status);
    }

    @Override
    public String getDataField() {
        return msg + status;
    }

    public int getRet() {
        return status;
    }

    public void setRet(int ret) {
        this.status = ret;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
