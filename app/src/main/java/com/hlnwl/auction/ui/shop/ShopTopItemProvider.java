package com.hlnwl.auction.ui.shop;

import android.content.Intent;
import android.view.View;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.hlnwl.auction.R;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 14:27
 * 描述：
 */
public class ShopTopItemProvider extends BaseItemProvider<ShopMultipleItem, BaseViewHolder> {
    @Override
    public int viewType() {
        return ShopListAdapter.TOP;
    }

    @Override
    public int layout() {
        return R.layout.shop_top;
    }

    @Override
    public void convert(BaseViewHolder helper, ShopMultipleItem data, int position) {
        CircleImageView img=helper.getView(R.id.item_shop_list_img);
        ImageLoaderUtils.display(mContext,img,data.getData().getPic());
        helper.setText(R.id.item_shop_list_name,data.getData().getName());
        SuperButton in=helper.getView(R.id.item_shop_list_in);
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext,ShopHomeAcitivity.class)
                        .putExtra("name", data.getData().getName())
                        .putExtra("pic",data.getData().getPic())
                        .putExtra("id",data.getData().getId()));
            }
        });
    }
}
