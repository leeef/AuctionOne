package com.hlnwl.auction.ui.category;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.app.MyApplication;
import com.hlnwl.auction.bean.category.CategoryErji;
import com.hlnwl.auction.ui.goods.GoodsListActivity;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;


import java.util.List;

/**
 * 版权：XXX公司 版权所有
 * 作者：yougui
 * 版本：1.0
 * 创建日期：2018/12/25
 * 描述：分类内容适配器
 */
public class ContentAdapter extends BaseQuickAdapter<CategoryErji, BaseViewHolder> {
    private Context mContext;

    public ContentAdapter(Context context) {
        super(R.layout.item_category_content);
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CategoryErji item) {
        helper.setText(R.id.item_category_right_name,item.getName());
        ImageView img=helper.getView(R.id.item_category_right_icon);
        ImageLoaderUtils.display(mContext,img, item.getIcon());
        LinearLayout content=helper.getView(R.id.category_content_item);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, GoodsListActivity.class)
                        .putExtra("title", item.getName())
                        .putExtra("id", item.getId())
                .putExtra("keyword","")
                        .putExtra("tag","class"));
            }
        });
    }
}
