package com.ocnyang.qbox.app.app;

import android.annotation.SuppressLint;

import com.ocnyang.qbox.app.utils.SdCardUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 全局变量存储类
 *
 * @author Ht
 */
public class G {

    /**
     * 应用程序名
     */
    public static final String APPNAME = "FastAndroid";

    /**
     * 文件根目录
     */
    public static final String STORAGEPATH = SdCardUtil.getNormalSDCardPath() + "/" + APPNAME + "/";

    /**
     * 自动更新文件下载路径
     */
    public static final String UPDATE_APP_SAVE_PATH = STORAGEPATH + APPNAME + ".apk";
    /**
     * 系统图片
     */
    public static final String APPIMAGE = STORAGEPATH + "img/";
    /**
     * 录音文件保存路径
     */
    public static final String APPRECORD = STORAGEPATH + "record/";

    /**
     * 调用拍照request code
     */
    public static final int ACTION_CAMERA = 0x01;
    /**
     * 调用相册request code
     */
    public static final int ACTION_ALBUM = 0x02;
    /**
     * 打开扫码request code
     */
    public static final int ACTION_BARCODE = 0x03;

    /**
     * 打开录音request code
     */
    public static final int ACTION_RECORDER = 0x04;

    /**
     * 打开通讯录request code
     */
    public static final int ACTION_ADDRESSLIST = 0x05;


    @SuppressLint("SimpleDateFormat")
    public static String getPhoneCurrentTime() {
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
        return date.format(Calendar.getInstance().getTime());
    }

}
