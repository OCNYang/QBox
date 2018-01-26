package com.ocnyang.qbox.app.app;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.module.mains.MainsActivity;
import com.ocnyang.qbox.app.utils.SPUtils;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;
import java.util.Locale;


/**
 * 自定义应用入口
 *
 * @author Ht
 */
public class BaseApplication extends Application {
    public static final boolean DEBUG = false;
    public static final boolean USE_SAMPLE_DATA = false;

    public static final String APP_ID = "2882303761517567158";
    public static final String APP_KEY = "5951756743158";
    public static final String TAG = "com.ocnyang.qbox.app";

    private static BaseApplication mInstance;
    private static DemoHandler sHandler = null;
    private static MainsActivity sMainActivity = null;
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initScreenSize();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        /*内存泄漏初始化*/
//        LeakCanary.install(this);

        /*Logger初始化*/
        Logger.init("OCN_Yang");

//        initImageLoader();

        //初始化小米push推送服务
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }

        //打开小米推送的Log
        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        com.xiaomi.mipush.sdk.Logger.setLogger(this, newLogger);
        if (sHandler == null) {
            sHandler = new DemoHandler(getApplicationContext());
        }
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化imageloader
     */
//    private void initImageLoader() {
//        File cacheDir = StorageUtils.getOwnCacheDirectory(
//                getApplicationContext(), "imageloader/Cache");
//
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .cacheInMemory(true) // 加载图片时会在内存中加载缓存
//                .cacheOnDisk(true) // 加载图片时会在磁盘中加载缓存
//                .build();
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                this)
//                .threadPoolSize(3)
//                        // default
//                .threadPriority(Thread.NORM_PRIORITY - 2)
//                        // default
//                .tasksProcessingOrder(QueueProcessingType.FIFO)
//                        // default
//                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
//                        // default
//                .diskCache(new UnlimitedDiscCache(cacheDir))
//                        // default
//                .diskCacheSize(20 * 1024 * 1024).diskCacheFileCount(100)
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) // default
//                .defaultDisplayImageOptions(defaultOptions) // default
//                .writeDebugLogs().build();
//        ImageLoader.getInstance().init(config);
//    }
    public static Context getInstance() {
        return mInstance;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = mInstance.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mInstance.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前系统语言
     *
     * @return 当前系统语言
     */
    public static String getLanguage() {
        Locale locale = mInstance.getResources().getConfiguration().locale;
        String language = locale.getDefault().toString();
        return language;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }


    public static DemoHandler getHandler() {
        return sHandler;
    }

    public static void setMainActivity(MainsActivity activity) {
        sMainActivity = activity;
    }

    public static class DemoHandler extends Handler {

        private Context context;

        public DemoHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;
            if ((!TextUtils.isEmpty(s)) && s.indexOf("新版本") == 0) {
                //通过小米推送的穿透推送，来推送新版本更新的通知
                //通知的格式：
                //新版本：2
                String[] split = s.split(":");
                try {
                    String new_version = split[1];
                    int integer = Integer.valueOf(new_version);
                    if (integer < 100) {
                        SPUtils.put(context, Const.QBOX_NEW_VERSION, integer);
                    }else {
                        switch(integer){
                            case 101://魔法
                                SPUtils.put(context,Const.OPENNEWS,"magicopen");
                                break;
                            default:
                                break;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    Log.e("Qbox_version", e.getMessage());
                } catch (Exception e) {
                    Log.e("Qbox_Version", e.getMessage());
                }
            }
            if (sMainActivity != null) {
                sMainActivity.refreshLogInfo();
            }
            if (!TextUtils.isEmpty(s)) {
//                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 突破64K问题，MultiDex构建
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
