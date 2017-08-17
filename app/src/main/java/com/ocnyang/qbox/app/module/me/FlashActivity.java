package com.ocnyang.qbox.app.module.me;

import android.Manifest;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class FlashActivity extends BaseCommonActivity implements View.OnClickListener {
    RelativeLayout root;
    Camera camera;
    ImageButton flashLight;
    ImageButton sos;
    Drawable[] controlDrawbles = null;
    Parameters parameters;
    volatile boolean continueSos;
    Handler sosHandler;
    final int FLASH_LIGHT_ON = 1;
    final int FLASH_LIGHT_OFF = -1;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_flash);
    }

    @Override
    public void initView() {
        Resources resources = getResources();
        controlDrawbles = new Drawable[]{
                resources.getDrawable(R.drawable.flash_light_off),
                resources.getDrawable(R.drawable.flash_light_on),
                resources.getDrawable(R.drawable.sos_off),
                resources.getDrawable(R.drawable.sos_on),
                resources.getDrawable(R.drawable.background),
                resources.getDrawable(R.drawable.background_on)};

        root = (RelativeLayout) findViewById(R.id.root);
        flashLight = (ImageButton) findViewById(R.id.flashLight);
        flashLight.setTag("open");
        sos = (ImageButton) findViewById(R.id.sos);
        sos.setTag("close");

        FlashActivityPermissionsDispatcher.startFlashWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    public void startFlash() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        int screenHeight = metrics.heightPixels;
        LayoutParams flashLightParams = (LayoutParams) flashLight
                .getLayoutParams();
        LayoutParams sosParams = (LayoutParams) sos.getLayoutParams();
        flashLightParams.setMargins(0, screenHeight * 1 / 2, 0, 0);
        sosParams.setMargins(0, screenHeight * 4 / 5, 0, 0);
        flashLight.setLayoutParams(flashLightParams);
        sos.setLayoutParams(sosParams);
        camera = Camera.open();
        parameters = camera.getParameters();
        parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
        flashLight.setImageDrawable(controlDrawbles[1]);
        root.setBackground(controlDrawbles[5]);

        flashLight.setOnClickListener(this);
        sos.setOnClickListener(this);
    }

    /**
     * 为什么要获取这个权限给用户的说明
     *
     * @param request
     */
    @OnShowRationale(Manifest.permission.CAMERA)
    public void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle("权限申请")
                .setMessage("你将用的闪光灯功能，请你授权照相机权限！")
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
        FlashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onClick(View v) {
        // Auto-generated method stub
        judge(v);

    }

    private void judge(View v) {
        if (!(v instanceof ImageButton))
            return;
        ImageButton controlIb = (ImageButton) v;

        continueSos = false;
        if (v.equals(flashLight)) {

            sos.setImageDrawable(controlDrawbles[2]);
            sos.setTag("close");
            if (v.getTag().equals("close")) {
                turnOnFlashLight();
                v.setTag("open");
                controlIb.setImageDrawable(controlDrawbles[1]);
                root.setBackground(controlDrawbles[5]);

            } else {
                turnOffFlashLight();
                v.setTag("close");
                controlIb.setImageDrawable(controlDrawbles[0]);
                root.setBackground(controlDrawbles[4]);

            }
        } else if (v.equals(sos)) {
            flashLight.setImageDrawable(controlDrawbles[0]);
            flashLight.setTag("close");
            if (v.getTag().equals("close")) {
                v.setTag("open");
                controlIb.setImageDrawable(controlDrawbles[3]);
                continueSos = true;
                sosHandler = new Handler() {
                    public void handleMessage(Message msg)

                    {
                        if (!continueSos)
                            return;
                        switch (msg.arg1) {
                            case FLASH_LIGHT_ON:
                                turnOnFlashLight();
                                root.setBackground(controlDrawbles[5]);
                                break;
                            case FLASH_LIGHT_OFF:
                                turnOffFlashLight();
                                root.setBackground(controlDrawbles[4]);
                                break;
                            default:
                                break;
                        }
                    }
                };
                new Thread() {
                    public void run() {
                        while (continueSos) {
                            Message msg = Message.obtain();
                            msg.arg1 = FLASH_LIGHT_ON;
                            sosHandler.sendMessage(msg);
                            try {
                                Thread.sleep(600);
                            } catch (Exception e) {
                                // handle exception
                                System.out.println("exception:"
                                        + e.getMessage());
                            }
                            Message message = Message.obtain();
                            message.arg1 = FLASH_LIGHT_OFF;
                            sosHandler.sendMessage(message);
                            try {
                                Thread.sleep(300);
                            } catch (Exception e) {
                                //  handle exception
                                System.out.println("exception:"
                                        + e.getMessage());
                            }

                        }
                        Message message = Message.obtain();
                        message.arg1 = FLASH_LIGHT_OFF;
                        sosHandler.sendMessage(message);
                    }

                    ;
                }.start();

            } else {
                v.setTag("close");
                turnOffFlashLight();
                controlIb.setImageDrawable(controlDrawbles[2]);
                root.setBackground(controlDrawbles[4]);

            }

        }

    }

    @Override
    protected void onDestroy() {
        //Auto-generated method stub
        if (camera != null) {
            camera.release();
            camera = null;
        }
        super.onDestroy();
    }


    private void turnOnFlashLight() {

        parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();

    }

    private void turnOffFlashLight() {
        parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.stopPreview();
    }

}
