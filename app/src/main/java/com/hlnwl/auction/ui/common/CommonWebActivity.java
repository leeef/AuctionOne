package com.hlnwl.auction.ui.common;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.home.NewHand;
import com.hlnwl.auction.bean.home.NewsBean;
import com.hlnwl.auction.bean.user.shop.GoodMsgBean;
import com.hlnwl.auction.utils.HtmlUtils;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.sp.SPUtils;


import butterknife.BindView;


/**
 * 版权：XXX公司 版权所有
 * 作者：yougui
 * 版本：1.0
 * 创建日期：2019
 * 描述：
 */
public class CommonWebActivity extends MyActivity {

    public final static String URL = "url";
    public final static String TITLE = "title";

    private final static String mimeType = "text/html";
    private final static String encoding = "utf-8";
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;

    WebView mWebView;



    public static void runActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, CommonWebActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }


    @Override
    public void initView() {
        mWebView=findViewById(R.id.webView);
        showLoading();
        mTitleTb.setTitle(getIntent().getStringExtra(TITLE));
//        mPb.setMax(100);
        WebSettings settings = mWebView.getSettings();
        String url = getIntent().getStringExtra(URL);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true); //将图片调整到适合WebView的大小
        settings.setLoadWithOverviewMode(true); //自适应屏幕
        settings.setDomStorageEnabled(true);
        settings.setSaveFormData(true);
        settings.setSupportMultipleWindows(true);
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); //优先使用缓存
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 取消滚动条白边效果
        mWebView.requestFocus();

        if(getIntent().getStringExtra(TITLE).equals(getString(R.string.new_user_on_road))){

            getHttpData();
        }else {
            mWebView.loadUrl(url);
        }



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showComplete();
            }
        }, 1000);

    }

    private void getHttpData() {
            RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                    .getNewHand(),this)
                    .subscribe(new ApiObserver<NewHand>() {
                        @Override
                        protected void success(NewHand data) {

                            boolean isSuccess = MessageUtils.setCode(getActivity(),
                                    data.getStatus() + "", data.getMsg());
                            if (!isSuccess) {

                                showError();
                                return;
                            }


                            if (data.getData().size() == 0) {

                                showEmpty();
                                return;
                            }
                            showComplete();

                            if(!data.getData().get(0).getContent().equals("")){
                                mWebView.loadDataWithBaseURL("file://",
                                        HtmlUtils.getHtml1(data.getData().get(0).getContent(),
                                                30), mimeType, encoding, "about:blank");
                            }

                        }

                        @Override
                        public void onError(Throwable t) {
                            super.onError(t);

                            showError();
                            toast(t.getMessage());
                        }
                    });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();//返回上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initData() {



    }



}
