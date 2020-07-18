package com.hlnwl.auction.ui.user.order;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alipay.sdk.app.PayTask;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.pay.AuthResult;
import com.hlnwl.auction.bean.pay.WeiXinPay;
import com.hlnwl.auction.bean.user.info.AddressData;
import com.hlnwl.auction.bean.user.order.OrderDetailBean;
import com.hlnwl.auction.bean.user.shop.JoinBean;
import com.hlnwl.auction.message.OrderMessage;
import com.hlnwl.auction.message.WeChatMessage;
import com.hlnwl.auction.ui.user.info.AddressActivity;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.pay.Constant;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.PayDialog;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

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
    @BindView(R.id.other_layout)
    LinearLayout otherLayout;
    @BindView(R.id.address_layout)
    LinearLayout addressLayout;
    @BindView(R.id.pay_layout)
    LinearLayout payLayout;
    @BindView(R.id.goods_order_goto_pay)
    TextView goodsOrderGotoPay;
    @BindView(R.id.express_company)
    TextView expressCompany;
    @BindView(R.id.express_number)
    TextView expressNumber;
    @BindView(R.id.express_layout)
    LinearLayout expressLayout;


    private String type;

    private String id;
    private String addressid = "";

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
        wxAPI = WXAPIFactory.createWXAPI(getActivity(), Constant.WECHAT_APPID, true);
        wxAPI.registerApp(Constant.WECHAT_APPID);
        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");
        if (type.equals("0")) {
            otherLayout.setVisibility(View.INVISIBLE);
            payLayout.setVisibility(View.VISIBLE);
            addressLayout.setOnClickListener(v -> {
                startActivityForResult(new Intent(OrderDetailActivity.this, AddressActivity.class)
                        .putExtra("type", "1"), 0);
            });
            goodsOrderGotoPay.setOnClickListener(v -> {

                new PayDialog.Builder(getActivity(), "join")
                        .setListener(new PayDialog.OnPayListener() {
                            @Override
                            public void onSelected(Dialog dialog, String pay_style) {
                                if (pay_style.equals("weChat")) {
                                    offer(id, "2");
                                } else if (pay_style.equals("alipay")) {
                                    offer(id, "1");
                                }
                                dialog.dismiss();
                            }

                            @Override
                            public void onCancel(Dialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
            });
        } else {
            otherLayout.setVisibility(View.VISIBLE);
            payLayout.setVisibility(View.GONE);
        }
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
            addressid = data.getAid();
            mOrderDetailStatus.setText(data.getStatus_text());
            mOrderDetailShName.setText(data.getAddr_name());
            mOrderDetailShAddress.setText(data.getAddress());
            mOrderDetailShPhone.setText(data.getAddr_phone());
            ImageLoaderUtils.display(this, mOrderDetailGoodImg, data.getGpic());
            mOrderDetailGoodName.setText(data.getGname());
            mOrderDetailPrice.setText(StringUtils.getString(R.string.danwei) + "  " + data.getPrice());
            mOrderDetailSn.setText(StringUtils.getString(R.string.order_sn) + " " + data.getOrder_sn());
            String payType = data.getPaytype().equals("1") ? StringUtils.getString(R.string.alipay) : StringUtils.getString(R.string.wechat);
            mOrderDetailPayType.setText(StringUtils.getString(R.string.pay_type) + " " + payType);
            mOrderDetailAddTime.setText(StringUtils.getString(R.string.add_time) + " " + data.getAddtime());
            mOrderDetailPayTime.setText(StringUtils.getString(R.string.pay_time) + " " + data.getPaytime());
            if (data.getStatus().equals("1")) {
                mOrderDetailFhTime.setVisibility(View.GONE);
            } else {
                mOrderDetailFhTime.setVisibility(View.VISIBLE);
                mOrderDetailFhTime.setText(StringUtils.getString(R.string.send_time) + " " + data.getSendtime());
            }
            if (data.getStatus().equals("2")) {
                expressLayout.setVisibility(View.VISIBLE);
                expressCompany.setText(StringUtils.getString(R.string.express_company) + " " + data.getExpress());
                expressNumber.setText(StringUtils.getString(R.string.express_number) + " " + data.getExpressnum());
            } else {
                expressLayout.setVisibility(View.GONE);
            }
            mOrderDetailCopy.setOnClickListener(view -> {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", data.getOrder_sn());
// 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
            });
            showComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI wxAPI;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    Thread.sleep(1000);//休眠3秒
                                    /**
                                     * 要执行的操作
                                     */
                                    getActivity().runOnUiThread(() -> {
                                        EventBus.getDefault().post(new OrderMessage("update"));
                                        ToastUtils.showShort(StringUtils.getString(R.string.pay_success));
                                        finish();
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                    } else {
                        toast(StringUtils.getString(R.string.cancel_pay));
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void aliPay(String payInfo) {
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(getActivity());
            Map<String, String> result = alipay.payV2(payInfo, true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /*微信支付*/
    private void weChatPay(String string1) {

        String[] list1 = string1.split("&");

        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < list1.length; i++) {
            String[] list2 = list1[i].split("=", 2);
            hashMap.put(list2[0], list2[1]);
        }


        WeiXinPay weiXinPay = new WeiXinPay();
        weiXinPay.setPartnerid(hashMap.get("partnerid"));
        weiXinPay.setPrepayid(hashMap.get("prepayid"));
        weiXinPay.setPackage_value(hashMap.get("package"));
        weiXinPay.setNoncestr(hashMap.get("noncestr"));
        weiXinPay.setTimestamp(hashMap.get("timestamp"));
        weiXinPay.setSign(hashMap.get("sign"));


        PayReq req = new PayReq();
        req.appId = Constant.WECHAT_APPID;//appid
        req.nonceStr = weiXinPay.getNoncestr();//随机字符串，不长于32位。推荐随机数生成算法
        req.packageValue = weiXinPay.getPackage_value();//暂填写固定值Sign=WXPay
        req.sign = weiXinPay.getSign();//签名
        req.partnerId = weiXinPay.getPartnerid();//微信支付分配的商户号
        req.prepayId = weiXinPay.getPrepayid();//微信返回的支付交易会话ID
        req.timeStamp = weiXinPay.getTimestamp();//时间戳

        wxAPI.registerApp(Constant.WECHAT_APPID);
        wxAPI.sendReq(req);
    }


    private void offer(String id, String paytype) {
        showLoading();
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .payNow(SPUtils.getUserId(), SPUtils.getToken(), id, paytype, addressid), this)
                .subscribe(new ApiObserver<JoinBean>() {
                               @Override
                               protected void success(JoinBean data) {
                                   showComplete();
                                   if (paytype.equals("1")) {
                                       aliPay(data.getData().get(0).getStr());
                                   } else if (paytype.equals("2")) {
                                       weChatPay(data.getData().get(0).getStr());
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

    @Subscribe
    public void onEventMainThread(WeChatMessage weiXin) {
        if (weiXin.getType() == 3) {//微信支付
            if (weiXin.getErrCode() == BaseResp.ErrCode.ERR_OK) {//成功
                Log.e("ansen", "微信支付成功.....");
                EventBus.getDefault().post(new OrderMessage("update"));
                ToastUtils.showShort(StringUtils.getString(R.string.pay_success));
                finish();
            } else {
                ToastUtils.showShort(StringUtils.getString(R.string.pay_fail));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 10) {
            if (data != null) {
                AddressData addressData = (AddressData) data.getSerializableExtra("address");
                mOrderDetailShName.setText(addressData.getTheme());
                mOrderDetailShAddress.setText(addressData.getXiangxi());
                mOrderDetailShPhone.setText(addressData.getPhone());
                addressid = addressData.getId();
            }
        }
    }

}
