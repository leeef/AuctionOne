package com.hlnwl.auction.ui.user.order;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.user.order.OrderDetailBean;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;
import com.hlnwl.auction.utils.sp.SPUtils;

import butterknife.BindView;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 14:02
 * 描述：
 */
public class OrderDetailActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.order_detail_status)
    TextView mOrderDetailStatus;
    @BindView(R.id.order_detail_time_sh)
    TextView mOrderDetailTimeSh;
    @BindView(R.id.order_detail_bag_status)
    TextView mOrderDetailBagStatus;
    @BindView(R.id.order_detail_bag_time)
    TextView mOrderDetailBagTime;
    @BindView(R.id.order_detail_sh_name)
    TextView mOrderDetailShName;
    @BindView(R.id.order_detail_sh_address)
    TextView mOrderDetailShAddress;
    @BindView(R.id.order_detail_sh_phone)
    TextView mOrderDetailShPhone;
    @BindView(R.id.order_detail_good_img)
    ImageView mOrderDetailGoodImg;
    @BindView(R.id.order_detail_good_name)
    TextView mOrderDetailGoodName;
    @BindView(R.id.order_detail_price)
    TextView mOrderDetailPrice;
    @BindView(R.id.order_detail_sn)
    TextView mOrderDetailSn;
    @BindView(R.id.order_detail_pay_type)
    TextView mOrderDetailPayType;
    @BindView(R.id.order_detail_add_time)
    TextView mOrderDetailAddTime;
    @BindView(R.id.order_detail_pay_time)
    TextView mOrderDetailPayTime;
    @BindView(R.id.order_detail_fh_time)
    TextView mOrderDetailFhTime;
    @BindView(R.id.order_detail_copy)
    TextView mOrderDetailCopy;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(StringUtils.getString(R.string.order_detail));
    }

    @Override
    protected void initData() {
        showLoading();
        getData();
    }

    private void getData() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getOrderDetail2(SPUtils.getUserId(), SPUtils.getToken(),
                        getIntent().getStringExtra("id")), this)
                .subscribe(new ApiObserver<OrderDetailBean>() {
                               @Override
                               protected void success(OrderDetailBean data) {
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

    private void initUI(OrderDetailBean.DataBean data) {
        try {
            mOrderDetailStatus.setText(data.getStatus_text());
            mOrderDetailShName.setText(data.getAddr_name());
            mOrderDetailShAddress.setText(data.getAddress());
            mOrderDetailShPhone.setText(data.getAddr_phone());
            ImageLoaderUtils.display(this, mOrderDetailGoodImg, data.getGpic());
            mOrderDetailGoodName.setText(data.getGname());
            mOrderDetailPrice.setText(StringUtils.getString(R.string.danwei) + "  " + data.getPrice());
            mOrderDetailSn.setText(StringUtils.getString(R.string.order_sn) + " " + data.getOrder_sn());
            mOrderDetailPayType.setText(StringUtils.getString(R.string.pay_type) + " " + data.getPaytype());
            mOrderDetailAddTime.setText(StringUtils.getString(R.string.add_time) + " " + data.getAddtime());
            mOrderDetailPayTime.setText(StringUtils.getString(R.string.pay_time) + " " + data.getPaytime());
            if (data.getStatus().equals("1")) {
                mOrderDetailFhTime.setVisibility(View.GONE);
            } else {
                mOrderDetailFhTime.setVisibility(View.VISIBLE);
                mOrderDetailFhTime.setText(StringUtils.getString(R.string.send_time) + " " + data.getSendtime());
            }
            mOrderDetailCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", data.getOrder_sn());
// 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                }
            });
            showComplete();
        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
