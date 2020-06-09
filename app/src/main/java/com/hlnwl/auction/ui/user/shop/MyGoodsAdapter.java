package com.hlnwl.auction.ui.user.shop;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.user.shop.MyGoodsBean;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;

import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/10/11 09:58
 * 描述：
 */
public class MyGoodsAdapter extends BaseQuickAdapter<MyGoodsBean.DataBean, BaseViewHolder> {
    public MyGoodsAdapter() {
        super(R.layout.item_my_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyGoodsBean.DataBean item) {
        ImageView img=helper.getView(R.id.item_my_goods_img);
        ImageLoaderUtils.display(mContext,img,item.getPic());
        TextView chujia=helper.getView(R.id.item_my_goods_chujia);
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
        helper.setText(R.id.item_my_goods_name, item.getName());
        //        helper.setText(R.id.item_upgrade_price, "¥ " + item.getLow_price());

        helper.addOnClickListener(R.id.item_my_goods_edit)
                .addOnClickListener(R.id.item_my_goods_del);
    }
}
