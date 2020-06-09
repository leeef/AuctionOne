package com.hlnwl.auction.ui.category;

import android.graphics.Color;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.category.CategoryData;


import java.util.List;

/**
 * 版权：XXX公司 版权所有
 * 作者：yougui
 * 版本：1.0
 * 创建日期：2018/12/25
 * 描述：分类菜单适配器
 */
public class MenuAdapter extends BaseQuickAdapter<CategoryData, BaseViewHolder> {
    private int selectPos=0;
    public MenuAdapter(@Nullable List<CategoryData> data) {
        super(R.layout.item_category_menu, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryData item) {
        if(selectPos==helper.getAdapterPosition()){
            helper.setVisible(R.id.item_main_left_bg,true);
            helper.convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            helper.setTextColor(R.id.item_main_menu_name, Color.parseColor("#D51400"));
        }else{
            helper.convertView.setBackgroundColor(Color.parseColor("#f7f7f7"));
            helper.setTextColor(R.id.item_main_menu_name, Color.parseColor("#333333"));
            helper.setVisible(R.id.item_main_left_bg,false);
        }
        helper.setText(R.id.item_main_menu_name,item.getName());
    }

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }
}
