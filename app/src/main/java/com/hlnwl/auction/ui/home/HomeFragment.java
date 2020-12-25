package com.hlnwl.auction.ui.home;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperButton;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;
import com.hjq.language.LanguagesManager;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyLazyFragment;
import com.hlnwl.auction.bean.LanguageBean;
import com.hlnwl.auction.bean.goods.GoodsData;
import com.hlnwl.auction.bean.home.HomeBean;
import com.hlnwl.auction.bean.home.HomeMultipleItem;
import com.hlnwl.auction.message.LanguageMessage;
import com.hlnwl.auction.ui.category.SearchActivity;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.popup.MorePopup;
import com.hlnwl.auction.view.widget.OnegoGridLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/16 10:58
 * 描述：
 */
public class HomeFragment extends MyLazyFragment implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.home_language_name)
    TextView mHomeLanguageName;
    @BindView(R.id.home_selet_language)
    LinearLayout mHomeSeletLanguage;
    @BindView(R.id.home_title)
    TextView mHomeTitle;
    @BindView(R.id.home_search)
    SuperButton mHomeSearch;
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.rv_list_common)
    RecyclerView mRvListCommon;
    @BindView(R.id.srl_list_common)
    SmartRefreshLayout mSrlListCommon;

    private HomeAdapter mAdapter;
    private List<HomeMultipleItem> datas = new ArrayList<>();
    private List<HomeMultipleItem> datas_more = new ArrayList<>();
    private int mPage = 1;
    private static final int PAGE_SIZE = 16;//每页数据条目
    private List<LanguageBean> languageList = new ArrayList<>();

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected int getTitleId() {
        return R.id.title_tb;
    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }


    @Override
    protected void initView() {
        languageList.add(new LanguageBean(1, getResources().getString(R.string.setting_simplified_chinese),
                "zh", false));
        languageList.add(new LanguageBean(2, getResources().getString(R.string.designer_frames),
                "en", false));


        if (SPUtils.getLanguage() == null) {
            String languageName = getCurrentLauguageUseResources();

            if (languageName.equals("zh")) {
                mHomeLanguageName.setText(getResources().getString(R.string.setting_simplified_chinese));
            } else {
                mHomeLanguageName.setText(getResources().getString(R.string.designer_frames));
            }
        } else {
            String selectedLanguage = SPUtils.getLanguage();
            if (selectedLanguage.equals("1")) {
                mHomeLanguageName.setText(getResources().getString(R.string.setting_simplified_chinese));
            } else {
                mHomeLanguageName.setText(getResources().getString(R.string.designer_frames));
            }
        }

        mSrlListCommon.setRefreshHeader(new ClassicsHeader(getActivity()));
        mSrlListCommon.setRefreshFooter(new ClassicsFooter(getActivity()));
        mSrlListCommon.setHeaderHeight(60);
        mSrlListCommon.setFooterHeight(60);
        mSrlListCommon.setOnRefreshListener(this);
        mSrlListCommon.setOnLoadMoreListener(this);
        mHomeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchActivity.class);
            }
        });


        //添加滑动监听
        mRvListCommon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();//获取LayoutManager
                if (manager != null && manager instanceof LinearLayoutManager) {
                    //第一个可见的位置
                    int firstPosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
                    //如果 dx>0 则表示 右滑 ,dx<0 表示 左滑,dy <0 表示 上滑, dy>0 表示下滑
                    if (dy < 0) {
                        //上滑监听
                        mHomeTitle.setVisibility(firstPosition > 1 ? View.GONE : View.VISIBLE);
                        mHomeSearch.setVisibility(firstPosition > 1 ? View.VISIBLE : View.GONE);
//                        mSearchBg.setBackgroundColor(firstPosition > 2
//                                ? getActivity().getResources().getColor(R.color.white)
//                                : getActivity().getResources().getColor(R.color.transparent));
                    } else {
                        //下滑监听
                        mHomeTitle.setVisibility(firstPosition == 0 ? View.VISIBLE : View.GONE);
                        mHomeSearch.setVisibility(firstPosition == 0 ? View.GONE : View.VISIBLE);
//                        mSearchBg.setBackgroundColor(firstPosition == 0
//                                ? getActivity().getResources().getColor(R.color.transparent)
//                                : getActivity().getResources().getColor(R.color.white));
                    }
                }
            }
        });

    }

    private String getCurrentLauguageUseResources() {
        /**
         * 获得当前系统语言
         */
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage(); // 获得语言码
        return language;
    }

    @Override
    protected void initData() {
        showLoading();
        getData("new");
    }


    // 是否需要重启
    boolean restart;

    @OnClick({R.id.home_selet_language, R.id.home_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_selet_language:
                MorePopup morePopup = new MorePopup(mActivity);
                morePopup.showPopupWindow(0, 200);
                morePopup.setOnMoreItemListener(new MorePopup.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int position, String content) {
                        switch (position) {
                            case 0:
                                restart = LanguagesManager.setAppLanguage(mActivity.getApplicationContext(), Locale.CHINA);
                                SPUtils.setLanguage("1");
                                break;
                            case 1:
                                restart = LanguagesManager.setAppLanguage(mActivity.getApplicationContext(), Locale.ENGLISH);
                                SPUtils.setLanguage("2");
                                break;
                        }
                        if (!mHomeLanguageName.getText().toString().equals(content)) {
                            mHomeLanguageName.setText(content);
                            if (restart) {
                                EventBus.getDefault().post(new LanguageMessage("change"));
                            }

                        }
                    }
                });
                break;
            case R.id.home_search:

                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        showLoading();
        mPage = 1;
        getData("new");
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getData("more");
    }

    private void getData(String style) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getHome(SPUtils.getLanguage(), mPage), this)
                .subscribe(new ApiObserver<HomeBean>() {
                               @Override
                               protected void success(HomeBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       showError();
                                       if (mSrlListCommon != null) {
                                           mSrlListCommon.finishRefresh();
                                           mSrlListCommon.finishLoadMoreWithNoMoreData();
                                       }
                                       return;
                                   }
                                   if (style.equals("more")) {
                                       boolean isRefresh = mPage == 1;
                                       setData(isRefresh, data.getData().get(0).getReclist());
                                   } else if (style.equals("new")) {
                                       datas.clear();
                                       datas.add(new HomeMultipleItem(HomeMultipleItem.BANNER, data.getData().get(0).getLunbo()));
                                       datas.add(new HomeMultipleItem(HomeMultipleItem.SEARCH));
                                       datas.add(new HomeMultipleItem(HomeMultipleItem.ICONS));
                                       datas.add(new HomeMultipleItem(HomeMultipleItem.MESSAGE, data.getData().get(0).getNews(), "msg"));
                                       datas.add(new HomeMultipleItem(HomeMultipleItem.IMAGE));
                                       if (data.getData().get(0).getReclist().size() == 0) {
                                           mSrlListCommon.finishRefresh();
                                           mSrlListCommon.finishLoadMoreWithNoMoreData();
                                           datas.add(new HomeMultipleItem(HomeMultipleItem.EMPTY));
                                           initAdapter();
                                           return;
                                       }
                                       setData(true, data.getData().get(0).getReclist());
                                       mSrlListCommon.setEnableLoadMore(true);
                                   }
                               }


                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   showError();
                                   if (mSrlListCommon != null) {
                                       mSrlListCommon.finishRefresh();
                                       mSrlListCommon.finishLoadMoreWithNoMoreData();
                                   }
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private void setData(boolean isRefresh, List<GoodsData> data) {

        mPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            for (int i = 0; i < data.size(); i++) {
                datas.add(new HomeMultipleItem(HomeMultipleItem.CONTENT, data.get(i)));
            }
            if (mSrlListCommon != null) {
                mSrlListCommon.finishRefresh();
            }
            initAdapter();
        } else {
            if (size > 0) {
                for (int i = 0; i < data.size(); i++) {
//                    mAdapter.addData(new HomeMultipleItem(HomeMultipleItem.CONTENT, data.get(i)));
                    datas.add(new HomeMultipleItem(HomeMultipleItem.CONTENT, data.get(i)));
                }
                mAdapter.notifyDataSetChanged();
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            if (mSrlListCommon != null) {
                mSrlListCommon.finishLoadMoreWithNoMoreData();
            }
        } else {
            if (mSrlListCommon != null) {
                mSrlListCommon.finishLoadMore();
            }
        }
        showComplete();

    }

    private void initAdapter() {

        OnegoGridLayoutManager manager = new OnegoGridLayoutManager(getActivity(), 2);
        mRvListCommon.setLayoutManager(manager);
        mAdapter = new HomeAdapter(datas);
        mRvListCommon.setAdapter(mAdapter);

        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                int type = datas.get(position).itemType;
                if (type == HomeMultipleItem.CONTENT) {
                    return 1;
                } else {
                    return 2;
                }
            }
        });


    }
}
