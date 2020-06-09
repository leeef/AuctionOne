package com.hlnwl.auction.ui.home;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperButton;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.home.SpecialExhibitionBean;
import com.hlnwl.auction.bean.home.SpecialExhibitionMultipleItem;
import com.hlnwl.auction.bean.shop.ShopListBean;
import com.hlnwl.auction.ui.category.SearchActivity;
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
import butterknife.OnClick;

/**
 * 展销专场
 */
public class SpecialExhibitionActivity extends MyActivity implements OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.home_search)
    SuperButton homeSearch;
    @BindView(R.id.rv_list_common)
    RecyclerView  mRvListCommon;
    @BindView(R.id.srl_list_common)
    SmartRefreshLayout mSrlListCommon;
    private SpecialExhibitionListAdapter mAdapter;
    private List<SpecialExhibitionMultipleItem> datas = new ArrayList<>();
    private static final int PAGE_SIZE = 16;//每页数据条目
    private int mPage = 1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_special_exhibition;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
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


    @OnClick({R.id.iv_back, R.id.home_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.home_search:

                startActivity(SearchActivity.class);
                break;
                default:

                    break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getData("more");
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        showLoading();
        mPage=1;
        getData("new");
    }

    private void getData(String style) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getSpecialExhibitionList(SPUtils.getLanguage(),mPage), this)
                .subscribe(new ApiObserver<SpecialExhibitionBean>() {
                               @Override
                               protected void success(SpecialExhibitionBean data) {
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

    private void setData(boolean isRefresh, List<SpecialExhibitionBean.DataBean> data) {
        mPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {

            initAdapter();
            if(size == 0){
                return;
            }

            datas.add(new SpecialExhibitionMultipleItem(SpecialExhibitionMultipleItem.BANNER
                    ,data.get(0).getLun(),""));

            if(data.get(0).getCate() == null || data.get(0).getCate().size() == 0){
                return;

            }


            for (int i = 0; i < data.get(0).getCate().size(); i++) {
                datas.add(new SpecialExhibitionMultipleItem(SpecialExhibitionMultipleItem.TOP,
                        data.get(0).getCate().get(i),0));
                if (data.get(0).getCate().get(i).getGood_list().size()>0){

                    for (int j = 0; j <data.get(0).getCate().get(i).getGood_list().size() ; j++) {
                        datas.add(new SpecialExhibitionMultipleItem(SpecialExhibitionMultipleItem.IMG
                                ,data.get(0).getCate().get(i).getGood_list().get(j)));
                    }
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



                for (int i = 0; i < data.get(0).getCate().size(); i++) {
                    datas.add(new SpecialExhibitionMultipleItem(SpecialExhibitionMultipleItem.TOP,
                            data.get(0).getCate().get(i),0));
                    if (data.get(i).getCate().size()>0){
                        for (int j = 0; j <data.get(i).getCate().size() ; j++) {
                            datas.add(new SpecialExhibitionMultipleItem(SpecialExhibitionMultipleItem.IMG
                                    ,data.get(i).getCate().get(j).getGood_list().get(j)));
                        }
                    }

//                for (int j = 0; j < data.get(i).getGdpic().size(); j++) {
//                    datas.add(new ShopMultipleItem(ShopMultipleItem.IMG,data.get(i).getGdpic().get(j)));
//                }
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
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mRvListCommon.setLayoutManager(manager);
        mAdapter = new SpecialExhibitionListAdapter(datas);
        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                int type = datas.get(position).itemType;
                if (type == SpecialExhibitionMultipleItem.IMG) {
                    return 1;
                }   else {
                    return 4;
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
