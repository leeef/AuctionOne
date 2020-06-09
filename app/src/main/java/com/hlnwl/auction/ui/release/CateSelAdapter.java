package com.hlnwl.auction.ui.release;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.category.CategoryErji;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;

/**
 * 版权：XXX公司 版权所有
 * 作者：yougui
 * 版本：1.0
 * 创建日期：2018/12/25
 * 描述：分类内容适配器
 */
public class CateSelAdapter extends BaseQuickAdapter<CategoryErji, BaseViewHolder> {
    private Context mContext;

    public CateSelAdapter(Context context) {
        super(R.layout.item_category_content);
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CategoryErji item) {
        helper.setText(R.id.item_category_right_name,item.getName());
        ImageView img=helper.getView(R.id.item_category_right_icon);
        ImageLoaderUtils.display(mContext,img, item.getIcon());
        LinearLayout chech=helper.getView(R.id.category_content_item);
        TextView title=helper.getView(R.id.item_category_right_name);
        LinearLayout content=helper.getView(R.id.category_content_item);

        if (item.isSelected()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                chech.setBackgroundResource(R.drawable.cate_bg);
            }
            title.setTextColor(mContext.getResources().getColor(R.color.main));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                chech.setBackgroundResource(R.drawable.cate_un_bg);
            }
            title.setTextColor(mContext.getResources().getColor(R.color.main));
        }
        helper.addOnClickListener(R.id.category_content_item);
    }
}
