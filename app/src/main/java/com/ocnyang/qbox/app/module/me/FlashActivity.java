package com.ocnyang.qbox.app.module.me;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;

public class FlashActivity extends BaseCommonActivity implements View.OnClickListener{
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
