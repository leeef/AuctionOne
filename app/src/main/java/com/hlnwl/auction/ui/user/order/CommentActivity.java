package com.hlnwl.auction.ui.user.order;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hlnwl.auction.R;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.NoDataBean;
import com.hlnwl.auction.message.OrderMessage;
import com.hlnwl.auction.ui.release.NineGridAdapter;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.photo.PictureUtile;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.MenuDialog;
import com.hlnwl.auction.view.dialog.WaitDialog;
import com.hlnwl.auction.view.widget.ClearEditText;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.willy.ratingbar.ScaleRatingBar;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/29 15:10
 * 描述：
 */
public class CommentActivity extends MyActivity implements BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.comment_content)
    ClearEditText mCommentContent;
    @BindView(R.id.comment_RatingBar)
    ScaleRatingBar mCommentRatingBar;
    @BindView(R.id.nine_grid)
    RecyclerView mNineGrid;

    private List<String> ninePaths = new ArrayList<>();
    private NineGridAdapter mNineGridAdapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<String> paths = new ArrayList<>();
    private List<String> up_imgs = new ArrayList<>();
    private String commit_imgs = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle(StringUtils.getString(R.string.release_comment));
        mTitleTb.setRightTitle(StringUtils.getString(R.string.release));
        mTitleTb.setRightColor(getResources().getColor(R.color.main));
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
                if (ninePaths.size() > 1) {
                    if (ninePaths.contains(imageTranslateUri(R.mipmap.xiangji))) {
                        ninePaths.remove(imageTranslateUri(R.mipmap.xiangji));
                    }
                    for (String path : paths) {
                        up_imgs.add(Bitmap2StrByBase64(BitmapFactory.decodeFile(path)));
                    }
                    commit_imgs = TextUtils.join(",", up_imgs);
                }
                comment();
            }
        });


        ninePaths.add(imageTranslateUri(R.mipmap.xiangji));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mNineGrid.setLayoutManager(layoutManager);
        if (mNineGridAdapter == null) {
            mNineGridAdapter = new NineGridAdapter();
            mNineGridAdapter.setNewData(ninePaths);
        } else {
            mNineGridAdapter.notifyDataSetChanged();
        }

        mNineGrid.setAdapter(mNineGridAdapter);
        mNineGridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KeyboardUtils.hideSoftInput(view);
                String imgs = mNineGridAdapter.getData().get(position);
                if (imageTranslateUri(R.mipmap.xiangji).equals(imgs)) {

                    XXPermissions.with(CommentActivity.this)
                            //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                            //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                            .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA) //不指定权限则自动获取清单中的危险权限
                            .request(new OnPermission() {

                                @Override
                                public void hasPermission(List<String> granted, boolean isAll) {
                                    List<String> data = new ArrayList<>();
                                    data.add(StringUtils.getString(R.string.album));
                                    data.add(StringUtils.getString(R.string.paizhao));
                                    new MenuDialog.Builder(CommentActivity.this)
                                            .setCancel(StringUtils.getString(R.string.picture_cancel)) // 设置 null 表示不显示取消按钮
                                            //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                                            .setList(data)
                                            .setListener(new MenuDialog.OnListener() {

                                                @Override
                                                public void onSelected(Dialog dialog, int position, String text) {
                                                    if (position == 0) {

//                                                        PictureSelector.create(CommentActivity.this)
//                                                                .openGallery(PictureMimeType.ofImage())
//                                                                .compress(true)
//                                                                .maxSelectNum(3)
//                                                                .selectionMedia(selectList)
//                                                                .forResult(PictureConfig.CHOOSE_REQUEST);
                                                        PictureUtile.addPic(CommentActivity.this, selectList, 3);
                                                    } else if (position == 1) {

                                                        PictureUtile.getCamera(CommentActivity.this, selectList);
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
                                    XXPermissions.gotoPermissionSettings(CommentActivity.this);
                                }
                            });
                }
            }
        });
        mNineGridAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private void comment() {
        if (StringUtils.isEmpty(mCommentContent.getText().toString().trim())) {
            ToastUtils.showShort(StringUtils.getString(R.string.comment_null));
            return;
        }

        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .comment2(SPUtils.getUserId(), SPUtils.getToken(),
                        getIntent().getStringExtra("id"),
                        Math.round(mCommentRatingBar.getRating()) + "",
                        mCommentContent.getText().toString().trim(),
                        commit_imgs, "1"), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(CommentActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }

                                   final BaseDialog dialog = new WaitDialog.Builder(CommentActivity.this)
                                           .setMessage(StringUtils.getString(R.string.releaseing)) // 消息文本可以不用填写
                                           .show();

                                   getHandler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           toast(data.getMsg());
                                           dialog.dismiss();
                                           EventBus.getDefault().post(new OrderMessage("update"));
                                           finish();
                                       }
                                   }, 1000);
                               }

                               @Override
                               public void onError(Throwable t) {
                                   super.onError(t);
                                   toast(t.getMessage());
                               }
                           }
                );
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
        if (ninePaths.size() < 3 && !ninePaths.contains(imageTranslateUri(R.mipmap.xiangji))) {
            mNineGridAdapter.addData(imageTranslateUri(R.mipmap.xiangji));
        }
        mNineGridAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == RESULT_OK) {
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
                            paths.clear();
                            for (LocalMedia localMedia : selectList) {
                                paths.add(localMedia.getCompressPath());
                            }
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
            ninePaths.clear();
            mNineGridAdapter.notifyDataSetChanged();
        }
        for (LocalMedia localMedia : paths) {
            ninePaths.add(localMedia.getCompressPath());
        }
//        mNineGridAdapter.addData(paths);

        if (ninePaths.size() < 3) {
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
