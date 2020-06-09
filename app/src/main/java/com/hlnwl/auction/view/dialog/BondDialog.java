package com.hlnwl.auction.view.dialog;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

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
public class BondDialog  {
    public static final class Builder
            extends BaseDialogFragment.Builder<BondDialog.Builder>
            implements View.OnClickListener {
        private SuperButton bond;
        private OnClickListener mListener;
        private boolean mAutoDismiss = true;
//        private String mPrice="";
        public Builder(FragmentActivity activity,String price) {
            super(activity);
            setContentView(R.layout.dialog_bond);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//            this.mPrice=price;
            bond=findViewById(R.id.bond_sure);
            TextView priceShow=findViewById(R.id.bond_price);
            priceShow.setText(price+activity.getResources().getString(R.string.money_unit));
            bond.setOnClickListener(this);
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
        public BondDialog.Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        public BondDialog.Builder setListener(BondDialog.OnClickListener l) {
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
