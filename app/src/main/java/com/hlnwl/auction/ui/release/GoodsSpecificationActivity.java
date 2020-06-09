package com.hlnwl.auction.ui.release;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperButton;
import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.goods.SpecBean;
import com.hlnwl.auction.ui.user.info.AddressAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/19 16:39
 * 描述：
 */
public class GoodsSpecificationActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.spec_content)
    RecyclerView mSpecContent;

    private SpecAdapter mAdapter;
    private List<SpecBean> datas = new ArrayList<>();
    private List<String> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_specification;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(getResources().getString(R.string.goods_specification));
        mTitleTb.setRightTitle(getResources().getString(R.string.over));
        mTitleTb.setRightColor(ColorUtils.getColor(R.color.black));
        mTitleTb.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                datas = (List<SpecBean>) mAdapter.getData();
                if (datas.size()==0){
                    toast(StringUtils.getString(R.string.null_spec));
                    return;
                }
                for (SpecBean specBean : datas) {
                    if (specBean.getName().length() == 0
                            || specBean.getContent().length() == 0) {
                        toast(getResources().getString(R.string.spec_null));
                        return;
                    }
                    mList.add(GsonUtils.toJson(specBean));
                }
                String result = TextUtils.join(",", mList);
                Intent intent = new Intent();
                intent.putExtra("spec", result);
                setResult(10, intent);
                finish();
            }
        });
    }


    @Override
    protected void initData() {

        mSpecContent.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (mAdapter == null) {
            mAdapter = new SpecAdapter();
            mSpecContent.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        List<String> specBeans = new ArrayList<>();
        specBeans = Arrays.asList(getIntent().getStringExtra("spec").split(";"));
        log(getIntent().getStringExtra("spec"));
        log(specBeans.get(0));
        for (String spec : specBeans) {
            mAdapter.addData(GsonUtils.fromJson(spec, SpecBean.class));
        }
    }


    @OnClick(R.id.add_spec)
    public void onViewClicked() {
        mAdapter.addData(new SpecBean("", ""));
    }


}
