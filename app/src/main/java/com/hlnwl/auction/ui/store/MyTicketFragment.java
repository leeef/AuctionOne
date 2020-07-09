package com.hlnwl.auction.ui.store;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyLazyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/6/25 11:29
 */
public class MyTicketFragment extends MyLazyFragment {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.list)
    RecyclerView list;


    private TicketAdapter ticketAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.my_ticket_fragment;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected void initView() {
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(ticketAdapter);
    }

    @Override
    protected void initData() {

    }
}
