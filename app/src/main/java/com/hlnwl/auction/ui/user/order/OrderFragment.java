package com.hlnwl.auction.ui.user.order;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyLazyFragment;
import com.hlnwl.auction.bean.NoDataBean;
import com.hlnwl.auction.bean.pay.AuthResult;
import com.hlnwl.auction.bean.pay.WeiXinPay;
import com.hlnwl.auction.bean.user.order.OrderBean;
import com.hlnwl.auction.bean.user.shop.JoinBean;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.message.OrderMessage;
import com.hlnwl.auction.message.WeChatMessage;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.pay.Constant;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.MessageDialog;
import com.hlnwl.auction.view.dialog.PayDialog;
import com.hlnwl.auction.view.dialog.WaitDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 11:22
 * 描述：
 */
@SuppressLint("ValidFragment")
public class OrderFragment extends MyLazyFragment implements OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_list_common)
    RecyclerView mRvListCommon;
    @BindView(R.id.srl_list_common)
    SmartRefreshLayout mSrlListCommon;

    private String type = "";
    private static final int PAGE_SIZE = 16;//每页数据条目
    private int mPage = 1;
    private OrderAdapter mAdapter;
    private List<OrderBean.DataBean> datas = new ArrayList<>();

    @SuppressLint("ValidFragment")
    public OrderFragment(String type) {
        this.type = type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshEventBus(OrderMessage update) {
        onRefresh(mSrlListCommon);
    }

    @Override
    protected void initView() {
        wxAPI = WXAPIFactory.createWXAPI(getActivity(), Constant.WECHAT_APPID, true);
        wxAPI.registerApp(Constant.WECHAT_APPID);
        mSrlListCommon.autoRefresh();
        mSrlListCommon.setRefreshHeader(new ClassicsHeader(getActivity()));
        mSrlListCommon.setRefreshFooter(new ClassicsFooter(getActivity()));
        mSrlListCommon.setHeaderHeight(60);
        mSrlListCommon.setFooterHeight(60);
        mSrlListCommon.setOnRefreshListener(this);
        mSrlListCommon.setOnLoadMoreListener(this);

        mRvListCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mAdapter == null) {
            mAdapter = new OrderAdapter();
            mRvListCommon.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

        mPage = 1;
        showLoading();
        getDatas("new");
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getDatas("more");
    }

    private void getDatas(String style) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .orderList(SPUtils.getUserId(), SPUtils.getToken(), type, mPage), this)
                .subscribe(new ApiObserver<OrderBean>() {
                               @Override
                               protected void success(OrderBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       showError();
                                       mSrlListCommon.finishRefresh();
                                       mSrlListCommon.finishLoadMoreWithNoMoreData();
                                       return;
                                   }
                                   datas.clear();
                                   if (style.equals("more")) {
                                       boolean isRefresh = mPage == 1;
                                       setData(isRefresh, data.getData());
                                   } else if (style.equals("new")) {

                                       if (data.getData().size() == 0) {
                                           mSrlListCommon.finishRefresh();
                                           mSrlListCommon.finishLoadMoreWithNoMoreData();
                                           showEmpty();
                                           return;
                                       }
                                       setData(true, data.getData());
                                       mSrlListCommon.setEnableLoadMore(true);

                                   }
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   if (mSrlListCommon != null) {
                                       mSrlListCommon.finishRefresh();
                                   }
                                   showError();
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private void setData(boolean isRefresh, List<OrderBean.DataBean> data) {
        showComplete();
        datas.addAll(data);
        mPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
            if (mSrlListCommon != null) {
                mSrlListCommon.finishRefresh();
            }
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mSrlListCommon.finishLoadMoreWithNoMoreData();
        } else {
            mSrlListCommon.finishLoadMore();
        }
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.item_order_good:
                if (!type.equals("0")) {
                    startActivity(new Intent(getActivity(), OrderDetailActivity.class)
                            .putExtra("id", datas.get(position).getId()));
                }
                break;
            case R.id.item_order_qrsh:
                new MessageDialog.Builder(getActivity())
                        .setTitle(StringUtils.getString(R.string.sure_shouhuo)) // 标题可以不用填写
                        .setMessage(getActivity().getResources().getString(R.string.sure_shouhuo_ask))
                        .setConfirm(getActivity().getResources().getString(R.string.confirm))
                        .setCancel(getActivity().getResources().getString(R.string.picture_cancel)) // 设置 null 表示不显示取消按钮
                        //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(Dialog dialog) {
                                sureOrder(datas.get(position).getId());
                            }

                            @Override
                            public void onCancel(Dialog dialog) {
                                toast(getActivity().getResources().getString(R.string.picture_cancel));
                            }
                        })
                        .show();
                break;
            case R.id.item_order_comment:
                startActivity(new Intent(getActivity(), CommentActivity.class)
                        .putExtra("id", datas.get(position).getId()));
                break;
            case R.id.pay:
                new PayDialog.Builder(getActivity(), "join")
                        .setListener(new PayDialog.OnPayListener() {
                            @Override
                            public void onSelected(Dialog dialog, String pay_style) {
                                if (pay_style.equals("weChat")) {
                                    offer(datas.get(position).getId(), "2");
                                } else if (pay_style.equals("alipay")) {
                                    offer(datas.get(position).getId(), "1");
                                }
                                dialog.dismiss();
                            }

                            @Override
                            public void onCancel(Dialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
        }
    }

    private void sureOrder(String id) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .confirmOrder2(SPUtils.getUserId(), SPUtils.getToken(), id), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }

                                   final BaseDialog dialog = new WaitDialog.Builder(getActivity())
                                           .setMessage(StringUtils.getString(R.string.sureing)) // 消息文本可以不用填写
                                           .show();

                                   new Handler().postDelayed(() -> {
                                       toast(data.getMsg());
                                       dialog.dismiss();
                                       onRefresh(mSrlListCommon);
                                   }, 1000);
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(getActivity(), OrderDetailActivity.class)
                .putExtra("id", datas.get(position).getId()));
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
                                        EventBus.getDefault().post(new LoginMessage("update"));
                                        ToastUtils.showShort(StringUtils.getString(R.string.pay_success));
                                        onRefresh(mSrlListCommon);
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
                .payNow(SPUtils.getUserId(), SPUtils.getToken(), id, paytype), this)
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
                onRefresh(mSrlListCommon);
            } else {
                ToastUtils.showShort(StringUtils.getString(R.string.pay_fail));
            }
        }
    }

}
