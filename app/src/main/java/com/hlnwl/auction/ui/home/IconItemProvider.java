package com.hlnwl.auction.ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.home.HomeMultipleItem;
import com.hlnwl.auction.ui.goods.GoodsCategoryAcitivity;
import com.hlnwl.auction.ui.shop.ShopListActivity;
import com.hlnwl.auction.ui.store.GoodListActivity;
import com.hlnwl.auction.utils.sp.SPUtils;

import java.util.Locale;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 10:06
 * 描述：
 */
public class IconItemProvider extends BaseItemProvider<HomeMultipleItem, BaseViewHolder> {
    @Override
    public int viewType() {
        return HomeAdapter.ICONS;
    }

    @Override
    public int layout() {
        return R.layout.home_icon;
    }

    @Override
    public void convert(BaseViewHolder helper, HomeMultipleItem data, int position) {
        LinearLayout shop_list = helper.getView(R.id.item_home_icons_shop);
        LinearLayout designer_frames = helper.getView(R.id.item_home_icons_designer_frames);
        LinearLayout leak_hunting_area = helper.getView(R.id.item_home_icons_leak_hunting_area);
        LinearLayout new_user_on_road = helper.getView(R.id.item_home_icons_new_user_on_road);
        LinearLayout website = helper.getView(R.id.item_home_icons_website);
        if (SPUtils.getLanguage() == null) {
            String languageName = getCurrentLauguageUseResources();

            if (languageName.equals("zh")) {
                leak_hunting_area.setVisibility(View.VISIBLE);
            } else {
                leak_hunting_area.setVisibility(View.GONE);
            }
        } else {
            String selectedLanguage = SPUtils.getLanguage();
            if (selectedLanguage.equals("1")) {
                leak_hunting_area.setVisibility(View.VISIBLE);
            } else {
                leak_hunting_area.setVisibility(View.GONE);
            }
        }


        shop_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, ShopListActivity.class));
            }
        });
        designer_frames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, GoodsCategoryAcitivity.class)
                        .putExtra("category", "jingpin"));
            }
        });
        leak_hunting_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, GoodsCategoryAcitivity.class)
                        .putExtra("category", "jianlou"));
            }
        });

        new_user_on_road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, SpecialExhibitionActivity.class));
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CommonWebActivity.runActivity(mContext, mContext.getString(R.string.website),
//                        Constants.URL);
                mContext.startActivity(new Intent(mContext, GoodListActivity.class));
            }
        });
    }

    private String getCurrentLauguageUseResources() {
        /**
         * 获得当前系统语言
         */
        Locale locale = mContext.getResources().getConfiguration().locale;
        String language = locale.getLanguage(); // 获得语言码
        return language;
    }
}
