package com.hlnwl.auction.utils.my;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.util.UUID;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/20 16:07
 * 描述：
 */
public class FrescoUtils {
    /**
     * 加载基本图片
     *
     * @param url
     * @param simpleDraweeView
     */
    public static void showBasicPic(String url, SimpleDraweeView simpleDraweeView) {
        Uri uri = Uri.parse(url);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载渐进式图片
     *
     * @param url
     * @param simpleDraweeView
     */
    public static void showProgressivePic(String url, SimpleDraweeView simpleDraweeView) {
        Uri uri = Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);
    }

    /**
     * 加载圆角图片
     *
     * @param url
     * @param simpleDraweeView
     * @param radius
     * @param width
     * @param color
     */
    public static void showfilletPic(String url, SimpleDraweeView simpleDraweeView, float radius, float width, int color) {
        Uri uri = Uri.parse(url);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(0f);
        if (width > 0) {
            roundingParams.setBorder(color, width);//描边线
        }
        roundingParams.setCornersRadius(radius);//总体圆角
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     *  圆角图片
     * @param url
     * @param simpleDraweeView
     * @param topLeft
     * @param topRight
     * @param bottmLeft
     * @param bottomRight
     * @param width
     * @param color
     */
    public static void showYuanJiaoPic(String url, SimpleDraweeView simpleDraweeView, float topLeft, float topRight, float bottmLeft, float bottomRight, float width, int color) {
        Uri uri = Uri.parse(url);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(0f);
        if (width > 0) {
            roundingParams.setBorder(color, width);//描边线
        }
        roundingParams.setCornersRadii(topLeft, topRight, bottmLeft, bottomRight);
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载圆形图片
     *
     * @param url
     * @param simpleDraweeView
     * @param width
     * @param color
     */
    public static void showCirclePic(String url, SimpleDraweeView simpleDraweeView, float width, int color) {
        if (url == null) {
            simpleDraweeView.setImageURI(url);
            return;
        }
        Uri uri = Uri.parse(url);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(0f);
        if (width > 0) {
            roundingParams.setBorder(color, width);
        }
        roundingParams.setRoundAsCircle(true);
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载gif动图
     *
     * @param url
     * @param simpleDraweeView
     */
    public static void showGIFPic(String url, SimpleDraweeView simpleDraweeView) {
        Uri uri = Uri.parse(url);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setAutoPlayAnimations(true)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);
    }

    /**
     * 高斯模糊图片
     *
     * @param url
     * @param simpleDraweeView
     * @param iterations
     * @param blurRadius
     */
    public static void showUrlBlur(String url, SimpleDraweeView simpleDraweeView, int iterations, int blurRadius) {
        Uri uri = Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(new IterativeBoxBlurPostProcessor(iterations, blurRadius))
                .build();
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController())
                .setImageRequest(request)
                .build();
        simpleDraweeView.setController(controller);
    }
}
