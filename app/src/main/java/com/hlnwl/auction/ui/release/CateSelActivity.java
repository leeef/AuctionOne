package com.hlnwl.auction.ui.release;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.category.CategoryBean;
import com.hlnwl.auction.bean.category.CategoryData;
import com.hlnwl.auction.bean.category.CategoryErji;
import com.hlnwl.auction.bean.category.CategoryErjiBean;
import com.hlnwl.auction.ui.category.MenuAdapter;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.sp.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/26 15:17
 * 描述：
 */
public class CateSelActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.category_menu)
    RecyclerView categoryMenu;
    @BindView(R.id.category_content)
    RecyclerView categoryContent;

    private List<CategoryData> menuList = new ArrayList<>();
    private CateSelAdapter mContentAdapter;
    private String cateOne="";
    private String cateTwo="";
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(getActivity().getResources().getString(R.string.sel_category));
        mTitleTb.setRightTitle(StringUtils.getString(R.string.confirm));
        mTitleTb.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                if (cateTwo.length()==0){
                    toast(StringUtils.getString(R.string.cate_null));
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra("one",cateOne);
                intent.putExtra("two",cateTwo);
                setResult(100,intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
        getData();
    }
    private void getData() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getCategory(SPUtils.getLanguage()), this)
                .subscribe(new ApiObserver<CategoryBean>() {
                               @Override
                               protected void success(CategoryBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       showError();
                                       return;
                                   }
                                   menuList.addAll(data.getData());
                                   getContent(data.getData().get(0).getId());
                                   cateOne= GsonUtils.toJson(data.getData().get(0));
                                   initAdapter();
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   showError();
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private void getContent(String id) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getCategoryContent(SPUtils.getLanguage(),id), this)
                .subscribe(new ApiObserver<CategoryErjiBean>() {
                               @Override
                               protected void success(CategoryErjiBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       showError();
                                       return;
                                   }
                                   initContent(data.getData());
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   showError();
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private void initContent(List<CategoryErji> data) {
        categoryContent.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        if (mContentAdapter==null){
            mContentAdapter = new CateSelAdapter(getActivity());
        }else {
            mContentAdapter.notifyDataSetChanged();
        }
        mContentAdapter.setNewData(data);
        categoryContent.setAdapter(mContentAdapter);
        mContentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setSelected(false);
                }
                data.get(position).setSelected(true);
                cateTwo= GsonUtils.toJson(data.get(position));
                mContentAdapter.notifyDataSetChanged();
            }
        });
        showComplete();
    }

    /*初始化布局*/
    private void initAdapter() {
//        showComplete();
        final MenuAdapter menuAdapter = new MenuAdapter(menuList);
        categoryMenu.setAdapter(menuAdapter);
        categoryMenu.setLayoutManager(new LinearLayoutManager(getActivity()));

        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                menuAdapter.notifyDataSetChanged();
                getContent(menuList.get(position).getId());
                cateOne= GsonUtils.toJson(menuList.get(position));
            }
        });

        categoryMenu.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, final int i) {

                menuAdapter.setSelectPos(i);
                menuAdapter.notifyDataSetChanged();
//                contentAdapter.setNewData(contenList.get(i));
//                contentAdapter.notifyDataSetChanged();
                getContent(menuList.get(i).getId());
                cateOne= GsonUtils.toJson(menuList.get(i));
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }
        });

    }
}
