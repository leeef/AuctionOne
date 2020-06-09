package com.hlnwl.auction.ui.home;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.hlnwl.auction.R;
import com.hlnwl.auction.app.MyApplication;
import com.hlnwl.auction.bean.home.HomeMultipleItem;
import com.hlnwl.auction.view.banner.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 09:39
 * 描述：
 */
public class BannerItemProvider extends BaseItemProvider <HomeMultipleItem, BaseViewHolder> {
    private List<String> bannerImgs = new ArrayList<>();
    @Override
    public int viewType() {
        return HomeAdapter.BANNER;
    }

    @Override
    public int layout() {
        return R.layout.home_banner;
    }

    @Override
    public void convert(BaseViewHolder helper, HomeMultipleItem data, int position) {
        bannerImgs.clear();
        for (int i = 0; i < data.getBanner().size(); i++) {
            bannerImgs.add(data.getBanner().get(i).getIcon());
        }
        Banner banner = helper.getView(R.id.home_banner);
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

    }
}
