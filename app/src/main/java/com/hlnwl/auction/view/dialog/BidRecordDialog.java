package com.hlnwl.auction.view.dialog;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;

import com.allen.library.SuperButton;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialogFragment;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/19 14:24
 * 描述：
 */
public class BidRecordDialog {
    public static final class Builder
            extends BaseDialogFragment.Builder<BidRecordDialog.Builder>
            implements View.OnClickListener {
        private LinearLayout see;
        private OnClickListener mListener;
        private boolean mAutoDismiss = true;
        public Builder(FragmentActivity activity) {
            super(activity);
            setContentView(R.layout.dialog_bid_record);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);


            see=findViewById(R.id.bid_record_see);
            see.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mAutoDismiss) {
                dismiss();
            }
            if (mListener != null) {
                mListener.setOnClick(view);
            }
        }
        public BidRecordDialog.Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        public BidRecordDialog.Builder setListener(BidRecordDialog.OnClickListener l) {
            mListener = l;
            return this;
        }
    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnClickListener {
        void setOnClick(View v);
    }

}
