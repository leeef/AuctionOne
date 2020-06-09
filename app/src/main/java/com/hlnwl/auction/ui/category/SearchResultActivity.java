package com.hlnwl.auction.ui.category;

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
 * 创建日期：2019/10/15 14:39
 * 描述：
 */
public class SearchResultActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.my_tl)
    SlidingTabLayout mMyTl;
    @BindView(R.id.my_vp)
    ViewPager mMyVp;

    private static final String[] CHANNELS = new String[]{StringUtils.getString(R.string.good),
            StringUtils.getString(R.string.shop),};
    private List<Fragment> fragmentList = new ArrayList<>();
    private String keyword="";
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
        keyword=getIntent().getStringExtra("keyword");
        mTitleTb.setTitle(keyword);
        initFragment();
    }

    private void initFragment() {
        fragmentList.add(new SearchGoodsFragment(keyword));
        fragmentList.add(new SearchShopsFragment(keyword));


        mMyVp.setAdapter(new CommonPagerAdapter(getSupportFragmentManager(), fragmentList));
        mMyTl.setViewPager(mMyVp, CHANNELS);
    }

    @Override
    protected void initData() {

    }
}
