package com.ocnyang.qbox.app.module.setting;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.utils.SPUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class AccountActivity extends BaseCommonActivity implements CompoundButton.OnCheckedChangeListener, PlatformActionListener, Handler.Callback {

    private String[] sBindPhoneText;
    private Drawable[] sBindPhoneImg;
    private int[] sBindPhoneTextColor;

    public static final String QQ_NAME = "QQ";
    public static final String WECHAT_NAME = "Wechat";
    public static final String SINAWEIBO_NAME = "SinaWeibo";

    @BindView(R.id.toolbar_account)
    Toolbar mToolbarAccount;
    @BindView(R.id.phone_account)
    LinearLayout mPhoneAccount;
    @BindView(R.id.changepassword_account)
    LinearLayout mChangepasswordAccount;
    @BindView(R.id.weiboname_account)
    TextView mWeibonameAccount;
    @BindView(R.id.switch_weibo_account)
    Switch mSwitchWeiboAccount;
    @BindView(R.id.weibo_account)
    LinearLayout mWeiboAccount;
    @BindView(R.id.wechatname_account)
    TextView mWechatnameAccount;
    @BindView(R.id.switch_wechat_account)
    Switch mSwitchWechatAccount;
    @BindView(R.id.wechat_account)
    LinearLayout mWechatAccount;
    @BindView(R.id.qqname_account)
    TextView mQqnameAccount;
    @BindView(R.id.switch_qq_account)
    Switch mSwitchQqAccount;
    @BindView(R.id.qq_account)
    LinearLayout mQqAccount;
    @BindView(R.id.phonebindtext_account)
    TextView mPhonebindtextAccount;
    @BindView(R.id.phonebindimg_account)
    ImageView mPhonebindimgAccount;
    @BindView(R.id.phonebindinfo_account)
    TextView mPhonebindinfoAccount;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_account);

    }

    @Override
    public void initView() {
        sBindPhoneText = new String[]{getString(R.string.noverification),
                getString(R.string.verification)};
        sBindPhoneImg = new Drawable[]{getResources().getDrawable(R.drawable.certification_no),
                getResources().getDrawable(R.drawable.certification_yes)};
        sBindPhoneTextColor = new int[]{getResources().getColor(R.color.secondary_text),
                getResources().getColor(R.color.colorAccent)};
        initToolbar();
        initSwitch();
        initPhone();
        Toast.makeText(this, "账号系统未开通，本页面的所有信息只会保存在本地！", Toast.LENGTH_LONG).show();

    }

    private void initPhone() {
        String userPhone = (String) SPUtils.get(this, Const.USER_PHONE, "");
        if (TextUtils.isEmpty(userPhone)) {
        } else {
            showBindPhone(IS_BIND_PHONE, userPhone);
        }
    }

    public static final int IS_BIND_PHONE = 1;
    public static final int NOT_BIND_PHONE = 0;

    private void showBindPhone(int i, String userPhone) {
        mPhonebindtextAccount.setText(sBindPhoneText[i]);
        mPhonebindimgAccount.setImageDrawable(sBindPhoneImg[i]);
        mPhonebindtextAccount.setTextColor(sBindPhoneTextColor[i]);
        if (TextUtils.isEmpty(userPhone) || userPhone.equals(getResources().getString(R.string.bindphonemessage))) {
            mPhonebindinfoAccount.setText(getString(R.string.bindphonemessage));
        } else {
            StringBuffer formatPhone = new StringBuffer(userPhone.substring(0, 3))
                    .append("****")
                    .append(userPhone.substring(userPhone.length() - 4, userPhone.length()));
            mPhonebindinfoAccount.setText(formatPhone.toString());
        }
    }

    private void initSwitch() {
        if (!TextUtils.isEmpty((String) SPUtils.get(this, Const.QQ_USERID, ""))) {
            mQqnameAccount.setText((String) SPUtils.get(this, Const.QQ_USERNAME, ""));
            mSwitchQqAccount.setChecked(true);
        }
        if (!TextUtils.isEmpty((String) SPUtils.get(this, Const.SINAWEIBO_USERID, ""))) {
            mWeibonameAccount.setText((String) SPUtils.get(this, Const.SINAWEIBO_USERNAME, ""));
            mSwitchWeiboAccount.setChecked(true);
        }
        if (!TextUtils.isEmpty((String) SPUtils.get(this, Const.WECHAT_USERID, ""))) {
            mWechatnameAccount.setText((String) SPUtils.get(this, Const.WECHAT_USERNAME, ""));
            mSwitchWechatAccount.setChecked(true);
        }
        mSwitchQqAccount.setOnCheckedChangeListener(this);
        mSwitchWechatAccount.setOnCheckedChangeListener(this);
        mSwitchWeiboAccount.setOnCheckedChangeListener(this);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbarAccount);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initPresenter() {

    }

    @OnClick({R.id.phone_account, R.id.changepassword_account, R.id.weibo_account, R.id.wechat_account, R.id.qq_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone_account:
                if (TextUtils.isEmpty((String) SPUtils.get(this, Const.USER_PHONE, ""))) {
                    AccountActivityPermissionsDispatcher.registerPhoneWithCheck(this);
                } else {
                    showChangePhone();
                }
                break;
            case R.id.changepassword_account:
                Toast.makeText(this, "目前账号系统未开通，有望在新的版本中加入！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.weibo_account:
                mSwitchWeiboAccount.setChecked(!mSwitchWeiboAccount.isChecked());
                break;
            case R.id.wechat_account:
                mSwitchWechatAccount.setChecked(!mSwitchWechatAccount.isChecked());
                break;
            case R.id.qq_account:
                mSwitchQqAccount.setChecked(!mSwitchQqAccount.isChecked());
                break;
        }
    }

    private void showChangePhone() {
        new AlertDialog.Builder(this)
                .setMessage("要修改当前认证的手机号吗？")
                .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AccountActivityPermissionsDispatcher.registerPhoneWithCheck(AccountActivity.this);
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    RegisterPage registerPage;

    @NeedsPermission(Manifest.permission.READ_SMS)
    public void registerPhone() {
        if (registerPage == null) {
            //建议在程序入口调用
            SMSSDK.initSDK(this, "1cc099ede9137", "ea2e6177ec1dc1ba9fbda844e0d21d86");
            //打开注册页面
            registerPage = new RegisterPage();
            registerPage.setRegisterCallback(new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    // 解析注册结果
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        @SuppressWarnings("unchecked")
                        HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                        String country = (String) phoneMap.get("country");
                        String phone = (String) phoneMap.get("phone");

                        // 提交用户信息（此方法可以不调用）
                        registerUser(country, phone);
                        Toast.makeText(AccountActivity.this,
                                "国家：" + country + "手机号：" + phone,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        registerPage.show(this);
    }

    /**
     * 为什么要获取这个权限给用户的说明
     *
     * @param request
     */
    @OnShowRationale(Manifest.permission.READ_SMS)
    public void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle("权限申请")
                .setMessage("你将通过短信进行手机验证，这里需要短信的权限！")
                .setPositiveButton(R.string.imtrue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    /**
     * 如果用户不授予权限调用的方法
     */
    @OnPermissionDenied(Manifest.permission.READ_SMS)
    public void showDeniedForCamera() {
        Toast.makeText(this, "没有授予短信的权限", Toast.LENGTH_SHORT).show();
    }

    /**
     * 如果用户选择了让设备“不再询问”，而调用的方法
     */
    @OnNeverAskAgain(Manifest.permission.READ_SMS)
    public void showNeverAskForCamera() {
        Toast.makeText(this, "不再询问!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 委托授权
        AccountActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void registerUser(String country, String phone) {
        SPUtils.put(this, Const.USER_PHONE, phone);
        showBindPhone(IS_BIND_PHONE, phone);
    }

    String bindNameFlag = null;

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_qq_account:
                String QQId = (String) SPUtils.get(this, Const.QQ_USERID, "");
                if (isChecked) {
                    //按钮 打开状态
                    if (TextUtils.isEmpty(QQId)) {
                        //开始绑定第三方登录
                        authorize(ShareSDK.getPlatform(QQ.NAME), false);
                        bindNameFlag = QQ_NAME;
                    } else {
                        //放弃 “取消绑定”
                    }

                } else {
                    //按钮 关闭状态
                    if ((!TextUtils.isEmpty(QQId))) {
                        //弹出弹窗提醒关闭绑定。
                        selectCloseBind(QQ_NAME);
                    } else {
                        //绑定失败
                    }
                }
                break;
            case R.id.switch_wechat_account:
                String WechatId = (String) SPUtils.get(this, Const.WECHAT_USERID, "");
                if (isChecked) {
                    if (TextUtils.isEmpty(WechatId)) {
                        authorize(ShareSDK.getPlatform(Wechat.NAME), false);
                        bindNameFlag = WECHAT_NAME;
                    } else {

                    }
                } else {
                    if (!TextUtils.isEmpty(WechatId)) {
                        selectCloseBind(WECHAT_NAME);
                    } else {

                    }
                }
                break;
            case R.id.switch_weibo_account:
                String SinaWeiboId = (String) SPUtils.get(this, Const.SINAWEIBO_USERID, "");
                if (isChecked) {
                    if (TextUtils.isEmpty(SinaWeiboId)) {
                        authorize(ShareSDK.getPlatform(SinaWeibo.NAME), false);
                        bindNameFlag = SINAWEIBO_NAME;
                    } else {

                    }
                } else {
                    if (!TextUtils.isEmpty(SinaWeiboId)) {
                        selectCloseBind(SINAWEIBO_NAME);
                    } else {

                    }
                }
                break;
        }
    }

    private void selectCloseBind(final String platName) {
        new AlertDialog.Builder(this)
                .setMessage(this.getString(R.string.closebind, platName))
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSwitchQqAccount.setChecked(true);
                    }
                })
                .setPositiveButton("解绑", new DialogInterface.OnClickListener() {
                    public Platform mPlatform;

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //确定解绑:删除数据，解除绑定
                        switch (platName) {
                            case QQ_NAME:
                                mPlatform = ShareSDK.getPlatform(QQ.NAME);
                                SPUtils.remove(AccountActivity.this, Const.QQ_USERID);
                                SPUtils.remove(AccountActivity.this, Const.QQ_USERNAME);
                                SPUtils.remove(AccountActivity.this, Const.QQ_USERICON);
                                mQqnameAccount.setText(getString(R.string.nobind));
                                break;
                            case WECHAT_NAME:
                                mPlatform = ShareSDK.getPlatform(Wechat.NAME);
                                SPUtils.remove(AccountActivity.this, Const.WECHAT_USERID);
                                SPUtils.remove(AccountActivity.this, Const.WECHAT_USERNAME);
                                SPUtils.remove(AccountActivity.this, Const.WECHAT_USERICON);
                                mWechatnameAccount.setText(getString(R.string.nobind));
                                break;
                            case SINAWEIBO_NAME:
                                mPlatform = ShareSDK.getPlatform(SinaWeibo.NAME);
                                SPUtils.remove(AccountActivity.this, Const.SINAWEIBO_USERID);
                                SPUtils.remove(AccountActivity.this, Const.SINAWEIBO_USERNAME);
                                SPUtils.remove(AccountActivity.this, Const.SINAWEIBO_USERICON);
                                mWeibonameAccount.setText(getString(R.string.nobind));
                                break;
                            default:
                                break;
                        }
                        mPlatform.removeAccount(true);

                    }
                })
                .create().show();
    }

    private void authorize(Platform platform, boolean isSSO) {
        if (platform.isAuthValid()) {
            cancleAuth(platform);
        }
        platform.setPlatformActionListener(this);
        platform.SSOSetting(isSSO);
        platform.authorize();       //要功能，不要数据（用户）
//        platform.showUser(null);  //要数据，不要功能
    }

    private void cancleAuth(Platform platform) {
        platform.removeAccount(true);
    }

    /**
     * 第三方登录回调 授权成功
     *
     * @param platform
     * @param action
     * @param hashMap
     */
    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {

        if (action == Platform.ACTION_AUTHORIZING) {
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            String userName = platform.getDb().getUserName();
            String userId = platform.getDb().getUserId();
            String userIcon = platform.getDb().getUserIcon();
            String platName = platform.getName();//平台名称
            Message message = Message.obtain();
            message.what = MSG_LOGIN;
            Bundle bundle = new Bundle();
            bundle.putString("userName", userName);
            bundle.putString("userIcon", userIcon);
            bundle.putString("userId", userId);
            bundle.putString("platName", platName);
            message.setData(bundle);
            UIHandler.sendMessage(message, this);
        }
    }

    /**
     * 第三方登录回调 授权失败
     *
     * @param platform
     * @param i
     * @param throwable
     */
    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

        if (i == Platform.ACTION_AUTHORIZING) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        throwable.printStackTrace();
    }

    /**
     * 第三方登录回调 授权取消
     *
     * @param platform
     * @param i
     */
    @Override
    public void onCancel(Platform platform, int i) {
        if (i == Platform.ACTION_AUTHORIZING) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }

    // 统一消息处理
    private static final int MSG_USERID_FOUND = 1; // 用户信息已存在
    private static final int MSG_LOGIN = 2; // 登录操作
    private static final int MSG_AUTH_CANCEL = 3; // 授权取消
    private static final int MSG_AUTH_ERROR = 4; // 授权错误
    private static final int MSG_AUTH_COMPLETE = 5; // 授权完成

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND:
                Toast.makeText(this, "用户信息已存在，正在跳转登录操作......", Toast.LENGTH_SHORT).show();
                break;
            case MSG_LOGIN:
                Toast.makeText(this, "使用帐号登录中...", Toast.LENGTH_SHORT).show();
                Bundle data = msg.getData();
                String userName = data.getString("userName");
                String userId = data.getString("userId");
                String userIcon = data.getString("userIcon");
                String platName = data.getString("platName");
                userLogin(platName, userName, userId, userIcon);
                break;
            case MSG_AUTH_CANCEL:
                restoreSwitch();
                Toast.makeText(this, "授权操作已取消", Toast.LENGTH_SHORT).show();
                break;
            case MSG_AUTH_ERROR:
                restoreSwitch();
                Toast.makeText(this, "授权操作遇到错误，请阅读Logcat输出", Toast.LENGTH_SHORT).show();
                break;
            case MSG_AUTH_COMPLETE:
                Toast.makeText(this, "授权成功，正在跳转登录操作…", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    private void restoreSwitch() {
        switch (bindNameFlag) {
            case QQ_NAME:
                mSwitchQqAccount.setChecked(false);
                break;
            case WECHAT_NAME:
                mSwitchWechatAccount.setChecked(false);
                break;
            case SINAWEIBO_NAME:
                mSwitchWeiboAccount.setChecked(false);
                break;
            default:
                break;
        }
        bindNameFlag = "";

    }

    private void userLogin(String platName, String userName, String userId, String userIcon) {

        switch (platName) {
            case "SinaWeibo":
                mWeibonameAccount.setText(TextUtils.isEmpty(userName) ? "" : userName);
                SPUtils.put(this, Const.SINAWEIBO_USERID, userId);
                SPUtils.put(this, Const.SINAWEIBO_USERNAME, userName);
                SPUtils.put(this, Const.SINAWEIBO_USERICON, userIcon);
                break;
            case "QQ":
                mQqnameAccount.setText(TextUtils.isEmpty(userName) ? "" : userName);
                SPUtils.put(this, Const.QQ_USERID, userId);
                SPUtils.put(this, Const.QQ_USERNAME, userName);
                SPUtils.put(this, Const.QQ_USERICON, userIcon);
                break;
            case "Wechat":
                mWechatnameAccount.setText(TextUtils.isEmpty(userName) ? "" : userName);
                SPUtils.put(this, Const.WECHAT_USERID, userId);
                SPUtils.put(this, Const.WECHAT_USERNAME, userName);
                SPUtils.put(this, Const.WECHAT_USERICON, userIcon);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registerPage != null) {
            registerPage.onDestroy();
        }

    }
}
