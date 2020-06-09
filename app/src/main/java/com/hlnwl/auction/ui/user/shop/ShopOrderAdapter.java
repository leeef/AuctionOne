package com.hlnwl.auction.ui.user.shop;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.shop.ShopOrderBean;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 16:15
 * 描述：
 */
public class ShopOrderAdapter extends BaseQuickAdapter<ShopOrderBean.DataBean, BaseViewHolder> {
    public ShopOrderAdapter() {
        super(R.layout.item_shop_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopOrderBean.DataBean item) {
        ImageView img=helper.getView(R.id.item_shop_order_img);
        LinearLayout bottom=helper.getView(R.id.item_shop_order_bottom);
        helper.setText(R.id.item_shop_order_sn, StringUtils.getString(R.string.order_sn)+": "+item.getOrder_sn());
        if (item.getStatus().equals("1")){
            bottom.setVisibility(View.GONE);
            helper.setText(R.id.item_shop_order_status, StringUtils.getString(R.string.wait_for_pay));
        }else if (item.getStatus().equals("2")){
            bottom.setVisibility(View.VISIBLE);
            helper.setText(R.id.item_shop_order_status, StringUtils.getString(R.string.buyer_had_pay));
        }else if (item.getStatus().equals("3")){
            bottom.setVisibility(View.GONE);
            helper.setText(R.id.item_shop_order_status, StringUtils.getString(R.string.had_fahuo));
        }else {
            bottom.setVisibility(View.GONE);
            helper.setText(R.id.item_shop_order_status, StringUtils.getString(R.string.completed));
        }
        ImageLoaderUtils.display(mContext,img,item.getGpic());
        helper.setText(R.id.item_shop_order_title, item.getGname());
        helper.setText(R.id.item_shop_order_chujia, StringUtils.getString(R.string.chujia)+": "+item.getPrice());
    }
}
