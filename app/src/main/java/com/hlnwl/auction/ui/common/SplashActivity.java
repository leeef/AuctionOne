package com.hlnwl.auction.ui.common;

import android.os.Handler;

import com.blankj.utilcode.util.StringUtils;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Locale;


/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/7/24 15:39
 * 描述：
 */
public class SplashActivity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        String selectedLanguage = StringUtils.null2Length0(SPUtils.getLanguage());
        if (selectedLanguage.length()==0){
            String languageName=getCurrentLauguageUseResources() ;
            if (languageName.equals("zh")){
                SPUtils.setLanguage("1");
            }else {
                SPUtils.setLanguage("2");
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityFinish(IndexActivity.class);
            }
        }, 1500);
//        XXPermissions.with(this)
//                //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                .permission(Permission.WRITE_EXTERNAL_STORAGE) //支持请求6.0悬浮窗权限8.0请求安装权限
//                //不指定权限则自动获取清单中的危险权限
//                .request(new OnPermission() {
//
//                    @Override
//                    public void hasPermission(List<String> granted, boolean isAll) {
//
//                    }
//
//                    @Override
//                    public void noPermission(List<String> denied, boolean quick) {
//                        XXPermissions.gotoPermissionSettings(SplashActivity.this);
//                    }
//                });
//        getData();

    }
    private String getCurrentLauguageUseResources(){
        /**
         * 获得当前系统语言
         */
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage(); // 获得语言码
        return language;
    }

}
