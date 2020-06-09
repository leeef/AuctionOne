package com.hlnwl.auction.ui.shop;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.allen.library.CircleImageView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.hlnwl.auction.R;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 14:37
 * 描述：
 */
public class ShopImgItemProvider extends BaseItemProvider<ShopMultipleItem, BaseViewHolder> {
    @Override
    public int viewType() {
        return ShopListAdapter.IMG;
    }

    @Override
    public int layout() {
        return R.layout.shop_img;
    }

    @Override
    public void convert(BaseViewHolder helper, ShopMultipleItem data, int position) {
        ImageView img=helper.getView(R.id.item_shop_list_imgs);
        ImageLoaderUtils.display(mContext,img,data.getUrl());
        if (position % 12 == 1) {
            setMargins(img,20,50,10,50);
        }else if (position % 12 == 2){
            setMargins(img,10,50,10,50);
        }else if (position % 12 == 3){
            setMargins(img,10,50,20,50);
        }else if (position % 12 == 5){
            setMargins(img,20,50,10,50);
        }else if (position % 12 ==6){
            setMargins(img,10,50,10,50);
        }else if (position % 12 == 7){
            setMargins(img,10,50,20,50);
        }else if (position % 12 == 9){
            setMargins(img,20,50,10,50);
        }else if (position % 12 == 10){
            setMargins(img,10,50,10,50);
        } else if (position % 12 == 11){
            setMargins(img,10,50,20,50);
        }
        helper.addOnClickListener(R.id.item_shop_list_imgs);
    }
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

}
