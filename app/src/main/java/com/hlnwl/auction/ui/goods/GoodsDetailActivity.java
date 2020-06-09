package com.hlnwl.auction.ui.goods;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperButton;
import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.StringUtils;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.NoDataBean;
import com.hlnwl.auction.bean.goods.GoodsDetailBean;
import com.hlnwl.auction.bean.goods.OfferBean;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.ui.common.ImagePagerActivity;
import com.hlnwl.auction.ui.common.LoginActivity;
import com.hlnwl.auction.ui.shop.ShopHomeAcitivity;
import com.hlnwl.auction.ui.user.bid.BidRecordActivity;
import com.hlnwl.auction.utils.CountdownUtils;
import com.hlnwl.auction.utils.StringsUtils;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.my.MyLinearLayoutManager;
import com.hlnwl.auction.utils.my.PhoneUtil;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.banner.GlideImageLoader;
import com.hlnwl.auction.view.dialog.BidRecordDialog;
import com.hlnwl.auction.view.dialog.BondDialog;
import com.hlnwl.auction.view.dialog.PayDialog;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/19 13:59
 * 描述：
 */
public class GoodsDetailActivity extends MyActivity {
    @BindView(R.id.goods_detail_banner)
    Banner mBanner;
    @BindView(R.id.goods_detail_price_start)
    TextView mPriceStart;
    @BindView(R.id.goods_detail_price_add_once)
    TextView mAddOnce;
    @BindView(R.id.goods_detail_title)
    TextView mTitle;
    @BindView(R.id.goods_detail_introduce)
    TextView mIntroduce;
    @BindView(R.id.goods_detail_weight)
    TextView mWeight;
    @BindView(R.id.goods_detail_from)
    TextView mFrom;
    @BindView(R.id.goods_detail_style)
    TextView mStyle;
    @BindView(R.id.goods_detail_bid_record)
    RecyclerView mGoodsBidRecord;
    @BindView(R.id.goods_detail_shop_img)
    ImageView mShopImg;
    @BindView(R.id.goods_detail_shop_name)
    TextView mShopName;
    @BindView(R.id.goods_detail_phone)
    TextView mPhone;
    @BindView(R.id.goods_detail_time_count)
    TextView mTimeCount;
    @BindView(R.id.goods_detail_like_tv)
    TextView mLikeNum;
    @BindView(R.id.goods_detail_see_tv)
    TextView mSeeNum;
    @BindView(R.id.good_detail_web)
    WebView mWeb;
    @BindView(R.id.goods_detail_is_j)
    TextView mIsJp;
    @BindView(R.id.goods_detail_attribute)
    RecyclerView mGoodsDetailAttribute;
    @BindView(R.id.offer_message)
    TextView mOfferMessage;
    @BindView(R.id.goods_detail_make_price)
    TextView mGoodsDetailMakePrice;
    @BindView(R.id.chujia_liebiao)
    LinearLayout liebiao;
    @BindView(R.id.countdownView1)
    CountdownView countdownView1;
    @BindView(R.id.ll_count)
    LinearLayout llCount;
    @BindView(R.id.ll_price)
    LinearLayout llPrice;
    @BindView(R.id.goods_detail_shop_in)
    SuperButton goodsDetailShopIn;
    @BindView(R.id.ll_shop)
    LinearLayout llShop;
    @BindView(R.id.goods_detail_like)
    LinearLayout goodsDetailLike;
    @BindView(R.id.tv_exhibition)
    TextView tvExhibition;


    private String good_id = "";
    private GoodsDetailBean.DataBean mGoodsDetailData;
    private TimeCount time;//倒计时
    private GoodsAttrAdapter mAttrAdapter;
    private OfferAdapter mOfferAdapter;
    private static boolean flag = true;
    private ShineButton shineButton;
    private String price = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        shineButton = (ShineButton) findViewById(R.id.shine_button);
        if (shineButton != null)
            shineButton.init(this);
        shineButton.setClickable(false);
        shineButton.setFocusable(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginEventBus(LoginMessage update) {
        getData();
        setTimer();
    }

    @Override
    protected void initData() {
        good_id = getIntent().getStringExtra("id");
        showLoading();
        getData();
        setTimer();

        if (getIntent().getIntExtra("tag", 0) == 1) {
            llCount.setVisibility(View.VISIBLE);
            llPrice.setVisibility(View.GONE);
            llShop.setVisibility(View.GONE);
            tvExhibition.setText(getResources().getString(R.string.exhibition1));
            mGoodsDetailMakePrice.setText(getResources().getString(R.string.display1));
            mTimeCount.setText(getResources().getString(R.string.call_customer1));
        } else {
            llCount.setVisibility(View.GONE);
            llPrice.setVisibility(View.VISIBLE);
        }


    }

    private void getData() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getGoodsData(SPUtils.getLanguage(), good_id, StringUtils.null2Length0(SPUtils.getUserId())), this)
                .subscribe(new ApiObserver<GoodsDetailBean>() {
                               @Override
                               protected void success(GoodsDetailBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       showError();
                                       return;
                                   }
                                   if (data.getData().get(0) != null) {
                                       mGoodsDetailData = data.getData().get(0);
                                       initUI(data.getData().get(0));
                                   }
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   showError();
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private void initUI(GoodsDetailBean.DataBean dataBean) {


        // 倒计时
        CountdownUtils.getCounDown(countdownView1,
                StringsUtils.getStrTime(String.valueOf(dataBean.getEndtime())));


        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(dataBean.getPic());
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//                banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(3500);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        ArrayList<String> imgUrls = new ArrayList<>();
        imgUrls.addAll(dataBean.getPic());
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(GoodsDetailActivity.this, ImagePagerActivity.class);
                intent.putStringArrayListExtra("imageUrls", imgUrls);
                intent.putExtra("position", position + "");
                startActivity(intent);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        log(mGoodsDetailData.getStatus());
        if (mGoodsDetailData.getIs_bid().equals("1")) {
            mPriceStart.setVisibility(View.VISIBLE);
            mAddOnce.setVisibility(View.VISIBLE);
            mPriceStart.setText(getResources().getString(R.string.price_start)
                    + getResources().getString(R.string.money) +
                    dataBean.getLow_price());
            mAddOnce.setText(getResources().getString(R.string.add_once)
                    + getResources().getString(R.string.money) +
                    dataBean.getBid_price());
            if (mGoodsDetailData.getStatus().equals("0")) {
                Date date = new Date();
                long timestamp = date.getTime();    //时间戳
                long midTime = mGoodsDetailData.getEndtime() * 1000 - timestamp;

//        long mytime=TimeUtils.getMillisByNow(mGoodsDetailData.getEndtime(),);
                if (midTime > 0) {
                    log(midTime + "");
//                    time = new TimeCount(this, midTime, 1000, mTimeCount);
//                    time.start();
                }
                //mGoodsDetailMakePrice.setBackgroundColor(ColorUtils.getColor(R.color.main));
            } else {
                // mGoodsDetailMakePrice.setBackgroundColor(ColorUtils.getColor(R.color.gray));
                // mTimeCount.setText(StringsUtils.getString(R.string.auction_over));

            }
            liebiao.setVisibility(View.VISIBLE);
        } else {
            mPriceStart.setVisibility(View.INVISIBLE);
            mAddOnce.setVisibility(View.INVISIBLE);
            // mTimeCount.setText(StringsUtils.getString(R.string.goods_zhanshi));
            // mGoodsDetailMakePrice.setBackgroundColor(ColorUtils.getColor(R.color.gray));
            //mGoodsDetailMakePrice.setText(StringsUtils.getString(R.string.zhanshi));
            liebiao.setVisibility(View.GONE);
        }


        mTitle.setText(dataBean.getName());
//        String css = "<style type=\"text/css\"> img {" +
//                "width:100%;" +
//                "height:auto;" +
//                "}" +
//                "body {" +
//                "margin-right:15px;" +
//                "margin-left:15px;" +
//                "margin-top:15px;" +
//                "font-size:15px;" +
//                "}" +
//                "</style>";
//        String url = goodsDetailData.getGoods_content();
//        url = url.replaceAll("<img src=\"", "<img src=\"" + CONFIG.URL);
//        String html = "<html><header>" + css + "</header><body>" + url + "</body></html>";
//        goodsWeb.loadDataWithBaseURL("file://", html, "text/html", "UTF-8", "about:blank");
        mWeb.loadDataWithBaseURL("file://", dataBean.getContent(),
                "text/html", "utf-8", "about:blank");
        if (dataBean.getSpeci().size() > 0) {
            log(dataBean.getSpeci().get(0).toString());
            mGoodsDetailAttribute.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAttrAdapter = new GoodsAttrAdapter();
            mAttrAdapter.setNewData(dataBean.getSpeci());
            mGoodsDetailAttribute.setAdapter(mAttrAdapter);
        }
        ImageLoaderUtils.display(this, mShopImg, dataBean.getSpic());
        mShopName.setText(dataBean.getSname());
        mPhone.setText(getResources().getString(R.string.hotline) + " " + dataBean.getSphone());
        if (dataBean.getGenre().equals("1")) {
            mIsJp.setVisibility(View.VISIBLE);
        } else {
            mIsJp.setVisibility(View.GONE);
        }

        log(mGoodsDetailData.getStatus() + "    " + mGoodsDetailData.getIs_bid());
        if (mGoodsDetailData.getGive().equals("0")) {
            shineButton.setChecked(false);
        } else {
            shineButton.setChecked(true);
        }
        mLikeNum.setText(mGoodsDetailData.getBang());
        mSeeNum.setText(mGoodsDetailData.getViews());
        getOfferList();
        showComplete();
    }

    private void setTimer() {
        mHandler.postDelayed(runnable, 30000);
    }

    private Handler mHandler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //在这里执行定时需要的操作
            getOfferList();
            if (flag) {
                mHandler.postDelayed(this, 30000);
            }
        }
    };

    private void getOfferList() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getOfferList(SPUtils.getLanguage(), 1, good_id), this)
                .subscribe(new ApiObserver<OfferBean>() {
                               @Override
                               protected void success(OfferBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }

                                   initOffer(data.getData());
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private void initOffer(List<OfferBean.DataBean> data) {
        List<OfferBean.DataBean> offerList = new ArrayList<>();
        offerList.clear();

        if (data.size() > 4) {
            for (int i = 0; i < 5; i++) {
                offerList.add(data.get(i));
            }
        } else {
            offerList.addAll(data);
        }
        if (offerList.size() > 0) {
            price = Double.parseDouble(offerList.get(0).getPrice())
                    + Double.parseDouble(mGoodsDetailData.getBid_price()) + "";
        } else {
            price = mGoodsDetailData.getLow_price();
        }

        mGoodsBidRecord.setLayoutManager(new MyLinearLayoutManager(getActivity()));
        if (mOfferAdapter == null) {
            mOfferAdapter = new OfferAdapter();
            mGoodsBidRecord.setAdapter(mOfferAdapter);
        } else {
            mOfferAdapter.notifyDataSetChanged();
        }
        mOfferAdapter.setNewData(offerList);

        //第一页如果不够一页就不显示没有更多数据布局
        if (offerList.size() < 5) {
            mOfferMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toast(getResources().getString(R.string.no_more_offer));
                }
            });
        } else {
            mOfferMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(GoodsDetailActivity.this, OfferRecordActivity.class)
                            .putExtra("good_id", good_id));
                }
            });

        }


        if (offerList.size() == 0) {
            mOfferMessage.setText(getResources().getString(R.string.null_offer));

        } else if (offerList.size() > 2) {
            mOfferMessage.setText(getResources().getString(R.string.see_more));
        }


    }

    private void stopTimer() {
        flag = false;
    }

    @OnClick({R.id.goods_detail_back, R.id.goods_detail_shop_in,
            R.id.goods_detail_like,
            R.id.goods_detail_make_price, R.id.goods_detail_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goods_detail_back:
                finish();
                break;
            case R.id.goods_detail_shop_in:
                startActivity(new Intent(this, ShopHomeAcitivity.class)
                        .putExtra("name", mGoodsDetailData.getSname())
                        .putExtra("pic", mGoodsDetailData.getSpic())
                        .putExtra("id", mGoodsDetailData.getSid()));
                break;
            case R.id.goods_detail_like:
                if (SPUtils.getLogin().length() == 0 || SPUtils.getLogin() == null) {
                    startActivity(LoginActivity.class);
                } else {
                    if (shineButton.isChecked()) {
                        like(false);
                    } else {
                        like(true);
                    }
                }
                break;

            case R.id.goods_detail_make_price:
                if (mGoodsDetailData.getIs_bid().equals("2")) {
                    toast(StringUtils.getString(R.string.goods_zhanshi));
                } else {
                    if (mGoodsDetailData.getStatus().equals("0")) {
                        if (SPUtils.getLogin().length() == 0 || SPUtils.getLogin() == null) {
                            startActivity(LoginActivity.class);
                        } else {
                            getIfOffer();
                        }
                    } else {
                        toast(StringUtils.getString(R.string.auction_over));
                    }
                }

                break;
            case R.id.goods_detail_phone:
                PhoneUtil.callPhone(this, mGoodsDetailData.getSphone());
                break;
        }
    }

    private void like(boolean islike) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .islike(SPUtils.getLanguage(), SPUtils.getUserId(), SPUtils.getToken(), good_id), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(GoodsDetailActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }
                                   if (islike) {
                                       shineButton.setChecked(true, true);
                                       mLikeNum.setText(Integer.parseInt(mLikeNum.getText().toString()) + 1 + "");
                                   } else {
                                       shineButton.setChecked(false, true);
                                       mLikeNum.setText(Integer.parseInt(mLikeNum.getText().toString()) - 1 + "");
                                   }

                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private BaseDialog payOffer() {
        return new PayDialog.Builder(GoodsDetailActivity.this, "pay")
                .setPrice(StringUtils.getString(R.string.chujia) + "   " +
                        price + StringUtils.getString(R.string.money_unit))
                .setListener(new PayDialog.OnPayListener() {
                    @Override
                    public void onSelected(Dialog dialog, String pay_style) {
                        if (pay_style.equals("weChat")) {

                        } else if (pay_style.equals("alipay")) {

                        } else {
                            offer("1");
                        }
                    }

                    @Override
                    public void onCancel(Dialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void offer(String paytype) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .offer(SPUtils.getLanguage(), StringUtils.null2Length0(SPUtils.getUserId()),
                        StringUtils.null2Length0(SPUtils.getToken()),
                        good_id, paytype), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }
                                   EventBus.getDefault().post(new LoginMessage("update"));
                                   getOfferList();
                                   toast(data.getMsg());
                                   getBidRecord();

                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private BaseDialog getBidRecord() {
        return new BidRecordDialog.Builder(GoodsDetailActivity.this)
                .setListener(new BidRecordDialog.OnClickListener() {
                    @Override
                    public void setOnClick(View v) {
                        startActivity(BidRecordActivity.class);
                    }
                }).show();
    }

    private void getIfOffer() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getGoodsData(SPUtils.getLanguage(),
                        good_id, StringUtils.null2Length0(SPUtils.getUserId())), this)
                .subscribe(new ApiObserver<GoodsDetailBean>() {
                               @Override
                               protected void success(GoodsDetailBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }
                                   if (data.getData().get(0) != null) {
                                       log(data.getData().get(0).getOffer());
                                       if (data.getData().get(0).getOffer().equals("0")) {
                                           new BondDialog.Builder(GoodsDetailActivity.this,
                                                   data.getData().get(0).getPrice())
                                                   .setListener(new BondDialog.OnClickListener() {
                                                       @Override
                                                       public void setOnClick(View v) {
                                                           payOffer();
                                                       }
                                                   })
//                                                   .setGravity(Gravity.BOTTOM)
//                                                   .setAnimStyle(BaseDialog.AnimStyle.BOTTOM)
                                                   .show();
                                       } else {
                                           payOffer();
                                       }
                                   }
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   toast(t.getMessage());
                               }
                           }
                );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private class TimeCount extends CountDownTimer {

        private TextView getVerCode;
        private Context mContext;


        public TimeCount(Context context, long millisInFuture,
                         long countDownInterval, TextView getVerCode) {
            super(millisInFuture, countDownInterval);
            this.getVerCode = getVerCode;
            this.mContext = context;

        }

        @Override
        public void onTick(long millisUntilFinished) {
            getVerCode.setEnabled(false);
            getVerCode.setText(mContext.getResources().getString(R.string.goods_detail_time_count) + "  " + secToTime(millisUntilFinished));
        }

        @Override
        public void onFinish() {
            getVerCode.setText(mContext.getResources().getString(R.string.auction_over));
            getVerCode.setEnabled(true);

        }
    }


    public String secToTime(long time) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;
        long day = time / dd;
        long hour = (time - day * dd) / hh;
        long minute = (time - day * dd - hour * hh) / mi;
        long second = (time - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = time - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strDay + getResources().getString(R.string.day) + "  " + strHour + " : " + strMinute + " : " + strSecond;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (time != null) {
            time.cancel();
        }
        mHandler.removeCallbacks(runnable);
        stopTimer();
    }
}


