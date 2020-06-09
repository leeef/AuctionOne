package com.hlnwl.auction.ui.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.home.SpecialExhibitionMultipleItem;
import com.hlnwl.auction.ui.goods.GoodsListActivity;
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
public class SpecialExhibitionTopItemProvider extends BaseItemProvider<SpecialExhibitionMultipleItem, BaseViewHolder> {
    @Override
    public int viewType() {
        return SpecialExhibitionListAdapter.TOP;
    }

    @Override
    public int layout() {
        return R.layout.special_exhibition_top_item;
    }

    @Override
    public void convert(BaseViewHolder helper, SpecialExhibitionMultipleItem data, int position) {
        ImageView img=helper.getView(R.id.iv_image);
        ImageLoaderUtils.display(mContext,img,data.getCateBeanList().getIcon());
        helper.setText(R.id.tv_name,data.getCateBeanList().getName());




        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    mContext.startActivity(new Intent(mContext, GoodsListActivity.class)
                            .putExtra("title", data.getCateBeanList().getName())
                            .putExtra("id", data.getCateBeanList().getId())
                            .putExtra("keyword","")
                    .putExtra("tag","zhuanchang"));

                Log.e("---",data.getCateBeanList().getId()+"");


            }
        });
    }
}
