package com.hlnwl.auction.utils.http;

import android.util.Log;

import com.hlnwl.auction.BuildConfig;

/**
 * @Description:
 * @Author: leeeef
 * @CreateDate: 2020/6/11 11:37
 */
public class LogUtils {
    private static boolean isRealease = BuildConfig.DEBUG ? false : true;

    public static void logXj(String str) {
        if (!isRealease) {
            Log.e("xj", str);
        }

    }

    public static void logD(String message) {
        if (!isRealease) {
            Log.d("xiaojw", message);
        }
    }

    public static void logXj(int str) {

        if (!isRealease) {
            Log.e("xj", str + "");
        }

    }

    public static void logTest(String str) {
        if (!isRealease) {
            Log.e("TEST", str);
        }
    }

    public static void logi(String Tag, String str) {
        if (!isRealease) {
            i(Tag, str);
        }
    }


    public static void logE(String Tag, String str) {
        if (!isRealease) {
            e(Tag, str);
        }
    }

    public static void i(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.i(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时v
        while (msg.length() > max_str_length) {
            Log.e(tag, "/\n" + msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.e(tag, "/\n" + msg);
    }

}
