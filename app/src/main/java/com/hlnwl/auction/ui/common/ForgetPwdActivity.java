package com.hlnwl.auction.ui.common;

import android.os.Bundle;
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
 * 创建日期：2019/9/16 14:31
 * 描述：
 */
public class ForgetPwdActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.forget_pwd_phone)
    ClearEditText mForgetPwdPhone;
    @BindView(R.id.forget_pwd_ver_code)
    ClearEditText mForgetPwdVerCode;
    @BindView(R.id.forget_pwd_get_ver_code)
    TextView mForgetPwdGetVerCode;
    @BindView(R.id.forget_pwd_new_pwd)
    ClearEditText mForgetPwd;
    @BindView(R.id.register_re_pwd)
    ClearEditText mForgetPwdRe;
    @BindView(R.id.regist_button)
    LoadingButton mRegistButton;

    private TimeCount time;//倒计时

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(getResources().getString(R.string.forget_pwd));
    }

    @Override
    protected void initData() {
        time = new TimeCount(this, 60000, 1000, mForgetPwdGetVerCode);
    }



    @OnClick({R.id.forget_pwd_get_ver_code, R.id.regist_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forget_pwd_get_ver_code:
                if (StringUtils.isEmpty(mForgetPwdPhone.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.phone_null));
                    return;
                }
                if (!PhoneUtil.isChinaPhoneLegal(mForgetPwdPhone.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.phone_wrong));
                    return;
                }

                getCode(mForgetPwdPhone.getText().toString().trim());
                break;
            case R.id.regist_button:
                if (StringUtils.isEmpty(mForgetPwdPhone.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.phone_null));
                    return;
                }
                if (!PhoneUtil.isChinaPhoneLegal(mForgetPwdPhone.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.phone_wrong));
                    return;
                }
                if (StringUtils.isEmpty(mForgetPwd.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.pwd_null));
                    return;
                }

                if (mForgetPwd.getText().toString().trim().length() < 6) {
                    ToastUtils.showShort(getResources().getString(R.string.pwd_less));
                    return;
                }

                if (StringUtils.isEmpty(mForgetPwdVerCode.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.ver_code_null));
                    return;
                }

                if (StringUtils.isEmpty(mForgetPwdRe.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.sure_pwd_null));
                    return;
                }
                if (!mForgetPwd.getText().toString().trim().equals(mForgetPwdRe.getText().toString().trim())){
                    ToastUtils.showShort(getResources().getString(R.string.pwd_no_same));
                    return;
                }

                resetPwd(mForgetPwdPhone.getText().toString().trim(), mForgetPwdVerCode.getText().toString().trim(),
                        mForgetPwd.getText().toString().trim(), mForgetPwdRe.getText().toString().trim());
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getCode(String phone) {

        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getVerCode(SPUtils.getLanguage(),phone, "2"), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(ForgetPwdActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }

                                   time.start();
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    /*
     * 修改密码
     * */
    private void resetPwd(String phone, String ver_code, String pwd, String pwd_sure) {

        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .forgetPwd(SPUtils.getLanguage(),phone, ver_code, pwd, pwd_sure), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(ForgetPwdActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       mRegistButton.fail();
                                       return;
                                   }


                                   final BaseDialog dialog = new WaitDialog.Builder(ForgetPwdActivity.this)
                                           .setMessage(getResources().getString(R.string.modifying)) // 消息文本可以不用填写
                                           .show();

                                   getHandler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           mRegistButton.complete();
                                           toast(data.getMsg());
                                           dialog.dismiss();
                                           finish();
                                       }
                                   }, 1000);
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   mRegistButton.fail();
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (time!=null){
            time.cancel();
        }
    }
}
