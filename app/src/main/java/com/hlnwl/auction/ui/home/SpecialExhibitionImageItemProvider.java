package com.hlnwl.auction.ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.home.SpecialExhibitionMultipleItem;
import com.hlnwl.auction.ui.goods.GoodsDetailActivity;
import com.hlnwl.auction.ui.shop.ShopListAdapter;
import com.hlnwl.auction.ui.shop.ShopMultipleItem;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 14:27
 * 描述：
 */
public class SpecialExhibitionImageItemProvider extends BaseItemProvider<SpecialExhibitionMultipleItem, BaseViewHolder> {
    @Override
    public int viewType() {
        return SpecialExhibitionListAdapter.IMG;
    }

    @Override
    public int layout() {
        return R.layout.shop_img;
    }

    @Override
    public void convert(BaseViewHolder helper, SpecialExhibitionMultipleItem data, int position) {

        TextView textView = helper.getView(R.id.tv_status);
        textView.setVisibility(View.VISIBLE);
        String status = "";

        if(data.getGoodListBeanList().getBarg().equals("0")){
            status = "议价";
            textView.setTextColor(mContext.getResources().getColor(R.color.main));

        }
        else if(data.getGoodListBeanList().getBarg().equals("1")){
            status = "正在议价中...";
            textView.setTextColor(mContext.getResources().getColor(R.color.green));
        }
        else if(data.getGoodListBeanList().getBarg().equals("2")){
            status = "议价已结束";
            textView.setTextColor(mContext.getResources().getColor(R.color.gray));
        }




        ImageView img=helper.getView(R.id.item_shop_list_imgs);
        ImageLoaderUtils.display(mContext,img,data.getGoodListBeanList().getPic());

        helper.setText(R.id.tv_name,data.getGoodListBeanList().getName())
                .setText(R.id.tv_status,status);

        helper.getView(R.id.iv_tag).setVisibility(View.VISIBLE);
        helper.getView(R.id.tv_name).setVisibility(View.VISIBLE);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class)
                            .putExtra("id",data.getGoodListBeanList().getId())
                .putExtra("tag",1));


            }
        });
    }
}
