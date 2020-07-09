package com.hlnwl.auction.ui.store;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.hlnwl.auction.bean.goods.GoodsDetailBean;
import com.hlnwl.auction.bean.pay.AuthResult;
import com.hlnwl.auction.bean.pay.WeiXinPay;
import com.hlnwl.auction.bean.user.bid.BidOrderBean;
import com.hlnwl.auction.bean.user.info.AddressData;
import com.hlnwl.auction.bean.user.shop.JoinBean;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.message.WeChatMessage;
import com.hlnwl.auction.ui.user.info.AddressActivity;
import com.hlnwl.auction.ui.user.shop.ShopJoinActivity;
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
import butterknife.OnClick;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/6/25 11:03
 */
public class OrderDetailActivity extends MyActivity {
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

    private static final int SDK_PAY_FLAG = 1;
    private String addressid = "";
    private GoodsDetailBean.DataBean mGoodsDetailData;

    private IWXAPI wxAPI;

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
        wxAPI = WXAPIFactory.createWXAPI(getActivity(), Constant.WECHAT_APPID, true);
        wxAPI.registerApp(Constant.WECHAT_APPID);
        mGoodsDetailData = getIntent().getParcelableExtra("data");
        getData(mGoodsDetailData.getId());
    }

    private void getData(String id) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .bidOrder(SPUtils.getLanguage(), id,
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
        if (data.getAddinfo().size() > 0) {
            mTvGoodsOrderAddress.setText(data.getAddinfo().get(0).getXiangxi());
            addressid = data.getAddinfo().get(0).getId();
        } else {
            mTvGoodsOrderAddress.setText("点击设置地址信息");
        }
        ImageLoaderUtils.display(this, mBidOrderImg, mGoodsDetailData.getPic().get(0));
        mBidOrderName.setText(mGoodsDetailData.getName());
        mBidOrderPrice.setText(StringUtils.getString(R.string.danwei) + mGoodsDetailData.getPrice());
        showComplete();
    }


    @OnClick({R.id.tv_goods_order_address, R.id.goods_order_goto_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_order_address:
                startActivityForResult(new Intent(OrderDetailActivity.this, AddressActivity.class)
                        .putExtra("type", "1"), 0);
                break;
            case R.id.goods_order_goto_pay:
                if (addressid.length() == 0) {
                    toast("暂未选择地址");
                    return;
                }
                new PayDialog.Builder(OrderDetailActivity.this, "join")
                        .setListener(new PayDialog.OnPayListener() {
                            @Override
                            public void onSelected(Dialog dialog, String pay_style) {
                                if (pay_style.equals("weChat")) {
                                    offer("2");
                                } else if (pay_style.equals("alipay")) {
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
                                        EventBus.getDefault().post(new LoginMessage("update"));
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

    private void offer(String paytype) {
        showLoading();
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .shopOrder(StringUtils.null2Length0(SPUtils.getUserId()),
                        StringUtils.null2Length0(SPUtils.getToken()),
                        mGoodsDetailData.getId(), addressid, "1", paytype), this)
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
                EventBus.getDefault().post(new LoginMessage("update"));
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
                mTvGoodsOrderAddress.setText(addressData.getXiangxi());
                addressid = addressData.getId();
            }
        }
    }
}
