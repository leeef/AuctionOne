package com.hlnwl.auction.ui.release;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.hlnwl.auction.R;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/9/13.
 */

public class GridAdapter extends BaseAdapter {
    private List<String> listUrls;
    private LayoutInflater inflater;
    private Context mContext;
    private OnItemClickListener mListener;
    public GridAdapter(Context context, List<String> listUrls) {
        this.listUrls = listUrls;
        this.mContext=context;
        if(listUrls.size() == 7){
            listUrls.remove(listUrls.size()-1);
        }
        inflater = LayoutInflater.from(context);
    }

    public int getCount(){
        return  listUrls.size();
    }
    @Override
    public String getItem(int position) {
        return listUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GridAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new GridAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.item_my_photo, parent,false);
            holder.image = (ImageView) convertView.findViewById(R.id.item_sell_car_img);
            holder.delete=(ImageView) convertView.findViewById(R.id.item_sell_car_delete);
            convertView.setTag(holder);
        } else {
            holder = (GridAdapter.ViewHolder)convertView.getTag();
        }

        final String path=listUrls.get(position);
        if (path.equals("paizhao")){
            holder.image.setImageResource(R.mipmap.xiangji);
            holder.delete.setVisibility(View.GONE);
        }else {
            holder.delete.setVisibility(View.VISIBLE);
            ImageLoaderUtils.display(mContext, holder.image, path);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.setOnItemClick(view,position);
                    }
                }
            });
        }
        return convertView;
    }
    class ViewHolder {
        ImageView image;
        ImageView delete;
    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(View v, int positoin);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
