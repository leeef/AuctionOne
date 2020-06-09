package com.hlnwl.auction.ui.goods;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.goods.GoodsData;
import com.hlnwl.auction.utils.my.FrescoUtils;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/21 11:20
 * 描述：
 */
public class GoodsAdapter extends BaseQuickAdapter<GoodsData, BaseViewHolder> {
    public GoodsAdapter() {
        super(R.layout.home_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsData item) {
        helper.setText(R.id.item_upgrade_name, item.getName());

//        helper.setText(R.id.item_upgrade_price, "¥ " + item.getLow_price());
        SimpleDraweeView upgradeImg = helper.getView(R.id.item_upgrade_img);
        FrescoUtils.showBasicPic(item.getPic(),upgradeImg);
//        ImageLoaderUtils.display(mContext, upgradeImg,  item.getContent().getPic());
        LinearLayout bg = helper.getView(R.id.good_btn);
        if (helper.getAdapterPosition() % 2 == 0) {
            setMargins(bg,20,0,10,0);
        }else{
            setMargins(bg,10,0,20,0);
        }
        TextView chujia=helper.getView(R.id.item_chujia);
        if (Integer.parseInt(item.getPut_count())>0){
            //首先是拼接字符串
            String content = StringUtils.getString(R.string.yichujia) + "<font color=\"#D51400\">" +
                    item.getPut_count() + "</font>"+StringUtils.getString(R.string.ci);
            chujia.setText(Html.fromHtml(content));
        }
        ImageView isFine=helper.getView(R.id.item_is_fine);
        if (item.getGenre().equals("1")){
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
}
