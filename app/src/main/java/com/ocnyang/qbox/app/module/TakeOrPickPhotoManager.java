package com.ocnyang.qbox.app.module;

import android.net.Uri;
import android.os.Environment;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/17 09:36.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class TakeOrPickPhotoManager {

    private TakePhoto takePhoto;
    private boolean isCrop;

    public TakePhoto getTakePhoto() {
        return takePhoto;
    }

    public void setTakePhoto(TakePhoto takePhoto) {
        this.takePhoto = takePhoto;
    }

    public boolean isCrop() {
        return isCrop;
    }

    public void setCrop(boolean crop) {
        isCrop = crop;
    }

    public TakeOrPickPhotoManager(TakePhoto takePhoto) {
        this.takePhoto = takePhoto;
        isCrop = true;
    }

    public void takeOrPickPhoto(boolean isTakePhoto) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
//        TakePhoto takePhoto = getTakePhoto();

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);

        if (isTakePhoto) {//拍照
            if (isCrop) {//是否裁剪
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
            } else {
                takePhoto.onPickFromCapture(imageUri);
            }
        } else {//选取图片
            int limit = 1;//选择图片的个数。
            if (limit > 1) {
                //当选择图片大于1个时是否裁剪
                if (isCrop) {
                    takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
                } else {
                    takePhoto.onPickMultiple(limit);
                }
                return;
            }
            //是否从文件中选取图片
            if (false) {
                if (isCrop) {//是否裁剪
                    takePhoto.onPickFromDocumentsWithCrop(imageUri, getCropOptions());
                } else {
                    takePhoto.onPickFromDocuments();
                }
                return;
            } else {
                if (isCrop) {//是否裁剪
                    takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                } else {
                    takePhoto.onPickFromGallery();
                }
            }
        }
    }

    /**
     * 拍照相关的配置
     *
     * @param takePhoto
     */
    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        //是否使用Takephoto自带的相册
        if (false) {
            builder.setWithOwnGallery(true);
        }
        //纠正拍照的旋转角度
        if (true) {
            builder.setCorrectImage(true);
        }
        takePhoto.setTakePhotoOptions(builder.create());
    }

    /**
     * 配置 压缩选项
     *
     * @param takePhoto
     */
    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 102400;
        int width = 800;
        int height = 800;
        //是否显示 压缩进度条
        boolean showProgressBar = true;
        //压缩后是否保存原图
        boolean enableRawFile = true;
        CompressConfig config;
        if (false) {
            //使用自带的压缩工具
            config = new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        } else {
            //使用开源的鲁班压缩工具
            LubanOptions option = new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    /**
     * 配置 裁剪选项
     *
     * @return
     */
    private CropOptions getCropOptions() {
        int height = 100;
        int width = 100;

        CropOptions.Builder builder = new CropOptions.Builder();

        //按照宽高比例裁剪
        builder.setAspectX(width).setAspectY(height);
        //是否使用Takephoto自带的裁剪工具
        builder.setWithOwnCrop(false);
        return builder.create();
    }



}
