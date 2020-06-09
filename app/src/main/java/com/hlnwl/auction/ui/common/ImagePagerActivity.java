package com.hlnwl.auction.ui.common;

import android.annotation.SuppressLint;

import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018/7/27.
 */

public class ImagePagerActivity extends MyActivity {

    @BindView(R.id.image_viewpager)
    ViewPager imageViewpager;
    ArrayList<String> urls = new ArrayList<>();
    @BindView(R.id.image_viewpager_current)
    TextView imageViewpagerCurrent;
    @BindView(R.id.image_viewpager_size)
    TextView imageViewpagerSize;
    private List<Fragment> fragments = new ArrayList<>();
    private int position = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_pager;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        urls = getIntent().getStringArrayListExtra("imageUrls");
        position = Integer.parseInt(getIntent().getStringExtra("position"));
        for (int i = 0; i < urls.size(); i++) {
            fragments.add(new ImageViewPagerFragment());
        }
        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, urls);
        imageViewpager.setAdapter(mAdapter);
        imageViewpagerCurrent.setText(position+1+"");
        imageViewpagerSize.setText(urls.size()+"");
        imageViewpager.setCurrentItem(position);
        imageViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                imageViewpagerCurrent.setText(position+1+"");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        BarUtils.setStatusBarColor(this,getResources().getColor(R.color.black));
    }

    @SuppressLint("ResourceType")
    @Override
    public void initData() {
    }




    private class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;
        private List<String> urls;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> urls) {
            super(fm);
            this.urls = urls;
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


        @Override
        public Fragment getItem(int position) {
            ImageViewPagerFragment imageViewPagerFragment = new ImageViewPagerFragment();
            imageViewPagerFragment.init(urls.get(position));
            return imageViewPagerFragment;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ImmersionBar.with(this).destroy();
    }
}
