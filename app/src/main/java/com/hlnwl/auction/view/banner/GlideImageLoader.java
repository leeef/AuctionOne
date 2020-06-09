package com.hlnwl.auction.view.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hlnwl.auction.R;


/**
 * Created by Administrator on 2017/7/31.
 */

public class GlideImageLoader extends com.youth.banner.loader.ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        Glide.with(context)
                .load(path)
                .apply(new RequestOptions()
                        //.placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                )
                .into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        return null;
    }
}
