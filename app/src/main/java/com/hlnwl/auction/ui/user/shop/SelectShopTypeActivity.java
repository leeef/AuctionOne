package com.hlnwl.auction.ui.user.shop;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flod.loadingbutton.LoadingButton;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.app.MyApplication;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.pay.AuthResult;
import com.hlnwl.auction.bean.pay.WeiXinPay;
import com.hlnwl.auction.bean.shop.ShopTypeBean;
import com.hlnwl.auction.bean.user.shop.JoinBean;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.message.WeChatMessage;
import com.hlnwl.auction.ui.common.CommonWebActivity;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.pay.Constant;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.radiogrouplib.NestedRadioGroup;
import com.hlnwl.auction.view.radiogrouplib.NestedRadioLayout;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/6/11 9:46
 */
public class SelectShopTypeActivity extends MyActivity {


    @BindView(R.id.pay_dialog_nestedGroup)
    NestedRadioGroup payDialogNestedGroup;
    @BindView(R.id.pay_dialog_alipay)
    NestedRadioLayout payDialogAlipay;
    @BindView(R.id.ticket_layout)
    NestedRadioLayout ticketLayout;
    @BindView(R.id.shop_join_pay)
    LoadingButton shopJoinPay;

    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.shop_price)
    TextView shopPrice;
    @BindView(R.id.integral)
    TextView integral;

    @BindView(R.id.type_list)
    RecyclerView typeList;

    @BindView(R.id.ticket_select)
    ImageView ticketSelect;
    @BindView(R.id.shop_join_xieyi)
    CheckBox mShopJoinXieyi;


    private String payType = "jifen";
    private IWXAPI wxAPI;

    private static final int SDK_PAY_FLAG = 1;

    private ShopTypeAdapter shopTypeAdapter;

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
                                        shopJoinPay.complete();
                                        EventBus.getDefault().post(new LoginMessage("update"));
                                        ToastUtils.showShort(StringUtils.getString(R.string.pay_success));
                                        startActivity(ShopJoinActivity.class);
                                        finish();
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                    } else {
                        shopJoinPay.fail();
                        toast(StringUtils.getString(R.string.cancel_pay));
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_select_shop_type;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(R.string.shop_to_choose);
        wxAPI = WXAPIFactory.createWXAPI(getActivity(), Constant.WECHAT_APPID, true);
        wxAPI.registerApp(Constant.WECHAT_APPID);
        typeList.setLayoutManager(new LinearLayoutManager(this));
        shopTypeAdapter = new ShopTypeAdapter(new ArrayList<>());
        typeList.setAdapter(shopTypeAdapter);
        ticketLayout.setChecked(true);
        payDialogNestedGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.pay_dialog_wechat:
                    payType = "weChat";
                    break;
                case R.id.pay_dialog_alipay:
                    payType = "alipay";
                    break;
                case R.id.ticket_layout:
                    payType = "jifen";
                    break;
            }
        });
    }

    @OnClick({R.id.shop_join_pay, R.id.shop_join_service_yrz})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_join_pay:
                if (!mShopJoinXieyi.isChecked()) {
                    ToastUtils.showShort(getResources().getString(R.string.regist_join));
                    return;
                }
                shopJoinPay.start();
                if (payType.equals("")) {
                    ToastUtils.showShort(R.string.chose_pay_type_sign);
                    return;
                } else if (payType.equals("weChat")) {
                    getPayInfo(2);
                } else if (payType.equals("alipay")) {
                    getPayInfo(1);
                } else if (payType.equals("jifen")) {
                    getPayInfo(3);
                }
                break;
            case R.id.shop_join_service_yrz:
                CommonWebActivity.runActivity(this, StringUtils.getString(R.string.join_protocol),
                        MyApplication.BaseUrl + "/index/index/agree");
                break;

            default:
                break;
        }
    }

    public void getPayInfo(int type) {
        ShopTypeBean.DataBean.ShopBean item = shopTypeAdapter.getItem(shopTypeAdapter.selectIndex);
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .payShop(SPUtils.getUserId(), SPUtils.getToken()
                        , item.getId()
                        , item.getValue(), type), this)
                .subscribe(new ApiObserver<JoinBean>() {
                    @Override
                    protected void success(JoinBean data) {
                        if (type == 1) {
                            aliPay(data.getData().get(0).getStr());
                        } else if (type == 2) {
                            weChatPay(data.getData().get(0).getStr());
                        } else if (type == 3) {
                            toast(data.getMsg());
                            if (data.getStatus() == 1) {
                                shopJoinPay.complete();
                                ToastUtils.showShort(StringUtils.getString(R.string.pay_success));
                                startActivity(ShopJoinActivity.class);
                            } else if (data.getStatus() == 0) {
                                shopJoinPay.fail();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
                        shopJoinPay.fail();
                    }
                });
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

    @Subscribe
    public void onEventMainThread(WeChatMessage weiXin) {
        if (weiXin.getType() == 3) {//微信支付
            if (weiXin.getErrCode() == BaseResp.ErrCode.ERR_OK) {//成功
                Log.e("ansen", "微信支付成功.....");
                shopJoinPay.complete();
                EventBus.getDefault().post(new LoginMessage("update"));
                ToastUtils.showShort(StringUtils.getString(R.string.pay_success));
                startActivity(ShopJoinActivity.class);
                finish();
            } else {
                shopJoinPay.fail();
                ToastUtils.showShort(StringUtils.getString(R.string.pay_fail));
            }
        }
    }

    @Override
    protected void initData() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getShopType(SPUtils.getUserId(), SPUtils.getToken()), this)
                .subscribe(new ApiObserver<ShopTypeBean>() {
                    @Override
                    protected void success(ShopTypeBean data) {
                        integral.setText(StringUtils.getString(R.string.free_to_enter) + "(" +
                                data.getData().get(0).getCoupon() +
                                StringUtils.getString(R.string.integral) + ")");
                        shopTypeAdapter.addData(data.getData().get(0).getShop());
                        for (int i = 0; i < data.getData().get(0).getShop().size(); i++) {
                            ShopTypeBean.DataBean.ShopBean shopBean = data.getData().get(0).getShop().get(i);
                            if (i == 0) {
                                shopPrice.setText("￥" + shopBean.getValue() + "积分");
                            }
                        }
                        shopTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
                            shopTypeAdapter.selectIndex = position;
                            shopPrice.setText("￥" + data.getData().get(0).getShop().get(position).getValue() + "积分");
                            shopTypeAdapter.notifyDataSetChanged();
                        });

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        shopJoinPay.fail();
                        toast(t.getMessage());
                    }
                });
    }

}
