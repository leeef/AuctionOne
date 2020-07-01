package com.hlnwl.auction.ui.store;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/6/25 13:45
 */
public class TicketAdapter extends BaseMultiItemQuickAdapter<BaseHolderBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TicketAdapter(List<BaseHolderBean> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseHolderBean item) {

    }
}
