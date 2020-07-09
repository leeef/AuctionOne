package com.hlnwl.auction.ui.store;

import android.graphics.Paint;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.goods.GoodListBean;
import com.hlnwl.auction.utils.my.FrescoUtils;

import java.util.List;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/6/25 9:46
 */
public class GoodListAdapter extends BaseQuickAdapter<GoodListBean.DataBean, BaseViewHolder> {
    public GoodListAdapter(@Nullable List<GoodListBean.DataBean> data) {
        super(R.layout.adapter_good_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodListBean.DataBean item) {
        SimpleDraweeView cover = helper.getView(R.id.cover);
        FrescoUtils.showBasicPic(item.getPic(), cover);
        helper.setText(R.id.name, item.getName());
        helper.setText(R.id.sell_price, mContext.getResources().getString(R.string.money) + item.getPrice());
        TextView marketPrice = helper.getView(R.id.market_price);
        marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        marketPrice.setText(mContext.getResources().getString(R.string.money) + item.getMoney());
    }
}
