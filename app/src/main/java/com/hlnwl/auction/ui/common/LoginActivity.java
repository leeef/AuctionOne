package com.hlnwl.auction.ui.common;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flod.loadingbutton.LoadingButton;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.user.LoginBean;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.my.PhoneUtil;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.WaitDialog;
import com.hlnwl.auction.view.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/16 11:38
 * 描述：
 */
public class LoginActivity extends MyActivity {
    @BindView(R.id.login_account)
    ClearEditText mLoginAccount;
    @BindView(R.id.login_pwd)
    ClearEditText mLoginPwd;
    @BindView(R.id.sign_in_button)
    LoadingButton mSignInButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.sign_in_button, R.id.login_register, R.id.login_forget_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                if (StringUtils.isEmpty(mLoginAccount.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.phone_null));
                    return;
                }
                if (!PhoneUtil.isChinaPhoneLegal(mLoginAccount.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.phone_wrong));
                    return;
                }
                if (StringUtils.isEmpty(mLoginPwd.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.pwd_null));
                    return;
                }
                mSignInButton.start();
                sign(mLoginAccount.getText().toString().trim(), mLoginPwd.getText().toString().trim());
                break;
            case R.id.login_register:
                startActivityForResult(new Intent(this, RegistActivity.class), 100);
                break;
            case R.id.login_forget_pwd:
                startActivity(ForgetPwdActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            finish();
        }
    }

    private void sign(String account, String pwd) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .login(SPUtils.getLanguage(), account, pwd), this)
                .subscribe(new ApiObserver<LoginBean>() {
                               @Override
                               protected void success(LoginBean data) {
                                   boolean isSuccess = MessageUtils.setCode(LoginActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       mSignInButton.fail();
                                       return;
                                   }
                                   SPUtils.setLogin("login");
                                   SPUtils.setToken(data.getData().get(0).getToken());
                                   SPUtils.setUserId(data.getData().get(0).getId());
                                   SPUtils.setChant(StringUtils.null2Length0(data.getData().get(0).getChant()));
                                   EventBus.getDefault().post(new LoginMessage("update"));
                                   final BaseDialog dialog = new WaitDialog.Builder(LoginActivity.this)
                                           .setMessage(getResources().getString(R.string.logining)) // 消息文本可以不用填写
                                           .show();

                                   getHandler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           mSignInButton.complete();
                                           toast(data.getMsg());
                                           dialog.dismiss();
                                           finish();
                                       }
                                   }, 1000);
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   mSignInButton.fail();
                                   toast(t.getMessage());
                               }
                           }
                );
    }
}
