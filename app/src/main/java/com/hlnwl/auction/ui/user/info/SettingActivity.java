package com.hlnwl.auction.ui.user.info;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;

import com.allen.library.SuperTextView;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.subscriber.ApiObserver;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flod.loadingbutton.LoadingButton;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import com.hlnwl.auction.R;
import com.hlnwl.auction.app.MyApplication;
import com.hlnwl.auction.base.BaseDialog;
import com.hlnwl.auction.base.MyActivity;
import com.hlnwl.auction.bean.NoDataBean;
import com.hlnwl.auction.message.LoginMessage;
import com.hlnwl.auction.message.QuitMessage;
import com.hlnwl.auction.ui.common.IndexActivity;
import com.hlnwl.auction.ui.common.LoginActivity;
import com.hlnwl.auction.utils.http.Api;
import com.hlnwl.auction.utils.http.MessageUtils;
import com.hlnwl.auction.utils.photo.ImageLoaderUtils;
import com.hlnwl.auction.utils.photo.PictureUtile;
import com.hlnwl.auction.utils.sp.SPUtils;
import com.hlnwl.auction.view.dialog.InputDialog;
import com.hlnwl.auction.view.dialog.MenuDialog;
import com.hlnwl.auction.view.dialog.MessageDialog;
import com.hlnwl.auction.view.dialog.WaitDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 版权：hlnwl 版权所有
 *
 * @author yougui
 * 版本：1.0
 * 创建日期：2019/9/5 17:58
 * 描述：
 */
public class SettingActivity extends MyActivity {
    @BindView(R.id.title_tb)
    TitleBar mTitleTb;
    @BindView(R.id.modify_headimg)
    SuperTextView mModifyHeadimg;
    @BindView(R.id.modify_nick_name)
    SuperTextView mModifyNickName;
//    @BindView(R.id.modify_pay_pwd)
//    SuperTextView mModifyPayPwd;
    @BindView(R.id.quit)
    LoadingButton mQuit;
    private List<LocalMedia> mSelectList;

    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_setting;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_tb;
    }

    @Override
    protected void initView() {
        mTitleTb.setTitle("设置");
        if (SPUtils.getNickname() != null && SPUtils.getNickname().length() > 0) {
            mModifyNickName.setRightString(SPUtils.getNickname());
        } else {
            mModifyNickName.setRightString("暂未设置昵称");
        }
        ImageLoaderUtils.display(this, mModifyHeadimg.getRightIconIV(),
                 StringUtils.null2Length0(SPUtils.getHeadimg()));

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.modify_headimg, R.id.modify_nick_name,
            R.id.modify_pwd, R.id.address,R.id.quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.modify_headimg:
                XXPermissions.with(this)
                        //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                        .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE,
                                Permission.CAMERA) //支持请求6.0悬浮窗权限8.0请求安装权限
                        //不指定权限则自动获取清单中的危险权限
                        .request(new OnPermission() {

                            @Override
                            public void hasPermission(List<String> granted, boolean isAll) {
                                List<String> data = new ArrayList<>();
                                data.add(getResources().getString(R.string.album));
                                data.add(getResources().getString(R.string.picture_photograph));
                                new MenuDialog.Builder(SettingActivity.this)
                                        .setCancel(getResources().getString(R.string.picture_cancel)) // 设置 null 表示不显示取消按钮
                                        //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                                        .setList(data)
                                        .setListener(new MenuDialog.OnListener() {

                                            @Override
                                            public void onSelected(Dialog dialog, int position, String text) {
                                                if (position == 0) {

                                                    PictureUtile.addPic(SettingActivity.this,mSelectList,1);

                                                } else if (position == 1) {

                                                    PictureUtile.getCamera(SettingActivity.this,mSelectList);
                                                }
                                            }

                                            @Override
                                            public void onCancel(Dialog dialog) {
                                                ToastUtils.showShort(getResources().getString(R.string.picture_cancel));
                                            }

                                        })
                                        .setGravity(Gravity.BOTTOM)
                                        .setAnimStyle(BaseDialog.AnimStyle.BOTTOM)
                                        .show();
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean quick) {
                                XXPermissions.gotoPermissionSettings(SettingActivity.this);
                            }
                        });
                break;
            case R.id.modify_nick_name:
                new InputDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.modify_nick_name)) // 标题可以不用填写
                        .setContent("")
                        .setHint(getResources().getString(R.string.modify_nick_name_sure))
                        .setConfirm(getResources().getString(R.string.confirm))
                        .setCancel(getResources().getString(R.string.picture_cancel)) // 设置 null 表示不显示取消按钮
                        //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                        .setListener(new InputDialog.OnListener() {

                            @Override
                            public void onConfirm(Dialog dialog, String content) {
                                if (content.length()==0){
                                    toast(StringUtils.getString(R.string.nick_name_null));
                                    return;
                                }
                                modifyNickName(content);
                            }

                            @Override
                            public void onCancel(Dialog dialog) {
                                toast(getResources().getString(R.string.picture_cancel));
                            }
                        })
                        .show();
                break;
            case R.id.modify_pwd:
                break;
            case R.id.address:
                startActivity(AddressActivity.class);
                break;
            case R.id.quit:
                mQuit.start();
                new MessageDialog.Builder(getActivity())
                        .setTitle(getActivity().getResources().getString(R.string.quit_login)) // 标题可以不用填写
                        .setMessage(getActivity().getResources().getString(R.string.sure_quit_login))
                        .setConfirm(getActivity().getResources().getString(R.string.confirm))
                        .setCancel(getActivity().getResources().getString(R.string.picture_cancel)) // 设置 null 表示不显示取消按钮
                        //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(Dialog dialog) {
                                SPUtils.clear(getActivity());
                                EventBus.getDefault().post(new QuitMessage("quit"));
                                mQuit.complete();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
                                startActivity(intent);
//                                ActivityUtils.finishToActivity(IndexActivity.class, true);
                            }

                            @Override
                            public void onCancel(Dialog dialog) {
                                mQuit.fail();
                                toast(getActivity().getResources().getString(R.string.picture_cancel));
                            }
                        })
                        .show();
                break;
        }
    }

    private void modifyNickName(String content) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .modifyNickName(SPUtils.getLanguage(),SPUtils.getUserId(),SPUtils.getToken(),content), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(SettingActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }
                                   EventBus.getDefault().post(new LoginMessage("update"));
                                   mModifyNickName.setRightString(content);
                                   final BaseDialog dialog = new WaitDialog.Builder(SettingActivity.this)
                                           .setMessage(getResources().getString(R.string.modifying)) // 消息文本可以不用填写
                                           .show();

                                   getHandler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           toast(data.getMsg());
                                           dialog.dismiss();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    if (mSelectList.size() > 0) {
                        ImageLoaderUtils.display(this, mModifyHeadimg.getRightIconIV(),
                                mSelectList.get(0).getCompressPath());
                        commitHead(mSelectList.get(0).getCompressPath());
                    }

                    break;
            }
        }
    }

    private void commitHead(String compressPath) {
        RxRetroHttp.composeRequest(RxRetroHttp.create(Api.class)
                .setHeadImg(SPUtils.getLanguage(),SPUtils.getUserId(),SPUtils.getToken(),
                        Bitmap2StrByBase64(BitmapFactory.decodeFile(compressPath))), this)
                .subscribe(new ApiObserver<NoDataBean>() {
                               @Override
                               protected void success(NoDataBean data) {
                                   boolean isSuccess = MessageUtils.setCode(SettingActivity.this,
                                           data.getStatus(), data.getMsg());
                                   if (!isSuccess) {
                                       return;
                                   }
                                   EventBus.getDefault().post(new LoginMessage("update"));
                                   final BaseDialog dialog = new WaitDialog.Builder(SettingActivity.this)
                                           .setMessage(getResources().getString(R.string.setting)) // 消息文本可以不用填写
                                           .show();

                                   getHandler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           toast(data.getMsg());
                                           dialog.dismiss();
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
     * 通过Base32将Bitmap转换成Base64字符串
     *
     * @param bit
     * @return
     */
    public String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
