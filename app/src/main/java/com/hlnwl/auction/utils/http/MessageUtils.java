package com.hlnwl.auction.utils.http;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hlnwl.auction.R;
import com.hlnwl.auction.message.QuitMessage;
import com.hlnwl.auction.ui.common.IndexActivity;
import com.hlnwl.auction.ui.common.LoginActivity;
import com.hlnwl.auction.utils.sp.SPUtils;


import org.greenrobot.eventbus.EventBus;


/**
 * . 类描述：
 * . 创建人：LoveTing(zhengleilei)
 * . 邮箱：ting970626@163.com
 * . 创建时间：2017/9/12 14:42
 * . 修改备注：
 * . 版本号：V 1.0.0
 */

public class MessageUtils {

    //失败
    private static final String ERROR = "0";

    //成功
    private static final String SUCCESS = "1";
    //必须返回登录
    private static final String RELOGIN = "2";



    /**
     * * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static MessageUtils instance = new MessageUtils();
    }

    /**
     * 私有化构造方法
     */
    private MessageUtils() {

    }

    public static MessageUtils getInstance() {
        return SingletonHolder.instance;
    }


    /**
     * @param mContext 上下文
     * @param code     code
     * @param message  message
     * @return true 标识可以走下一步 false标识不可以走下一步
     */
    public static boolean setCode(Activity mContext, String code, String message) {
//        if (code.equals(RELOGIN)){
//            showLoginError(mContext, message);
//            return false;
//        }else if (code.equals(SUCCESS)){
//            return true;
//        } else if (code.equals(ERROR)) {
//            ToastUtils.showShort(message);
//            return false;
//        }
        switch (code) {
            case ERROR:
                if (message.length()>0){
                    ToastUtils.showShort(message);
                }
                return false;

            case SUCCESS:
                //当成功的时候不显示
//                ToastUtils.showToastThree(mContext,message);
                return true;
            case RELOGIN:
                showLoginError(mContext, message);
                return false;

            //TODO 后面可以写很多
            default:
                if (message.length()>0){
                    ToastUtils.showShort(message);
                }
                return false;
        }

    }

    //登录问题的提示框框
    private static void showLoginError(final Activity mContext, String content) {
        AlertDialog.Builder phone_dialog = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.activity_message_code, null);
        phone_dialog.setView(view);
        final AlertDialog alertDialog = phone_dialog.create();
        alertDialog.setCancelable(false);
        TextView activity_message_ok = (TextView) view.findViewById(R.id.activity_message_ok);//確定按钮
        activity_message_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                SPUtils.clear(mContext);
                EventBus.getDefault().post(new QuitMessage("quit"));
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                ActivityUtils.finishToActivity(IndexActivity.class, true);


//                ActivityStackManager.getInstance().finishAllActivities();
//                mContext.finish();
            }
        });

        TextView activity_message_context = (TextView) view.findViewById(R.id.activity_message_context);
        activity_message_context.setText(content);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
