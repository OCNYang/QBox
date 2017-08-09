package com.ocnyang.qbox.app.module.start;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.module.mains.MainsActivity;
import com.ocnyang.qbox.app.utils.SPUtils;
import com.ocnyang.qbox.app.utils.StateBarTranslucentUtils;
import com.umeng.analytics.MobclickAgent;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SplashActivity extends AppCompatActivity implements SplashView {

    private KenBurnsView mKenBurns;
    private ImageView mLogo;
    private TextView welcomeText;

    private SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        mPresenter = new SplashPresenterImpl(this);

        // 判断是否是第一次开启应用
        boolean isFirstOpen = (boolean) SPUtils.get(this, Const.FIRST_OPEN, false);
        mPresenter.isFirstOpen(isFirstOpen);
    }

    //=======================动态权限的申请===========================================================<
    @Override
    @NeedsPermission({Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startWelcomeGuideActivity() {
        com.ocnyang.qbox.app.module.start.welcome.WelcomeActivity.start(this);
        finish();
    }

    /**
     * 为什么要获取这个权限给用户的说明
     *
     * @param request
     */
    @OnShowRationale({Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("有部分权限需要你的授权")
//                .setPositiveButton(R.string.imtrue, (dialog, button) -> request.proceed())
//                .setNegativeButton(R.string.cancel, (dialog, button) -> request.cancel())
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
                .show()
        ;
    }

    /**
     * 如果用户不授予权限调用的方法
     */
    @OnPermissionDenied({Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForCamera() {
        showPermissionDenied();
    }

    public void showPermissionDenied(){
        new AlertDialog.Builder(this)
                .setTitle("权限说明")
                .setCancelable(false)
                .setMessage("本应用需要部分必要的权限，如果不授予可能会影响正常使用！")
                .setNegativeButton("退出应用",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("赋予权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SplashActivityPermissionsDispatcher.startWelcomeGuideActivityWithCheck(SplashActivity.this);
                    }
                })
                .create().show();
    }

    /**
     * 如果用户选择了让设备“不再询问”，而调用的方法
     */
    @OnNeverAskAgain({Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForCamera() {
        Toast.makeText(this, "不再询问授权权限！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    //=======================动态权限的申请===========================================================>


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_splash);
        //设置状态栏透明
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        mLogo = ((ImageView) findViewById(R.id.logo_splash));
        welcomeText = ((TextView) findViewById(R.id.welcome_text));

        Glide.with(this)
                .load(R.drawable.welcometoqbox)
                .into(mKenBurns);

        animation2();
        animation3();
    }

    Animation anim;

    private void animation2() {
        mLogo.setAlpha(1.0F);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        mLogo.startAnimation(anim);
    }

    ObjectAnimator alphaAnimation;

    private void animation3() {
        alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();
    }

    @Override
    public void startHomeActivity() {
        startActivity(new Intent(this, MainsActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mKenBurns != null) {
            mKenBurns.pause();
        }
        if (alphaAnimation != null) {
            alphaAnimation.cancel();
        }
        if (anim != null) {
            anim.cancel();
        }
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mKenBurns != null) {
            mKenBurns.resume();
        }
        MobclickAgent.onResume(this);
    }
}
