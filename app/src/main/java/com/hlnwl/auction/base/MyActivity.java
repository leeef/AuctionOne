package com.hlnwl.auction.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;

import android.util.Log;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.blankj.utilcode.util.ToastUtils;
import com.fengchen.uistatus.UiStatusController;
import com.fengchen.uistatus.annotation.UiStatus;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.language.LanguagesManager;
import com.hlnwl.auction.app.MyApplication;
import com.hlnwl.auction.utils.language.LanguageUtil;
import com.hlnwl.auction.utils.my.ActivityStackManager;
import com.hlnwl.auction.utils.my.DebugUtils;
import com.hlnwl.auction.utils.my.EventBusManager;
import com.hlnwl.auction.utils.my.StatusManager;
import com.hlnwl.auction.utils.networkstatus.Constants;
import com.hlnwl.auction.utils.networkstatus.annotation.NetType;
import com.hlnwl.auction.utils.networkstatus.annotation.NetworkListener;
import com.hlnwl.auction.utils.networkstatus.manager.MyNetworkManager;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *    author : hlnwl
 *    time   : 2019/03/12
 *    desc   : 项目中的 Activity 基类
 */
public abstract class MyActivity extends UIActivity
        implements OnTitleBarListener {


    private Unbinder mButterKnife;//View注解
    private UiStatusController mUiStatusController;
    @Override
    protected void initLayout() {
        super.initLayout();

        // 初始化标题栏的监听
        if (getTitleBarId() > 0) {
            if (findViewById(getTitleBarId()) instanceof TitleBar) {
                ((TitleBar) findViewById(getTitleBarId())).setOnTitleBarListener(this);
            }
        }

        mButterKnife = ButterKnife.bind(this);
        EventBusManager.register(this);
        //注册广播
//        MyNetworkManager.getDefault().registerObserver(this);
        mUiStatusController = UiStatusController.get().bind(this);
        mUiStatusController.changeUiStatusIgnore(UiStatus.CONTENT);
        initOrientation();
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences preferences = newBase.getSharedPreferences("language", Context.MODE_PRIVATE);
        String selectedLanguage = preferences.getString("language", "");
        // 国际化适配（绑定语种）
        super.attachBaseContext(LanguagesManager.attach(newBase));
    }

    /**
     * 初始化横竖屏方向，会和 LauncherTheme 主题样式有冲突，注意不要同时使用
     */
    protected void initOrientation() {
        // 当前 Activity 不能是透明的并且没有指定屏幕方向，默认设置为竖屏
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(int titleId) {
        setTitle(getText(titleId));
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TitleBar titleBar = getTitleBar();
        if (titleBar != null) {
            titleBar.setTitle(title);
        }
    }

    @Nullable
    public TitleBar getTitleBar() {
        if (getTitleBarId() > 0 && findViewById(getTitleBarId()) instanceof TitleBar) {
            return findViewById(getTitleBarId());
        }
        return null;
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return true;
    }

    /**
     * {@link OnTitleBarListener}
     */

    // 标题栏左边的View被点击了
    @Override
    public void onLeftClick(View v) {
        onBackPressed();
    }

    // 标题栏中间的View被点击了
    @Override
    public void onTitleClick(View v) {}

    // 标题栏右边的View被点击了
    @Override
    public void onRightClick(View v) {}

    @Override
    protected void onResume() {
        super.onResume();
        // 友盟统计
//        UmengHelper.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 友盟统计
//        UmengHelper.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mButterKnife != null) mButterKnife.unbind();
        ActivityStackManager.getInstance().onActivityDestroyed(this);
        EventBusManager.unregister(this);
        //注销目标广播
//        MyNetworkManager.getDefault().unRegisterObserver(this);
        //注销所有广播
//        MyNetworkManager.getDefault().unRegisterAllObserver();
    }

    /**
     * 显示吐司
     */
    public void toast(CharSequence s) {
        ToastUtils.showShort(s);
    }

    public void toast(int id) {
        ToastUtils.showShort(id);
    }

    /**
     * 打印日志
     */
    public void log(Object object) {
        if (DebugUtils.isDebug(this)) {
            Log.e(getClass().getSimpleName(), object != null ? object.toString() : "null");
        }
    }

    /**
     * 获取当前的 Application 对象
     */
    public final MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

    private final StatusManager mStatusManager = new StatusManager();

    /**
     * 显示加载中
     */
    public void showLoading() {
        mStatusManager.showLoading(this);
    }

    /**
     * 显示加载完成
     */
    public void showComplete() {
        mStatusManager.showComplete();
    }

    /**
     * 显示空提示
     */
    public void showEmpty() {
        mStatusManager.showEmpty(getContentView());
    }

    /**
     * 显示错误提示
     */
    public void showError() {
        mStatusManager.showError(getContentView());
    }

    //网络监听
    @NetworkListener(type = NetType.WIFI)
    public void netork(@NetType String type){
        switch (type){
            case NetType.AUTO:
                Log.i(Constants.TAG,"AUTO");
                if (mUiStatusController.isVisibleUiStatus(UiStatus.WIDGET_NETWORK_ERROR)) {
                    mUiStatusController.hideWidget(UiStatus.WIDGET_NETWORK_ERROR);
                }
                showComplete();
                break;
            case NetType.CMNET:
                Log.i(Constants.TAG,"CMNET");
                if (mUiStatusController.isVisibleUiStatus(UiStatus.WIDGET_NETWORK_ERROR)) {
                    mUiStatusController.hideWidget(UiStatus.WIDGET_NETWORK_ERROR);
                }
                showComplete();
                break;
            case NetType.CMWAP:
                Log.i(Constants.TAG,"CMWAP");
                if (mUiStatusController.isVisibleUiStatus(UiStatus.WIDGET_NETWORK_ERROR)) {
                    mUiStatusController.hideWidget(UiStatus.WIDGET_NETWORK_ERROR);
                }
                showComplete();
                break;
            case NetType.WIFI:
                Log.i(Constants.TAG,"WIFI");
                if (mUiStatusController.isVisibleUiStatus(UiStatus.WIDGET_NETWORK_ERROR)) {
                    mUiStatusController.hideWidget(UiStatus.WIDGET_NETWORK_ERROR);
                }
                showComplete();
                break;
            case NetType.NONE:
                Log.i(Constants.TAG,"NONE");
                mUiStatusController.showWidget(UiStatus.WIDGET_NETWORK_ERROR);
                showError();
                break;
        }
    }

    /**
     * 显示自定义提示
     */
    public void showLayout(@DrawableRes int iconId, @StringRes int textId) {
        mStatusManager.showLayout(getContentView(), iconId, textId);
    }

    public void showLayout(Drawable drawable, CharSequence hint) {
        mStatusManager.showLayout(getContentView(), drawable, hint);
    }
}