package com.hlnwl.auction.utils.photo;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hlnwl.auction.R;


import java.io.File;

/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class ImageLoaderUtils {

//    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
//        if (imageView == null) {
//            throw new IllegalArgumentException("argument error");
//        }
//        Glide.with(context).load(url)
//                .placeholder(placeholder)
//                .error(error).crossFade().into(imageView);
//    }




    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }

        Glide.with(context).load(url)
                .apply(new RequestOptions().placeholder(R.color.image_bg).error(R.color.image_bg))
                .into(imageView);
    }



    public static void display(Context context, ImageView imageView, File url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .apply(new RequestOptions().placeholder(R.color.image_bg).error(R.color.image_bg))
                .into(imageView);
    }


    public static void display(Context context, ImageView imageView, int url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .apply(new RequestOptions().placeholder(R.color.image_bg).error(R.color.image_bg))
                .into(imageView);
    }
    public static void displayRound(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }
    public static void displayRound(Context context, ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(resId)
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

}
