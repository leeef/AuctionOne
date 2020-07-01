package com.hlnwl.auction.ui.store;

import android.graphics.Paint;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hlnwl.auction.R;

import java.util.List;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/6/25 9:46
 */
public class GoodListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public GoodListAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_good_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        SimpleDraweeView cover = helper.getView(R.id.cover);
//        FrescoUtils.showBasicPic("", cover);
        helper.setText(R.id.name, "111");
        helper.setText(R.id.sell_price, "$111");
        TextView marketPrice = helper.getView(R.id.market_price);
        marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        marketPrice.setText("$111");
    }
}
