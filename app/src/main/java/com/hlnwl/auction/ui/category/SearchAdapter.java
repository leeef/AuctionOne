package com.hlnwl.auction.ui.category;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/10/15 14:29
 * 描述：
 */
public class SearchAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SearchAdapter() {
        super(R.layout.activity_search_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_flow,item);
    }
}
