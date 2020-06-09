package com.hlnwl.auction.ui.common;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
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
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.my.PhoneUtil;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.WaitDialog;
import com.hlnwl.auction.view.timecount.TimeCount;
import com.hlnwl.auction.view.widget.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/4/12 09:57
 * 描述：
 */
public class RegistActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.register_phone)
    ClearEditText mRegisterPhone;
    @BindView(R.id.register_ver_code)
    ClearEditText mRegisterVerCode;
    @BindView(R.id.register_get_ver_code)
    TextView mRegisterGetVerCode;
    @BindView(R.id.register_pwd)
    ClearEditText mRegisterPwd;
    @BindView(R.id.register_re_pwd)
    ClearEditText mRegisterRePwd;
    @BindView(R.id.regist_button)
    LoadingButton mRegistButton;
    @BindView(R.id.regist_xieyi)
    RadioButton mRegistXieyi;


    private TimeCount time;//倒计时

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mTitleTb.setTitle(getResources().getString(R.string.register));
        time = new TimeCount(this, 60000, 1000, mRegisterGetVerCode);
    }


    @OnClick({R.id.register_get_ver_code, R.id.regist_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_get_ver_code:
                if (StringUtils.isEmpty(mRegisterPhone.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.phone_null));
                    return;
                }
                if (!PhoneUtil.isChinaPhoneLegal(mRegisterPhone.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.phone_wrong));
                    return;
                }
                getCode(mRegisterPhone.getText().toString().trim());
                break;
            case R.id.regist_button:


                if (StringUtils.isEmpty(mRegisterPhone.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.phone_null));
                    return;
                }
                if (!PhoneUtil.isChinaPhoneLegal(mRegisterPhone.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.phone_wrong));
                    return;
                }
                if (StringUtils.isEmpty(mRegisterPwd.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.pwd_null));
                    return;
                }

                if (mRegisterPwd.getText().toString().trim().length() < 6) {
                    ToastUtils.showShort(getResources().getString(R.string.pwd_less));
                    return;
                }

                if (StringUtils.isEmpty(mRegisterVerCode.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.ver_code_null));
                    return;
                }
//                if (StringsUtils.isEmpty(mRegisterTuijianren.getText().toString().trim())) {
//                    ToastUtils.showShort(getResources().getString(R.string.invitation_code_null));
//                    return;
//                }
                if (StringUtils.isEmpty(mRegisterRePwd.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.sure_pwd_null));
                    return;
                }
                if (!mRegisterPwd.getText().toString().trim().equals(mRegisterRePwd.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.pwd_no_same));
                    return;
                }
//                if (!mRegistXieyi.isChecked()){
//                    ToastUtils.showShort(getResources().getString(R.string.regist_xieyi));
//                    return;
//                }
                mRegistButton.start();
                getRegist(mRegisterPhone.getText().toString().trim(), mRegisterVerCode.getText().toString().trim(),
                        mRegisterPwd.getText().toString().trim(), mRegisterRePwd.getText().toString().trim());
                break;
        }
    }

    /*
     * 获取验证码
     * */

    private void getCode(String phone) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getVerCode(SPUtils.getLanguage(),phone, "0"), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(RegistActivity.this,
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
     * 注册
     * */
    private void getRegist(String phone, String ver_code, String pwd, String pwd_sure) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .regist(SPUtils.getLanguage(),phone, ver_code, pwd, pwd_sure), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(RegistActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       mRegistButton.fail();
                                       return;
                                   }

                                   final BaseDialog dialog = new WaitDialog.Builder(RegistActivity.this)
                                           .setMessage(getResources().getString(R.string.registing)) // 消息文本可以不用填写
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
        if (time != null) {
            time.cancel();
        }
    }


}
