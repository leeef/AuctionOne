package com.hlnwl.auction.utils.my;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/16 14:02
 * 描述：
 */
public class PhoneUtil {

    /*
     *判断手机号码正则
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {

        String regExp = "^1[2-9]\\d{9}$";

        Pattern p = Pattern.compile(regExp);

        Matcher m = p.matcher(str);

        return m.matches();

    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

}
