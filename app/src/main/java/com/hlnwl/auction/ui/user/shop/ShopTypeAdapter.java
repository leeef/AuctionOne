package com.hlnwl.auction.ui.user.shop;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.shop.ShopTypeBean;

import java.util.List;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/6/11 16:01
 */
public class ShopTypeAdapter extends BaseQuickAdapter<ShopTypeBean.DataBean.ShopBean, BaseViewHolder> {
    public int selectIndex;

    public ShopTypeAdapter(@Nullable List<ShopTypeBean.DataBean.ShopBean> data) {
        super(R.layout.adapter_shop_type, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopTypeBean.DataBean.ShopBean item) {
        helper.setText(R.id.shop_name, item.getName());
        ImageView typeImage = helper.getView(R.id.type_image);
        if (helper.getAdapterPosition() == selectIndex) {
            typeImage.setImageResource(R.mipmap.kuang_xz);
        } else {
            typeImage.setImageResource(R.mipmap.kuang);
        }
    }
}
