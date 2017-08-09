package com.ocnyang.qbox.app.module.me;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ZxingActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar_zxing)
    Toolbar mToolbarZxing;
    @BindView(R.id.all_zxing)
    LinearLayout mAllZxing;
    @BindView(R.id.text_zxing)
    LinearLayout mTextZxing;
    @BindView(R.id.web_zxing)
    LinearLayout mWebZxing;
    @BindView(R.id.download_zxing)
    LinearLayout mDownloadZxing;
    @BindView(R.id.img_zxing)
    LinearLayout mImgZxing;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_zxing);

    }

    @Override
    public void initView() {
        initToolbar();
    }

    @Override
    public void initPresenter() {

    }

    private void initToolbar() {
        mToolbarZxing.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbarZxing.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @OnClick({R.id.all_zxing, R.id.text_zxing, R.id.web_zxing, R.id.download_zxing, R.id.img_zxing})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all_zxing:
                ZxingActivityPermissionsDispatcher.startSpotWithCheck(this,ZxingStartActivity.STYLE_ALL);
                break;
            case R.id.text_zxing:
                ZxingActivityPermissionsDispatcher.startSpotWithCheck(this,ZxingStartActivity.STYLE_TEXT);
                break;
            case R.id.web_zxing:
                ZxingActivityPermissionsDispatcher.startSpotWithCheck(this,ZxingStartActivity.STYLE_WEB);
                break;
            case R.id.download_zxing:
                ZxingActivityPermissionsDispatcher.startSpotWithCheck(this,ZxingStartActivity.STYLE_DOWNLOAD);
                break;
            case R.id.img_zxing:
                ZxingActivityPermissionsDispatcher.startSpotWithCheck(this,ZxingStartActivity.STYLE_IMG);
                break;
        }
    }
    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startSpot(int styleAll) {
        Intent intent = new Intent(this, ZxingStartActivity.class);
        intent.putExtra(ZxingStartActivity.STYLE,styleAll);
        startActivity(intent);
    }

    /**
     * 为什么要获取这个权限给用户的说明
     *
     * @param request
     */
    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle("权限申请")
                .setMessage("照相机的权限：为了扫描二维码（必需），\n读取存储的权限：为了从相册选取二维码（可选）")
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
    @OnPermissionDenied(Manifest.permission.CAMERA)
    public void showDeniedForCamera() {
        Toast.makeText(this, "没有授予照相机的权限", Toast.LENGTH_SHORT).show();
    }

    /**
     * 如果用户选择了让设备“不再询问”，而调用的方法
     */
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    public void showNeverAskForCamera() {
        Toast.makeText(this, "Don't ask again!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 委托授权
        ZxingActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
