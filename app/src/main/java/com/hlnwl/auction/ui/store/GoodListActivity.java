package com.hlnwl.auction.ui.store;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.ui.goods.GoodsDetailActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.Arrays;

import butterknife.BindView;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/6/25 9:33
 */
public class GoodListActivity extends MyActivity {


    @BindView(R.id.srl_list_common)
    SmartRefreshLayout srlListCommon;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;

    private GoodListAdapter goodListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_good_list;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        srlListCommon.setRefreshHeader(new ClassicsHeader(getActivity()));
        srlListCommon.setRefreshFooter(new ClassicsFooter(getActivity()));
        srlListCommon.setHeaderHeight(60);
        srlListCommon.setFooterHeight(60);
        mTitleTb.setTitle(R.string.goods_list);
    }

    @Override
    protected void initData() {
        list.setLayoutManager(new GridLayoutManager(this, 2));
        goodListAdapter = new GoodListAdapter(Arrays.asList(getResources().getStringArray(R.array.test)));
        list.setAdapter(goodListAdapter);
        goodListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(GoodListActivity.this, GoodsDetailActivity.class)
                        .putExtra("id", "5119").putExtra("tag", 2));
            }
        });
    }
}
