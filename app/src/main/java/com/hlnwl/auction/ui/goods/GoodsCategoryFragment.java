package com.hlnwl.auction.ui.goods;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyLazyFragment;
import com.hlnwl.auction.bean.goods.GoodsCategoryBean;
import com.hlnwl.auction.bean.goods.GoodsData;
import com.hlnwl.auction.message.RefreshMessage;
import com.hlnwl.auction.message.StopRefreshMessage;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.sp.SPUtils;

import org.greenrobot.eventbus.EventBus;
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
 * 创建日期：2019/9/21 10:43
 * 描述：
 */
@SuppressLint("ValidFragment")
public class GoodsCategoryFragment extends MyLazyFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.rv_list_common)
    RecyclerView mRvListCommon;

    private String cid="";
    private String mType="";
    private GoodsAdapter mAdapter;
    private List<GoodsData> datas = new ArrayList<>();
    private static final int PAGE_SIZE = 16;//每页数据条目
    private int mPage = 1;

    @SuppressLint("ValidFragment")
    public GoodsCategoryFragment(String cid,String type) {
        this.cid = cid;
        this.mType=type;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_nosrl;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected void initView() {
        mRvListCommon.setLayoutManager(new GridLayoutManager(getActivity(),
                2));
//        mRvListCommon.addItemDecoration(new DividerItemDecoration(getActivity()));
        if (mAdapter == null) {
            mAdapter = new GoodsAdapter();
            mRvListCommon.setAdapter(mAdapter);

        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this,mRvListCommon);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshEventBus(RefreshMessage refresh) {
        showLoading();
        mPage = 1;
        if (mType.equals("jingpin")){
            getDatas("new");
        }else {
            getHuntDatas("new");
        }

    }
    @Override
    protected void initData() {

        showLoading();
        mPage = 1;
        if (mType.equals("jingpin")){
            getDatas("new");
        }else {
           getHuntDatas("new");
        }

    }
    private void getDatas(String style) {

        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getDesigner(SPUtils.getLanguage(),mPage,cid), this)
                .subscribe(new ApiObserver<GoodsCategoryBean>() {
                               @Override
                               protected void success(GoodsCategoryBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       EventBus.getDefault().post(new StopRefreshMessage("refresh"));
                                       showError();
                                       return;
                                   }
                                   Log.e("09211140",data.toString());

                                   if (style.equals("more")) {
                                       boolean isRefresh = mPage == 1;
                                       setData(isRefresh, data.getData().get(0).getList());
                                   } else if (style.equals("new")) {
                                       datas.clear();

                                       if (data.getData().get(0).getList().size() == 0) {
                                           EventBus.getDefault().post(new StopRefreshMessage("refresh"));
//                                           mSrlListCommon.finishLoadMoreWithNoMoreData();
                                           showEmpty();
                                           return;
                                       }
                                       setData(true, data.getData().get(0).getList());
                                   }
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   EventBus.getDefault().post(new StopRefreshMessage("refresh"));
                                   showError();
                                   toast(t.getMessage());
                               }
                           }
                );
    }
    private void getHuntDatas(String style) {

        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getHunt(SPUtils.getLanguage(),mPage,cid), this)
                .subscribe(new ApiObserver<GoodsCategoryBean>() {
                               @Override
                               protected void success(GoodsCategoryBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       EventBus.getDefault().post(new StopRefreshMessage("refresh"));
                                       showError();
                                       return;
                                   }
                                   Log.e("09211140",data.toString());

                                   if (style.equals("more")) {
                                       boolean isRefresh = mPage == 1;
                                       setData(isRefresh, data.getData().get(0).getList());
                                   } else if (style.equals("new")) {
                                       datas.clear();
                                       if (data.getData().get(0).getList().size() == 0) {
                                           EventBus.getDefault().post(new StopRefreshMessage("refresh"));
//                                           mSrlListCommon.finishLoadMoreWithNoMoreData();
                                           showEmpty();
                                           return;
                                       }
                                       setData(true, data.getData().get(0).getList());
                                   }
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   EventBus.getDefault().post(new StopRefreshMessage("refresh"));
                                   showError();
                                   toast(t.getMessage());
                               }
                           }
                );
    }
    private void setData(boolean isRefresh, List<GoodsData> data) {
        showComplete();
        datas.addAll(data);
        mPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
            EventBus.getDefault().post(new StopRefreshMessage("refresh"));
            mAdapter.setEnableLoadMore(true);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd();
//            mSrlListCommon.finishLoadMoreWithNoMoreData();
        } else {
//            mSrlListCommon.finishLoadMore();
            mAdapter.loadMoreComplete();
        }
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        getActivity().startActivity(new Intent(getActivity(), GoodsDetailActivity.class)
                .putExtra("id", datas.get(position).getId()));
    }

    @Override
    public void onLoadMoreRequested() {
        if (mType.equals("jingpin")){
            getDatas("more");
        }else {
            getHuntDatas("more");
        }

    }
}
