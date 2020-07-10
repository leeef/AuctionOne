package com.hlnwl.auction.ui.store;

import android.content.Intent;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.goods.GoodListBean;
import com.hlnwl.auction.ui.goods.ShopDetailActivity;
import com.hlnwl.auction.utils.http.Api;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

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
    private int page = 1;

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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void quitEventBus(String update) {
        if (update.equals("quit")) {
            finish();
        }
    }

    @Override
    protected void initData() {
        list.setLayoutManager(new GridLayoutManager(this, 2));
        goodListAdapter = new GoodListAdapter(new ArrayList<>());
        list.setAdapter(goodListAdapter);
        goodListAdapter.setOnItemClickListener((adapter, view, position) -> {
                    GoodListBean.DataBean goodListBean = goodListAdapter.getItem(position);
                    startActivity(new Intent(GoodListActivity.this, ShopDetailActivity.class)
                            .putExtra("id", goodListBean.getId()));
                }
        );
        srlListCommon.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getData();
        });
        srlListCommon.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getData();
        });
        getData();
    }

    private void getData() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getGoodList(page), this)
                .subscribe(new ApiObserver<GoodListBean>() {
                    @Override
                    protected void success(GoodListBean data) {
                        if (page == 1) {
                            srlListCommon.finishRefresh();
                            goodListAdapter.getData().clear();
                        } else {
                            srlListCommon.finishLoadMore();
                        }
                        goodListAdapter.addData(data.getData());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                        showError();
                        toast(t.getMessage());
                    }
                });
    }
}
