package com.hlnwl.auction.ui.user.info;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.user.info.MoneyDetailBean;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 09:08
 * 描述：
 */
public class MoneyDetailAdapter extends BaseQuickAdapter<MoneyDetailBean .DataBean, BaseViewHolder> {
    public MoneyDetailAdapter() {
        super(R.layout.item_money_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, MoneyDetailBean.DataBean item) {
        helper.setText(R.id.item_money_common_name, item.getDes());
        helper.setText(R.id.item_money_common_time, item.getAddtime());
        helper.setText(R.id.item_money_common_num,  item.getAmount());
    }
}
