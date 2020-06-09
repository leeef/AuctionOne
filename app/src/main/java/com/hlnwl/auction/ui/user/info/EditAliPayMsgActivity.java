package com.hlnwl.auction.ui.user.info;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flod.loadingbutton.LoadingButton;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.NoDataBean;
import com.hlnwl.auction.message.AddressMessage;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.my.PhoneUtil;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.WaitDialog;
import com.hlnwl.auction.view.timecount.TimeCount;
import com.hlnwl.auction.view.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/27 17:37
 * 描述：
 */
public class EditAliPayMsgActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.alipay_msg_realname)
    ClearEditText mAlipayMsgRealname;
    @BindView(R.id.alipay_msg_get_account)
    ClearEditText mAlipayMsgGetAccount;
    @BindView(R.id.alipay_msg_ver_code)
    ClearEditText mAlipayMsgVerCode;
    @BindView(R.id.alipay_msg_get_ver_code)
    TextView mAlipayMsgGetVerCode;
    @BindView(R.id.alipay_msg_confirm)
    LoadingButton mAlipayMsgConfirm;

    private TimeCount time;//倒计时
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_alipay_msg;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(StringUtils.getString(R.string.eidt_alipay_msg));
        time = new TimeCount(this, 60000, 1000, mAlipayMsgGetVerCode);
    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.alipay_msg_get_ver_code, R.id.alipay_msg_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alipay_msg_get_ver_code:
                time.start();
                break;
            case R.id.alipay_msg_confirm:
                if (StringUtils.isEmpty(mAlipayMsgRealname.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.alipay_account_name_null));
                    return;
                }

                if (StringUtils.isEmpty(mAlipayMsgGetAccount.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.alipay_account_null));
                    return;
                }
                if (!PhoneUtil.isChinaPhoneLegal(mAlipayMsgGetAccount.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.alipay_account_wrong));
                    return;
                }
                if (StringUtils.isEmpty(mAlipayMsgVerCode.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.ver_code_null));
                    return;
                }
                mAlipayMsgConfirm.start();
                RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                        .bindAliPay(SPUtils.getLanguage(),SPUtils.getUserId(), SPUtils.getToken(),
                                mAlipayMsgRealname.getText().toString().trim(),
                                mAlipayMsgGetAccount.getText().toString().trim(),
                                mAlipayMsgVerCode.getText().toString().trim()), this)
                        .subscribe(new ApiObserver<NoDataBean>() {
                                       @Override
                                       protected void success(NoDataBean data) {
                                           boolean isSuccess = MessageUtils.setCode(EditAliPayMsgActivity.this,
                                                   data.getStatus(), data.getMsg());
                                           if (!isSuccess) {
                                               mAlipayMsgConfirm.fail();
                                               return;
                                           }

                                           EventBus.getDefault().post(new LoginMessage("update"));
                                           final BaseDialog dialog = new WaitDialog.Builder(EditAliPayMsgActivity.this)
                                                   .setMessage(getResources().getString(R.string.binding)) // 消息文本可以不用填写
                                                   .show();

                                           getHandler().postDelayed(new Runnable() {
                                               @Override
                                               public void run() {
                                                   mAlipayMsgConfirm.complete();
                                                   toast(data.getMsg());
                                                   dialog.dismiss();
                                                   finish();
                                               }
                                           }, 1000);
                                       }

                                       @Override
                                       public void onError(Throwable t) {
                                           super.onError(t);
                                           mAlipayMsgConfirm.fail();
                                           toast(t.getMessage());
                                       }
                                   }
                        );
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (time!=null){
            time.cancel();
        }
    }
}
