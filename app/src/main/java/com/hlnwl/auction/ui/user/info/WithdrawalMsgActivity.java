package com.hlnwl.auction.ui.user.info;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.user.info.ALiPayMsgBean;
import com.hlnwl.auction.bean.user.info.BankMsgBean;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.sp.SPUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/27 17:03
 * 描述：
 */
public class WithdrawalMsgActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.alipay_msg_name)
    TextView mAlipayMsgName;
    @BindView(R.id.alipay_msg_account)
    TextView mAlipayMsgAccount;
    @BindView(R.id.bank_card_msg_name)
    TextView mBankCardMsgName;
    @BindView(R.id.bank_card_msg_bank_name)
    TextView mBankCardMsgBankName;
    @BindView(R.id.bank_card_msg_card_num)
    TextView mBankCardMsgCardNum;
    @BindView(R.id.bank_card_msg_khh)
    TextView mBankCardMsgKhh;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginEventBus(LoginMessage update) {
        getData();

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_widthdrawal_msg;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(StringUtils.getString(R.string.chose_withdrawal_type));
        showLoading();
        getData();
    }

    private void getData() {
        getAliPayMsg();
       getBankMsg();
       showComplete();
    }

    private void getBankMsg() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getBankMsg(SPUtils.getLanguage(),SPUtils.getUserId(), SPUtils.getToken()), this)
                .subscribe(new ApiObserver<BankMsgBean>() {
                               @Override
                               protected void success(BankMsgBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }
                                   if (data.getData().size()>0){
                                       mBankCardMsgName.setText(StringUtils.getString(R.string.realname)+data.getData().get(0).getRealname());
                                       mBankCardMsgBankName.setText(StringUtils.getString(R.string.bank_name)+data.getData().get(0).getName());
                                       mBankCardMsgCardNum.setText(StringUtils.getString(R.string.card_num)+data.getData().get(0).getNumber());
                                       mBankCardMsgKhh.setText(StringUtils.getString(R.string.bank_khh)+data.getData().get(0).getBank_addr());
                                   }
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private void getAliPayMsg() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getAliPayMsg(SPUtils.getLanguage(),SPUtils.getUserId(), SPUtils.getToken()), this)
                .subscribe(new ApiObserver<ALiPayMsgBean>() {
                               @Override
                               protected void success(ALiPayMsgBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }

                                   if (data.getData().size()>0){
                                       mAlipayMsgName.setText(StringUtils.getString(R.string.realname)+data.getData().get(0).getRealname());
                                       mAlipayMsgAccount.setText(StringUtils.getString(R.string.account)+data.getData().get(0).getNumber());
                                   }

                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.alipay_msg_edit, R.id.alipay_msg, R.id.bank_card_msg_edit, R.id.bank_card_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alipay_msg_edit:
                startActivity(EditAliPayMsgActivity.class);
                break;
            case R.id.alipay_msg:
                if (StringUtils.isEmpty(mAlipayMsgName.getText().toString().trim())||
                StringUtils.isEmpty(mAlipayMsgAccount.getText().toString().trim())){
                    toast(StringUtils.getString(R.string.alipay_msg_null));
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra("type","1");
                setResult(10,intent);
                finish();
                break;
            case R.id.bank_card_msg_edit:
                startActivity(EditBankMsgActivity.class);
                break;
            case R.id.bank_card_msg:
                if (StringUtils.isEmpty(mBankCardMsgName.getText().toString().trim())||
                        StringUtils.isEmpty(mBankCardMsgBankName.getText().toString().trim())||
                        StringUtils.isEmpty(mBankCardMsgCardNum.getText().toString().trim())||
                        StringUtils.isEmpty(mBankCardMsgKhh.getText().toString().trim())){
                    toast(StringUtils.getString(R.string.bank_msg_null));
                    return;
                }
                Intent intentBank=new Intent();
                intentBank.putExtra("type","2");
                setResult(10,intentBank);
                finish();
                break;
        }
    }
}
