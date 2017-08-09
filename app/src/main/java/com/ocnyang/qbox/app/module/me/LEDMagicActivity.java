package com.ocnyang.qbox.app.module.me;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.config.Const;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LEDMagicActivity extends BaseCommonActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    private boolean isStart = false;
    private int countLog = 0;
    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    String[] mStrings;

    Timer timer = null;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private HTextView mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    public View mView;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String string = mStrings[countLog % mStrings.length];
                if (TextUtils.isEmpty(string)){
                    string = getString(R.string.app_name);
                }
                mContentView.animateText(string);

            }
            super.handleMessage(msg);
        }
    };


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_ledmagic);

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String ledContent = extras.getString(Const.LED_CONTENT);
        int ledBgcolor = extras.getInt(Const.LED_BG_COLOR);
        int ledFontcolor = extras.getInt(Const.LED_FONT_COLOR);
        String magicStyle = extras.getString(Const.LED_MAGIC_STYLE);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = (HTextView) findViewById(R.id.fullscreen_content);

        if (!TextUtils.isEmpty(ledContent)) {
            mStrings = ledContent.split("#");
        }

        if (ledFontcolor != 0) {
            mContentView.setTextColor(ledFontcolor);
        }
        if (ledBgcolor != 0) {
            mContentView.setBackgroundColor(ledBgcolor);
        }
        String[] stringArray = getResources().getStringArray(R.array.arrays_led_magicstyle);
        Map<String,HTextViewType> viewTypeMap = new HashMap<String,HTextViewType>();
        viewTypeMap.put("scale",HTextViewType.SCALE);
        viewTypeMap.put("evaporate",HTextViewType.EVAPORATE);
        viewTypeMap.put("fall",HTextViewType.FALL);
        viewTypeMap.put("pixelate",HTextViewType.PIXELATE);
        viewTypeMap.put("anvil",HTextViewType.ANVIL);
        viewTypeMap.put("sparkle",HTextViewType.SPARKLE);
        viewTypeMap.put("line",HTextViewType.LINE);
        viewTypeMap.put("typer",HTextViewType.TYPER);
        viewTypeMap.put("rainbow",HTextViewType.RAINBOW);

        if (!TextUtils.isEmpty(magicStyle)) {
            mContentView.setAnimateType(viewTypeMap.get(magicStyle));
        }

        mContentView.animateText(TextUtils.isEmpty(mStrings[0])?getString(R.string.app_name):mStrings[0]);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        mView = findViewById(R.id.dummy_button);
        mView.setOnTouchListener(mDelayHideTouchListener);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = !isStart;
                if (isStart) {
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new MyTask(), 1, 3000);
                }else {
                    if (timer != null) {
                        timer.cancel();
                    }
                }
            }
        });

        ((AppCompatSeekBar) findViewById(R.id.seekbar_ledmagic))
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        mContentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1*progress+50);
                        mContentView.reset(mContentView.getText());
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
    }

    @Override
    public void initPresenter() {

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


    private class MyTask extends TimerTask {
        @Override
        public void run() {
//            if (isStart) {
                countLog++;
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
//            }else {
//                timer.cancel();
//            }
        }
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }
}
