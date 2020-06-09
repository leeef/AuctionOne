package com.hlnwl.auction.ui.shop;


import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 *         版本：1.0
 *         创建日期：2018/12/20
 *         描述：fragmentadapter
 */
public class CommonPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> fragmentList;

    public CommonPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;

    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }
    /**
     * //此方法用来显示tab上的名字
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
