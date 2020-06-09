package com.hlnwl.auction.ui.release;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.goods.SpecBean;
import com.hlnwl.auction.view.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/25 17:34
 * 描述：
 */
public class SpecAdapter extends BaseQuickAdapter<SpecBean, BaseViewHolder> {

    public SpecAdapter() {
        super(R.layout.item_spec);
    }


    @Override
    protected void convert(BaseViewHolder helper, SpecBean item) {
        final ClearEditText name=helper.getView(R.id.item_spec_name);
        final ClearEditText content=helper.getView(R.id.item_spec_content);
        final int position=helper.getAdapterPosition();

        LinearLayout del=helper.getView(R.id.item_spec_del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(position);
            }
        });

        TextWatcher nameWatcher = new TextWatcher() {
            private String mBefore;// 用于记录变化前的文字
            private int mCursor;// 用于记录变化时光标的位置

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mBefore = s.toString();
                mCursor = start;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 这里图方便，是在afterTextChanged判断是否输入之后含有空格，并不是最好的方案
                // 理论上应该在onTextChanged中判断输入内容
                if (s.toString().contains(" ")) {
                    name.removeTextChangedListener(this);
                    name.setText(mBefore);
                    name.addTextChangedListener(this);
                    name.setSelection(mCursor);

                }else {
//                    item.setName(name.getText().toString());
                    getData().get(position).setName(name.getText().toString());
//                    if (mInterface!=null){
//                        mInterface.nameChange(helper.getAdapterPosition(),item);
//                    }
                }
            }
        };
        TextWatcher contentWatcher = new TextWatcher() {
            private String mBefore;// 用于记录变化前的文字
            private int mCursor;// 用于记录变化时光标的位置

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mBefore = s.toString();
                mCursor = start;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 这里图方便，是在afterTextChanged判断是否输入之后含有空格，并不是最好的方案
                // 理论上应该在onTextChanged中判断输入内容
                if (s.toString().contains(" ")) {
                    content.removeTextChangedListener(this);
                    content.setText(mBefore);
                    content.addTextChangedListener(this);
                    content.setSelection(mCursor);

                }else {
//                    item.setContent(content.getText().toString());
//                    if (mInterface!=null){
//                        mInterface.contentChange(helper.getAdapterPosition(),item);
//
//                    }
                        getData().get(position).setContent(content.getText().toString());
                    Log.e("adapter",item.getContent()+"   position  "+helper.getAdapterPosition());
                }
            }
        };
        name.removeTextChangedListener(nameWatcher);
        name.setText(item.getName());
        content.removeTextChangedListener(contentWatcher);
        content.setText(item.getContent());
        name.addTextChangedListener(nameWatcher);
        content.addTextChangedListener(contentWatcher);

    }





}
