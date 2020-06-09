package com.hlnwl.auction.ui.home;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.hlnwl.auction.bean.home.HomeMultipleItem;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 09:34
 * 描述：
 */
public class HomeAdapter extends MultipleItemRvAdapter<HomeMultipleItem, BaseViewHolder> {
    public static final int BANNER = 100;
    public static final int SEARCH = 200;
    public static final int ICONS = 300;
    public static final int MESSAGE = 400;
    public static final int IMAGE = 500;
    public static final int CONTENT = 600;
    public static final int EMPTY = 700;

    public HomeAdapter(@Nullable List<HomeMultipleItem> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(HomeMultipleItem entity) {
        if (entity.itemType == HomeMultipleItem.BANNER) {
            return BANNER;
        } else if (entity.itemType == HomeMultipleItem.SEARCH) {
            return SEARCH;
        } else if (entity.itemType == HomeMultipleItem.ICONS) {
            return ICONS;
        }else if (entity.itemType == HomeMultipleItem.MESSAGE) {
            return MESSAGE;
        } else if (entity.itemType == HomeMultipleItem.IMAGE) {
            return IMAGE;
        } else if (entity.itemType == HomeMultipleItem.CONTENT) {
            return CONTENT;
        }else if (entity.itemType == HomeMultipleItem.EMPTY){
            return EMPTY;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new BannerItemProvider());
        mProviderDelegate.registerProvider(new SearchItemProvider());
        mProviderDelegate.registerProvider(new IconItemProvider());
        mProviderDelegate.registerProvider(new MessageItemProvider());
        mProviderDelegate.registerProvider(new ImageItemProvider());
        mProviderDelegate.registerProvider(new ContentItemProvider());
        mProviderDelegate.registerProvider(new EmptyItemProvider());
    }
}
