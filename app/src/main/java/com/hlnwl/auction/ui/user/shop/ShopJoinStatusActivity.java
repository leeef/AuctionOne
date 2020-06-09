package com.hlnwl.auction.ui.user.shop;

import android.os.Bundle;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/25 16:21
 * 描述：
 */
public class ShopJoinStatusActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.shop_join_status_time)
    TextView mShopJoinStatusTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_join_status;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(getResources().getString(R.string.join_status));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
