package com.hlnwl.auction.ui.home;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.home.HomeBean;
import com.hlnwl.auction.bean.home.HomeMultipleItem;
import com.hlnwl.auction.bean.home.NewsData;

import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/17 10:08
 * 描述：
 */
public class MessageItemProvider extends BaseItemProvider<HomeMultipleItem, BaseViewHolder> {
    private List<String> titles=new ArrayList<>();
    @Override
    public int viewType() {
        return HomeAdapter.MESSAGE;
    }

    @Override
    public int layout() {
        return R.layout.home_message;
    }

    @Override
    public void convert(BaseViewHolder helper, HomeMultipleItem data, int position) {
        titles.clear();
        for (NewsData newsBean:data.getMessage()){
            titles.add(newsBean.getTitle());
        }
        MarqueeView gonggao=helper.getView(R.id.marqueeView);
        gonggao.startWithList(titles);
        // 在代码里设置自己的动画
//        gonggao.startWithList(titles, R.anim.anim_bottom_in, R.anim.anim_top_out);
    }
}
