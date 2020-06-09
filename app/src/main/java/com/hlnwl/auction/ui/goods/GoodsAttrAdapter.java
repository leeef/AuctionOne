package com.hlnwl.auction.ui.goods;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.goods.GoodsDetailBean;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/23 14:33
 * 描述：
 */
public class GoodsAttrAdapter extends BaseQuickAdapter<GoodsDetailBean.DataBean.GoodsAttr, BaseViewHolder> {
    public GoodsAttrAdapter() {
        super(R.layout.item_goods_attr);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailBean.DataBean.GoodsAttr item) {
        helper.setText(R.id.item_goods_attr_title,"【"+item.getSname()+"】");
        helper.setText(R.id.item_goods_attr_name,item.getSprice());
    }
}
