package com.hlnwl.auction.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/10/21 09:52
 * 描述：
 */
public class OnegoGridLayoutManager extends GridLayoutManager {
    public OnegoGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public OnegoGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public OnegoGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            return super.scrollHorizontallyBy(dx, recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("hello", "scrollHorizontallyBy, IndexOutOfBoundsException");
            e.printStackTrace();
            return 0;
        }
    }

}
