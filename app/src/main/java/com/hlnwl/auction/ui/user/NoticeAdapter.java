package com.hlnwl.auction.ui.user;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.home.NewsData;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/30 15:29
 * 描述：
 */
public class NoticeAdapter extends BaseQuickAdapter<NewsData, BaseViewHolder> {
    public NoticeAdapter() {
        super(R.layout.item_notice);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsData item) {
        helper.setText(R.id.notice_title,item.getTitle());
        helper.setText(R.id.notice_time,item.getAdd_time());
    }
}
