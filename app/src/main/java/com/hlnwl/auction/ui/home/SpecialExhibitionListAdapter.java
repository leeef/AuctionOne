package com.hlnwl.auction.ui.home;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.hlnwl.auction.bean.home.SpecialExhibitionMultipleItem;
import com.hlnwl.auction.ui.shop.ShopMultipleItem;

import java.util.List;

/**
  *
 *
 */
public class SpecialExhibitionListAdapter extends MultipleItemRvAdapter<SpecialExhibitionMultipleItem, BaseViewHolder> {
    public static final int TOP = 1;
    public static final int IMG = 2;
    public static final int BANNER = 3;
    public SpecialExhibitionListAdapter(@Nullable List<SpecialExhibitionMultipleItem> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(SpecialExhibitionMultipleItem entity) {
        if (entity.itemType == SpecialExhibitionMultipleItem.TOP) {
            return TOP;
        } else if (entity.itemType == SpecialExhibitionMultipleItem.IMG) {
            return IMG;
        } else if (entity.itemType == SpecialExhibitionMultipleItem.BANNER) {
            return BANNER;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new SpecialExhibitionBannerItemProvider());
        mProviderDelegate.registerProvider(new SpecialExhibitionTopItemProvider());
        mProviderDelegate.registerProvider(new SpecialExhibitionImageItemProvider());
    }
}
