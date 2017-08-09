package com.ocnyang.qbox.app.utils.webviewutils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.webkit.JavascriptInterface;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.app.BaseApplication;
import com.ocnyang.qbox.app.app.G;
import com.ocnyang.qbox.app.base.BaseActivity;
import com.ocnyang.qbox.app.base.BaseFragment;
import com.ocnyang.qbox.app.module.mains.MainsActivity;
import com.ocnyang.qbox.app.utils.CommonUtils;

public class JSInterface {

    private BaseActivity mContext;
    private BaseFragment mFragment;

    public JSInterface(BaseFragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getBaseActivity();
    }

    public JSInterface(BaseActivity mContext) {
        this.mContext = mContext;
    }

    /**
     * 依据type类型弹出 Toast/Dialog
     *
     * @param type    1 Toast  2  Dialog
     * @param message
     */
    @JavascriptInterface
    public void showMessage(String type, String message) {
        mContext.showToast(message);
    }

    /**
     * 调用系统相册
     */
    @JavascriptInterface
    public void openPhotoAlbum() {
        Intent albumIntent = new Intent();
        albumIntent.setType("image/*");
        albumIntent.setAction(Intent.ACTION_GET_CONTENT);
        if (mFragment != null) {
            mFragment.startActivityForResult(albumIntent, G.ACTION_ALBUM);
        } else {
            mContext.startActivityForResult(albumIntent, G.ACTION_ALBUM);
        }
    }

    @JavascriptInterface
    public void openNativeCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mFragment.startActivityForResult(intent, G.ACTION_CAMERA);
    }

    /**
     * 打开第三方应用
     *
     * @param appPackageName 应用包名
     * @param className      应用主Activity
     */
    @JavascriptInterface
    public void openThirdApp(String appPackageName, String className) {
        if (mFragment != null) {
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName(appPackageName, className);
            intent.setComponent(componentName);
            mContext.startActivity(intent);
        }
    }

    /**
     * 显示进度条
     *
     * @param message 提示消息
     */
    @JavascriptInterface
    public void showProgressBar(String message) {
        mContext.showProgress(message);
    }

    /**
     * 隐藏进度条
     */
    @JavascriptInterface
    public void hideProgressBar() {
        mContext.hideProgress();
    }


    /**
     * 启动一个通知栏
     *
     * @param titleName   通知栏标题
     * @param contentText 通知栏内容
     */
    @JavascriptInterface
    public void notificationBar(String titleName, String contentText) {
        Context context = BaseApplication.getInstance();
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        // 实例化通知栏构造器
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        //
        Intent intent = new Intent(context, MainsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // 对Builder进行配置
        mBuilder.setContentTitle(titleName)//设置通知栏标题
                .setContentText(contentText)
                .setContentIntent(pendingIntent) //设置通知栏点击意图
                .setTicker(titleName) //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis()) //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON

        //用mNotificationManager的notify方法通知用户生成标题栏消息通知
        mNotificationManager.notify(Notification.FLAG_ONLY_ALERT_ONCE, mBuilder.build());
    }

    /**
     * 打开扫码页面
     */
    @JavascriptInterface
    public void openDimensionCode() {
//        if (mFragment != null) {
//            Intent openCameraIntent = new Intent(mContext, CaptureActivity.class);
//            mFragment.startActivityForResult(openCameraIntent, G.ACTION_BARCODE);
//        } else {
//            Intent openCameraIntent = new Intent(mContext, CaptureActivity.class);
//            mContext.startActivityForResult(openCameraIntent, G.ACTION_BARCODE);
//        }
    }

    /**
     * 发送短信
     *
     * @param phoneNumber 要发送的号码
     * @param message     要发送的消息
     */
    @JavascriptInterface
    public void sendMessage(String type, String phoneNumber, String message) {
        if ("1".equals(type)) {
            String SENT_SMS_ACTION = "SENT_SMS_ACTION";
            Intent sentIntent = new Intent(SENT_SMS_ACTION);
            PendingIntent sentPI = PendingIntent.getBroadcast(mContext, 0, sentIntent,
                    0);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, sentPI, null);
        } else if ("2".equals(type)) {
            Uri uri = Uri.parse("smsto:" + phoneNumber);
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
            sendIntent.putExtra("sms_body", message);
            mContext.startActivity(sendIntent);
        }
    }

    /**
     * 打开录音机
     */
    @JavascriptInterface
    public void openRecorder() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/amr");
        intent.setClassName("com.android.soundrecorder", "com.android.soundrecorder.SoundRecorder");
        mFragment.startActivityForResult(intent, G.ACTION_RECORDER);
    }

    /**
     * 调用震动器
     */
    @JavascriptInterface
    public void deviceVibrate() {
        CommonUtils.vibrate(mContext, 300);
    }

    /**
     * 播放音乐
     */
    @JavascriptInterface
    public void playAudio() {
        CommonUtils.playMusic(mContext);
    }

    /**
     * 拨打电话
     *
     * @param phoneNumber 要拨打的号码
     */
    @JavascriptInterface
    public void openMobilePhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
//        mContext.startActivity(intent);
    }

    /**
     * 打开通讯录
     */
    @JavascriptInterface
    public void openAddressList() {
        //打开系统联系人，查找
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.ContactsContract.Contacts.CONTENT_URI);
        mFragment.startActivityForResult(intent, G.ACTION_ADDRESSLIST);
    }
}
