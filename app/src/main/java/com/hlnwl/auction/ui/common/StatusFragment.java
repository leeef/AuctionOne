package com.hlnwl.auction.ui.common;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flod.loadingbutton.LoadingButton;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyLazyFragment;
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
 * 创建日期：2019/10/16 15:39
 * 描述：
 */

public class StatusFragment extends MyLazyFragment {


    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.login_account)
    ClearEditText mLoginAccount;
    @BindView(R.id.login_pwd)
    ClearEditText mLoginPwd;
    @BindView(R.id.sign_in_button)
    LoadingButton mSignInButton;
    @BindView(R.id.login_bg)
    LinearLayout mLoginBg;
    @BindView(R.id.join_bg)
    LinearLayout mJoinBg;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_status;
    }

    @Override
    protected int getTitleId() {
        return R.id.title_tb;
    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(StringUtils.getString(R.string.release));
        mTitleTb.setLeftIcon(null);
        if (SPUtils.getLogin().length() == 0 || SPUtils.getLogin() == null) {
            mLoginBg.setVisibility(View.VISIBLE);
            mJoinBg.setVisibility(View.GONE);
        }else {
            mLoginBg.setVisibility(View.GONE);
            mJoinBg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.join_now, R.id.sign_in_button, R.id.login_register, R.id.login_forget_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.join_now:

                break;
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
                sign(mLoginAccount.getText().toString().trim(),mLoginPwd.getText().toString().trim());
                break;
            case R.id.login_register:
                startActivity(RegistActivity.class);
                break;
            case R.id.login_forget_pwd:
                startActivity(ForgetPwdActivity.class);
                break;
        }
    }
    private void sign(String account, String pwd) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .login(SPUtils.getLanguage(),account, pwd), this)
                .subscribe(new ApiObserver<LoginBean>() {
                               @Override
                               protected void success(LoginBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
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
                                   final BaseDialog dialog = new WaitDialog.Builder(getActivity())
                                           .setMessage(getResources().getString(R.string.logining)) // 消息文本可以不用填写
                                           .show();

                                  new Handler().postDelayed(new Runnable() {
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
