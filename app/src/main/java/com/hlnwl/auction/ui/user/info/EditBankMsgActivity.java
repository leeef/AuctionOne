package com.hlnwl.auction.ui.user.info;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
 * 创建日期：2019/9/27 17:59
 * 描述：
 */
public class EditBankMsgActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.bank_msg_realname)
    ClearEditText mBankMsgRealname;
    @BindView(R.id.bank_msg_name)
    ClearEditText mBankMsgName;
    @BindView(R.id.bank_msg_num)
    ClearEditText mBankMsgNum;
    @BindView(R.id.bank_msg_get_khh)
    ClearEditText mBankMsgKhh;
    @BindView(R.id.bank_msg_ver_code)
    ClearEditText mBankMsgVerCode;
    @BindView(R.id.bank_msg_get_ver_code)
    TextView mBankMsgGetVerCode;
    @BindView(R.id.bank_msg_confirm)
    LoadingButton mBankMsgConfirm;

    private TimeCount time;//倒计时
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_bank_msg;
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
        mTitleTb.setTitle(StringUtils.getString(R.string.eidt_bank_msg));
        bankCardNumAddSpace(mBankMsgNum);
        time = new TimeCount(this, 60000, 1000, mBankMsgGetVerCode);
    }



    @OnClick({R.id.bank_msg_get_ver_code, R.id.bank_msg_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bank_msg_get_ver_code:
                time.start();
                break;
            case R.id.bank_msg_confirm:
                if (StringUtils.isEmpty(mBankMsgRealname.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.bank_real_name_null));
                    return;
                }

                if (StringUtils.isEmpty(mBankMsgName.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.bank_name_null));
                    return;
                }
                if (StringUtils.isEmpty(mBankMsgNum.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.bank_num_null));
                    return;
                }

                if (StringUtils.isEmpty(mBankMsgKhh.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.bank_khh_null));
                    return;
                }
                if (StringUtils.isEmpty(mBankMsgGetVerCode.getText().toString().trim())) {
                    ToastUtils.showShort(getResources().getString(R.string.ver_code_null));
                    return;
                }
                mBankMsgConfirm.start();
                RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                        .bindBank(SPUtils.getLanguage(),SPUtils.getUserId(), SPUtils.getToken(),
                                mBankMsgName.getText().toString().trim(),
                                mBankMsgRealname.getText().toString().trim(),
                                mBankMsgNum.getText().toString().trim(),
                                mBankMsgRealname.getText().toString().trim(),
                                mBankMsgKhh.getText().toString().trim()), this)
                        .subscribe(new ApiObserver<NoDataBean>() {
                                       @Override
                                       protected void success(NoDataBean data) {
                                           boolean isSuccess = MessageUtils.setCode(EditBankMsgActivity.this,
                                                   data.getStatus(), data.getMsg());
                                           if (!isSuccess) {
                                               mBankMsgConfirm.fail();
                                               return;
                                           }

                                           EventBus.getDefault().post(new LoginMessage("update"));
                                           final BaseDialog dialog = new WaitDialog.Builder(EditBankMsgActivity.this)
                                                   .setMessage(getResources().getString(R.string.binding)) // 消息文本可以不用填写
                                                   .show();

                                           getHandler().postDelayed(new Runnable() {
                                               @Override
                                               public void run() {
                                                   mBankMsgConfirm.complete();
                                                   toast(data.getMsg());
                                                   dialog.dismiss();
                                                   finish();
                                               }
                                           }, 1000);
                                       }

                                       @Override
                                       public void onError(Throwable t) {
                                           super.onError(t);
                                           mBankMsgConfirm.fail();
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

    /**
     * 银行卡四位加空格
     *
     * @param mEditText
     */
    public static void bankCardNumAddSpace(final EditText mEditText) {
        mEditText.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }
                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }
}
