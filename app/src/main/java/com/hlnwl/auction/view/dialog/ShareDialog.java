package com.hlnwl.auction.view.dialog;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialogFragment;


/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 *         版本：1.0
 *         创建日期：2019/4/23 17:31
 *         描述：
 */
public class ShareDialog {
    public static final class Builder
            extends BaseDialogFragment.Builder<ShareDialog.Builder>
            implements View.OnClickListener {
        private TextView cancel;
        private LinearLayout wechat, pyq;
        private OnClickListener mListener;
        private boolean mAutoDismiss = true;
        public Builder(FragmentActivity activity) {
            super(activity);
            setContentView(R.layout.hare_popup);
            wechat = (LinearLayout)findViewById(R.id.tv_popup_wechat);
            pyq = (LinearLayout)findViewById(R.id.tv_popup_pyq);
            cancel= (TextView) findViewById(R.id.tv_popup_cancel);

            wechat.setOnClickListener(this);
            pyq.setOnClickListener(this);
            cancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mAutoDismiss) {
                dismiss();
            }
            if (mListener != null) {
                mListener.setOnClick(v);
            }
        }


        public ShareDialog.Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        public ShareDialog.Builder setListener(ShareDialog.OnClickListener l) {
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
