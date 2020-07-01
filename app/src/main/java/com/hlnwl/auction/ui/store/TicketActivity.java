package com.hlnwl.auction.ui.store;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;

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
        titleBar.setTitle(R.string.ticket);
        for (String title : getResources().getStringArray(R.array.ticket_title)) {
            tab.addTab(tab.newTab().setText(title));
        }
    }

    @Override
    protected void initData() {

    }
}
