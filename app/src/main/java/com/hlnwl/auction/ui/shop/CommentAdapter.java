package com.hlnwl.auction.ui.shop;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.allen.library.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.hlnwl.auction.R;
import com.hlnwl.auction.bean.shop.ShopComment;
import com.hlnwl.auction.ui.common.ImagePagerActivity;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 15:57
 * 描述：
 */
public class CommentAdapter extends BaseQuickAdapter<ShopComment, BaseViewHolder> {


    public CommentAdapter() {
        super(R.layout.item_shop_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopComment item) {
        CircleImageView img=helper.getView(R.id.item_shop_comment_top_img);
        ScaleRatingBar scaleRatingBar=helper.getView(R.id.item_shop_comment_top_ratingBar);
        scaleRatingBar.setRating(Float.parseFloat(item.getScore()));
        ImageLoaderUtils.display(mContext,img,item.getFace_img());
        helper.setText(R.id.item_shop_comment_top_name,item.getTheme());
        helper.setText(R.id.item_shop_comment_top_time,item.getAddtime());
        helper.setText(R.id.item_shop_comment_top_content,item.getDes());
        ImageView img1=helper.getView(R.id.image1);
        ImageView img2=helper.getView(R.id.image2);
        ImageView img3=helper.getView(R.id.image3);
        if (item.getIcon().size()==0){
            img1.setImageResource(R.mipmap.ic_launcher);
        }else if (item.getIcon().size()==1){
            ImageLoaderUtils.display(mContext,img1,item.getIcon().get(0));
            ArrayList<String> imgUrls=new ArrayList<>();
            imgUrls.addAll(item.getIcon());

            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ImagePagerActivity.class);
                    intent.putStringArrayListExtra("imageUrls", imgUrls);
                    intent.putExtra("position",  "1");
                    mContext.startActivity(intent);
                }
            });
        }else if (item.getIcon().size()==2){
            ImageLoaderUtils.display(mContext,img1,item.getIcon().get(0));
            ImageLoaderUtils.display(mContext,img2,item.getIcon().get(1));
            ArrayList<String> imgUrls=new ArrayList<>();
            imgUrls.addAll(item.getIcon());
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ImagePagerActivity.class);
                    intent.putStringArrayListExtra("imageUrls", imgUrls);
                    intent.putExtra("position",  "1");
                    mContext.startActivity(intent);
                }
            });
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ImagePagerActivity.class);
                    intent.putStringArrayListExtra("imageUrls", imgUrls);
                    intent.putExtra("position",  "2");
                    mContext.startActivity(intent);
                }
            });

        }else {
            ImageLoaderUtils.display(mContext,img1,item.getIcon().get(0));
            ImageLoaderUtils.display(mContext,img2,item.getIcon().get(1));
            ImageLoaderUtils.display(mContext,img3,item.getIcon().get(2));
            ArrayList<String> imgUrls=new ArrayList<>();
            imgUrls.addAll(item.getIcon());
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ImagePagerActivity.class);
                    intent.putStringArrayListExtra("imageUrls", imgUrls);
                    intent.putExtra("position",  "1");
                    mContext.startActivity(intent);
                }
            });
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ImagePagerActivity.class);
                    intent.putStringArrayListExtra("imageUrls", imgUrls);
                    intent.putExtra("position",  "2");
                    mContext.startActivity(intent);
                }
            });
            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ImagePagerActivity.class);
                    intent.putStringArrayListExtra("imageUrls", imgUrls);
                    intent.putExtra("position",  "3");
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
