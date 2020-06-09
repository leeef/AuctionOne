package com.hlnwl.auction.ui.user.bid;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.app.MyApplication;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.NoDataBean;
import com.hlnwl.auction.bean.user.bid.BidOrderBean;
import com.hlnwl.auction.bean.user.info.AddressData;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.message.PayBidMessage;
import com.hlnwl.auction.ui.goods.GoodsDetailActivity;
import com.hlnwl.auction.ui.user.info.AddressActivity;
import com.hlnwl.auction.ui.user.info.BalanceActivity;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.PayDialog;
import com.hlnwl.auction.view.dialog.WaitDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 09:36
 * 描述：
 */
public class BidOrderActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.tv_goods_order_address)
    TextView mTvGoodsOrderAddress;
    @BindView(R.id.bid_order_img)
    ImageView mBidOrderImg;
    @BindView(R.id.bid_order_name)
    TextView mBidOrderName;
    @BindView(R.id.bid_order_price)
    TextView mBidOrderPrice;

    private String addressid="";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bid_order;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(StringUtils.getString(R.string.pay_order));
    }

    @Override
    protected void initData() {
        showLoading();
        getData();
    }

    private void getData() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .bidOrder(SPUtils.getLanguage(),getIntent().getStringExtra("id"),
                        SPUtils.getUserId(), SPUtils.getToken()), this)
                .subscribe(new ApiObserver<BidOrderBean>() {
                               @Override
                               protected void success(BidOrderBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       showError();
                                       return;
                                   }
                                   initUI(data.getData().get(0));
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   showError();
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private void initUI(BidOrderBean.DataBean data) {
        if (data.getAddinfo().size()>0) {
            mTvGoodsOrderAddress.setText(data.getAddinfo().get(0).getXiangxi());
            addressid=data.getAddinfo().get(0).getId();
        } else {
            mTvGoodsOrderAddress.setText("点击设置地址信息");
        }
        ImageLoaderUtils.display(this, mBidOrderImg,  data.getOrinfo().get(0).getGpic());
        mBidOrderName.setText(data.getOrinfo().get(0).getGname());
        mBidOrderPrice.setText(StringUtils.getString(R.string.chujia)+": "+data.getOrinfo().get(0).getPrice());
        showComplete();
    }


    @OnClick({R.id.tv_goods_order_address, R.id.goods_order_goto_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_order_address:
                startActivityForResult(new Intent(BidOrderActivity.this, AddressActivity.class)
                        .putExtra("type", "1"), 0);
                break;
            case R.id.goods_order_goto_pay:
                if (addressid.length() == 0) {
                    toast("暂未选择地址");
                    return;
                }
                new PayDialog.Builder(BidOrderActivity.this,"pay")
                        .setListener(new PayDialog.OnPayListener() {
                            @Override
                            public void onSelected(Dialog dialog, String pay_style) {
                                if (pay_style.equals("weChat")) {

                                } else if (pay_style.equals("alipay")) {

                                } else {
                                    offer("1");
                                }
                            }

                            @Override
                            public void onCancel(Dialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
        }
    }

    private void offer(String paytype) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .pay(SPUtils.getLanguage(),StringUtils.null2Length0(SPUtils.getUserId()),
                        StringUtils.null2Length0(SPUtils.getToken()),
                        getIntent().getStringExtra("id"),addressid, paytype), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }
                                   EventBus.getDefault().post(new LoginMessage("update"));
                                   EventBus.getDefault().post(new PayBidMessage("update"));
                                   toast(data.getMsg());
                                   finish();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 10) {
            if (data != null) {
                AddressData addressData = (AddressData) data.getSerializableExtra("address");
                mTvGoodsOrderAddress.setText(addressData.getXiangxi());
                addressid = addressData.getId();
            }
        }
    }
}
