package com.hlnwl.auction.ui.user.order;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyLazyFragment;
import com.hlnwl.auction.bean.NoDataBean;
import com.hlnwl.auction.bean.user.bid.BidRecordBean;
import com.hlnwl.auction.bean.user.order.OrderBean;
import com.hlnwl.auction.message.OrderMessage;
import com.hlnwl.auction.ui.goods.GoodsDetailActivity;
import com.hlnwl.auction.ui.user.bid.BidRecordAdapter;
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
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 11:22
 * 描述：
 */
@SuppressLint("ValidFragment")
public class OrderFragment extends MyLazyFragment implements OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_list_common)
    RecyclerView mRvListCommon;
    @BindView(R.id.srl_list_common)
    SmartRefreshLayout mSrlListCommon;

    private String type="";
    private static final int PAGE_SIZE = 16;//每页数据条目
    private int mPage = 1;
    private OrderAdapter mAdapter;
    private List<OrderBean.DataBean> datas=new ArrayList<>();
    @SuppressLint("ValidFragment")
    public OrderFragment(String type) {
        this.type = type;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshEventBus(OrderMessage update) {
        onRefresh(mSrlListCommon);
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
            mAdapter = new OrderAdapter();
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
                .getOrder(SPUtils.getLanguage(),mPage, SPUtils.getUserId(),SPUtils.getToken(),type), this)
                .subscribe(new ApiObserver<OrderBean>() {
                               @Override
                               protected void success(OrderBean
                                                              data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       showError();
                                       mSrlListCommon.finishRefresh();
                                       mSrlListCommon.finishLoadMoreWithNoMoreData();
                                       return;
                                   }
                                   datas.clear();
                                   if (style.equals("more")) {
                                       boolean isRefresh = mPage == 1;
                                       setData(isRefresh, data.getData());
                                   } else if (style.equals("new")) {

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

    private void setData(boolean isRefresh, List<OrderBean.DataBean> data) {
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
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.item_order_good:
                startActivity(new Intent(getActivity(), GoodsDetailActivity.class)
                        .putExtra("id",datas.get(position).getGid()));
                break;
            case R.id.item_order_yfh_see:
                startActivity(new Intent(getActivity(),OrderDetailActivity.class)
                        .putExtra("id",datas.get(position).getId()));
                break;
            case R.id.item_order_qrsh:
                new MessageDialog.Builder(getActivity())
                        .setTitle(StringUtils.getString(R.string.sure_shouhuo)) // 标题可以不用填写
                        .setMessage(getActivity().getResources().getString(R.string.sure_shouhuo_ask))
                        .setConfirm(getActivity().getResources().getString(R.string.confirm))
                        .setCancel(getActivity().getResources().getString(R.string.picture_cancel)) // 设置 null 表示不显示取消按钮
                        //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(Dialog dialog) {
                                sureOrder(datas.get(position).getId());
                            }

                            @Override
                            public void onCancel(Dialog dialog) {
                                toast(getActivity().getResources().getString(R.string.picture_cancel));
                            }
                        })
                        .show();
                break;
            case R.id.item_order_comment:
                startActivity(new Intent(getActivity(), CommentActivity.class)
                        .putExtra("id",datas.get(position).getId()));
                break;
        }
    }

    private void sureOrder(String id) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .confirmOrder(SPUtils.getLanguage(),SPUtils.getUserId(), SPUtils.getToken(), id), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }
                                   onRefresh(mSrlListCommon);
                                   final BaseDialog dialog = new WaitDialog.Builder(getActivity())
                                           .setMessage(StringUtils.getString(R.string.sureing)) // 消息文本可以不用填写
                                           .show();

                                   new Handler().postDelayed(new Runnable() {
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(getActivity(),OrderDetailActivity.class)
        .putExtra("id",datas.get(position).getId()));
    }
}
