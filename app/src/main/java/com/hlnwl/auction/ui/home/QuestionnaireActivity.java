package com.hlnwl.auction.ui.home;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.flod.loadingbutton.LoadingButton;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.QuestionListBean;
import com.hlnwl.auction.ui.user.shop.SelectShopTypeActivity;
import com.hlnwl.auction.ui.user.shop.ShopJoinActivity;
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
import butterknife.OnClick;

/**
 * 调查问卷
 */
public class QuestionnaireActivity extends MyActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.submit)
    LoadingButton submit;
    private QuestionnaireAdapter mAdapter;

    private List<QuestionListBean.DataBean> mList = new ArrayList<>();
    private static final int PAGE_SIZE = 16;//每页数据条目
    private int mPage = 1;

    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.rv_list_common)
    RecyclerView mRvListCommon;
    @BindView(R.id.srl_list_common)
    SmartRefreshLayout mSrlListCommon;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_questionnaire;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {

        mTitleTb.setTitle(getString(R.string.questionnaire));


        mSrlListCommon.autoRefresh();
        mSrlListCommon.setRefreshHeader(new ClassicsHeader(getActivity()));
        mSrlListCommon.setRefreshFooter(new ClassicsFooter(getActivity()));
        mSrlListCommon.setHeaderHeight(60);
        mSrlListCommon.setFooterHeight(60);
        mSrlListCommon.setOnRefreshListener(this);
        mSrlListCommon.setOnLoadMoreListener(this);

        mRvListCommon.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        if (mAdapter == null) {
            mAdapter = new QuestionnaireAdapter(this);
            mRvListCommon.setAdapter(mAdapter);
            mAdapter.addData(mList);

        } else {
            mAdapter.notifyDataSetChanged();
        }


    }

    @Override
    protected void initData() {

    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getData("more");
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        showLoading();
        mPage = 1;
        getData("new");
    }

    private void getData(String style) {

        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getQuestionList(SPUtils.getLanguage(), mPage, SPUtils.getUserId(), SPUtils.getToken()), this)
                .subscribe(new ApiObserver<QuestionListBean>() {
                               @Override
                               protected void success(QuestionListBean data) {
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
                                       mList.clear();

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
                                   showError();
                                   mSrlListCommon.finishRefresh();
                                   mSrlListCommon.finishLoadMoreWithNoMoreData();
                                   toast(t.getMessage());
                               }
                           }
                );

    }

    private void setData(boolean isRefresh, List<QuestionListBean.DataBean> data) {
        showComplete();
        mList.addAll(data);
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


    @OnClick(R.id.submit)
    public void onViewClicked() {

        for (int i = 0; i < mList.size(); i++) {


            if (mAdapter.getMap().size() != mList.size()) {
                toast("请选择答题");
                return;
            }
            if (mList.get(i).getCheck() != Integer.parseInt(mList.get(i).getType())) {
                toast("答案有误请重新申请或者联系对应工作人员");
                return;
            }


        }

        if (mAdapter.getData().get(0).getShoppay() == 2) {
            startActivity(ShopJoinActivity.class);
        } else {
            startActivity(SelectShopTypeActivity.class);
        }


        finish();
    }
}
