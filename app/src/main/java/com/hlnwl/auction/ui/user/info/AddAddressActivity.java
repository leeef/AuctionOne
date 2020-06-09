package com.hlnwl.auction.ui.user.info;

import android.app.Dialog;
import android.view.View;
import android.widget.TextView;


import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flod.loadingbutton.LoadingButton;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.NoDataBean;
import com.hlnwl.auction.bean.user.info.AddressData;
import com.hlnwl.auction.message.AddressMessage;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.my.PhoneUtil;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.AddressDialog;
import com.hlnwl.auction.view.dialog.WaitDialog;
import com.hlnwl.auction.view.widget.ClearEditText;


import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/4 17:32
 * 描述：
 */
public class AddAddressActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.add_address_name)
    ClearEditText mAddAddressName;
    @BindView(R.id.add_address_phone)
    ClearEditText mAddAddressPhone;
    @BindView(R.id.add_address_region)
    ClearEditText mAddAddressRegion;
    @BindView(R.id.add_address_detail)
    ClearEditText mAddAddressDetail;
    @BindView(R.id.add_address_sure)
    LoadingButton mAddAddressSure;


    private AddressData editData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        editData = GsonUtils.fromJson(getIntent().getStringExtra("edit"), AddressData.class);
        if (editData != null) {
            mTitleTb.setTitle("编辑地址");
            mAddAddressName.setText(editData.getTheme());
            mAddAddressPhone.setText(editData.getPhone());
            mAddAddressRegion.setText(editData.getCity());
            mAddAddressDetail.setText(editData.getAddress());
        } else {
            mTitleTb.setTitle("添加新地址");
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.add_address_region, R.id.add_address_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.add_address_region:
//                KeyboardUtils.hideSoftInput(this);
//                new AddressDialog.Builder(this)
//                        .setTitle("选择地区")
//                        //.setIgnoreArea() // 不选择县级区域
//                        .setListener(new AddressDialog.OnListener() {
//
//
//                            @Override
//                            public void onSelected(Dialog dialog, String province, String city, String area) {
//                                mAddAddressRegion.setText(province + city + area);
//                            }
//
//                            @Override
//                            public void onCancel(Dialog dialog) {
//                                toast("取消了");
//                            }
//                        })
//                        .show();
//                break;
            case R.id.add_address_sure:
                if (StringUtils.isEmpty(mAddAddressName.getText().toString().trim())) {
                    ToastUtils.showShort("收货人姓名不能为空");
                    return;
                }
                if (StringUtils.isEmpty(mAddAddressPhone.getText().toString().trim())) {
                    ToastUtils.showShort("收货人电话不能为空");
                    return;
                }
                if (!PhoneUtil.isChinaPhoneLegal(mAddAddressPhone.getText().toString().trim())) {
                    ToastUtils.showShort("手机号码格式不正确");
                    return;
                }
                if (StringUtils.isEmpty(mAddAddressRegion.getText().toString().trim())) {
                    ToastUtils.showShort("暂未填写地区");
                    return;
                }
                if (StringUtils.isEmpty(mAddAddressDetail.getText().toString().trim())) {
                    ToastUtils.showShort("收货人详细地址不能为空");
                    return;
                }
                mAddAddressSure.start();
                if (editData != null) {
                    editAddress(
                            mAddAddressName.getText().toString().trim(),
                            mAddAddressPhone.getText().toString().trim(),
                            mAddAddressRegion.getText().toString().trim(),
                            mAddAddressDetail.getText().toString().trim());
                } else {

                    addAddress(mAddAddressName.getText().toString().trim(),
                            mAddAddressPhone.getText().toString().trim(),
                            mAddAddressRegion.getText().toString().trim(),
                            mAddAddressDetail.getText().toString().trim());
                }
                break;
        }
    }

    private void editAddress(String name, String phone, String city,String address) {

        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .editAddress(SPUtils.getLanguage(),SPUtils.getUserId(), SPUtils.getToken(),editData.getId(),name,phone,city,address), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(AddAddressActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       mAddAddressSure.fail();
                                       return;
                                   }

                                   EventBus.getDefault().post(new AddressMessage("update"));
                                   final BaseDialog dialog = new WaitDialog.Builder(AddAddressActivity.this)
                                           .setMessage(getResources().getString(R.string.editing)) // 消息文本可以不用填写
                                           .show();

                                   getHandler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           mAddAddressSure.complete();
                                           toast(data.getMsg());
                                           dialog.dismiss();
                                           finish();
                                       }
                                   }, 1000);
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   mAddAddressSure.fail();
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private void addAddress(String name, String phone, String city,String address) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .addAddress(SPUtils.getLanguage(),SPUtils.getUserId(), SPUtils.getToken(),name,phone,city,address), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(AddAddressActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       mAddAddressSure.fail();
                                       return;
                                   }

                                   EventBus.getDefault().post(new AddressMessage("update"));
                                   final BaseDialog dialog = new WaitDialog.Builder(AddAddressActivity.this)
                                           .setMessage(getResources().getString(R.string.adding)) // 消息文本可以不用填写
                                           .show();

                                   getHandler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           mAddAddressSure.complete();
                                           toast(data.getMsg());
                                           dialog.dismiss();
                                           finish();
                                       }
                                   }, 1000);
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   mAddAddressSure.fail();
                                   toast(t.getMessage());
                               }
                           }
                );
    }




}
