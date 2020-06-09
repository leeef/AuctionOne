package com.hlnwl.auction.ui.shop;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/18 14:20
 * 描述：
 */
public class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TestAdapter() {
        super(R.layout.item_test);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.test,item);
    }
}
