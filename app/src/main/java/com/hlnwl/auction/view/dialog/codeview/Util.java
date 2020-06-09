package com.hlnwl.auction.view.dialog.codeview;

import android.content.Context;
import android.graphics.Paint;

/**
 * Created by Administrator on 2018/8/1.
 */

public class Util {
    /**
     * DP 转 PX
     */
    public static int dpToPx(Context context, float dpSize) {
        return (int) (context.getResources().getDisplayMetrics().density * dpSize);
    }

    /**
     * @param backgroundTop
     * @param backgroundBottom
     * @param paint
     * @return paint 绘制居中文字时，获取文本底部坐标
     */
    public static float getTextBaseLine(float backgroundTop, float backgroundBottom, Paint paint) {
        final Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (backgroundTop + backgroundBottom - fontMetrics.bottom - fontMetrics.top) / 2;
    }
}
