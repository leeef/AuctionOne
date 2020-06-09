package com.hlnwl.auction.utils.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.hlnwl.auction.app.MyApplication;


/**
 * .                            _ooOoo_
 * .                           o8888888o
 * .                           88" . "88
 * .                           (| -_- |)
 * .                            O\ = /O
 * .                        ____/`---'\____
 * .                      .   ' \\| |// `.
 * .                       / \\||| : |||// \
 * .                     / _||||| -:- |||||- \
 * .                       | | \\\ - /// | |
 * .                     | \_| ''\---/'' | |
 * .                      \ .-\__ `-` ___/-. /
 * .                   ___`. .' /--.--\ `. . __
 * .                ."" '< `.___\_<|>_/___.' >'"".
 * .               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * .                 \ \ `-. \_ __\ /__ _/ .-` / /
 * .         ======`-.____`-.___\_____/___.-`____.-'======
 * .                            `=---='
 * .
 * .         .............................................
 * .                  佛祖镇楼                  BUG辟易
 * .          佛曰:
 * .                  写字楼里写字间，写字间里程序员；
 * .                  程序人员写程序，又拿程序换酒钱。
 * .                  酒醒只在网上坐，酒醉还来网下眠；
 * .                  酒醉酒醒日复日，网上网下年复年。
 * .                  但愿老死电脑间，不愿鞠躬老板前；
 * .                  奔驰宝马贵者趣，公交自行程序员。
 * .                  别人笑我忒疯癫，我笑自己命太贱；
 * .                  不见满街漂亮妹，哪个归得程序员？
 * .
 * . 项目名称：SuperGo
 * . 包名：com.hlnwl.supergo.utils
 * . 类描述：
 * . 创建人：LoveTing(zhengleilei)
 * . 邮箱：ting970626@163.com
 * . 创建时间：2017/9/12 11:09
 * . 修改备注：
 * . 版本号：V 1.0.0
 */

public class SPUtils {
    private static String USERINFO = "paimai";//这个名字自己起
    private static SharedPreferences settings;

    private static SharedPreferences getSP(Context ctx) {
        return ctx.getSharedPreferences(USERINFO, ctx.MODE_PRIVATE);
    }
    private static final String LOGIN = "login";
    private static final String TOKEN = "token";
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String NICKNAME = "nickname";
    private static final String HEADIMG = "headimg";
    private static final String REALNAME = "realname";
    private static final String IS_SET_PAY_PWD = "is_set_pay_pwd";//是否设置支付密码
    private static final String MIN_TX = "min_tx";
    private static final String DAGE = "dage";
    private static final String CUSTOMER_PHONE = "customer_phone";
    private static final String LANGUAGE = "language";
    private static final String JING = "jing";
    private static final String LOU = "lou";

    private static final String IDCODE = "idcode";
    private static final String PHONE = "phone";
    private static final String BALANCE = "balance";
    private static final String SHOPQB = "shopqb";

    private static final String CHANT="chant";//0-未提交1-未审核2-正常3-关闭 商户状态
    private static final String IS_BANK_CARD = "is_bank_card";
    private static final String IS_ALIPAY = "is_alipay";
    //保存历史搜索中的数据
    private static final String HISTORY_SEARCH = "history";





    /*-----------------------------------------------------*/


    /**
     * set login_judge
     */
    public static void setLogin( String login) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(LOGIN, login).commit();
    }

    /**
     * get login_judge
     */
    public static String getLogin() {
        settings = getSP(MyApplication.getmContext());
        return settings.getString(LOGIN, "");
    }

    /*-----------------------------------------------------*/


    /**
     * set token
     */
    public static void setToken( String token) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(TOKEN, token).commit();
    }

    /**
     * get token
     */
    public static String getToken() {
        settings = getSP(MyApplication.getmContext());
        return settings.getString(TOKEN, "");
    }

    /*-----------------------------------------------------*/


    /**
     * set username
     */
    public static void setUserName( String userName) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(USER_NAME, userName).commit();
    }

    /**
     * get username
     */
    public static String getUserName() {
        settings = getSP(MyApplication.getmContext());
        return settings.getString(USER_NAME, "");
    }

    /*-----------------------------------------------------*/


    /**
     * set userid
     */
    public static void setUserId( String userId) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(USER_ID, userId).commit();
    }

    /**
     * get userid
     */
    public static String getUserId() {
        settings = getSP(MyApplication.getmContext());
        return settings.getString(USER_ID, "");
    }

    /*-----------------------------------------------------*/



    /**
     * set 身份证号码
     */
    public static void setIdcode( String idcode) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(IDCODE, idcode).commit();
    }

    /**
     * get 身份证号码
     */
    public static String getIdcode() {
        settings = getSP(MyApplication.getmContext());
        return settings.getString(IDCODE, "");
    }

    /*-----------------------------------------------------*/


    /**
     * set 电话号码
     */
    public static void setPhone( String phone) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(PHONE, phone).commit();
    }

    /**
     * get 电话号码
     */
    public static String getPhone() {
        settings = getSP(MyApplication.getmContext());
        return settings.getString(PHONE, "");
    }

    /*-----------------------------------------------------*/

    /**
     * set 昵称
     */
    public static void setNickname( String nickname) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(NICKNAME, nickname).commit();
    }

    /**
     * get 昵称
     */
    public static String getNickname() {
        settings = getSP(MyApplication.getmContext());
        return settings.getString(NICKNAME, "");
    }

    /*-----------------------------------------------------*/

    /**
     * set 真是姓名
     */
    public static void setRealname( String realname) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(REALNAME, realname).commit();
    }

    /**
     * get 真实姓名
     */
    public static String getRealname() {
        settings = getSP(MyApplication.getmContext());
        return settings.getString(REALNAME, "");
    }

    /*-----------------------------------------------------*/


    /**
     * set 头像
     */
    public static void setHeadimg( String headimg) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(HEADIMG, headimg).commit();
    }

    /**
     * get 头像
     */
    public static String getHeadimg() {
        settings = getSP(MyApplication.getmContext());
        return settings.getString(HEADIMG, "");
    }

    /*-----------------------------------------------------*/


    /**
     * set 搜索历史
     */
    public static void setHistorySearch( String historySearch) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(HISTORY_SEARCH, historySearch).commit();
    }

    /**
     * get 搜索历史
     */
    public static String getHistorySearch() {
        settings = getSP(MyApplication.getmContext());
        return settings.getString(HISTORY_SEARCH, "");
    }

    /*-----------------------------------------------------*/

    /**
     * set 商户状态
     */
    public static void setChant( String chant) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(CHANT, chant).commit();
    }

    /**
     * get 商户状态
     */
    public static String getChant() {
        settings = getSP(MyApplication.getmContext());
        return settings.getString(CHANT, "");
    }

    /*-----------------------------------------------------*/




    /**
     * set 余额
     */
    public static void setBalance( String balance) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(BALANCE, DESUtils.encryptDES(balance)).apply();
    }

    /**
     * get 余额
     */
    public static String getBalance() {
        settings = getSP(MyApplication.getmContext());
        return DESUtils.decryptDES(settings.getString(BALANCE, ""));
    }


    /*-----------------------------------------------------*/

    /**
     * set 商户钱包余额
     */
    public static void setShopqb( String shopqb) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(SHOPQB, DESUtils.encryptDES(shopqb)).apply();
    }

    /**
     * get 商户钱包余额
     */
    public static String getShopqb() {
        settings = getSP(MyApplication.getmContext());
        return DESUtils.decryptDES(settings.getString(SHOPQB, ""));
    }


    /*-----------------------------------------------------*/

    /**
     * set 是否设置支付密码
     */
    public static void setIsSetPayPwd( String content) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(IS_SET_PAY_PWD, DESUtils.encryptDES(content)).commit();
    }

    /**
     * get 是否设置支付密码
     */
    public static String getIsSetPayPwd() {
        settings = getSP(MyApplication.getmContext());
        return DESUtils.decryptDES(settings.getString(IS_SET_PAY_PWD, ""));
    }

    /*-----------------------------------------------------*/


    /**
     * set 是否设置绑定银行卡
     */
    public static void setIsBankCard( String isBankCard) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(IS_BANK_CARD, DESUtils.encryptDES(isBankCard)).commit();
    }

    /**
     * get 是否设置绑定银行卡
     */
    public static String getIsBankCard() {
        settings = getSP(MyApplication.getmContext());
        return DESUtils.decryptDES(settings.getString(IS_BANK_CARD, ""));
    }

    /*-----------------------------------------------------*/


    /**
     * set 是否绑定支付宝
     */
    public static void setIsAlipay( String isAlipay) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(IS_ALIPAY, DESUtils.encryptDES(isAlipay)).commit();
    }

    /**
     * get 是否绑定支付宝
     */
    public static String getIsAlipay() {
        settings = getSP(MyApplication.getmContext());
        return DESUtils.decryptDES(settings.getString(IS_ALIPAY, ""));
    }

    /*-----------------------------------------------------*/

    /**
     * set 最低提现金额
     */
    public static void setMinTx( String minTx) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(MIN_TX, DESUtils.encryptDES(minTx)).commit();
    }

    /**
     * get 最低提现金额
     */
    public static String getMinTx() {
        settings = getSP(MyApplication.getmContext());
        return DESUtils.decryptDES(settings.getString(MIN_TX, ""));
    }

    /*-----------------------------------------------------*/


    /**
     * set 提现手续费
     */
    public static void setDage( String dage) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(DAGE, DESUtils.encryptDES(dage)).commit();
    }

    /**
     * get 提现手续费
     */
    public static String getDage() {
        settings = getSP(MyApplication.getmContext());
        return DESUtils.decryptDES(settings.getString(DAGE, ""));
    }

    /*-----------------------------------------------------*/

    /**
     * set 精品店铺押金
     */
    public static void setJing( String jing) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(JING, DESUtils.encryptDES(jing)).commit();
    }

    /**
     * get 精品店铺押金
     */
    public static String getJing() {
        settings = getSP(MyApplication.getmContext());
        return DESUtils.decryptDES(settings.getString(JING, ""));
    }

    /*-----------------------------------------------------*/


    /**
     * set 捡漏店铺押金
     */
    public static void setLou( String lou) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(LOU, DESUtils.encryptDES(lou)).commit();
    }

    /**
     * get 捡漏店铺押金
     */
    public static String getLou() {
        settings = getSP(MyApplication.getmContext());
        return DESUtils.decryptDES(settings.getString(LOU, ""));
    }

    /*-----------------------------------------------------*/

    /**
     * set 客服电话
     */
    public static void setCustomerPhone( String customerPhone) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(CUSTOMER_PHONE, DESUtils.encryptDES(customerPhone)).commit();
    }

    /**
     * get 客服电话
     */
    public static String getCustomerPhone() {
        settings = getSP(MyApplication.getmContext());
        return DESUtils.decryptDES(settings.getString(CUSTOMER_PHONE, ""));
    }

    /*-----------------------------------------------------*/


    /**
     * set 语言
     */
    public static void setLanguage( String language) {
        settings = getSP(MyApplication.getmContext());
        settings.edit().putString(LANGUAGE, DESUtils.encryptDES(language)).commit();
    }

    /**
     * get 语言
     */
    public static String getLanguage() {
        settings = getSP(MyApplication.getmContext());
        return DESUtils.decryptDES(settings.getString(LANGUAGE, ""));
    }

    /*-----------------------------------------------------*/
    /**
     * SP中清除所有数据
     */
    public static void clear(Context c) {
        settings = getSP(c);
        settings.edit().clear().apply();
    }


}
