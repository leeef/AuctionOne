package com.hlnwl.auction.ui.goods;

import androidx.annotation.Nullable;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.goods.OfferBean;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/24 10:05
 * 描述：
 */
public class OfferAdapter extends BaseQuickAdapter<OfferBean.DataBean, BaseViewHolder> {
    public OfferAdapter() {
        super(R.layout.item_offer);
    }

    @Override
    protected void convert(BaseViewHolder helper, OfferBean.DataBean item) {
        SuperTextView offer=helper.getView(R.id.item_offfer);
        ImageLoaderUtils.display(mContext,offer.getLeftIconIV(),item.getFace_img());
        offer.setLeftTopString(item.getTheme());
        offer.setLeftBottomString(mContext.getResources().getString(R.string.danwei)+item.getPrice());
        if (helper.getAdapterPosition()==0){
            offer.setRightTopString(mContext.getResources().getString(R.string.leading));
            offer.setRightTopTextColor(mContext.getResources().getColor(R.color.main));
        }else {
            offer.setRightTopString(mContext.getResources().getString(R.string.out));
            offer.setRightTopTextColor(mContext.getResources().getColor(R.color.default_color));
        }
        offer.setRightBottomString(item.getAddtime());
    }
}
