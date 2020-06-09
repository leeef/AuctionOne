package com.hlnwl.auction.view.timecount;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.hlnwl.auction.R;


/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 *         版本：1.0
 *         创建日期：2018/6/14
 *         描述：
 */
public class TimeCount extends CountDownTimer {

    private TextView getVerCode;
    private Context mContext;

    public TimeCount(Context context, long millisInFuture, long countDownInterval, TextView getVerCode) {
        super(millisInFuture, countDownInterval);
        this.getVerCode = getVerCode;
        this.mContext=context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        getVerCode.setEnabled(false);
        getVerCode.setText("(" + millisUntilFinished / 1000 + ") "+mContext.getResources().getString(R.string.re_sent));
    }

    @Override
    public void onFinish() {
        getVerCode.setText(mContext.getResources().getString(R.string.re_get_ver_code));
        getVerCode.setEnabled(true);

    }
}
