package com.hlnwl.auction.ui.shop;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.hlnwl.auction.bean.home.HomeMultipleItem;
import com.hlnwl.auction.ui.home.BannerItemProvider;
import com.hlnwl.auction.ui.home.SearchItemProvider;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 14:25
 * 描述：
 */
public class ShopListAdapter  extends MultipleItemRvAdapter<ShopMultipleItem, BaseViewHolder> {
    public static final int TOP = 100;
    public static final int IMG = 200;
    public ShopListAdapter(@Nullable List<ShopMultipleItem> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(ShopMultipleItem entity) {
        if (entity.itemType == ShopMultipleItem.TOP) {
            return TOP;
        } else if (entity.itemType == ShopMultipleItem.IMG) {
            return IMG;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new ShopTopItemProvider());
        mProviderDelegate.registerProvider(new ShopImgItemProvider());
    }
}
