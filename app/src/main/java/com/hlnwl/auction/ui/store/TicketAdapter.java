package com.hlnwl.auction.ui.store;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.user.TicketListBean;
import com.hlnwl.auction.utils.StringsUtils;

import java.util.List;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/6/25 13:45
 */
public class TicketAdapter extends BaseQuickAdapter<TicketListBean.DataBean.CouponBean, BaseViewHolder> {

    public TicketAdapter(@Nullable List<TicketListBean.DataBean.CouponBean> data) {
        super(R.layout.adapter_ticket, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TicketListBean.DataBean.CouponBean item) {
        helper.setText(R.id.name, item.getName());
        helper.setText(R.id.money, item.getPrice());
        helper.setText(R.id.time, StringUtils.getString(R.string.expiration_time) + StringsUtils.date(item.getEndtime() + "000"));

    }
}
