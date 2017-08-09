package com.ocnyang.qbox.app.widget.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

public class NoZoomControllWebView extends WebView {

    private ZoomButtonsController mZoomButtonsController = null;

    public NoZoomControllWebView(Context context) {
        super(context);
        disableControls();
    }

    public NoZoomControllWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        disableControls();
    }

    public NoZoomControllWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        disableControls();
    }

    /**
     * Disable the controls
     */
    @SuppressLint("NewApi")
    private void disableControls() {
        this.getSettings().setBuiltInZoomControls(true);
        this.getSettings().setDisplayZoomControls(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        if (mZoomButtonsController != null) {
            // Hide the controlls AFTER they where made visible by the default implementation.
            mZoomButtonsController.setVisible(false);
        }
        return true;
    }
}
