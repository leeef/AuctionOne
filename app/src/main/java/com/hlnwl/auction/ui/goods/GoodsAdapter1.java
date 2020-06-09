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
public class GoodsAdapter1 extends BaseQuickAdapter<GoodsData, BaseViewHolder> {
    public GoodsAdapter1() {
        super(R.layout.shop_img1);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsData data) {
        TextView textView = helper.getView(R.id.tv_status);
        textView.setVisibility(View.VISIBLE);
        String status = "";

        if(data .getBarg().equals("0")){
            status = "议价";
            textView.setTextColor(mContext.getResources().getColor(R.color.main));

        }
        else if(data .getBarg().equals("1")){
            status = "正在议价中...";
            textView.setTextColor(mContext.getResources().getColor(R.color.green));
        }
        else if(data .getBarg().equals("2")){
            status = "议价已结束";
            textView.setTextColor(mContext.getResources().getColor(R.color.gray));
        }

        helper.setText(R.id.tv_name,data.getName())
                .setText(R.id.tv_status,status);

        helper.getView(R.id.iv_tag).setVisibility(View.VISIBLE);
        helper.getView(R.id.tv_name).setVisibility(View.VISIBLE);

//        helper.setText(R.id.item_upgrade_price, "¥ " + item.getLow_price());
        SimpleDraweeView upgradeImg = helper.getView(R.id.item_shop_list_imgs);
        FrescoUtils.showBasicPic(data.getPic(),upgradeImg);
//        ImageLoaderUtils.display(mContext, upgradeImg,  item.getContent().getPic());
        LinearLayout bg = helper.getView(R.id.good_btn);
        if (helper.getAdapterPosition() % 2 == 0) {
            setMargins(bg,20,0,10,0);
        }else{
            setMargins(bg,10,0,20,0);
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
