package com.hlnwl.auction.ui.user.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.blankj.utilcode.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.ui.release.ReleaseActivity;
import com.hlnwl.auction.ui.user.info.BalanceActivity;
import com.hlnwl.auction.ui.user.order.OrderActivity;
import com.hlnwl.auction.utils.my.FrescoUtils;
import com.hlnwl.auction.utils.sp.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/16 16:49
 * 描述：
 */
public class ShopCenterActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.shop_center_img)
    SimpleDraweeView mShopCenterImg;
    @BindView(R.id.shop_center_nick)
    TextView mShopCenterNick;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_center;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(StringUtils.getString(R.string.seller_center));
        if (SPUtils.getNickname().length() > 0) {
            mShopCenterNick.setText(SPUtils.getNickname());
        } else {
            mShopCenterNick.setText(SPUtils.getPhone());
        }
        FrescoUtils.showBasicPic(SPUtils.getHeadimg(),mShopCenterImg);

    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.user_dfh, R.id.user_dsh, R.id.user_dfk, R.id.user_ywc,
            R.id.shop_center_apply, R.id.shop_center_release,
            R.id.shop_center_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_dfh:
                startActivity(new Intent(getActivity(), ShopOrderActivity.class)
                        .putExtra("type","1"));
                break;
            case R.id.user_dsh:
                startActivity(new Intent(getActivity(), ShopOrderActivity.class)
                        .putExtra("type","2"));
                break;
            case R.id.user_dfk:
                startActivity(new Intent(getActivity(),ShopOrderActivity.class)
                        .putExtra("type","0"));
                break;
            case R.id.user_ywc:
                startActivity(new Intent(getActivity(),ShopOrderActivity.class)
                        .putExtra("type","3"));
                break;
            case R.id.shop_center_apply:
                startActivity(BalanceActivity.class);
                break;
            case R.id.shop_center_release:
                if (SPUtils.getChant().equals("2")){
                    startActivity(ReleaseActivity.class);
                }else if (SPUtils.getChant().equals("0")){
                    startActivity(ShopJoinActivity.class);
                }else if (SPUtils.getChant().equals("1")){
                    startActivity(ShopJoinStatusActivity.class);
                }else if (SPUtils.getChant().equals("3")){
                    toast(StringUtils.getString(R.string.shop_colse));
                }
                //        startActivity(ReleaseActivity.class);
                break;
            case R.id.shop_center_edit:
                startActivity(GoodsEditActivity.class);
                break;
        }
    }



}
