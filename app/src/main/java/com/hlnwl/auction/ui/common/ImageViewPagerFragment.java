package com.hlnwl.auction.ui.common;


import android.net.Uri;

import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyLazyFragment;

import butterknife.BindView;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by Administrator on 2018/7/27.
 */

public class ImageViewPagerFragment extends MyLazyFragment {

    @BindView(R.id.photo_drawee_view)
    PhotoDraweeView photoView;
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;


    private String url;

    public void init(String url) {
        this.url = url;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_viewpager;
    }

    @Override
    protected int getTitleId() {
        return R.id.title_tb;
    }


    @Override
    protected void initView() {
        photoView.setPhotoUri(Uri.parse(url));
        mTitleTb.setLeftIcon(null);
//        ImmersionBar.with(this)
//                .flymeOSStatusBarFontColor(R.color.white)
//                .init();
    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }

    /**
     * 获取状态栏字体颜色
     */
    protected boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        ImmersionBar.with(this).destroy();
    }


}
