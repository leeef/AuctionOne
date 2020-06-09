package com.hlnwl.auction.view.popup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;


/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/7/19 08:51
 * 描述：
 */
public class MorePopupAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MorePopupAdapter() {
        super(R.layout.item_popup_more);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_popup_more_content,item);
    }
}
