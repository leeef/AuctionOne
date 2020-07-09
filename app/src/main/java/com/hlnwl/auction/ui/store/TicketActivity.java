package com.hlnwl.auction.ui.store;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.google.android.material.tabs.TabLayout;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.user.TicketListBean;
import com.hlnwl.auction.utils.SimpleDividerItemDecoration;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.sp.SPUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/6/25 11:19
 */
public class TicketActivity extends MyActivity {

    @BindView(R.id.title_tb)
    TitleBar titleBar;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.total_count)
    TextView totalCount;

    private TicketAdapter ticketAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.acitivty_ticket;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        titleBar.setTitle(R.string.my_ticket);
        for (String title : getResources().getStringArray(R.array.ticket_title)) {
            tab.addTab(tab.newTab().setText(title));
        }
        ticketAdapter = new TicketAdapter(new ArrayList<>());
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(ticketAdapter);
        list.addItemDecoration(new SimpleDividerItemDecoration(this, 1));
    }

    @Override
    protected void initData() {
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getData(0);
    }

    private void getData(int type) {
        showLoading();
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                        .ticketList(SPUtils.getUserId(), SPUtils.getToken(), type)
                , this)
                .subscribe(new ApiObserver<TicketListBean>() {
                    @Override
                    protected void success(TicketListBean data) {
                        showComplete();
                        if (data.getData().size() > 0) {
                            totalCount.setText(data.getData().get(0).getSumcoupon());
                            ticketAdapter.getData().clear();
                            ticketAdapter.addData(data.getData().get(0).getCoupon());
                            if (data.getData().get(0).getCoupon().size() == 0) {
                                showEmpty();
                            }
                        } else {
                            totalCount.setText("0");
                            ticketAdapter.getData().clear();
                            ticketAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        showError();
                        toast(t.getMessage());
                    }
                });
    }
}
