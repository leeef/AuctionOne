package com.hlnwl.auction.ui.category;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyLazyFragment;
import com.hlnwl.auction.bean.goods.GoodsData;
import com.hlnwl.auction.bean.goods.GoodsListBean;
import com.hlnwl.auction.bean.shop.ShopListBean;
import com.hlnwl.auction.ui.goods.GoodsAdapter;
import com.hlnwl.auction.ui.goods.GoodsDetailActivity;
import com.hlnwl.auction.ui.shop.ShopListAdapter;
import com.hlnwl.auction.ui.shop.ShopMultipleItem;
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
 * 创建日期：2019/10/15 14:42
 * 描述：
 */
@SuppressLint("ValidFragment")
public class SearchShopsFragment extends MyLazyFragment implements OnRefreshListener, OnLoadMoreListener{

    @BindView(R.id.rv_list_common)
    RecyclerView mRvListCommon;
    @BindView(R.id.srl_list_common)
    SmartRefreshLayout mSrlListCommon;


    private ShopListAdapter mAdapter;
    private List<ShopMultipleItem> datas = new ArrayList<>();
    private static final int PAGE_SIZE = 16;//每页数据条目
    private int mPage = 1;
    private String keyword="";

    @SuppressLint("ValidFragment")
    public SearchShopsFragment(String keyword) {
        this.keyword = keyword;
    }

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
                .getShopList(SPUtils.getLanguage(),mPage,keyword), this)
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
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mSrlListCommon.finishLoadMoreWithNoMoreData();
        } else {
            mSrlListCommon.finishLoadMore();
        }

        initAdapter();
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
