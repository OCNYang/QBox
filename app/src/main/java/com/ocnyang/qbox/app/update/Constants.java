package com.ocnyang.qbox.app.update;

/**
 * App自动检测更新库
 * https://github.com/feicien/android-auto-update
 *
 * 用法：
 * 使用Dialog          UpdateChecker.checkForDialog(this);
 * 使用Notification    UpdateChecker.checkForNotification(this);
 */
class Constants {
    static final String APK_DOWNLOAD_URL = "url";
    static final String APK_UPDATE_CONTENT = "updateMessage";
    static final String APK_VERSION_CODE = "versionCode";

    static final int TYPE_NOTIFICATION = 2;
    static final int TYPE_DIALOG = 1;

    static final String TAG = "UpdateChecker";
    /**
     * 检测是否有新版本APP的地址
     */
    static final String UPDATE_URL = "https://raw.githubusercontent.com/OCNYang/QBox/master/version.json";
    /**
     * 返回的json字符串的格式如下：
        {
          "url":"https://raw.githubusercontent.com/feicien/android-auto-update/develop/extras/android-auto-update-v1.1.apk",
          "versionCode":2,
          "updateMessage":"1. 新增XX功能;<br/>2. 修复了Bug;<br/>3. 优化了性能。"
        }
    */
}
