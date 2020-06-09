package com.hlnwl.auction.ui.common;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.message.LanguageMessage;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.message.QuitMessage;
import com.hlnwl.auction.ui.category.CategoryFragment;
import com.hlnwl.auction.ui.customer.CustomerFragment;
import com.hlnwl.auction.ui.home.HomeFragment;
import com.hlnwl.auction.ui.home.QuestionnaireActivity;
import com.hlnwl.auction.ui.release.ReleaseActivity;
import com.hlnwl.auction.ui.user.UserFragment;
import com.hlnwl.auction.ui.user.shop.ShopJoinActivity;
import com.hlnwl.auction.ui.user.shop.ShopJoinStatusActivity;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.MessageDialog;
import com.hlnwl.auction.view.radiogrouplib.NestedRadioGroup;
import com.hlnwl.auction.view.radiogrouplib.NestedRadioLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/16 09:14
 * 描述：
 */
public class IndexActivity extends MyActivity {

    @BindView(R.id.main_content)
    FrameLayout mMainContent;
    @BindView(R.id.index_home)
    NestedRadioLayout mIndexHome;
    @BindView(R.id.index_nestedGroup)
    NestedRadioGroup mIndexNestedGroup;
    @BindView(R.id.index_category)
    NestedRadioLayout mIndexCategory;
    @BindView(R.id.index_customer_service)
    NestedRadioLayout mIndexCustomerService;
    @BindView(R.id.user_center)
    NestedRadioLayout mUserCenter;

    private FragmentManager mFragmentManager;

    private HomeFragment mHomeFragment = new HomeFragment();
    private CategoryFragment mCategoryFragment = new CategoryFragment();
    private CustomerFragment mCustomerFragment = new CustomerFragment();
    private UserFragment mUserFragment = new UserFragment();
    private StatusFragment mStatusFragment = new StatusFragment();
    private NestedRadioLayout current;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void quitEventBus(QuitMessage update) {
        startActivity(IndexActivity.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void languageEventBus(LanguageMessage change) {

        Intent intent = new Intent(getActivity(), IndexActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_alpha_in, R.anim.activity_alpha_out);
        finish();
    }

    @Override
    protected void initView() {
//        showLoading();
        mIndexHome.setChecked(true);
        mFragmentManager = getSupportFragmentManager();


        FragmentUtils.add(mFragmentManager, mHomeFragment, R.id.main_content);
        FragmentUtils.add(mFragmentManager, mCategoryFragment, R.id.main_content);
        FragmentUtils.add(mFragmentManager, mCustomerFragment, R.id.main_content);
        FragmentUtils.add(mFragmentManager, mUserFragment, R.id.main_content);
        FragmentUtils.hide(mCategoryFragment);
        FragmentUtils.hide(mCustomerFragment);
        FragmentUtils.hide(mUserFragment);
        FragmentUtils.show(mHomeFragment);
        current=mIndexHome;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                showComplete();
//            }
//        }, 1500);

        mIndexNestedGroup.setOnCheckedChangeListener(new NestedRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(NestedRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.index_home:
                        if (current!=mIndexHome){
                            FragmentUtils.hide(mCategoryFragment);
                            FragmentUtils.hide(mCustomerFragment);
                            FragmentUtils.hide(mUserFragment);
                            FragmentUtils.show(mHomeFragment);
                            current=mIndexHome;
                        }

                        break;
                    case R.id.index_category:
                        if (current!=mIndexCategory){
                            FragmentUtils.hide(mHomeFragment);
                            FragmentUtils.hide(mCustomerFragment);
                            FragmentUtils.hide(mUserFragment);
                            FragmentUtils.show(mCategoryFragment);
                            current=mIndexCategory;
                        }

                        break;
                    case R.id.index_customer_service:
                        if (current!=mIndexCustomerService){
                            FragmentUtils.hide(mHomeFragment);
                            FragmentUtils.hide(mUserFragment);
                            FragmentUtils.hide(mCategoryFragment);
                            FragmentUtils.show(mCustomerFragment);
                            current=mIndexCustomerService;
                        }

                        break;
                    case R.id.user_center:
                        if (SPUtils.getLogin().length() == 0 || SPUtils.getLogin() == null) {
                            startActivity(LoginActivity.class);
                            current.setChecked(true);
                            mUserCenter.setChecked(false);
                        } else {
                            if (current!=mUserCenter){
                                current=mUserCenter;
                                FragmentUtils.hide(mCategoryFragment);
                                FragmentUtils.hide(mCustomerFragment);
                                FragmentUtils.hide(mHomeFragment);
                                FragmentUtils.show(mUserFragment);
                            }
                        }

                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
//        CrashReport.testJavaCrash();
    }

    //上一次点击返回按钮的时间戳
    private long quitTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - quitTime > 2000) {
            ToastUtils.showShort(getResources().getString(R.string.app_name)
                    + getResources().getString(R.string.more_quit));
            quitTime = System.currentTimeMillis();
        } else {
            System.exit(0);

        }
    }

    @OnClick(R.id.index_public)
    public void onViewClicked() {
        if (SPUtils.getLogin().length() == 0 || SPUtils.getLogin() == null) {
            startActivity(LoginActivity.class);
            current.setChecked(true);
            mUserCenter.setChecked(false);
        }else {
            EventBus.getDefault().post(new LoginMessage("update"));
            if (SPUtils.getChant().equals("2")){
                startActivity(ReleaseActivity.class);
            }else if (SPUtils.getChant().equals("0")){
                new MessageDialog.Builder(getActivity())
                        .setTitle(getActivity().getResources().getString(R.string.join_now)) // 标题可以不用填写
                        .setMessage(getActivity().getResources().getString(R.string.join_now_login))
                        .setConfirm(getActivity().getResources().getString(R.string.confirm))
                        .setCancel(getActivity().getResources().getString(R.string.picture_cancel)) // 设置 null 表示不显示取消按钮
                        //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(Dialog dialog) {

                                startActivity(QuestionnaireActivity.class);


                            }

                            @Override
                            public void onCancel(Dialog dialog) {
                                toast(getActivity().getResources().getString(R.string.picture_cancel));
                            }
                        })
                        .show();

            }else if (SPUtils.getChant().equals("1")){
                startActivity(ShopJoinStatusActivity.class);
            }else if (SPUtils.getChant().equals("3")){
                toast(StringUtils.getString(R.string.shop_colse));
            }
        }

    }

}
