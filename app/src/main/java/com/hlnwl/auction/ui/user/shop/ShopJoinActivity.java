package com.hlnwl.auction.ui.user.shop;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.allen.library.CircleImageView;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flod.loadingbutton.LoadingButton;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hlnwl.auction.R;
import com.hlnwl.auction.app.MyApplication;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.pay.AuthResult;
import com.hlnwl.auction.bean.pay.WeiXinPay;
import com.hlnwl.auction.bean.user.shop.JoinBean;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.message.WeChatMessage;
import com.hlnwl.auction.ui.common.CommonWebActivity;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.my.PhoneUtil;
import com.hlnwl.auction.utils.pay.Constant;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;
import com.hlnwl.auction.utils.photo.PictureUtile;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.MenuDialog;
import com.hlnwl.auction.view.dialog.PayDialog;
import com.hlnwl.auction.view.dialog.WaitDialog;
import com.hlnwl.auction.view.widget.ClearEditText;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/16 15:44
 * 描述：
 */
public class ShopJoinActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.shop_join_name)
    ClearEditText mShopJoinName;
    @BindView(R.id.shop_join_id)
    ClearEditText mShopJoinId;
    @BindView(R.id.shop_join_phone)
    ClearEditText mShopJoinPhone;
    @BindView(R.id.shop_join_zheng)
    ImageView mShopJoinZheng;
    @BindView(R.id.shop_join_fan)
    ImageView mShopJoinFan;
    @BindView(R.id.shop_join_shop_name)
    ClearEditText mShopJoinShopName;
    @BindView(R.id.shop_join_shop_img)
    CircleImageView mShopJoinShopImg;
    @BindView(R.id.shop_join_pay)
    LoadingButton mShopJoinPay;
    @BindView(R.id.shop_join_xieyi)
    CheckBox mShopJoinXieyi;

    private List<LocalMedia> selectListPositive = new ArrayList<>();
    private List<LocalMedia> selectListNegative = new ArrayList<>();
    private List<LocalMedia> selectListDianPu = new ArrayList<>();
    private static final int POSITIVE_IMAGE = 1;
    private static final int NEGATIVE_IMAGE = 2;
    private static final int DIANPU_IMAGE = 30;
    private Bitmap zheng;
    private Bitmap fan;
    private Bitmap dianpu;
    private String genre = "1";

    private String idzheng = "", idfan = "";

    private IWXAPI wxAPI;
    private static final int SDK_PAY_FLAG = 1;

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
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
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mShopJoinPay.complete();
                                            EventBus.getDefault().post(new LoginMessage("update"));
                                            ToastUtils.showShort(StringUtils.getString(R.string.pay_success));
                                            startActivity(ShopJoinStatusActivity.class);
                                            finish();
                                        }


                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                    } else {
                        mShopJoinPay.fail();
                        toast(StringUtils.getString(R.string.cancel_pay));
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private BaseDialog mDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_join;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(getResources().getString(R.string.shop_join));


    }

    private String getCurrentLauguageUseResources() {
        /**
         * 获得当前系统语言
         */
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage(); // 获得语言码
        return language;
    }

    @Override
    protected void initData() {
        wxAPI = WXAPIFactory.createWXAPI(getActivity(), Constant.WECHAT_APPID, true);
        wxAPI.registerApp(Constant.WECHAT_APPID);

    }

    private void Join(String paystyle) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .joinShop(SPUtils.getLanguage(), SPUtils.getUserId(), SPUtils.getToken(),
                        mShopJoinName.getText().toString().trim(),
                        mShopJoinId.getText().toString().trim(),
                        mShopJoinPhone.getText().toString().trim(),
                        idzheng,
                        idfan,
                        mShopJoinShopName.getText().toString().trim(),
                        Bitmap2StrByBase64(dianpu),
                        genre, paystyle
                ), this)
                .subscribe(new ApiObserver<JoinBean>() {
                               @Override
                               protected void success(JoinBean data) {
                                   mDialog.dismiss();
                                   boolean isSuccess = MessageUtils.setCode(ShopJoinActivity.this,
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       mShopJoinPay.fail();
                                       return;
                                   }

                                   EventBus.getDefault().post(new LoginMessage("update"));
                                   startActivity(ShopJoinStatusActivity.class);
                                   finish();

//                                   if (paystyle.equals("2")) {
//                                       weChatPay(data.getData().get(0).getStr());
//                                   } else {
//                                       Runnable payRunnable = new Runnable() {
//
//                                           @Override
//                                           public void run() {
//                                               PayTask alipay = new PayTask(getActivity());
//                                               Map<String, String> result = alipay.payV2(data.getData().get(0).getStr(), true);
//                                               Message msg = new Message();
//                                               msg.what = SDK_PAY_FLAG;
//                                               msg.obj = result;
//                                               mHandler.sendMessage(msg);
//                                           }
//                                       };
//                                       // 必须异步调用
//                                       Thread payThread = new Thread(payRunnable);
//                                       payThread.start();
//                                   }

                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   mShopJoinPay.fail();
                                   mDialog.dismiss();
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private void goPayForJoin() {
        new PayDialog.Builder(ShopJoinActivity.this, "join")
                .setListener(new PayDialog.OnPayListener() {
                    @Override
                    public void onSelected(Dialog dialog, String pay_style) {


//                        if (pay_style.equals("weChat")) {
//
//                            if (!AppUtils.isAppInstalled("com.tencent.mm")) {
//                                ToastUtils.showShort(StringsUtils.getString(R.string.null_wechat));
//                                return;
//                            }
//                            Join("2");
//                        } else if (pay_style.equals("alipay")) {
//                            Join("1");
////                            toast("申请中");
//                        }
                    }

                    @Override
                    public void onCancel(Dialog dialog) {
                        mShopJoinPay.fail();
                        dialog.dismiss();
                    }
                }).show();
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

    @Subscribe
    public void onEventMainThread(WeChatMessage weiXin) {
        if (weiXin.getType() == 3) {//微信支付
            if (weiXin.getErrCode() == BaseResp.ErrCode.ERR_OK) {//成功
                Log.e("ansen", "微信支付成功.....");
                mShopJoinPay.complete();
                EventBus.getDefault().post(new LoginMessage("update"));
                ToastUtils.showShort(StringUtils.getString(R.string.pay_success));
                startActivity(ShopJoinStatusActivity.class);
                finish();
            } else {
                mShopJoinPay.fail();
                ToastUtils.showShort(StringUtils.getString(R.string.pay_fail));
            }
        }
    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     *
     * @param bit
     * @return
     */
    public String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    @OnClick({R.id.shop_join_zheng, R.id.shop_join_fan,
            R.id.shop_join_shop_img_selet, R.id.shop_join_pay, R.id.shop_join_service_yrz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shop_join_service_yrz:
                CommonWebActivity.runActivity(this, StringUtils.getString(R.string.join_protocol),
                        MyApplication.BaseUrl + "/index/index/agree");
                break;

            case R.id.shop_join_zheng:
                selectPhoto(POSITIVE_IMAGE, selectListPositive);
                break;
            case R.id.shop_join_fan:
                selectPhoto(NEGATIVE_IMAGE, selectListNegative);
                break;
            case R.id.shop_join_shop_img_selet:
                selectPhoto(DIANPU_IMAGE, selectListDianPu);
                break;
            case R.id.shop_join_pay:


                if (StringUtils.isEmpty(mShopJoinName.getText().toString().trim())) {
                    ToastUtils.showShort(StringUtils.getString(R.string.name_null));
                    return;
                }

//                if (StringsUtils.isEmpty(mShopJoinId.getText().toString().trim())) {
//                    ToastUtils.showShort(StringsUtils.getString(R.string.id_null));
//                    return;
//                }
//
//                if (!RegexUtils.isIDCard18(mShopJoinId.getText().toString().trim())) {
//                    ToastUtils.showShort(StringsUtils.getString(R.string.id_error));
//                    return;
//                }
                if (StringUtils.isEmpty(mShopJoinPhone.getText().toString().trim())) {
                    ToastUtils.showShort(StringUtils.getString(R.string.phone_null));
                    return;
                }

                if (!PhoneUtil.isChinaPhoneLegal(mShopJoinPhone.getText().toString().trim())) {
                    ToastUtils.showShort(StringUtils.getString(R.string.phone_wrong));
                    return;
                }

                if (StringUtils.isEmpty(mShopJoinShopName.getText().toString().trim())) {
                    ToastUtils.showShort(StringUtils.getString(R.string.shop_name_null));
                    return;
                }

//                if (zheng == null) {
//                    ToastUtils.showShort(StringsUtils.getString(R.string.id_zheng_null));
//                    return;
//                }
//                if (fan == null) {
//                    ToastUtils.showShort(StringsUtils.getString(R.string.id_fan_null));
//                    return;
//                }
                if (dianpu == null) {
                    ToastUtils.showShort(StringUtils.getString(R.string.shop_img_null));
                    return;
                }
                if (!mShopJoinXieyi.isChecked()) {
                    ToastUtils.showShort(getResources().getString(R.string.regist_join));
                    return;
                }
                mShopJoinPay.start();
                mDialog = new WaitDialog.Builder(ShopJoinActivity.this)
                        .setMessage(StringUtils.getString(R.string.joining)) // 消息文本可以不用填写
                        .show();


                Join("1");
                // goPayForJoin();
                break;
        }
    }

    private void selectPhoto(int mode, List<LocalMedia> datas) {
        XXPermissions.with(this)
                //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE,
                        Permission.CAMERA) //支持请求6.0悬浮窗权限8.0请求安装权限
                //不指定权限则自动获取清单中的危险权限
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        List<String> data = new ArrayList<>();
                        data.add(getResources().getString(R.string.album));
                        data.add(getResources().getString(R.string.picture_photograph));
                        new MenuDialog.Builder(ShopJoinActivity.this)
                                .setCancel(getResources().getString(R.string.picture_cancel)) // 设置 null 表示不显示取消按钮
                                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                                .setList(data)
                                .setListener(new MenuDialog.OnListener() {

                                    @Override
                                    public void onSelected(Dialog dialog, int position, String text) {
                                        if (position == 0) {


                                            PictureUtile.addPic(ShopJoinActivity.this, datas, 1, mode);
                                        } else if (position == 1) {


                                            PictureUtile.getCamera(ShopJoinActivity.this, datas, mode);
                                        }
                                    }

                                    @Override
                                    public void onCancel(Dialog dialog) {
                                        ToastUtils.showShort(getResources().getString(R.string.picture_cancel));
                                    }

                                })
                                .setGravity(Gravity.BOTTOM)
                                .setAnimStyle(BaseDialog.AnimStyle.BOTTOM)
                                .show();
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        XXPermissions.gotoPermissionSettings(ShopJoinActivity.this);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case POSITIVE_IMAGE:
                    selectListPositive.clear();
                    selectListPositive = PictureSelector.obtainMultipleResult(data);
                    if (selectListPositive != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            zheng = BitmapFactory.decodeFile(selectListPositive.get(0).getAndroidQToPath());
                            ImageLoaderUtils.display(this, mShopJoinZheng, selectListPositive.get(0).getAndroidQToPath());
                        } else {
                            zheng = BitmapFactory.decodeFile(selectListPositive.get(0).getPath());
                            ImageLoaderUtils.display(this, mShopJoinZheng, selectListPositive.get(0).getPath());
                        }
                        idzheng = Bitmap2StrByBase64(zheng);

                    }
                    break;
                case NEGATIVE_IMAGE:
                    selectListNegative.clear();
                    selectListNegative = PictureSelector.obtainMultipleResult(data);
                    if (selectListNegative != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            fan = BitmapFactory.decodeFile(selectListNegative.get(0).getAndroidQToPath());
                            ImageLoaderUtils.display(this, mShopJoinFan, selectListNegative.get(0).getAndroidQToPath());
                        } else {
                            fan = BitmapFactory.decodeFile(selectListNegative.get(0).getPath());
                            ImageLoaderUtils.display(this, mShopJoinFan, selectListNegative.get(0).getPath());
                        }
                        idfan = Bitmap2StrByBase64(fan);
                    }
                    break;
                case DIANPU_IMAGE:
                    selectListDianPu.clear();
                    selectListDianPu = PictureSelector.obtainMultipleResult(data);
                    if (selectListDianPu != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            dianpu = BitmapFactory.decodeFile(selectListDianPu.get(0).getAndroidQToPath());
                            ImageLoaderUtils.display(this, mShopJoinShopImg, selectListDianPu.get(0).getAndroidQToPath());
                        } else {
                            dianpu = BitmapFactory.decodeFile(selectListDianPu.get(0).getPath());
                            ImageLoaderUtils.display(this, mShopJoinShopImg, selectListDianPu.get(0).getPath());
                        }
                    }
                    break;
            }
        }
    }
}
