package com.hlnwl.auction.ui.user.shop;

import android.app.Dialog;
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
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.NoDataBean;
import com.hlnwl.auction.bean.user.shop.MyGoodsBean;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.ui.goods.GoodsDetailActivity;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.MessageDialog;
import com.hlnwl.auction.view.dialog.WaitDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/10/11 09:46
 * 描述：
 */
public class GoodsEditActivity extends MyActivity implements OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.rv_list_common)
    RecyclerView mRvListCommon;
    @BindView(R.id.srl_list_common)
    SmartRefreshLayout mSrlListCommon;

    private static final int PAGE_SIZE = 16;//每页数据条目
    private int mPage = 1;
    private MyGoodsAdapter mAdapter;
    private List<MyGoodsBean.DataBean> datas=new ArrayList<>();
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginEventBus(LoginMessage update) {
        onRefresh(mSrlListCommon);

    }
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
        mTitleTb.setTitle(StringUtils.getString(R.string.shop_relese_list));
        mSrlListCommon.autoRefresh();
        mSrlListCommon.setRefreshHeader(new ClassicsHeader(getActivity()));
        mSrlListCommon.setRefreshFooter(new ClassicsFooter(getActivity()));
        mSrlListCommon.setHeaderHeight(60);
        mSrlListCommon.setFooterHeight(60);
        mSrlListCommon.setOnRefreshListener(this);
        mSrlListCommon.setOnLoadMoreListener(this);

        mRvListCommon.setLayoutManager(new LinearLayoutManager(this));
//        mRvListCommon.addItemDecoration(new DividerItemDecoration(getActivity()));
        if (mAdapter == null) {
            mAdapter = new MyGoodsAdapter();
            mRvListCommon.setAdapter(mAdapter);

        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
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
                .getMyGoodsList(SPUtils.getLanguage(),mPage,
                      SPUtils.getUserId(),SPUtils.getToken()), this)
                .subscribe(new ApiObserver<MyGoodsBean>() {
                               @Override
                               protected void success(MyGoodsBean data) {
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

    private void setData(boolean isRefresh, List<MyGoodsBean.DataBean> data) {
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(this, GoodsDetailActivity.class)
                .putExtra("id",datas.get(position).getId()));
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.item_my_goods_edit:
                startActivity(new Intent(this, EditGoodActivity.class)
                .putExtra("id",datas.get(position).getId()));
                break;
            case R.id.item_my_goods_del:
                new MessageDialog.Builder(getActivity())
                        .setTitle(getActivity().getResources().getString(R.string.delete)) // 标题可以不用填写
                        .setMessage(getActivity().getResources().getString(R.string.sure_del))
                        .setConfirm(getActivity().getResources().getString(R.string.confirm))
                        .setCancel(getActivity().getResources().getString(R.string.picture_cancel)) // 设置 null 表示不显示取消按钮
                        //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(Dialog dialog) {
                                delete(datas.get(position).getId());
                            }

                            @Override
                            public void onCancel(Dialog dialog) {
                                toast(getActivity().getResources().getString(R.string.picture_cancel));
                            }
                        })
                        .show();

                break;
        }
    }

    private void delete(String id) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .delGoods(SPUtils.getLanguage(),SPUtils.getUserId(),SPUtils.getToken(), id), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(GoodsEditActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }
                                   onRefresh(mSrlListCommon);
                                   final BaseDialog dialog = new WaitDialog.Builder(GoodsEditActivity.this)
                                           .setMessage(StringUtils.getString(R.string.deleteing)) // 消息文本可以不用填写
                                           .show();
                                   getHandler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           toast(data.getMsg());
                                           dialog.dismiss();
                                       }
                                   }, 1000);
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   toast(t.getMessage());
                               }
                           }
                );
    }
}
