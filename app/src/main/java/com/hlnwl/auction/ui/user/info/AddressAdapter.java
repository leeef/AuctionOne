package com.hlnwl.auction.ui.user.info;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.user.info.AddressData;


/**
 * 版权：XXX公司 版权所有
 * 作者：yougui
 * 版本：1.0
 * 创建日期：2018/12/25
 * 描述：我的地址列表适配器
 */
public class AddressAdapter extends BaseQuickAdapter<AddressData, BaseViewHolder> {
    public AddressAdapter() {
        super(R.layout.item_address);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressData item) {
        helper.setText(R.id.item_address_name, item.getTheme());
        helper.setText(R.id.item_address_phone, item.getPhone());
        helper.setText(R.id.item_address_content, item.getXiangxi());
        ImageView defaultImg = helper.getView(R.id.my_address_undefault);
        ImageView selImg = helper.getView(R.id.my_address_default);
//        LinearLayout defaultDelete = helper.getView(R.id.item_my_address_default_delete);
//        LinearLayout delete = helper.getView(R.id.item_my_address_delete);
        if (item.getCold().equals("1")) {
            defaultImg.setVisibility(View.GONE);
            selImg.setVisibility(View.VISIBLE);
//            defaultDelete.setVisibility(View.VISIBLE);
//            delete.setVisibility(View.GONE);
        } else {
            defaultImg.setVisibility(View.VISIBLE);
            selImg.setVisibility(View.GONE);
//            defaultDelete.setVisibility(View.GONE);
//            delete.setVisibility(View.VISIBLE);
        }
        helper.addOnClickListener(R.id.my_address_undefault)
                .addOnClickListener(R.id.item_address_edit)
                .addOnClickListener(R.id.item_address_delete);

    }
}
