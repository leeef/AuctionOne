package com.hlnwl.auction.ui.user.info;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.allen.library.SuperTextView;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.StringUtils;
import com.flod.loadingbutton.LoadingButton;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.NoDataBean;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.ui.common.RegistActivity;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.my.CashierInputFilter;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.WaitDialog;
import com.hlnwl.auction.view.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/19 14:48
 * 描述：
 */
public class BalanceActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.balance_money)
    TextView mBalanceMoney;
    @BindView(R.id.balance_chose)
    SuperTextView mBalanceChose;
    @BindView(R.id.withdrawal_num)
    ClearEditText mWithdrawalNum;
    @BindView(R.id.cash_withdrawals)
    TextView mCashWithdrawals;
    @BindView(R.id.arrival_time)
    TextView mArrivalTime;
    @BindView(R.id.withdrawal)
    LoadingButton mWithdrawal;

    private String payType="";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_balance;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginEventBus(LoginMessage update) {
        mBalanceMoney.setText(StringUtils.null2Length0(SPUtils.getBalance()));
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(StringUtils.getString(R.string.balance));
        mBalanceMoney.setText(StringUtils.null2Length0(SPUtils.getBalance()));
        InputFilter[] filters = {new CashierInputFilter()};
        mWithdrawalNum.setFilters(filters); //设置金额输入的过滤器，保证只能输入金额类型
        mWithdrawalNum.setHint(StringUtils.getString(R.string.hint_min_tx)+SPUtils.getMinTx());
        mWithdrawalNum.addTextChangedListener(new TextWatcher() {
            private String mBefore;// 用于记录变化前的文字
            private int mCursor;// 用于记录变化时光标的位置
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains(" ")) {
                    mWithdrawalNum.removeTextChangedListener(this);
                    mWithdrawalNum.setText(mBefore);
                    mWithdrawalNum.addTextChangedListener(this);
                    mWithdrawalNum.setSelection(mCursor);

                }else {
                    mCashWithdrawals.setText(Double.parseDouble(mWithdrawalNum.getText().toString().trim())*
                            Double.parseDouble(SPUtils.getDage())+StringUtils.getString(R.string.money_unit));
                }

            }
        });
        mArrivalTime.setText(StringUtils.getString(R.string.tx_time));
        mTitleTb.setRightTitle(StringUtils.getString(R.string.detail));
        mTitleTb.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                startActivity(MoneyDetailActivity.class);
            }
        });
    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.balance_bond, R.id.balance_chose, R.id.withdrawal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.balance_bond:

                break;
            case R.id.balance_chose:
//                showWithdrawalTypeDialog();
                startActivityForResult(new Intent(this,WithdrawalMsgActivity.class),0);
                break;
            case R.id.withdrawal:
                if (payType.length()==0){
                    toast(StringUtils.getString(R.string.withdrawal_type_null));
                    return;
                }
                if (StringUtils.isEmpty(mWithdrawalNum.getText().toString().trim())){
                    toast(StringUtils.getString(R.string.withdrawal_num_null));
                    return;
                }
                mWithdrawal.start();
                withdrawal();
                break;
        }
    }

    private void withdrawal() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .withdrawal(SPUtils.getLanguage(),SPUtils.getUserId(),SPUtils.getToken(),
                        mWithdrawalNum.getText().toString().trim(),payType), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(BalanceActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       mWithdrawal.fail();
                                       return;
                                   }

                                   final BaseDialog dialog = new WaitDialog.Builder(BalanceActivity.this)
                                           .setMessage(getResources().getString(R.string.withdrawaling)) // 消息文本可以不用填写
                                           .show();
                                   EventBus.getDefault().post(new LoginMessage("update"));
                                   getHandler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           mWithdrawal.complete();
                                           toast(data.getMsg());
                                           dialog.dismiss();
                                           finish();
                                       }
                                   }, 1000);
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   mWithdrawal.fail();
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private List<String> types=new ArrayList<>();
    private void showWithdrawalTypeDialog() {
        types.clear();
        types.add(StringUtils.getString(R.string.alipay));
        types.add(StringUtils.getString(R.string.bank_card));
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mBalanceChose.setRightString(types.get(options1));
            }
        })
                .setTitleText(StringUtils.getString(R.string.chose_withdrawal_type))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(types);//一级选择器*/

        pvOptions.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            if (requestCode==0&&resultCode==10){
                if (data.getStringExtra("type").equals("1")){
                    mBalanceChose.setRightString(StringUtils.getString(R.string.alipay));
                    payType="1";

                }else {
                    mBalanceChose.setRightString(StringUtils.getString(R.string.bank_card));
                    payType="2";
                }
            }
        }
    }
}
