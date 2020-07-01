package com.hlnwl.auction.ui.user;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.flod.loadingbutton.LoadingButton;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyLazyFragment;
import com.hlnwl.auction.bean.user.MineBean;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.message.QuitMessage;
import com.hlnwl.auction.ui.common.LoginActivity;
import com.hlnwl.auction.ui.home.QuestionnaireActivity;
import com.hlnwl.auction.ui.store.TicketActivity;
import com.hlnwl.auction.ui.user.bid.BidRecordActivity;
import com.hlnwl.auction.ui.user.info.BalanceActivity;
import com.hlnwl.auction.ui.user.info.SettingActivity;
import com.hlnwl.auction.ui.user.order.OrderActivity;
import com.hlnwl.auction.ui.user.shop.ShopCenterActivity;
import com.hlnwl.auction.ui.user.shop.ShopJoinStatusActivity;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.my.FrescoUtils;
import com.hlnwl.auction.utils.my.PhoneUtil;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.MessageDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/16 11:07
 * 描述：
 */
public class UserFragment extends MyLazyFragment {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.user_img)
    SimpleDraweeView mUserImg;
    @BindView(R.id.user_nick)
    TextView mUserNick;
    @BindView(R.id.user_seller_center)
    SuperTextView mUserSellerCenter;
    @BindView(R.id.quit)
    LoadingButton mQuit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected int getTitleId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setLeftIcon(null);
        mTitleTb.setTitle(getActivity().getResources().getString(R.string.mine));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (SPUtils.getLogin() != null && SPUtils.getLogin().length() > 0) {
                getData();
            }
        }

    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }

    @Override
    protected void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginEventBus(LoginMessage update) {
        getData();

    }

    private void getData() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getUserInfo(SPUtils.getLanguage(), SPUtils.getUserId(), SPUtils.getToken()), this)
                .subscribe(new ApiObserver<MineBean>() {
                               @Override
                               protected void success(MineBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }

//                                   mData = data.getData().get(0);
                                   SPUtils.setToken(data.getData().get(0).getToken());
                                   SPUtils.setUserId(data.getData().get(0).getId());
                                   SPUtils.setNickname(StringUtils.null2Length0(data.getData().get(0).getTheme()));

                                   SPUtils.setRealname(StringUtils.null2Length0(data.getData().get(0).getRealname()));
                                   SPUtils.setPhone(StringUtils.null2Length0(data.getData().get(0).getMobile()));
                                   SPUtils.setHeadimg(StringUtils.null2Length0(data.getData().get(0).getFace_img()));
                                   SPUtils.setChant(StringUtils.null2Length0(data.getData().get(0).getChant()));
                                   SPUtils.setIsBankCard(StringUtils.null2Length0(data.getData().get(0).getBank()));
                                   SPUtils.setIsAlipay(StringUtils.null2Length0(data.getData().get(0).getAlipay()));
                                   SPUtils.setBalance(StringUtils.null2Length0(data.getData().get(0).getU_money()));
                                   SPUtils.setMinTx(StringUtils.null2Length0(data.getData().get(0).getTixian_num()));
                                   SPUtils.setDage(StringUtils.null2Length0(data.getData().get(0).getDage()));
                                   SPUtils.setJing(StringUtils.null2Length0(data.getData().get(0).getJing()));
                                   SPUtils.setLou(StringUtils.null2Length0(data.getData().get(0).getLou()));
//                                   SPUtils.setShopqb(StringsUtils.null2Length0(data.getData().get(0).getPay_points()));

//                                   if ( data.getData().get(0).getPaypwd() != null &&  data.getData().get(0).getPaypwd().length() > 0) {
//                                       SPUtils.setIsSetPayPwd("paypwd");
//                                   }
                                   if (SPUtils.getNickname().length() > 0) {
                                       mUserNick.setText(SPUtils.getNickname());
                                   } else {
                                       mUserNick.setText(SPUtils.getPhone());
                                   }
                                   if (SPUtils.getChant().equals("2")) {
                                       mUserSellerCenter.setLeftString(StringUtils.getString(R.string.seller_center));
                                   } else {
                                       mUserSellerCenter.setLeftString(StringUtils.getString(R.string.shop_join));
                                   }
                                   FrescoUtils.showBasicPic(SPUtils.getHeadimg(), mUserImg);
//                                   ImageLoaderUtils.display(getActivity(), mUserImg,  SPUtils.getHeadimg());
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    @OnClick({R.id.user_dfk, R.id.user_dfh, R.id.user_dsh, R.id.user_ywc,
            R.id.user_balance, R.id.user_share, R.id.user_modify_data,
            R.id.user_system_bulletin, R.id.user_customer,
            R.id.user_bid_record, R.id.user_seller_center, R.id.quit, R.id.my_ticket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_dfk://评价
                startActivity(new Intent(getActivity(), OrderActivity.class)
                        .putExtra("type", "2"));
                break;
            case R.id.user_dfh://代发货
                startActivity(new Intent(getActivity(), OrderActivity.class)
                        .putExtra("type", "0"));
                break;
            case R.id.user_dsh://待收货
                startActivity(new Intent(getActivity(), OrderActivity.class)
                        .putExtra("type", "1"));
                break;
            case R.id.user_ywc://已完成
                startActivity(new Intent(getActivity(), OrderActivity.class)
                        .putExtra("type", "3"));
                break;
            case R.id.user_balance://我的余额
                startActivity(BalanceActivity.class);
                break;
            case R.id.user_share://我的分享
                break;
            case R.id.user_modify_data://修改资料
                startActivity(SettingActivity.class);
                break;
            case R.id.user_system_bulletin://系统公告
                startActivity(NoticeActivity.class);
                break;
            case R.id.user_customer://客服
                PhoneUtil.callPhone(getActivity(), SPUtils.getCustomerPhone());
                break;
            case R.id.user_bid_record://出价记录
                startActivity(BidRecordActivity.class);
                break;
            case R.id.user_seller_center://卖家中心
                if (SPUtils.getChant().equals("2")) {
                    startActivity(ShopCenterActivity.class);
                } else if (SPUtils.getChant().equals("0")) {
                    startActivity(QuestionnaireActivity.class);

                } else if (SPUtils.getChant().equals("1")) {
                    startActivity(ShopJoinStatusActivity.class);
                } else if (SPUtils.getChant().equals("3")) {
                    toast(StringUtils.getString(R.string.shop_colse));
                }
                break;
            case R.id.my_ticket://优惠券
                startActivity(TicketActivity.class);
                break;
            case R.id.quit:
                mQuit.start();
                new MessageDialog.Builder(getActivity())
                        .setTitle(getActivity().getResources().getString(R.string.quit_login)) // 标题可以不用填写
                        .setMessage(getActivity().getResources().getString(R.string.sure_quit_login))
                        .setConfirm(getActivity().getResources().getString(R.string.confirm))
                        .setCancel(getActivity().getResources().getString(R.string.picture_cancel)) // 设置 null 表示不显示取消按钮
                        //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(Dialog dialog) {
                                SPUtils.clear(getActivity());
                                EventBus.getDefault().post(new QuitMessage("quit"));
                                mQuit.complete();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
                                startActivity(intent);
//                                ActivityUtils.finishToActivity(IndexActivity.class, true);
                            }

                            @Override
                            public void onCancel(Dialog dialog) {
                                mQuit.fail();
                                toast(getActivity().getResources().getString(R.string.picture_cancel));
                            }
                        })
                        .show();
                break;
        }
    }
}
