package com.zhoukp.signer.module.me;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.zhoukp.signer.R;
import com.zhoukp.signer.activity.MainActivity;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.utils.BaseApi;
import com.zhoukp.signer.utils.CacheUtils;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.module.login.UserUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author zhoukp
 * @time 2018/3/18 16:25
 * @email 275557625@qq.com
 * @function
 */

public class MePagerPresenter {
    private MePagerView mePagerView;

    /**
     * 绑定视图
     *
     * @param mePagerView MePagerView
     */
    public void attachView(MePagerView mePagerView) {
        this.mePagerView = mePagerView;
    }

    /**
     * 选择头像照片
     *
     * @param context context
     */
    public void selectPicture(Context context) {
        //新建一个File，传入文件夹目录
        File file = new File(Constant.appPath);
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!file.exists()) {
            //通过file的mkdirs()方法创建<span style="color:#FF0000;">目录中包含却不存在</span>的文件夹
            file.mkdirs();
        }
        PictureSelector.create((MainActivity) context)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
                .compressSavePath(Constant.appPath)//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(true)// 是否开启点击声音 true or false
                //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(220, 220)// 裁剪宽高，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .videoQuality(1)// 视频录制质量 0 or 1 int
                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(10)//视频秒数录制 默认60s int
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    public void upLoadHeadIcon(final Context context, String compressPath) {
        mePagerView.showLoadingView();
        final LoginBean.UserBean userBean = UserUtil.getInstance().getUser();
        File file = new File(compressPath);
        String fileName = userBean.getUserId() + ".jpg";

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("headIcon", fileName, RequestBody.create(MediaType.parse("image/*"), file))
                .build();

        BaseApi.request(BaseApi.createApi(IMePagerApi.class).uploadHeadIcon(userBean.getUserId(), requestBody, fileName),
                new BaseApi.IResponseListener<UploadHeadIconBean>() {
                    @Override
                    public void onSuccess(final UploadHeadIconBean data) {
                        //CacheUtils.putString(context, "headIconUrl", data.getHeadIconUrl());
                        mePagerView.refreshHeadIcon(Constant.BaseUrl + data.getHeadIconUrl());
                        //saveHeadIcon(context, Constant.BaseUrl + data.getHeadIconUrl());

                        mePagerView.uploadHeadIconSuccess(data);
                        mePagerView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        mePagerView.uploadHeadIconError();
                        mePagerView.hideLoadingView();
                    }
                });
    }

    /**
     * 将URL转化成bitmap形式
     *
     * @param context
     * @param url     图片链接
     * @return bitmap type
     */
    public void saveHeadIcon(final Context context, final String url) {
        BaseApi.request(BaseApi.createApi(IMePagerApi.class).downloadHeadIcon(url),
                new BaseApi.IResponseListener<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody data) {
                        Bitmap bitmap = BitmapFactory.decodeStream(data.byteStream());
                        if (bitmap != null) {
                            Log.e("zkp", "bitmap: width=" + bitmap.getWidth() + " height=" + bitmap.getHeight());
                        }

                        //将请求到的图片保存到本地，以供下次使用
                        File file = new File(Constant.appPath, UserUtil.getInstance().getUser().getUserId() + ".jpg");
                        //如果文件已存在则先删除原先保存的头像图片

                        if (file.exists()) {
                            file.delete();
                        }

                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        CacheUtils.putString(context, "headIcon", file.getAbsolutePath());
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    /**
     * 获取用户头像
     *
     * @param userId 用户ID
     */
    public void getHeadIcon(String userId) {
        mePagerView.showLoadingView();

        BaseApi.request(BaseApi.createApi(IMePagerApi.class).getHeadIcon(userId),
                new BaseApi.IResponseListener<UploadHeadIconBean>() {
                    @Override
                    public void onSuccess(UploadHeadIconBean data) {
                        Log.e("zkp", "getHeadIcon==" + data.getStatus());
                        if (data.getStatus() == 200) {
                            mePagerView.getHeadIconSuccess(data);
                        } else {
                            mePagerView.getHeadIconError(data.getStatus());
                        }
                        mePagerView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        mePagerView.getHeadIconError(100);
                        mePagerView.hideLoadingView();
                    }
                });
    }

    /**
     * 解绑视图
     */

    public void detachView() {
        this.mePagerView = null;
    }
}
