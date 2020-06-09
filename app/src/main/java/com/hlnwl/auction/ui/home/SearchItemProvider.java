package com.hlnwl.auction.ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.hlnwl.auction.R;
import com.hlnwl.auction.app.MyApplication;
import com.hlnwl.auction.bean.home.HomeMultipleItem;
import com.hlnwl.auction.ui.category.SearchActivity;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 10:02
 * 描述：
 */
public class SearchItemProvider extends BaseItemProvider<HomeMultipleItem, BaseViewHolder> {
    @Override
    public int viewType() {
        return HomeAdapter.SEARCH;
    }

    @Override
    public int layout() {
        return R.layout.home_search;
    }

    @Override
    public void convert(BaseViewHolder helper, HomeMultipleItem data, int position) {
        TextView search=helper.getView(R.id.home_search);
        LinearLayout se=helper.getView(R.id.home_search_img);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, SearchActivity.class));
            }
        });
        se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, SearchActivity.class));
            }
        });
    }
}
