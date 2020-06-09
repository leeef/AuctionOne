package com.hlnwl.auction.ui.user.info;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyLazyFragment;
import com.hlnwl.auction.bean.user.info.MoneyDetailBean;
import com.hlnwl.auction.bean.user.info.WithdrawalRecordBean;
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
 * 创建日期：2019/9/29 09:15
 * 描述：
 */
public class WithdrawalFragment extends MyLazyFragment implements OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.rv_list_common)
    RecyclerView mRvListCommon;
    @BindView(R.id.srl_list_common)
    SmartRefreshLayout mSrlListCommon;

    private static final int PAGE_SIZE = 16;//每页数据条目
    private int mPage = 1;
    private WithdrawalAdapter mAdapter;
    private List<WithdrawalRecordBean.DataBean> datas=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected void initView() {
        mSrlListCommon.autoRefresh();
        mSrlListCommon.setRefreshHeader(new ClassicsHeader(getActivity()));
        mSrlListCommon.setRefreshFooter(new ClassicsFooter(getActivity()));
        mSrlListCommon.setHeaderHeight(60);
        mSrlListCommon.setFooterHeight(60);
        mSrlListCommon.setOnRefreshListener(this);
        mSrlListCommon.setOnLoadMoreListener(this);

        mRvListCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mAdapter == null) {
            mAdapter = new WithdrawalAdapter();
            mRvListCommon.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
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
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getWithdrawalRecord(SPUtils.getLanguage(),mPage, SPUtils.getUserId(),SPUtils.getToken()), this)
                .subscribe(new ApiObserver<WithdrawalRecordBean>() {
                               @Override
                               protected void success(WithdrawalRecordBean data) {
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

    private void setData(boolean isRefresh, List<WithdrawalRecordBean.DataBean> data) {
        showComplete();
        datas.addAll(data);
        mPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
            if (mSrlListCommon != null) {
                mSrlListCommon.finishRefresh();
            }
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mSrlListCommon.finishLoadMoreWithNoMoreData();
        } else {
            mSrlListCommon.finishLoadMore();
        }
    }
}
