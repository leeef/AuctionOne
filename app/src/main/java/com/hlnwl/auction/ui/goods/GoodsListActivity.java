package com.hlnwl.auction.ui.goods;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.goods.GoodsData;
import com.hlnwl.auction.bean.goods.GoodsListBean;
import com.hlnwl.auction.bean.user.order.OrderBean;
import com.hlnwl.auction.ui.user.order.OrderAdapter;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 17:32
 * 描述：
 */
public class GoodsListActivity extends MyActivity implements OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.rv_list_common)
    RecyclerView mRvListCommon;
    @BindView(R.id.srl_list_common)
    SmartRefreshLayout mSrlListCommon;
    private static final int PAGE_SIZE = 16;//每页数据条目
    private int mPage = 1;
    private GoodsAdapter mAdapter;
    private GoodsAdapter1 mAdapter1;
    private List<GoodsData> datas=new ArrayList<>();
    private String keyword="";
    private String tag = "";
    @Override
    protected int getLayoutId() {
        return R.layout.list;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        tag = getIntent().getStringExtra("tag");


        mTitleTb.setTitle(getIntent().getStringExtra("title"));
        mSrlListCommon.autoRefresh();
        mSrlListCommon.setRefreshHeader(new ClassicsHeader(getActivity()));
        mSrlListCommon.setRefreshFooter(new ClassicsFooter(getActivity()));
        mSrlListCommon.setHeaderHeight(60);
        mSrlListCommon.setFooterHeight(60);
        mSrlListCommon.setOnRefreshListener(this);
        mSrlListCommon.setOnLoadMoreListener(this);

        mRvListCommon.setLayoutManager(new GridLayoutManager(getActivity(),
                2));
//        mRvListCommon.addItemDecoration(new DividerItemDecoration(getActivity()));

        if(tag.equals("class")){
            if (mAdapter == null) {
                mAdapter = new GoodsAdapter();
                mRvListCommon.setAdapter(mAdapter);

            } else {
                mAdapter.notifyDataSetChanged();
            }
            mAdapter.setOnItemClickListener(this);

        }else {
            if (mAdapter1 == null) {
                mAdapter1 = new GoodsAdapter1();
                mRvListCommon.setAdapter(mAdapter1);

            } else {
                mAdapter1.notifyDataSetChanged();
            }
            mAdapter1.setOnItemClickListener(this);
        }


    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

        mPage = 1;
        showLoading();
        getDatas("new");
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getDatas("more");
    }

    private void getDatas(String style) {

        if(tag.equals("class")){
            RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                    .getGoodsList(SPUtils.getLanguage(),mPage,
                            getIntent().getStringExtra("id"),
                            getIntent().getStringExtra("keyword")), this)
                    .subscribe(new ApiObserver<GoodsListBean>() {
                                   @Override
                                   protected void success(GoodsListBean data) {
                                       boolean isSuccess = MessageUtils.setCode(getActivity(),
                                               data.getStatus() + "", data.getMsg());
                                       if (!isSuccess) {
                                           showError();
                                           mSrlListCommon.finishRefresh();
                                           mSrlListCommon.finishLoadMoreWithNoMoreData();
                                           return;
                                       }

                                       if (style.equals("more")) {
                                           boolean isRefresh = mPage == 1;
                                           setData(isRefresh, data.getData());
                                       } else if (style.equals("new")) {

                                           datas.clear();
                                           if (data.getData().size() == 0) {
                                               mSrlListCommon.finishRefresh();
                                               mSrlListCommon.finishLoadMoreWithNoMoreData();
                                               showEmpty();
                                               return;
                                           }
                                           setData(true, data.getData());
                                           mSrlListCommon.setEnableLoadMore(true);

                                       }
                                   }

                                   @Override
                                   public void onError(Throwable t) {
                                       super.onError(t);
                                       if (mSrlListCommon != null) {
                                           mSrlListCommon.finishRefresh();
                                       }
                                       showError();
                                       toast(t.getMessage());
                                   }
                               }
                    );
        }else {
            RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                    .getGoodsList1(SPUtils.getLanguage(),mPage,
                            getIntent().getStringExtra("id")), this)
                    .subscribe(new ApiObserver<GoodsListBean>() {
                                   @Override
                                   protected void success(GoodsListBean data) {
                                       boolean isSuccess = MessageUtils.setCode(getActivity(),
                                               data.getStatus() + "", data.getMsg());
                                       if (!isSuccess) {
                                           showError();
                                           mSrlListCommon.finishRefresh();
                                           mSrlListCommon.finishLoadMoreWithNoMoreData();
                                           return;
                                       }

                                       if (style.equals("more")) {
                                           boolean isRefresh = mPage == 1;
                                           setData(isRefresh, data.getData());
                                       } else if (style.equals("new")) {

                                           datas.clear();
                                           if (data.getData().size() == 0) {
                                               mSrlListCommon.finishRefresh();
                                               mSrlListCommon.finishLoadMoreWithNoMoreData();
                                               showEmpty();
                                               return;
                                           }
                                           setData(true, data.getData());
                                           mSrlListCommon.setEnableLoadMore(true);

                                       }
                                   }

                                   @Override
                                   public void onError(Throwable t) {
                                       super.onError(t);
                                       if (mSrlListCommon != null) {
                                           mSrlListCommon.finishRefresh();
                                       }
                                       showError();
                                       toast(t.getMessage());
                                   }
                               }
                    );
        }

    }

    private void setData(boolean isRefresh, List<GoodsData> data) {
        showComplete();
        datas.addAll(data);
        mPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            if(tag.equals("class")){
                mAdapter.setNewData(data);
            }else {
                mAdapter1.setNewData(data);
            }
            if (mSrlListCommon != null) {
                mSrlListCommon.finishRefresh();
            }
        } else {
            if (size > 0) {
                if(tag.equals("class")){
                    mAdapter.addData(data);
                }else {
                    mAdapter1.addData(data);
                }

            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mSrlListCommon.finishLoadMoreWithNoMoreData();
        } else {
            mSrlListCommon.finishLoadMore();
        }
    }



    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if(tag.equals("class")){
            startActivity(new Intent(this, GoodsDetailActivity.class)
                    .putExtra("id",datas.get(position).getId()));
        } else {
            startActivity(new Intent(this, GoodsDetailActivity.class)
                    .putExtra("id",datas.get(position).getId())
            .putExtra("tag",1));
        }

    }
}
