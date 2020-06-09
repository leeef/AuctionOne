package com.hlnwl.auction.ui.shop;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
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
import com.hlnwl.auction.bean.home.HomeBean;
import com.hlnwl.auction.bean.home.HomeMultipleItem;
import com.hlnwl.auction.bean.shop.ShopListBean;
import com.hlnwl.auction.ui.home.HomeAdapter;
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
import butterknife.ButterKnife;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 14:09
 * 描述：
 */
public class ShopListActivity extends MyActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.rv_list_common)
    RecyclerView mRvListCommon;
    @BindView(R.id.srl_list_common)
    SmartRefreshLayout mSrlListCommon;


    private ShopListAdapter mAdapter;
    private List<ShopMultipleItem> datas = new ArrayList<>();
    private static final int PAGE_SIZE = 16;//每页数据条目
    private int mPage = 1;

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
        mTitleTb.setTitle(getResources().getString(R.string.shop));

        mSrlListCommon.autoRefresh();
        mSrlListCommon.setRefreshHeader(new ClassicsHeader(getActivity()));
        mSrlListCommon.setRefreshFooter(new ClassicsFooter(getActivity()));
        mSrlListCommon.setHeaderHeight(60);
        mSrlListCommon.setFooterHeight(60);
        mSrlListCommon.setOnRefreshListener(this);
        mSrlListCommon.setOnLoadMoreListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        showLoading();
        mPage=1;
        getData("new");
    }



    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getData("more");
    }
    private void getData(String style) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getShopList(SPUtils.getLanguage(),mPage,""), this)
                .subscribe(new ApiObserver<ShopListBean>() {
                               @Override
                               protected void success(ShopListBean data) {
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
                                   showError();
                                   mSrlListCommon.finishRefresh();
                                   mSrlListCommon.finishLoadMoreWithNoMoreData();
                                   toast(t.getMessage());
                               }
                           }
                );


    }

    private void setData(boolean isRefresh, List<ShopListBean.DataBean> data) {
        mPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            for (int i = 0; i < data.size(); i++) {
                datas.add(new ShopMultipleItem(ShopMultipleItem.TOP,data.get(i)));
                if (data.get(i).getGdpic().size()>0){
                    for (String img:data.get(i).getGdpic()){
                        datas.add(new ShopMultipleItem(ShopMultipleItem.IMG,img));
                    }
                }else {
                    datas.add(new ShopMultipleItem(ShopMultipleItem.IMG,imageTranslateUri(R.mipmap.ic_launcher)));
                }

//                for (int j = 0; j < data.get(i).getGdpic().size(); j++) {
//                    datas.add(new ShopMultipleItem(ShopMultipleItem.IMG,data.get(i).getGdpic().get(j)));
//                }
            }

            if (mSrlListCommon != null) {
                mSrlListCommon.finishRefresh();
            }
            initAdapter();
        } else {
            if (size > 0) {
                for (int i = 0; i < data.size(); i++) {
                    datas.add(new ShopMultipleItem(ShopMultipleItem.TOP,data.get(i)));
                    if (data.get(i).getGdpic().size()>0){
                        for (String img:data.get(i).getGdpic()){
                            datas.add(new ShopMultipleItem(ShopMultipleItem.IMG,img));
                        }
                    }else {
                        datas.add(new ShopMultipleItem(ShopMultipleItem.IMG,imageTranslateUri(R.mipmap.ic_launcher)));
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mSrlListCommon.finishLoadMoreWithNoMoreData();
        } else {
            mSrlListCommon.finishLoadMore();
        }


    }

    private void initAdapter() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        mRvListCommon.setLayoutManager(manager);
        mAdapter = new ShopListAdapter(datas);
        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                int type = datas.get(position).itemType;
                if (type == ShopMultipleItem.TOP) {
                    return 3;
                }   else {
                    return 1;
                }
            }
        });
        mRvListCommon.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        showComplete();
    }

    /**
     * res/drawable(mipmap)/xxx.png::::uri－－－－>url
     *
     * @return
     */
    private String imageTranslateUri(int resId) {

        Resources r = getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(resId) + "/"
                + r.getResourceTypeName(resId) + "/"
                + r.getResourceEntryName(resId));

        return uri.toString();
    }
}
