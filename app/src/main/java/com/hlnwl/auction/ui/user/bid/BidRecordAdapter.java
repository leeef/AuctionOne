package com.hlnwl.auction.ui.user.bid;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.user.bid.BidRecordBean;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/28 17:17
 * 描述：
 */
public class BidRecordAdapter extends BaseQuickAdapter<BidRecordBean.DataBean, BaseViewHolder> {
    public BidRecordAdapter() {
        super(R.layout.item_bid_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, BidRecordBean.DataBean item) {
        TextView time=helper.getView(R.id.item_bid_record_time);
        LinearLayout bg=helper.getView(R.id.item_bid_record_bg);
        ImageView img=helper.getView(R.id.item_bid_record_img);
        if (item.getStatus().equals("1")){
            bg.setVisibility(View.GONE);
            time.setText(StringUtils.getString(R.string.from_over)+"  "+item.getEndtime());
        }else {
            if (item.getStatus().equals("4")){
                bg.setVisibility(View.VISIBLE);
                time.setText(StringUtils.getString(R.string.pay_time_count)+"  "+item.getEndtime());
            }else {
                bg.setVisibility(View.GONE);
                time.setText(StringUtils.getString(R.string.had_pay));
            }
        }
        helper.setText(R.id.item_bid_record_margin,StringUtils.getString(R.string.margin)+": "+item.getMargin());
        ImageLoaderUtils.display(mContext,img,item.getPic());
        helper.setText(R.id.item_bid_record_title,item.getGname());
        helper.setText(R.id.item_bid_record_price,StringUtils.getString(R.string.chujia)+": "+item.getPrice());
        helper.addOnClickListener(R.id.item_bid_record_pay);
    }
}
