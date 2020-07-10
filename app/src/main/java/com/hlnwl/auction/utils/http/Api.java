package com.hlnwl.auction.utils.http;

import com.hlnwl.auction.bean.NoDataBean;
import com.hlnwl.auction.bean.PhoneBean;
import com.hlnwl.auction.bean.QuestionListBean;
import com.hlnwl.auction.bean.category.CategoryBean;
import com.hlnwl.auction.bean.category.CategoryErjiBean;
import com.hlnwl.auction.bean.goods.GoodListBean;
import com.hlnwl.auction.bean.goods.GoodsCategoryBean;
import com.hlnwl.auction.bean.goods.GoodsDetailBean;
import com.hlnwl.auction.bean.goods.GoodsListBean;
import com.hlnwl.auction.bean.goods.OfferBean;
import com.hlnwl.auction.bean.home.HomeBean;
import com.hlnwl.auction.bean.home.NewHand;
import com.hlnwl.auction.bean.home.NewsBean;
import com.hlnwl.auction.bean.home.SpecialExhibitionBean;
import com.hlnwl.auction.bean.shop.ShopGoodsBean;
import com.hlnwl.auction.bean.shop.ShopListBean;
import com.hlnwl.auction.bean.shop.ShopOrderBean;
import com.hlnwl.auction.bean.shop.ShopTypeBean;
import com.hlnwl.auction.bean.user.LoginBean;
import com.hlnwl.auction.bean.user.MineBean;
import com.hlnwl.auction.bean.user.TicketListBean;
import com.hlnwl.auction.bean.user.bid.BidOrderBean;
import com.hlnwl.auction.bean.user.bid.BidRecordBean;
import com.hlnwl.auction.bean.user.info.ALiPayMsgBean;
import com.hlnwl.auction.bean.user.info.AddressBean;
import com.hlnwl.auction.bean.user.info.BankMsgBean;
import com.hlnwl.auction.bean.user.info.MoneyDetailBean;
import com.hlnwl.auction.bean.user.info.WithdrawalRecordBean;
import com.hlnwl.auction.bean.user.order.OrderBean;
import com.hlnwl.auction.bean.user.order.OrderDetailBean;
import com.hlnwl.auction.bean.user.shop.GoodMsgBean;
import com.hlnwl.auction.bean.user.shop.JoinBean;
import com.hlnwl.auction.bean.user.shop.MyGoodsBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/20 09:09
 * 描述：
 */
public interface Api {

    @FormUrlEncoded
    @POST("Interfun/getcode")
    Observable<NoDataBean> getVerCode(@Field("lang") String lang,
                                      @Field("mobile") String mobile,
                                      @Field("type") String type);

    @FormUrlEncoded
    @POST("entran/register")
    Observable<NoDataBean> regist(@Field("lang") String lang,
                                  @Field("mobile") String mobile,
                                  @Field("mobile_code") String mobile_code,
                                  @Field("pwd") String pwd,
                                  @Field("pay_pwd") String pay_pwd);

    @FormUrlEncoded
    @POST("entran/forget")
    Observable<NoDataBean> forgetPwd(@Field("lang") String lang,
                                     @Field("mobile") String mobile,
                                     @Field("mobile_code") String mobile_code,
                                     @Field("pwd") String pwd,
                                     @Field("repwd") String repwd);

    @FormUrlEncoded
    @POST("entran/in")
    Observable<LoginBean> login(@Field("lang") String lang,
                                @Field("mobile") String mobile,
                                @Field("pwd") String pwd);

    @FormUrlEncoded
    @POST("usercenter/is_face")
    Observable<NoDataBean> setHeadImg(@Field("lang") String lang,
                                      @Field("userid") String userid,
                                      @Field("token") String token,
                                      @Field("img") String img);

    @FormUrlEncoded
    @POST("usercenter/up_info")
    Observable<NoDataBean> modifyNickName(@Field("lang") String lang,
                                          @Field("userid") String userid,
                                          @Field("token") String token,
                                          @Field("theme") String theme);

    @FormUrlEncoded
    @POST("index/index")
    Observable<HomeBean> getHome(@Field("lang") String lang,
                                 @Field("page") int page);

    @FormUrlEncoded
    @POST("goods/good_detail")
    Observable<GoodsDetailBean> getGoodsData(@Field("lang") String lang,
                                             @Field("id") String id,
                                             @Field("userid") String userid);

    @FormUrlEncoded
    @POST("Goods/shopgoodsdetail")
    Observable<GoodsDetailBean> getGoodsData2(@Field("id") String id,
                                              @Field("userid") String userid);

    @FormUrlEncoded
    @POST("order/single")
    Observable<NoDataBean> offer(@Field("lang") String lang,
                                 @Field("userid") String userid,
                                 @Field("token") String token,
                                 @Field("gid") String gid,
                                 @Field("paytype") String paytype);


    @FormUrlEncoded
    @POST("goods/good_record")
    Observable<OfferBean> getOfferList(@Field("lang") String lang,
                                       @Field("page") int page,
                                       @Field("id") String id);

    @FormUrlEncoded
    @POST("goods/fine")
    Observable<GoodsCategoryBean> getDesigner(@Field("lang") String lang,
                                              @Field("page") int page,
                                              @Field("cid") String cid);

    @FormUrlEncoded
    @POST("goods/rule")
    Observable<GoodsCategoryBean> getHunt(@Field("lang") String lang,
                                          @Field("page") int page,
                                          @Field("cid") String cid);

    @FormUrlEncoded
    @POST("goods/cateone")
    Observable<CategoryBean> getCategory(@Field("lang") String lang);

    @FormUrlEncoded
    @POST("index/news")
    Observable<NewsBean> getNews(@Field("lang") String lang);

    @FormUrlEncoded
    @POST("order/sub_give")
    Observable<NoDataBean> islike(@Field("lang") String lang,
                                  @Field("userid") String userid,
                                  @Field("token") String token,
                                  @Field("id") String id);

    @FormUrlEncoded
    @POST("goods/catetwo")
    Observable<CategoryErjiBean> getCategoryContent(@Field("lang") String lang,
                                                    @Field("cid") String cid);

    @FormUrlEncoded
    @POST("goods/goodlist")
    Observable<GoodsListBean> getGoodsList(@Field("lang") String lang,
                                           @Field("page") int page,
                                           @Field("cid") String cid,
                                           @Field("keystr") String keystr);

    @FormUrlEncoded
    @POST("usercenter/address")
    Observable<AddressBean> getAddress(@Field("lang") String lang,
                                       @Field("userid") String userid,
                                       @Field("token") String token);

    @FormUrlEncoded
    @POST("usercenter/addr_default")
    Observable<NoDataBean> setDefaultAddress(@Field("lang") String lang,
                                             @Field("userid") String userid,
                                             @Field("token") String token,
                                             @Field("id") String id);

    @FormUrlEncoded
    @POST("usercenter/adddel")
    Observable<NoDataBean> delAddress(@Field("lang") String lang,
                                      @Field("userid") String userid,
                                      @Field("token") String token,
                                      @Field("id") String id);

    @FormUrlEncoded
    @POST("usercenter/address_add")
    Observable<NoDataBean> addAddress(@Field("lang") String lang,
                                      @Field("userid") String userid,
                                      @Field("token") String token,
                                      @Field("theme") String theme,
                                      @Field("phone") String phone,
                                      @Field("city") String city,
                                      @Field("address") String address);

    @FormUrlEncoded
    @POST("usercenter/address_edit")
    Observable<NoDataBean> editAddress(@Field("lang") String lang,
                                       @Field("userid") String userid,
                                       @Field("token") String token,
                                       @Field("id") String id,
                                       @Field("theme") String theme,
                                       @Field("phone") String phone,
                                       @Field("city") String city,
                                       @Field("address") String address);

    @FormUrlEncoded
    @POST("merchant/sub_tenant")
    Observable<JoinBean> joinShop(@Field("lang") String lang,
                                  @Field("userid") String userid,
                                  @Field("token") String token,
                                  @Field("realname") String realname,
                                  @Field("identity") String identity,
                                  @Field("phone") String phone,
                                  @Field("pos_card") String pos_card,
                                  @Field("rev_card") String rev_card,
                                  @Field("name") String name,
                                  @Field("pic") String pic,
                                  @Field("genre") String genre,
                                  @Field("paytype") String paytype);

    @FormUrlEncoded
    @POST("merchant/add_good")
    Observable<ResponseBody> release(@Field("lang") String lang,
                                     @Field("userid") String userid,
                                     @Field("token") String token,
                                     @Field("is_bid") String is_bid,
                                     @Field("name") String name,
                                     @Field("cid") String cid,
                                     @Field("price") String price,
                                     @Field("low_price") String low_price,
                                     @Field("bid_price") String bid_price,
                                     @Field("content") String content,
                                     @Field("speci") String speci,
                                     @Field("pic") String pic,
                                     @Field("endtime") String endtime);

    @FormUrlEncoded
    @POST("index/kefu")
    Observable<PhoneBean> getPhone(@Field("lang") String lang);

    @FormUrlEncoded
    @POST("usercenter/index")
    Observable<MineBean> getUserInfo(@Field("lang") String lang,
                                     @Field("userid") String userid,
                                     @Field("token") String token);

    @FormUrlEncoded
    @POST("usercenter/cash_ali")
    Observable<ALiPayMsgBean> getAliPayMsg(@Field("lang") String lang,
                                           @Field("userid") String userid,
                                           @Field("token") String token);

    @FormUrlEncoded
    @POST("usercenter/cash_bank")
    Observable<BankMsgBean> getBankMsg(@Field("lang") String lang,
                                       @Field("userid") String userid,
                                       @Field("token") String token);

    @FormUrlEncoded
    @POST("usercenter/ali_add")
    Observable<NoDataBean> bindAliPay(@Field("lang") String lang,
                                      @Field("userid") String userid,
                                      @Field("token") String token,
                                      @Field("realname") String realname,
                                      @Field("number") String number,
                                      @Field("mobile_code") String mobile_code);

    @FormUrlEncoded
    @POST("usercenter/bank_add")
    Observable<NoDataBean> bindBank(@Field("lang") String lang,
                                    @Field("userid") String userid,
                                    @Field("token") String token,
                                    @Field("name") String name,
                                    @Field("realname") String realname,
                                    @Field("number") String number,
                                    @Field("bank_addr") String bank_addr,
                                    @Field("mobile_code") String mobile_code);


    @FormUrlEncoded
    @POST("goods/stolist")
    Observable<ShopListBean> getShopList(@Field("lang") String lang,
                                         @Field("page") int page,
                                         @Field("keystr") String keystr);


    // 展销专场
    @FormUrlEncoded
    @POST("goods/excate")
    Observable<SpecialExhibitionBean> getSpecialExhibitionList(@Field("lang") String lang,
                                                               @Field("page") int page);


    // 展销专场 商品列表
    @FormUrlEncoded
    @POST("goods/exlist")
    Observable<GoodsListBean> getGoodsList1(@Field("lang") String lang,
                                            @Field("page") int page,
                                            @Field("cid") String cid
    );


    // 问卷调查
    @FormUrlEncoded
    @POST("merchant/surlist")
    Observable<QuestionListBean> getQuestionList(@Field("lang") String lang,
                                                 @Field("page") int page,
                                                 @Field("userid") String userid,
                                                 @Field("token") String token);


    // 店铺分类
    @FormUrlEncoded
    @POST("merchant/shopselect")
    Observable<ShopTypeBean> getShopType(@Field("userid") String id, @Field("token") String token);

    // 商铺付款信息
    @FormUrlEncoded
    @POST("merchant/shoppay")
    Observable<JoinBean> payShop(@Field("userid") String id,
                                 @Field("token") String token,
                                 @Field("shopid") String shopId,
                                 @Field("price") String price,
                                 //支付类型 1支付宝 2微信
                                 @Field("paytype") int type);


    @FormUrlEncoded
    @POST("goods/shoplist")
    Observable<ShopGoodsBean> getShopGoods(@Field("lang") String lang,
                                           @Field("page") int page,
                                           @Field("sid") String sid);

    @FormUrlEncoded
    @POST("order/bidlist")
    Observable<BidRecordBean> getBidRecord(@Field("lang") String lang,
                                           @Field("page") int page,
                                           @Field("userid") String userid,
                                           @Field("token") String token,
                                           @Field("type") String type);

    @FormUrlEncoded
    @POST("usercenter/tixian_mon")
    Observable<NoDataBean> withdrawal(@Field("lang") String lang,
                                      @Field("userid") String userid,
                                      @Field("token") String token,
                                      @Field("amount") String amount,
                                      @Field("paytype") String paytype);

    @FormUrlEncoded
    @POST("usercenter/money_list")
    Observable<MoneyDetailBean> getMoneyDetail(@Field("lang") String lang,
                                               @Field("page") int page,
                                               @Field("userid") String userid,
                                               @Field("token") String token,
                                               @Field("type") String type);


    @FormUrlEncoded
    @POST("usercenter/cash_list")
    Observable<WithdrawalRecordBean> getWithdrawalRecord(@Field("lang") String lang,
                                                         @Field("page") int page,
                                                         @Field("userid") String userid,
                                                         @Field("token") String token);

    @FormUrlEncoded
    @POST("order/affirm")
    Observable<BidOrderBean> bidOrder(@Field("lang") String lang,
                                      @Field("id") String id,
                                      @Field("userid") String userid,
                                      @Field("token") String token);

    @FormUrlEncoded
    @POST("order/sub_pay")
    Observable<NoDataBean> pay(@Field("lang") String lang,
                               @Field("userid") String userid,
                               @Field("token") String token,
                               @Field("id") String id,
                               @Field("addrid") String addrid,
                               @Field("paytype") String paytype);

    @FormUrlEncoded
    @POST("order/order_list")
    Observable<OrderBean> getOrder(@Field("lang") String lang,
                                   @Field("page") int page,
                                   @Field("userid") String userid,
                                   @Field("token") String token,
                                   @Field("type") String type);

    @FormUrlEncoded
    @POST("merchant/bolist")
    Observable<ShopOrderBean> getShopOrder(@Field("lang") String lang,
                                           @Field("page") int page,
                                           @Field("userid") String userid,
                                           @Field("token") String token,
                                           @Field("type") String type,
                                           @Field("status") String status);

    @FormUrlEncoded
    @POST("order/details")
    Observable<OrderDetailBean> getOrderDetail(@Field("lang") String lang,
                                               @Field("userid") String userid,
                                               @Field("token") String token,
                                               @Field("id") String id);

    @FormUrlEncoded
    @POST("Order/shoporderdeta")
    Observable<OrderDetailBean> getOrderDetail2(@Field("userid") String userid,
                                                @Field("token") String token,
                                                @Field("id") String id);

    @FormUrlEncoded
    @POST("order/set_over")
    Observable<NoDataBean> confirmOrder(@Field("lang") String lang,
                                        @Field("userid") String userid,
                                        @Field("token") String token,
                                        @Field("id") String id);

    @FormUrlEncoded
    @POST("Order/shoporderover")
    Observable<NoDataBean> confirmOrder2(@Field("userid") String userid,
                                         @Field("token") String token,
                                         @Field("oid") String id);

    @FormUrlEncoded
    @POST("order/set_over")
    Observable<NoDataBean> confirmFh(@Field("lang") String lang,
                                     @Field("userid") String userid,
                                     @Field("token") String token,
                                     @Field("id") String id);

    @FormUrlEncoded
    @POST("order/to_ment")
    Observable<NoDataBean> comment(@Field("lang") String lang,
                                   @Field("userid") String userid,
                                   @Field("token") String token,
                                   @Field("id") String id,
                                   @Field("score") String score,
                                   @Field("content") String content,
                                   @Field("icon") String icon);

    @FormUrlEncoded
    @POST("Order/shoporderment")
    Observable<NoDataBean> comment2(@Field("userid") String userid,
                                    @Field("token") String token,
                                    @Field("id") String id,
                                    @Field("score") String score,
                                    @Field("content") String content,
                                    @Field("icon") String icon,
                                    @Field("type") String type);

    @FormUrlEncoded
    @POST("merchant/sgoods")
    Observable<MyGoodsBean> getMyGoodsList(@Field("lang") String lang,
                                           @Field("page") int page,
                                           @Field("userid") String userid,
                                           @Field("token") String token);

    @FormUrlEncoded
    @POST("merchant/gdel")
    Observable<NoDataBean> delGoods(@Field("lang") String lang,
                                    @Field("userid") String userid,
                                    @Field("token") String token,
                                    @Field("id") String id);

    @FormUrlEncoded
    @POST("merchant/gedit")
    Observable<GoodMsgBean> getGoodMsg(@Field("lang") String lang,
                                       @Field("userid") String userid,
                                       @Field("token") String token,
                                       @Field("id") String id);

    @FormUrlEncoded
    @POST("merchant/edit_good")
    Observable<NoDataBean> editGood(@Field("lang") String lang,
                                    @Field("userid") String userid,
                                    @Field("token") String token,
                                    @Field("id") String id,
                                    @Field("is_bid") String is_bid,
                                    @Field("name") String name,
                                    @Field("cid") String cid,
                                    @Field("price") String price,
                                    @Field("low_price") String low_price,
                                    @Field("bid_price") String bid_price,
                                    @Field("content") String content,
                                    @Field("speci") String speci,
                                    @Field("pic") String pic,
                                    @Field("new_pic") String new_pic,
                                    @Field("endtime") String endtime);


    @GET("index/novice")
    Observable<NewHand> getNewHand();

    @FormUrlEncoded
    @POST("Goods/shopgoods")
    Observable<GoodListBean> getGoodList(@Field("page") int page);

    //自营商城提交订单
    @FormUrlEncoded
    @POST("Merchant/buyshoporder")
    Observable<JoinBean> shopOrder(@Field("userid") String userid,
                                   @Field("token") String token,
                                   @Field("gid") String gid,
                                   @Field("aid") String aid,
                                   @Field("num") String num,
                                   @Field("paytype") String paytype);

    //用户优惠券列表
    @FormUrlEncoded
    @POST("Usercenter/couponlist")
    Observable<TicketListBean> ticketList(@Field("userid") String userid,
                                          @Field("token") String token,
                                          @Field("status") int status);

    //订单列表
    //订单类型type 0-待付款 1-已付款 2-已发货3-等待评论 4-完成
    @FormUrlEncoded
    @POST("Order/shoporderlist")
    Observable<OrderBean> orderList(@Field("userid") String userid,
                                    @Field("token") String token,
                                    @Field("type") String type,
                                    @Field("page") int page);
}
