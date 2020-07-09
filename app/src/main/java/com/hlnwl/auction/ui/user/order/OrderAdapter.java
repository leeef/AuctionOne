package com.hlnwl.auction.ui.user.order;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.allen.library.SuperButton;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.user.order.OrderBean;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 11:26
 * 描述：
 */
public class OrderAdapter extends BaseQuickAdapter<OrderBean.DataBean, BaseViewHolder> {
    public OrderAdapter() {
        super(R.layout.item_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean.DataBean item) {
        ImageView goodImg = helper.getView(R.id.item_order_good_img);
        LinearLayout bottom = helper.getView(R.id.item_order_yfh);
        SuperButton ckwl = helper.getView(R.id.item_order_yfh_see);
        SuperButton qrsh = helper.getView(R.id.item_order_qrsh);
        SuperButton pingjia = helper.getView(R.id.item_order_comment);
        SuperButton shanchudingdan = helper.getView(R.id.item_order_cancel);

        ImageLoaderUtils.display(mContext, goodImg, item.getGpic());


        helper.setText(R.id.item_order_good_name, item.getGname());
        helper.setText(R.id.item_order_bid, StringUtils.getString(R.string.danwei) + item.getTotal());

        if (item.getStatus().equals("0")) {
            helper.setText(R.id.item_order_status, StringUtils.getString(R.string.to_be_payment));
//            bottom.setVisibility(View.GONE);
        } else if (item.getStatus().equals("1")) {
            helper.setText(R.id.item_order_status, StringUtils.getString(R.string.to_be_shipped));
            bottom.setVisibility(View.GONE);
        } else if (item.getStatus().equals("4")) {
            helper.setText(R.id.item_order_status, StringUtils.getString(R.string.completed));
            bottom.setVisibility(View.GONE);
        } else {
            bottom.setVisibility(View.VISIBLE);
            if (item.getStatus().equals("2")) {
                helper.setText(R.id.item_order_status, StringUtils.getString(R.string.to_be_received));
                ckwl.setVisibility(View.VISIBLE);
                qrsh.setVisibility(View.VISIBLE);
                pingjia.setVisibility(View.GONE);
                shanchudingdan.setVisibility(View.GONE);
            } else if (item.getStatus().equals("3")) {
                helper.setText(R.id.item_order_status, StringUtils.getString(R.string.to_be_comment));
                ckwl.setVisibility(View.GONE);
                qrsh.setVisibility(View.GONE);
                pingjia.setVisibility(View.VISIBLE);
                shanchudingdan.setVisibility(View.GONE);
            }
        }
        helper.addOnClickListener(R.id.item_order_yfh_see)
                .addOnClickListener(R.id.item_order_qrsh)
                .addOnClickListener(R.id.item_order_comment)
                .addOnClickListener(R.id.item_order_good);
    }
}
