package com.hlnwl.auction.app;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bakerj.rxretrohttp.RxRetroHttp;

import com.blankj.utilcode.util.StringUtils;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.fengchen.uistatus.UiStatusManager;
import com.fengchen.uistatus.annotation.UiStatus;
import com.fengchen.uistatus.controller.IUiStatusController;
import com.fengchen.uistatus.listener.OnCompatRetryListener;
import com.fengchen.uistatus.listener.OnRetryListener;
import com.hjq.language.LanguagesManager;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.UIApplication;
import com.hlnwl.auction.utils.http.MyApiResult;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.IOException;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 *         版本：1.0
 *         创建日期：2019
 *         描述：
 */

public class MyApplication extends UIApplication {
    public static MyApplication   mContext;
    public static String BaseUrl="http://www.yicang123.com";
//public static String BaseUrl="http:/192.168.0.9:8060";
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initStatus();
        //Fresco缓存
        Fresco.initialize(this, ImagePipelineConfig.newBuilder(MyApplication.this)
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(this)
                                .setBaseDirectoryName("CacgPic")
                                .setBaseDirectoryPath(Environment.getExternalStoragePublicDirectory("photo"))
                                .build()
                )
                .build());
        initHttp();
        // 在 Application 中初始化
        LanguagesManager.init(this);
        CrashReport.initCrashReport(getApplicationContext(), "546a6019bf", false);
    }
    @Override
    protected void attachBaseContext(Context base) {
        // 国际化适配（绑定语种）
        super.attachBaseContext(LanguagesManager.attach(base));
    }

    private void initHttp() {
//        RxRetroHttp.init(this)
////                .setApiResultClass(MyApiResult.class)
//                .setBaseUrl(BaseUrl+"/index/")
//                .setDefaultErrMsg("服务器开小差了")
//                .generateRetroClient();

        RxRetroHttp.init(this)
//                .setApiResultClass(MyApiResult.class)

                .setBaseUrl(BaseUrl+"/index/")
                .setDefaultErrMsg("服务器开小差了");


        String selectedLanguage = StringUtils.null2Length0(SPUtils.getLanguage());
        if (selectedLanguage.length()==0){
            String languageName=mContext.getCurrentLauguageUseResources() ;
            if (languageName.equals("zh")){
                selectedLanguage="1";
                SPUtils.setLanguage("1");
            }else {
                selectedLanguage="2";
                SPUtils.setLanguage("2");
            }
        }
        OkHttpClient.Builder client = RxRetroHttp.getOkHttpClientBuilder();

        String finalSelectedLanguage = selectedLanguage;
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl originalHttpUrl = originalRequest.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("lang", finalSelectedLanguage)
                        .build();
                Request request = originalRequest.newBuilder()
                        .url(url)
                        .method(originalRequest.method(), originalRequest.body())
                        .build();

                return chain.proceed(request);
            }
        });
        RxRetroHttp.getInstance().generateRetroClient();

    }

    private void initStatus() {
        UiStatusManager.getInstance()
                .setWidgetMargin(UiStatus.WIDGET_NETWORK_ERROR, 60 * 3, 0)
                .setWidgetMargin(UiStatus.WIDGET_ELFIN, 50 * 3, 0)
                .setWidgetMargin(UiStatus.WIDGET_FLOAT, 0, 0)
//                .addUiStatusConfig(UiStatus.LOADING, R.layout.ui_status_layout_loading)//加载中.
                .addUiStatusConfig(UiStatus.NETWORK_ERROR, R.layout.ui_status_layout_network_error, R.id.tv_network_error_retry
                        , null
//                        new OnRetryListener() {
//                            @Override
//                            public void onUiStatusRetry(Object target, final IUiStatusController controller, View trigger) {
//                                Toast.makeText(trigger.getContext(), "网络错误重试", Toast.LENGTH_LONG).show();
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        controller.changeUiStatus(UiStatus.LOAD_ERROR);
//                                    }
//                                }, 1000);
//                            }
//                        }
                )//网络错误.
                .addUiStatusConfig(UiStatus.LOAD_ERROR, R.layout.ui_status_layout_load_error, R.id.tv_load_error_retry
                        , new OnRetryListener() {
                            @Override
                            public void onUiStatusRetry(Object target, final IUiStatusController controller, View trigger) {
                                Toast.makeText(trigger.getContext(), "加载失败重试", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        controller.changeUiStatus(UiStatus.EMPTY);
                                    }
                                }, 1000);
                            }
                        })//加载失败.
                .addUiStatusConfig(UiStatus.EMPTY, R.layout.ui_status_layout_empty, R.id.tv_empty_retry
                        , new OnRetryListener() {
                            @Override
                            public void onUiStatusRetry(Object target, final IUiStatusController controller, View trigger) {
                                Toast.makeText(trigger.getContext(), "空布局重试", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        controller.changeUiStatus(UiStatus.NOT_FOUND);
                                    }
                                }, 1000);
                            }
                        })//空布局.
                .addUiStatusConfig(UiStatus.NOT_FOUND, R.layout.ui_status_layout_not_found, R.id.tv_not_found_retry
                        , new OnRetryListener() {
                            @Override
                            public void onUiStatusRetry(Object target, final IUiStatusController controller, View trigger) {
                                Toast.makeText(trigger.getContext(), "未找到内容重试", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        controller.changeUiStatus(UiStatus.CONTENT);
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                controller.changeUiStatus(UiStatus.WIDGET_ELFIN);
                                            }
                                        }, 1000);
                                    }
                                }, 1000);
                            }
                        })//未找到内容布局.
                .addUiStatusConfig(UiStatus.WIDGET_ELFIN, R.layout.ui_status_layout_hint, R.id.tv_hint_retry
                        , new OnRetryListener() {
                            @Override
                            public void onUiStatusRetry(Object target, final IUiStatusController controller, View trigger) {
                                Toast.makeText(trigger.getContext(), "提示内容重试", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                controller.changeUiStatus(UiStatus.WIDGET_ELFIN);
                                            }
                                        }, 1000);
                                    }
                                }, 1000);
                            }
                        })//提示布局.
                .addUiStatusConfig(UiStatus.WIDGET_NETWORK_ERROR, R.layout.widget_ui_status_network_error_widget, R.id.tv_check_network, new OnRetryListener() {
                    @Override
                    public void onUiStatusRetry(Object target, IUiStatusController controller, View trigger) {
                        Toast.makeText(trigger.getContext(), "检查网络设置", Toast.LENGTH_SHORT).show();
                    }
                })
                .addUiStatusConfig(UiStatus.WIDGET_FLOAT, R.layout.widget_ui_status__widget_float, R.id.tv_float, new OnRetryListener() {
                    @Override
                    public void onUiStatusRetry(Object target, IUiStatusController controller, View trigger) {
                        Toast.makeText(trigger.getContext(), "我是Float", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnCompatRetryListener(new OnCompatRetryListener() {
                    @Override
                    public void onUiStatusRetry(int uiStatus, @NonNull Object target, final @NonNull IUiStatusController controller, @NonNull View trigger) {
                        Log.i("--", "全局设置" + uiStatus);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                controller.changeUiStatus(UiStatus.LOAD_ERROR);
                            }
                        }, 1000);
                    }
                });

//        UiStatusNetworkStatusProvider.getInstance()
//                .registerOnRequestNetworkStatusEvent(new OnRequestNetworkStatusEvent() {
//                    @Override
//                    public boolean onRequestNetworkStatus(@NonNull Context context) {
//                        return MyNetworkManager.isConnected(context);
//                    }
//                });
    }

    public static MyApplication getmContext() {
        return mContext;
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
