package com.hlnwl.auction.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.allen.library.SuperButton;
import com.blankj.utilcode.util.ToastUtils;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.BaseDialogFragment;
import com.hlnwl.auction.view.radiogrouplib.NestedRadioGroup;
import com.hlnwl.auction.view.radiogrouplib.NestedRadioLayout;


/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 *         版本：1.0
 *         创建日期：2019/4/25 09:23
 *         描述：
 */
public class PayDialog {
    public static final class Builder
            extends BaseDialogFragment.Builder<PayDialog.Builder> implements View.OnClickListener {

        private NestedRadioGroup payGroup;
        private NestedRadioLayout yue,alipay;
        private LinearLayout close;
        private SuperButton pay;
        private boolean mAutoDismiss = true;
        private String payType = "alipay";
        private OnPayListener mListener;
        private String mType;
        private TextView title;

        public Builder(FragmentActivity activity,String type) {
            super(activity);
            this.mType=type;

            setContentView(R.layout.dialog_pay);
            setGravity(Gravity.BOTTOM);
            setAnimStyle(BaseDialog.AnimStyle.LEFT);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);


            payGroup = findViewById(R.id.pay_dialog_nestedGroup);
            close = findViewById(R.id.pay_dialog_close);
            pay = findViewById(R.id.pay_dialog_pay);
            alipay=findViewById(R.id.pay_dialog_alipay);
            yue=findViewById(R.id.rpay_dialog_yue);
            title=findViewById(R.id.title);
            alipay.setChecked(true);
            if (mType.equals("join")){
                yue.setVisibility(View.GONE);
            }else {
                yue.setVisibility(View.VISIBLE);
            }
//            if (mType.equals("tixian")){
//                title.setText("提现方式");
//                pay.setText("确认提现");
//            }else {
//                title.setText("支付方式");
//                pay.setText("确认支付");
//            }
            payGroup.setOnCheckedChangeListener(new NestedRadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(NestedRadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rpay_dialog_yue:
                            payType = "yue";
                            break;
                        case R.id.pay_dialog_wechat:
                            payType = "weChat";
                            break;
                        case R.id.pay_dialog_alipay:
                            payType = "alipay";
                            break;
                    }
                }
            });

            close.setOnClickListener(this);
            pay.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.pay_dialog_close:
                    if (mAutoDismiss) {
                        dismiss();
                    }
                    if (mListener != null) {
                        mListener.onCancel(getDialog());
                    }
                    break;
                case R.id.pay_dialog_pay:

                    if (payType.equals("")){
                        ToastUtils.showShort("您暂未选择支付方式");
                        return;
                    }
                    if (mListener != null) {
                        mListener.onSelected(getDialog(),payType);
                        dismiss();
                    }
                    break;
            }

        }
        public Builder setPrice(int resId) {
            return setPrice(getContext().getText(resId));
        }
        public Builder setPrice(CharSequence text) {
            title.setText(text);
            return this;
        }
        public PayDialog.Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        public PayDialog.Builder setListener(PayDialog.OnPayListener l) {
            mListener = l;
            return this;
        }
    }

    public interface OnPayListener {
        /**
         * 选择完成后回调
         */
        void onSelected(Dialog dialog, String pay_style);

        /**
         * 点击取消时回调
         */
        void onCancel(Dialog dialog);
    }

}
