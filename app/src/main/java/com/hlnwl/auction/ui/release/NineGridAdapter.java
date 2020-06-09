package com.hlnwl.auction.ui.release;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hlnwl.auction.R;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/27 08:32
 * 描述：
 */
public class NineGridAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public NineGridAdapter() {
        super(R.layout.item_my_photo);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView img=helper.getView(R.id.item_sell_car_img);
        ImageView del=helper.getView(R.id.item_sell_car_delete);
        if (item.equals(imageTranslateUri(R.mipmap.xiangji))){
            del.setVisibility(View.GONE);
        }else {
            del.setVisibility(View.VISIBLE);
        }
//        if (item.equals("paizhao")){
//            img.setImageResource(R.mipmap.xiangji);
//        }else {
//            ImageLoaderUtils.display(mContext,img,item);
//        }
        ImageLoaderUtils.display(mContext,img,item);
        helper.addOnClickListener(R.id.item_sell_car_delete);
    }
    /**
     * res/drawable(mipmap)/xxx.png::::uri－－－－>url
     *
     * @return
     */
    private String imageTranslateUri(int resId) {

        Resources r = mContext.getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(resId) + "/"
                + r.getResourceTypeName(resId) + "/"
                + r.getResourceEntryName(resId));

        return uri.toString();
    }

}
