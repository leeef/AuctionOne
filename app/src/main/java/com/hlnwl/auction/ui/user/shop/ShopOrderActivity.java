package com.hlnwl.auction.ui.user.shop;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.StringUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.ui.shop.CommonPagerAdapter;
import com.hlnwl.auction.ui.user.order.OrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 11:15
 * 描述：
 */
public class ShopOrderActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.my_tl)
    SlidingTabLayout mMyTl;
    @BindView(R.id.my_vp)
    ViewPager mMyVp;

    private static final String[] CHANNELS = new String[]{
            StringUtils.getString(R.string.to_be_payment),
            StringUtils.getString(R.string.to_be_shipped),
            StringUtils.getString(R.string.to_be_received),
            StringUtils.getString(R.string.completed)};
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(StringUtils.getString(R.string.bid_record));
        initFragment();
    }

    private void initFragment() {
        fragmentList.add(new ShopOrderFragment("1","1"));
        fragmentList.add(new ShopOrderFragment("2","1"));
        fragmentList.add(new ShopOrderFragment("2","2"));
        fragmentList.add(new ShopOrderFragment("2","3"));


        mMyVp.setAdapter(new CommonPagerAdapter(getSupportFragmentManager(), fragmentList));
        mMyTl.setViewPager(mMyVp, CHANNELS);
        mMyTl.setCurrentTab(Integer.parseInt(getIntent().getStringExtra("type")));

    }
    @Override
    protected void initData() {

    }
}
