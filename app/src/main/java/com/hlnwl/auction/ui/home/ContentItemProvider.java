package com.hlnwl.auction.ui.home;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hlnwl.auction.R;
import com.hlnwl.auction.app.MyApplication;
import com.hlnwl.auction.bean.home.HomeMultipleItem;
import com.hlnwl.auction.ui.goods.GoodsDetailActivity;
import com.hlnwl.auction.utils.my.FrescoUtils;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 10:22
 * 描述：
 */
public class ContentItemProvider extends BaseItemProvider<HomeMultipleItem, BaseViewHolder> {
    @Override
    public int viewType() {
        return HomeAdapter.CONTENT;
    }

    @Override
    public int layout() {
        return R.layout.home_goods;
    }

    @Override
    public void convert(BaseViewHolder helper, HomeMultipleItem item, int position) {
        helper.setText(R.id.item_upgrade_name, item.getContent().getName());
//        helper.setText(R.id.item_upgrade_price, "¥ " + item.getContent().getLow_price());
        SimpleDraweeView upgradeImg = helper.getView(R.id.item_upgrade_img);
        FrescoUtils.showBasicPic(item.getContent().getPic(),upgradeImg);
//        ImageLoaderUtils.display(mContext, upgradeImg,  item.getContent().getPic());
        LinearLayout bg = helper.getView(R.id.good_btn);
        if (position % 2 == 0) {
            setMargins(bg,10,0,20,0);
        }else{
            setMargins(bg,20,0,10,0);
        }
        TextView chujia=helper.getView(R.id.item_chujia);
        if (Integer.parseInt(item.getContent().getPut_count())>0){
            //首先是拼接字符串
            String content ="已出价"+ "<font color=\"#D51400\">" + item.getContent().getPut_count() + "</font>"+"次";
            chujia.setText(Html.fromHtml(content));
        }
        ImageView isFine=helper.getView(R.id.item_is_fine);
        if (item.getContent().getGenre().equals("1")){
            isFine.setVisibility(View.VISIBLE);
        }else {
            isFine.setVisibility(View.GONE);
        }
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    @Override
    public void onClick(BaseViewHolder helper, HomeMultipleItem item, int position) {
        mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class)
        .putExtra("id",item.getContent().getId()));
    }
}
