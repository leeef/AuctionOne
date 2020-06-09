package com.hlnwl.auction.ui.user;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.home.NewsBean;
import com.hlnwl.auction.bean.home.NewsData;
import com.hlnwl.auction.bean.user.info.AddressBean;
import com.hlnwl.auction.bean.user.info.AddressData;
import com.hlnwl.auction.ui.common.CommonWebActivity;
import com.hlnwl.auction.ui.user.info.AddressAdapter;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/30 14:45
 * 描述：
 */
public  class NoticeActivity extends MyActivity implements OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.rv_list_common)
    RecyclerView mRvListCommon;
    @BindView(R.id.srl_list_common)
    SmartRefreshLayout mRefreshLayout;

    private NoticeAdapter mAdapter;
    private List<NewsData> datas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(StringUtils.getString(R.string.system_bulletin));
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));

        mRefreshLayout.setHeaderHeight(60);

        mRefreshLayout.setOnRefreshListener(this);
//        onRefresh(mSrlListCommon);

        mRvListCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mAdapter == null) {
            mAdapter = new NoticeAdapter();
            mRvListCommon.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        showLoading();
        getData();
    }
    private void getData() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getNews(SPUtils.getLanguage()),this)
                .subscribe(new ApiObserver<NewsBean>() {
                    @Override
                    protected void success(NewsBean data) {
                        log(data.toString());
                        boolean isSuccess = MessageUtils.setCode(getActivity(),
                                data.getStatus() + "", data.getMsg());
                        if (!isSuccess) {
                            mRefreshLayout.finishRefresh();
                            mRefreshLayout.finishLoadMoreWithNoMoreData();
                            showError();
                            return;
                        }
                        datas.clear();

                        if (data.getData().size() == 0) {
                            mRefreshLayout.finishRefresh();
                            showEmpty();
                            return;
                        }
                        showComplete();
                        datas.addAll(data.getData());
                        mAdapter.setNewData(data.getData());
                        if (mRefreshLayout != null) {
                            mRefreshLayout.finishRefresh();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mRefreshLayout != null) {
                            mRefreshLayout.finishRefresh();
                        }
                        showError();
                        toast(t.getMessage());
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CommonWebActivity.runActivity(this, datas.get(position).getTitle(),
                datas.get(position).getLink());
    }
}
