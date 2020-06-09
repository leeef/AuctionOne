package com.hlnwl.auction.utils.networkstatus.manager;

import android.app.Application;
import android.content.IntentFilter;

import com.hlnwl.auction.utils.networkstatus.Constants;
import com.hlnwl.auction.utils.networkstatus.NetStateReceiver;


public class MyNetworkManager {
    private static volatile MyNetworkManager instance;
    private NetStateReceiver mReceiver;
    private Application mApplication;

    public MyNetworkManager(){
        mReceiver = new NetStateReceiver();
    }

    public static MyNetworkManager getDefault(){
        if(instance == null){
            synchronized (MyNetworkManager.class){
                if(instance == null){
                    instance = new MyNetworkManager();
                }
            }
        }
        return instance;
    }
    public Application getApplication(){
        if(mApplication == null){
            throw new RuntimeException("MyNetworkManager.getDefault().init()没有初始化");
        }
        return mApplication;
    }

    public void init(Application application){
        this.mApplication = application;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        mApplication.registerReceiver(mReceiver,intentFilter);
    }


    public void logout(){
        getApplication().unregisterReceiver(mReceiver);
    }

    public void registerObserver(Object activity) {
        mReceiver.registerObserver(activity);
    }

    public void unRegisterObserver(Object activity) {
        mReceiver.unRegisterObserver(activity);
    }

    public void unRegisterAllObserver() {
        mReceiver.unRegisterAllObserver();
    }
}