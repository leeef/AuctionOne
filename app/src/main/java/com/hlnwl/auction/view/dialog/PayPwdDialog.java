package com.hlnwl.auction.view.dialog;

import android.content.Context;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.fragment.app.FragmentActivity;


import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialogFragment;
import com.hlnwl.auction.message.PayMessage;
import com.hlnwl.auction.view.dialog.codeview.CodeView;
import com.hlnwl.auction.view.dialog.codeview.KeyboardView;

import org.greenrobot.eventbus.EventBus;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 *         版本：1.0
 *         创建日期：2019/4/25 11:03
 *         描述：
 */
public class PayPwdDialog {
    public static final class Builder
            extends BaseDialogFragment.Builder<PayPwdDialog.Builder> {
        private boolean mAutoDismiss = true;
        public Builder(FragmentActivity activity) {
            super(activity);
            setContentView(R.layout.dialog_pay_pwd);
            final KeyboardView keyboardView = (KeyboardView) findViewById(R.id.password_input);
            final CodeView codeView = (CodeView) findViewById(R.id.password_view);
            codeView.setShowType(activity.getIntent().getIntExtra("showType", CodeView.SHOW_TYPE_PASSWORD));
            codeView.setLength(activity.getIntent().getIntExtra("length", 6));
            keyboardView.setCodeView(codeView);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

            codeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keyboardView.show();
                }
            });
            codeView.setListener(new CodeView.Listener() {
                @Override
                public void onValueChanged(String value) {
                    // TODO: 2017/2/5  内容发生变化
                }

                @Override
                public void onComplete(String value) {
                    // TODO: 2017/2/5 输入完成
                    EventBus.getDefault().post(new PayMessage(codeView.getCode()));
                    if (mAutoDismiss) {
                        dismiss();
                    }
                }
            });
        }


    }
}
