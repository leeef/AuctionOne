package com.hlnwl.auction.ui.user.info;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.StringUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.ui.shop.CommonPagerAdapter;
import com.hlnwl.auction.ui.user.bid.BidFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 08:56
 * 描述：
 */
public class MoneyDetailActivity extends MyActivity {

    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.my_tl)
    SlidingTabLayout mMyTl;
    @BindView(R.id.my_vp)
    ViewPager mMyVp;

    private static final String[] CHANNELS = new String[]
            {StringUtils.getString(R.string.income),
            StringUtils.getString(R.string.spending),
            StringUtils.getString(R.string.withdrawal)};
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
        mTitleTb.setTitle(StringUtils.getString(R.string.detail));
        initFragment();
    }
    private void initFragment() {
        fragmentList.add(new MoneyDetailFragment("0"));
        fragmentList.add(new MoneyDetailFragment("1"));
        fragmentList.add(new WithdrawalFragment());

        mMyVp.setAdapter(new CommonPagerAdapter(getSupportFragmentManager(), fragmentList));
        mMyTl.setViewPager(mMyVp, CHANNELS);

    }


    @Override
    protected void initData() {

    }
}
