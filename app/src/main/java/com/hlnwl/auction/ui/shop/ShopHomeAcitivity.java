package com.hlnwl.auction.ui.shop;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.allen.library.CircleImageView;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.shop.ShopListBean;
import com.hlnwl.auction.message.RefreshMessage;
import com.hlnwl.auction.message.StopRefreshMessage;
import com.hlnwl.auction.ui.home.HomeFragment;
import com.hlnwl.auction.utils.my.XCollapsingToolbarLayout;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/18 09:48
 * 描述：
 */
public class ShopHomeAcitivity extends MyActivity implements OnRefreshListener {

    @BindView(R.id.my_tl)
    SlidingTabLayout mMyTl;
    @BindView(R.id.my_vp)
    ViewPager mMyVp;
    @BindView(R.id.shop_home_name)
    TextView mShopHomeName;
    @BindView(R.id.shop_home_img)
    CircleImageView mShopHomeImg;


    @BindView(R.id.tb_test_a_bar)
    TitleBar mTbTestABar;
    @BindView(R.id.ctl_test_bar)
    XCollapsingToolbarLayout mCtlTestBar;
    @BindView(R.id.abl_test_bar)
    AppBarLayout mAblTestBar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSrlListCommon;

    //    @BindView(R.id.title_tb)
//    TitleBar mTitleTb;

    private List<Fragment> fragmentList = new ArrayList<>();
    private static final String[] CHANNELS = new String[]{StringUtils.getString(R.string.goods_list),
            StringUtils.getString(R.string.shop_comment)};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
//        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 0);

        mShopHomeName.setText(getIntent().getStringExtra("name"));
        ImageLoaderUtils.display(this,mShopHomeImg,getIntent().getStringExtra("pic"));
        mSrlListCommon.setRefreshHeader(new ClassicsHeader(getActivity()));
        mSrlListCommon.setHeaderHeight(60);
        mSrlListCommon.setOnRefreshListener(this);

        mSrlListCommon.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(3000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
//                mOffset = offset / 2;
//                parallax.setTranslationY(mOffset - mScrollY);
//                mAblTestBar.setAlpha(1 - Math.min(percent, 1));
            }
        });
        setCollsapsing();
        initFragment();
    }

    private void setCollsapsing() {

        mCtlTestBar.setStatusBarScrim(getResources().getDrawable(R.mipmap.xiangqing_beijing));

        mCtlTestBar.setContentScrim(getResources().getDrawable(R.mipmap.xiangqing_beijing));
    }

    private void initFragment() {
        fragmentList.add(new ShopGoodsFragment(getIntent().getStringExtra("id")));
        fragmentList.add(new ShopCommentFragment(getIntent().getStringExtra("id")));
        mMyVp.setAdapter(new CommonPagerAdapter(getSupportFragmentManager(), fragmentList));
        mMyTl.setViewPager(mMyVp, CHANNELS);
    }


    @Override
    protected void initData() {

    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        EventBus.getDefault().post(new RefreshMessage("refresh"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void stopRefreshEventBus(StopRefreshMessage stopRefresh) {
        if (mSrlListCommon != null) {
            mSrlListCommon.finishRefresh();
        }
    }
}
