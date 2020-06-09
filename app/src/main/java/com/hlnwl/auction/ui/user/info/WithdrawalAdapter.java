package com.hlnwl.auction.ui.user.info;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.user.info.WithdrawalRecordBean;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 09:18
 * 描述：
 */
public class WithdrawalAdapter extends BaseQuickAdapter<WithdrawalRecordBean.DataBean, BaseViewHolder> {
    public WithdrawalAdapter() {
        super(R.layout.item_withdrawal);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawalRecordBean.DataBean item) {
        helper.setText(R.id.item_money_common_name, item.getPaytype());
        helper.setText(R.id.item_money_common_time, item.getAddtime());
        helper.setText(R.id.item_money_common_num,  item.getAmount());
        helper.setText(R.id.item_money_common_status,  item.getText_status());
    }
}
