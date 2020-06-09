package com.hlnwl.auction.ui.category;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.hlnwl.auction.MainActivity;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.ui.goods.GoodsListActivity;
import com.hlnwl.auction.utils.my.JsonMutualList;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/27.
 */

public class SearchActivity extends MyActivity implements View.OnClickListener {
    private LinearLayout mActivitySearchLL;
    private EditText mSearch;
    private TextView mSearchIv;
    private TextView mClean;
//    private TagFlowLayout mFlowlayout;
    private TagFlowLayout mFlowlayoutHot;
    private ImageView back;

    private RecyclerView searchHistory;

    private List<String> data=new ArrayList<>();
//    private String searchData;



    private SearchAdapter  mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        initData();
        initListener();
    }


    @Override
    public void initView() {
        back=findViewById(R.id.search_back);
        mSearch = (EditText) findViewById(R.id.activity_search_et);//输入框
        mSearchIv = (TextView) findViewById(R.id.activity_search_iv);//搜索
        mClean = (TextView) findViewById(R.id.activity_search_clean);//清除
        searchHistory = (RecyclerView) findViewById(R.id.activity_search_flowlayout);//控件的显示
        mFlowlayoutHot = (TagFlowLayout) findViewById(R.id.activity_search_flowlayout_hot);//控件的显示
        mActivitySearchLL = (LinearLayout) findViewById(R.id.activity_search_ll);//历史搜索的父布局
    }



    @Override
    protected void initData() {

        //先获取数据
        String history = SPUtils.getHistorySearch();


//        Log.e("spsp",history);

        data = JsonMutualList.jsonToList(history);
        if (data != null && data.size() > 0) {
            mActivitySearchLL.setVisibility(View.VISIBLE);
            //赋值历史搜索
            //设置布局管理器
            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
            //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
            flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
            //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
            flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
            //justifyContent 属性定义了项目在主轴上的对齐方式。
            flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。
            searchHistory.setLayoutManager(flexboxLayoutManager);
            if (mAdapter==null){
                mAdapter=new SearchAdapter();
                mAdapter.setNewData(data);
                searchHistory.setAdapter(mAdapter);
            }else {
                mAdapter.notifyDataSetChanged();
            }

            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                intActivity(data.get(position));
                }
            });
        } else {
            //没有搜索历史 Gone掉
            mActivitySearchLL.setVisibility(View.GONE);
        }

        //获取热门搜索历史
//        getHotSearch();
    }

    public void initListener() {
        mClean.setOnClickListener(this);
        mSearchIv.setOnClickListener(this);
        back.setOnClickListener(this);
        mSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    /*隐藏软键盘*/
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //点击键盘的确定按键就触发这个监听
                    getSearchEt();
                    return true;
                }
                return false;
            }
        });

    }

    private void intActivity(String content) {
       startActivity(new Intent(this, SearchResultActivity.class)
       .putExtra("keyword",content));



//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString("content", content);
////        bundle.putString("dataInterface",);
//        intent.putExtras(bundle);
////        intent.setClass(SearchActivity.this, SearchResultActivity.class);
//        startActivity(intent);
    }



    public void getSearchEt() {
        String content = mSearch.getText().toString().trim();

        if (!TextUtils.isEmpty(content)) {//数据不为空显示
            intActivity(content);//TODO 这边不做数据的请求处理，只需要传递输入的字符串，在SearchDetailsActivity的界面中做搜索的处理
            if (data == null) {
                data = new ArrayList<>(); //防止第一次输入的时候报空指针
            }
            if (data.size() >= 20) {
                data.remove(data.size() - 1);//删除最后一个  因为只显示20个历史搜索
            }
            //在遍历 防止出现重复添加的问题
            for (int a = 0; a < data.size(); a++) {
                if (data.get(a).equals(content)) {
                    data.remove(a);//删除这个数据 这样在添加的时候就显示第一位
                }
            }
            data.add(0, content);//最近历史数据显示在第一个位置

            SPUtils.setHistorySearch(JsonMutualList.objectToJson(data));//存入到sp文件中
        } else {
            ToastUtils.showShort("您还没有输入数据");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_search_iv:
                getSearchEt();
                break;
            case R.id.activity_search_clean:
                //当不为空并且有数据的时候做操作
                if (data != null && data.size() != 0) {
                    //Gone掉控件
                    mActivitySearchLL.setVisibility(View.GONE);
                    //清空集合
                    data.clear();
                    //存入空的集合
                    SPUtils.setHistorySearch( JsonMutualList.objectToJson(data));
                }
                break;
            case R.id.search_back:
                this.finish();
                break;
        }
    }

}
