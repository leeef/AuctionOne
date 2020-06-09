package com.hlnwl.auction.ui.user.shop;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flod.loadingbutton.LoadingButton;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.NoDataBean;
import com.hlnwl.auction.bean.category.CategoryData;
import com.hlnwl.auction.bean.category.CategoryErji;
import com.hlnwl.auction.bean.goods.SpecBean;
import com.hlnwl.auction.bean.user.shop.GoodMsgBean;
import com.hlnwl.auction.bean.user.shop.MyGoodsBean;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.ui.release.CateSelActivity;
import com.hlnwl.auction.ui.release.GoodsSpecificationActivity;
import com.hlnwl.auction.ui.release.NineGridAdapter;
import com.hlnwl.auction.ui.release.ReleaseActivity;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.photo.PictureUtile;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.ExpandableGridView;
import com.hlnwl.auction.view.dialog.MenuDialog;
import com.hlnwl.auction.view.dialog.WaitDialog;
import com.hlnwl.auction.view.widget.ClearEditText;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/10/11 11:14
 * 描述：
 */
public class EditGoodActivity extends MyActivity implements BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.release_yse)
    RadioButton mReleaseYse;
    @BindView(R.id.release_no)
    RadioButton mReleaseNo;
    @BindView(R.id.release_bid_price)
    ClearEditText mReleaseBidPrice;
    @BindView(R.id.release_bid)
    LinearLayout mReleaseBid;
    @BindView(R.id.release_min_price)
    ClearEditText mReleaseMinPrice;
    @BindView(R.id.release_min)
    LinearLayout mReleaseMin;
    @BindView(R.id.release_time_tv)
    TextView mReleaseTimeTv;
    @BindView(R.id.release_time)
    LinearLayout mReleaseTime;
    @BindView(R.id.release_title)
    ClearEditText mReleaseTitle;
    @BindView(R.id.release_spec)
    TextView mReleaseSpec;
    @BindView(R.id.release_bid_category)
    TextView mReleaseBidCategory;
    @BindView(R.id.release_desc)
    ClearEditText mReleaseDesc;
    @BindView(R.id.gridView)
    ExpandableGridView mGridView;
    @BindView(R.id.release_sure)
    LoadingButton mReleaseSure;
    @BindView(R.id.release_select)
    RadioGroup mReleaseSelect;
    @BindView(R.id.nine_grid)
    RecyclerView mNineGrid;
    @BindView(R.id.release_margin_price)
    ClearEditText mReleaseMarginPrice;
    @BindView(R.id.release_margin)
    LinearLayout mReleaseMargin;

    private NineGridAdapter mNineGridAdapter;
    private List<String> ninePaths = new ArrayList<>();
    private List<String> new_pic = new ArrayList<>();
    private List<String> old_pic = new ArrayList<>();
    private List<LocalMedia> selectList = new ArrayList<>();
    //    private List<String> paths = new ArrayList<>();
    private String spec = "";
    private String is_bid = "2";
    private String cid = "";
    private String commit_new_imgs = "";
    private String commit_old_imgs = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_good_edit;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(StringUtils.getString(R.string.edit_goods));
    }

    @Override
    protected void initData() {
        showLoading();
        getData();
    }

    private void getData() {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .getGoodMsg(SPUtils.getLanguage(), SPUtils.getUserId(),
                        SPUtils.getToken(), getIntent().getStringExtra("id")), this)
                .subscribe(new ApiObserver<GoodMsgBean>() {
                               @Override
                               protected void success(GoodMsgBean data) {
                                   boolean isSuccess = MessageUtils.setCode(getActivity(),
                                           data.getStatus() + "", data.getMsg());
                                   if (!isSuccess) {
                                       showError();
                                       return;
                                   }
                                   initUI(data.getData().get(0));

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

    private void initUI(GoodMsgBean.DataBean data) {

        if (data.getIs_bid().equals("1")) {
            mReleaseYse.setChecked(true);
            mReleaseNo.setChecked(false);
            mReleaseBid.setVisibility(View.VISIBLE);
            mReleaseMin.setVisibility(View.VISIBLE);
            mReleaseTime.setVisibility(View.VISIBLE);
            mReleaseMargin.setVisibility(View.VISIBLE);
            mReleaseBidPrice.setText(data.getBid_price());
            mReleaseMarginPrice.setText(data.getPrice());
            mReleaseMinPrice.setText(data.getLow_price());
            mReleaseTimeTv.setText(data.getEndtime());
        } else {
            mReleaseYse.setChecked(false);
            mReleaseNo.setChecked(true);
            mReleaseBid.setVisibility(View.GONE);
            mReleaseMin.setVisibility(View.GONE);
            mReleaseTime.setVisibility(View.GONE);
            mReleaseMargin.setVisibility(View.GONE);
        }
        mReleaseTitle.setText(data.getName());
        mReleaseBidCategory.setText(data.getCname());
        mReleaseDesc.setText(data.getContent());
        cid = data.getCid();
        ninePaths.addAll(data.getPic());
        if (ninePaths.size() < 9) {
            ninePaths.add(imageTranslateUri(R.mipmap.xiangji));
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mNineGrid.setLayoutManager(layoutManager);
        if (mNineGridAdapter == null) {
            mNineGridAdapter = new NineGridAdapter();
            mNineGridAdapter.setNewData(ninePaths);
        } else {
            mNineGridAdapter.notifyDataSetChanged();
        }

        mNineGrid.setAdapter(mNineGridAdapter);
        if (spec.length() == 0) {
            if (data.getSpeci().size() == 0) {
                List<String> specs = new ArrayList<>();
                specs.add(GsonUtils.toJson(new SpecBean("", "")));
                spec = TextUtils.join(";", specs);
            } else {
                List<String> specs = new ArrayList<>();
                for (SpecBean specBean : data.getSpeci()) {
                    specs.add(GsonUtils.toJson(specBean));
                }
                spec = TextUtils.join(";", specs);
            }

        }
        mReleaseSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(EditGoodActivity.this, GoodsSpecificationActivity.class)
                                .putExtra("spec", spec)
                        , 0);
            }
        });
        mNineGridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KeyboardUtils.hideSoftInput(view);
                String imgs = mNineGridAdapter.getData().get(position);
                if (imageTranslateUri(R.mipmap.xiangji).equals(imgs)) {

                    XXPermissions.with(EditGoodActivity.this)
                            //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                            //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                            .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA) //不指定权限则自动获取清单中的危险权限
                            .request(new OnPermission() {

                                @Override
                                public void hasPermission(List<String> granted, boolean isAll) {
                                    List<String> data = new ArrayList<>();
                                    data.add(StringUtils.getString(R.string.album));
                                    data.add(StringUtils.getString(R.string.paizhao));
                                    new MenuDialog.Builder(EditGoodActivity.this)
                                            .setCancel(StringUtils.getString(R.string.picture_cancel)) // 设置 null 表示不显示取消按钮
                                            //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                                            .setList(data)
                                            .setListener(new MenuDialog.OnListener() {

                                                @Override
                                                public void onSelected(Dialog dialog, int position, String text) {
                                                    if (position == 0) {
                                                        PictureUtile.addPic(EditGoodActivity.this,selectList,9);
                                                    } else if (position == 1) {

                                                        PictureUtile.getCamera(EditGoodActivity.this,selectList);
                                                    }
                                                }

                                                @Override
                                                public void onCancel(Dialog dialog) {
                                                    ToastUtils.showShort("取消");
                                                }

                                            })
                                            .setGravity(Gravity.BOTTOM)
                                            .setAnimStyle(BaseDialog.AnimStyle.BOTTOM)
                                            .show();
                                }

                                @Override
                                public void noPermission(List<String> denied, boolean quick) {
                                    XXPermissions.gotoPermissionSettings(EditGoodActivity.this);
                                }
                            });
                }
            }
        });
        mNineGridAdapter.setOnItemChildClickListener(this);
        showComplete();
    }

    /**
     * res/drawable(mipmap)/xxx.png::::uri－－－－>url
     *
     * @return
     */
    private String imageTranslateUri(int resId) {

        Resources r = getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(resId) + "/"
                + r.getResourceTypeName(resId) + "/"
                + r.getResourceEntryName(resId));

        return uri.toString();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ninePaths.remove(ninePaths.get(position));
        if (ninePaths.size() < 9 && !ninePaths.contains(imageTranslateUri(R.mipmap.xiangji))) {
            mNineGridAdapter.addData(imageTranslateUri(R.mipmap.xiangji));
        }
        mNineGridAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.release_time_tv,
            R.id.release_bid_category, R.id.release_sure})
    public void onViewClicked(View view) {
        KeyboardUtils.hideSoftInput(view);
        switch (view.getId()) {
            case R.id.release_time_tv:
                showTimePicker();
                break;

            case R.id.release_bid_category:
                startActivityForResult(new Intent(this, CateSelActivity.class)
                        , 10);
                break;
            case R.id.release_sure:
                if (is_bid.equals("1")) {
                    if (StringUtils.isEmpty(mReleaseMarginPrice.getText().toString().trim())) {
                        toast(StringUtils.getString(R.string.release_margin_price_null));
                        return;
                    }
                    if (StringUtils.isEmpty(mReleaseMinPrice.getText().toString().trim())) {
                        toast(StringUtils.getString(R.string.release_min_price_null));
                        return;
                    }
                    if (StringUtils.isEmpty(mReleaseBidPrice.getText().toString().trim())) {
                        toast(StringUtils.getString(R.string.release_bid_price_null));
                        return;
                    }
                    if (StringUtils.isEmpty(mReleaseTimeTv.getText().toString().trim())) {
                        toast(StringUtils.getString(R.string.release_time_null));
                        return;
                    }
                }
                if (StringUtils.isEmpty(mReleaseTitle.getText().toString().trim())) {
                    toast(StringUtils.getString(R.string.release_title_null));
                    return;
                }
                if (cid.length() == 0) {
                    toast(StringUtils.getString(R.string.category_null));
                    return;
                }
                if (ninePaths.size() == 1) {
                    toast(StringUtils.getString(R.string.good_pic_null));
                    return;
                }
                if (ninePaths.size() > 0) {
                    if (ninePaths.contains(imageTranslateUri(R.mipmap.xiangji))) {
                        ninePaths.remove(imageTranslateUri(R.mipmap.xiangji));
                    }
                    for (String path : ninePaths) {
                        if (path.contains("http://")) {
                            old_pic.add(path);
                        } else {
                            new_pic.add(Bitmap2StrByBase64(BitmapFactory.decodeFile(path)));
                        }

                    }
                    if (new_pic.size() > 0) {
                        commit_new_imgs = TextUtils.join(",", new_pic);
                    }
                    if (old_pic.size() > 0) {
                        commit_old_imgs = TextUtils.join(",", old_pic);
                    }

                }
                if (spec.length() == 24) {
                    spec = "";
                }
                mReleaseSure.start();
                releaseGood();
                break;
        }
    }

    private void releaseGood() {
        log("[" + spec + "]");
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .editGood(SPUtils.getLanguage(), SPUtils.getUserId(), SPUtils.getToken(),
                        getIntent().getStringExtra("id"), is_bid,
                        mReleaseTitle.getText().toString().trim(), cid,
                        StringUtils.null2Length0(mReleaseMarginPrice.getText().toString().trim()),
                        StringUtils.null2Length0(mReleaseMinPrice.getText().toString().trim()),
                        StringUtils.null2Length0(mReleaseBidPrice.getText().toString().trim()),
                        StringUtils.null2Length0(mReleaseDesc.getText().toString().trim()),
                        "[" + spec + "]",
                        commit_old_imgs, commit_new_imgs,
                        StringUtils.null2Length0(mReleaseTimeTv.getText().toString().trim())
                ), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(EditGoodActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       mReleaseSure.fail();
                                       return;
                                   }

                                   EventBus.getDefault().post(new LoginMessage("update"));
                                   final BaseDialog dialog = new WaitDialog.Builder(EditGoodActivity.this)
                                           .setMessage(StringUtils.getString(R.string.editing)) // 消息文本可以不用填写
                                           .show();

                                   getHandler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           mReleaseSure.complete();
                                           toast(data.getMsg());
                                           dialog.dismiss();
//                                           startActivity(ShopJoinStatusActivity.class);
                                           finish();
                                       }
                                   }, 1000);
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   mReleaseSure.fail();
                                   toast(t.getMessage());
                               }
                           }
                );
    }

    private void showTimePicker() {
        Calendar selectedDate = Calendar.getInstance();

        Calendar endDate = Calendar.getInstance();
        endDate.set(2099, 12, 31);


        TimePickerView timePicker = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mReleaseTimeTv.setText(TimeUtils.date2String(date, "yyyy-MM-dd HH:mm:ss"));
            }
        })
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(ColorUtils.getColor(R.color.main))//确定按钮文字颜色
                .setCancelColor(ColorUtils.getColor(R.color.main))//取消按钮文字颜色
                .setTitleText("选择日期")
                .setDate(selectedDate)
                .setRangDate(selectedDate, endDate)
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, true, true, true})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
        timePicker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 0 && resultCode == 10) {
                spec = data.getStringExtra("spec");
                log(spec);

            } else if (requestCode == 10 && resultCode == 100) {
                CategoryData cateOne = GsonUtils.fromJson(data.getStringExtra("one"), CategoryData.class);
                CategoryErji cateTwo = GsonUtils.fromJson(data.getStringExtra("two"), CategoryErji.class);
                cid = cateTwo.getId();
                mReleaseBidCategory.setText(cateOne.getName() + "  " + cateTwo.getName());
            } else if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case PictureConfig.CHOOSE_REQUEST:
                        // 图片、视频、音频选择结果回调
                        selectList.clear();
                        selectList = PictureSelector.obtainMultipleResult(data);
                        // 例如 LocalMedia 里面返回三种path
                        // 1.media.getPath(); 为原图path
                        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                        if (selectList.size() > 0) {
//                            paths.clear();
//                            for (LocalMedia localMedia : selectList) {
//                                paths.add(localMedia.getCompressPath());
//                            }
                            loadMyAdapter(selectList);
                        }

                        break;
                }
            }

        }
    }

    private void loadMyAdapter(List<LocalMedia> paths) {

        if (paths.size() == 0) {
            return;
        } else {
            ninePaths.remove(imageTranslateUri(R.mipmap.xiangji));
        }
        for (LocalMedia localMedia : paths) {
            ninePaths.add(localMedia.getCompressPath());
        }
        mNineGridAdapter.notifyDataSetChanged();
//        mNineGridAdapter.addData(paths);

        if (ninePaths.size() < 9) {
            mNineGridAdapter.addData(imageTranslateUri(R.mipmap.xiangji));
        }
    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     *
     * @param bit
     * @return
     */
    public String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
