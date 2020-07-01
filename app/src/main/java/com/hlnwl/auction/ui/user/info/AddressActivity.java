package com.hlnwl.auction.ui.user.info;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;

import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.NoDataBean;
import com.hlnwl.auction.bean.user.info.AddressBean;
import com.hlnwl.auction.bean.user.info.AddressData;
import com.hlnwl.auction.message.AddressMessage;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.MessageDialog;
import com.hlnwl.auction.view.dialog.WaitDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/4 17:08
 * 描述：
 */
public class AddressActivity extends MyActivity implements OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.rv_list_common)
    RecyclerView mRvListCommon;
    @BindView(R.id.srl_list_common)
    SmartRefreshLayout mRefreshLayout;

    private AddressAdapter mAdapter;
    private List<AddressData> datas = new ArrayList<>();
    private String type = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void orderEventBus(AddressMessage addressMessage) {
        onRefresh(mRefreshLayout);
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(StringUtils.getString(R.string.sh_address));
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));

        mRefreshLayout.setHeaderHeight(60);

        mRefreshLayout.setOnRefreshListener(this);
//        onRefresh(mSrlListCommon);

        mRvListCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mAdapter == null) {
            mAdapter = new AddressAdapter();
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
        showLoading();
        getData();
    }

    private void getData() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getAddress(SPUtils.getLanguage(),SPUtils.getUserId(),SPUtils.getToken()),this)
                .subscribe(new ApiObserver<AddressBean>() {
                    @Override
                    protected void success(AddressBean data) {
                        log(data.toString());
                        boolean isSuccess = MessageUtils.setCode(getActivity(),
                                data.getStatus() + "", data.getMsg());
                        if (!isSuccess) {
                            mRefreshLayout.finishRefresh();
                            mRefreshLayout.finishLoadMoreWithNoMoreData();
                            showError();
                            return;
                        }
                        datas.clear();

                        if (data.getData().size() == 0) {
                            mRefreshLayout.finishRefresh();
                            showEmpty();
                            return;
                        }
                        showComplete();
                        datas.addAll(data.getData());
                        mAdapter.setNewData(data.getData());
                        if (mRefreshLayout != null) {
                            mRefreshLayout.finishRefresh();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mRefreshLayout != null) {
                            mRefreshLayout.finishRefresh();
                        }
                        showError();
                        toast(t.getMessage());
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (getIntent().getStringExtra("type") != null
                && getIntent().getStringExtra("type").equals("1")) {
            Intent intent = new Intent();
            intent.putExtra("address", datas.get(position));
            setResult(10, intent);
            finish();
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.my_address_undefault:
                setDefault(datas.get(position).getId());
                break;
            case R.id.item_address_edit:
                startActivity(new Intent(this,AddAddressActivity.class)
                .putExtra("edit", GsonUtils.toJson(datas.get(position))));
                break;
            case R.id.item_address_delete:
                new MessageDialog.Builder(getActivity())
                        .setTitle(getActivity().getResources().getString(R.string.delete)) // 标题可以不用填写
                        .setMessage(getActivity().getResources().getString(R.string.sure_del))
                        .setConfirm(getActivity().getResources().getString(R.string.confirm))
                        .setCancel(getActivity().getResources().getString(R.string.picture_cancel)) // 设置 null 表示不显示取消按钮
                        //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(Dialog dialog) {
                                deleteAddress(datas.get(position).getId());
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

    private void deleteAddress(String address_id) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .delAddress(SPUtils.getLanguage(),SPUtils.getUserId(),SPUtils.getToken(), address_id), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(AddressActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }
                                   onRefresh(mRefreshLayout);
                                   final BaseDialog dialog = new WaitDialog.Builder(AddressActivity.this)
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

    private void setDefault(String address_id) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .setDefaultAddress(SPUtils.getLanguage(),SPUtils.getUserId(),SPUtils.getToken(), address_id), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(AddressActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }
                                onRefresh(mRefreshLayout);
                                   final BaseDialog dialog = new WaitDialog.Builder(AddressActivity.this)
                                           .setMessage("设置中...") // 消息文本可以不用填写
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

    @OnClick(R.id.add_address)
    public void onViewClicked() {
        startActivity(AddAddressActivity.class);
    }
}
