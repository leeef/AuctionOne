package com.hlnwl.auction.view.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hlnwl.auction.MainActivity;
import com.hlnwl.auction.R;


import java.util.ArrayList;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;


/**
 * Created by 大灯泡 on 2016/1/22.
 * 菜单。
 */
public class MorePopup extends BasePopupWindow implements BaseQuickAdapter.OnItemClickListener {


    private RecyclerView more;
    private List<String> datas = new ArrayList<>();
    private MorePopupAdapter mAdapter;
    private OnItemClickListener mOnItemClickListener;

    public MorePopup(Context context) {
        super(context);
        datas.add(context.getResources().getString(R.string.setting_simplified_chinese));
        datas.add(context.getResources().getString(R.string.designer_frames));
        more = findViewById(R.id.popup_more_content);
        setAlignBackground(false);
        setPopupGravity(Gravity.BOTTOM | Gravity.RIGHT);
        more.setLayoutManager(new LinearLayoutManager(context));
        more.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        mAdapter=new MorePopupAdapter();
        more.setAdapter(mAdapter);
        mAdapter.setNewData(datas);
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    protected Animation onCreateShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(getScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0));
        set.addAnimation(getDefaultAlphaAnimation());
        return set;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(getScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0));
        set.addAnimation(getDefaultAlphaAnimation(false));
        return set;
    }

    @Override
    public void showPopupWindow(View v) {
        setOffsetX(v.getWidth()/4);
        super.showPopupWindow(v);
    }


    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_more);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mOnItemClickListener!=null){
            this.mOnItemClickListener.OnItemClick(position,datas.get(position));
        }
    }
    /**
     * 点击内容那个TextView对应的调用
     * @param onContentListener
     */
    public void setOnMoreItemListener(OnItemClickListener
                                             onContentListener){
        this.mOnItemClickListener=onContentListener;
    }
    public interface OnItemClickListener {
        //第一个参数是适配器的item，第二个参数是位置
     //短按事件
        void OnItemClick(int position,String content);

    }
}
