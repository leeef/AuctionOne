package com.hlnwl.auction.view.widget;

import android.content.Context;

import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 *         版本：1.0
 *         创建日期：2019/4/9 15:55
 *         描述：
 */
public class DividerItemDecoration extends Y_DividerItemDecoration {
    public DividerItemDecoration(Context context) {
        super(context);
    }

    @Override
    public Y_Divider getDivider(int itemPosition) {
        Y_Divider divider = null;
        switch (itemPosition % 2) {
            case 0:
                //每一行第一个显示rignt和bottom
                divider = new Y_DividerBuilder()
                        .setRightSideLine(true, 0xffF4F4F4, 5, 0, 0)
                        .setLeftSideLine(true, 0xffF4F4F4, 10, 0, 0)
                        .setTopSideLine(true, 0xffF4F4F4, 5, 0, 0)
                        .create();
                break;
            case 1:
                //第二个显示Left和bottom
                divider = new Y_DividerBuilder()
                        .setLeftSideLine(true, 0xffF4F4F4, 5, 0, 0)
                        .setRightSideLine(true, 0xffF4F4F4, 10, 0, 0)
                        .setTopSideLine(true, 0xffF4F4F4, 5, 0, 0)
                        .create();
                break;
            default:
                break;
        }
        return divider;
    }
}
