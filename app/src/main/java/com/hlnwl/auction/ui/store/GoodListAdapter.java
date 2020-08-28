package com.hlnwl.auction.ui.store;

import android.graphics.Paint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
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
public class GoodListAdapter extends BaseMultiItemQuickAdapter<BaseHolderBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GoodListAdapter(List<BaseHolderBean> data) {
        super(data);
        addItemType(1, R.layout.adapter_good_list_top);
        addItemType(0, R.layout.adapter_good_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseHolderBean item) {

        if (helper.getItemViewType() == 0) {
            GoodListBean.DataBean dataBean = (GoodListBean.DataBean) item;
            SimpleDraweeView cover = helper.getView(R.id.cover);
            FrescoUtils.showBasicPic(dataBean.getPic(), cover);
            helper.setText(R.id.name, dataBean.getName());
            helper.setText(R.id.sell_price, mContext.getResources().getString(R.string.money) + dataBean.getPrice());
            TextView marketPrice = helper.getView(R.id.market_price);
            marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            marketPrice.setText(mContext.getResources().getString(R.string.money) + dataBean.getMoney());
        }
    }
}
