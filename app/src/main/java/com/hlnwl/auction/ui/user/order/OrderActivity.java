package com.hlnwl.auction.ui.user.order;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.StringUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.message.OrderMessage;
import com.hlnwl.auction.ui.shop.CommonPagerAdapter;
import com.hlnwl.auction.ui.user.bid.BidFragment;
import com.hlnwl.auction.utils.sp.SPUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public class OrderActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.my_tl)
    SlidingTabLayout mMyTl;
    @BindView(R.id.my_vp)
    ViewPager mMyVp;

    private static final String[] CHANNELS = new String[]{StringUtils.getString(R.string.to_be_shipped),
            StringUtils.getString(R.string.to_be_received),
            StringUtils.getString(R.string.to_be_comment),
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
        mTitleTb.setTitle(StringUtils.getString(R.string.my_order));
        initFragment();
    }

    private void initFragment() {
        fragmentList.add(new OrderFragment("1"));
        fragmentList.add(new OrderFragment("2"));
        fragmentList.add(new OrderFragment("3"));
        fragmentList.add(new OrderFragment("4"));


        mMyVp.setAdapter(new CommonPagerAdapter(getSupportFragmentManager(), fragmentList));
        mMyTl.setViewPager(mMyVp, CHANNELS);
        mMyTl.setCurrentTab(Integer.parseInt(getIntent().getStringExtra("type")));

    }
    @Override
    protected void initData() {

    }
}
