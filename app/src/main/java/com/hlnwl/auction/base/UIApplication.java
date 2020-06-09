package com.hlnwl.auction.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.hlnwl.auction.utils.my.ActivityStackManager;
import com.hlnwl.auction.utils.networkstatus.manager.MyNetworkManager;


import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;


/**
 *    author : hlnwl
 *    time   : 2019/03/12
 *    desc   : 支持侧滑的Application基类
 */
public abstract class UIApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        CrashReport.initCrashReport(getApplicationContext(), "867868a5f0", true);
        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);
        // Activity 栈管理
        ActivityStackManager.init(this);
        //工具包初始化
        Utils.init(this);
        MyNetworkManager.getDefault().init(this);
//        OkGo.getInstance().init(this);
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//        //全局的读取超时时间
//        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
////全局的写入超时时间
//        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
////全局的连接超时时间
//        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
//
//        //使用sp保持cookie，如果cookie不过期，则一直有效
//        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
//////使用数据库保持cookie，如果cookie不过期，则一直有效
////        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
//////使用内存保持cookie，app退出后，cookie消失
////        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
//
//        //方法一：信任所有证书,不安全有风险
//        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
//
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
////log打印级别，决定了log显示的详细程度
//        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
////log颜色级别，决定了log在控制台显示的颜色
//        loggingInterceptor.setColorLevel(Level.WARNING);
//        builder.addInterceptor(loggingInterceptor);
//
//        OkGo.getInstance()
//                //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
//                //如果使用默认的 60秒,以下三行也不需要传
////                .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST);//缓存
//                .setCacheMode(CacheMode.DEFAULT)//缓存
//                .setRetryCount(3);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 使用 Dex分包
        MultiDex.install(this);
    }




}