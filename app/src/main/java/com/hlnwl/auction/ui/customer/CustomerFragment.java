package com.hlnwl.auction.ui.customer;

import com.allen.library.SuperButton;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.StringUtils;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyLazyFragment;
import com.hlnwl.auction.bean.PhoneBean;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.my.PhoneUtil;
import com.hlnwl.auction.utils.sp.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/16 11:06
 * 描述：
 */
public class CustomerFragment extends MyLazyFragment {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.customer_call)
    SuperButton mCustomerCall;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_customer;
    }

    @Override
    protected int getTitleId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setLeftIcon(null);
        mTitleTb.setTitle(getActivity().getResources().getString(R.string.customer));
    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getPhone(SPUtils.getLanguage()), this)
                .subscribe(new ApiObserver<PhoneBean>() {
                               @Override
                               protected void success(PhoneBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }

                                   SPUtils.setCustomerPhone(StringUtils.null2Length0(data.getData().get(0).getKfphone()));
                                   mCustomerCall.setText(StringUtils.getString(R.string.call) + "  " + SPUtils.getCustomerPhone());
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    @OnClick(R.id.customer_call)
    public void onViewClicked() {
        PhoneUtil.callPhone(getActivity(),SPUtils.getCustomerPhone());
    }
}
