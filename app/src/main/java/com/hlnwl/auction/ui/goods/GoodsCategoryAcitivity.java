package com.hlnwl.auction.ui.goods;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.flyco.tablayout.SlidingTabLayout;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.goods.GoodsCategoryBean;
import com.hlnwl.auction.message.RefreshMessage;
import com.hlnwl.auction.message.StopRefreshMessage;
import com.hlnwl.auction.ui.shop.CommonPagerAdapter;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.banner.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;
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
 * 创建日期：2019/9/21 10:01
 * 描述：精品区
 */
public class GoodsCategoryAcitivity extends MyActivity implements OnRefreshListener {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.home_banner)
    Banner banner;
    @BindView(R.id.my_tl)
    SlidingTabLayout mMyTl;
    @BindView(R.id.my_vp)
    ViewPager mMyVp;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSrlListCommon;

    private String mType="";
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private List<String> bannerImgs = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_designer;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mSrlListCommon.setRefreshHeader(new ClassicsHeader(getActivity()));
        mSrlListCommon.setHeaderHeight(60);
        mSrlListCommon.setOnRefreshListener(this);

    }

    @Override
    protected void initData() {
        mType=getIntent().getStringExtra("category");
        if (mType.equals("jingpin")){
            mTitleTb.setTitle(getResources().getString(R.string.designer_frames));
            getData();
        }else {
            mTitleTb.setTitle(getResources().getString(R.string.leak_hunting_area));
            getDataHunt( );
        }
    }

    private void getData() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getDesigner(SPUtils.getLanguage(),1, "1"), this)
                .subscribe(new ApiObserver<GoodsCategoryBean>() {
                               @Override
                               protected void success(GoodsCategoryBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       showError();
                                       return;
                                   }
                                   initUi(data.getData().get(0));
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   showError();
                                   toast(t.getMessage());
                               }
                           }
                );
    }
    private void getDataHunt() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getHunt(SPUtils.getLanguage(),1, "1"), this)
                .subscribe(new ApiObserver<GoodsCategoryBean>() {
                               @Override
                               protected void success(GoodsCategoryBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       showError();
                                       return;
                                   }
                                   initUi(data.getData().get(0));
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   showError();
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private void initUi(GoodsCategoryBean.DataBean dataBean) {
        bannerImgs.clear();
        for (int i = 0; i < dataBean.getLun().size(); i++) {
            bannerImgs.add(dataBean.getLun().get(i).getIcon());
        }

//                banner.setFocusableInTouchMode(true);
//                banner.requestFocus();
        //设置banner指示器样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(bannerImgs);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//                banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        for (GoodsCategoryBean.DataBean.CateBean cate : dataBean.getCate()) {
            titleList.add(cate.getName());
        }
        String[] titles = new String[titleList.size()];
        titleList.toArray(titles);
        for (int i = 0; i < dataBean.getCate().size(); i++) {
            fragmentList.add(new GoodsCategoryFragment(dataBean.getCate().get(i).getId(),mType));
        }
        mMyVp.setAdapter(new CommonPagerAdapter(getSupportFragmentManager(), fragmentList));
        mMyTl.setViewPager(mMyVp, titles);

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
