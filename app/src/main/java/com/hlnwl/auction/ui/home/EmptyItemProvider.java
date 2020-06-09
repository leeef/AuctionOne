package com.hlnwl.auction.ui.home;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.home.HomeMultipleItem;


/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/5 15:41
 * 描述：
 */
public class EmptyItemProvider extends BaseItemProvider<HomeMultipleItem, BaseViewHolder> {
    @Override
    public int viewType() {
        return HomeAdapter.EMPTY;
    }

    @Override
    public int layout() {
        return R.layout.layout_empty;
    }

    @Override
    public void convert(BaseViewHolder helper, HomeMultipleItem data, int position) {

    }
}
