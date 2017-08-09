package com.ocnyang.qbox.app.module.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.model.entities.LEDRecommendColor;
import com.ocnyang.qbox.app.model.entities.LEDRecommendColorManager;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LEDActivity extends BaseCommonActivity implements SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.toolbar_led)
    Toolbar mToolbarLed;
    @BindView(R.id.content_led)
    TextInputEditText mContentLed;
    @BindView(R.id.fontcolor_btn_led)
    AppCompatButton mFontcolorBtnLed;
    @BindView(R.id.bgcolor_btn_led)
    AppCompatButton mBgcolorBtnLed;
    @BindView(R.id.recommendcolor_btn_led)
    AppCompatButton mRecommendcolorBtnLed;
    @BindView(R.id.preview_led)
    AppCompatTextView mPreviewLed;
    @BindView(R.id.single_radiobtn_led)
    AppCompatRadioButton mSingleRadiobtnLed;
    @BindView(R.id.single_toss_radiobtn_led)
    AppCompatRadioButton mSingleTossBtnLed;
    @BindView(R.id.rollspeed_seekbar_led)
    AppCompatSeekBar mRollspeedSeekbarLed;
    @BindView(R.id.adaptive_radiobtn_led)
    AppCompatRadioButton mAdaptiveRadiobtnLed;
    @BindView(R.id.magic_radiobtn_led)
    AppCompatRadioButton mMagicRadiobtnLed;
    @BindView(R.id.showstyle_radiogroup_led)
    RadioGroup mShowstyleRadiogroupLed;
    @BindView(R.id.start_btn_led)
    AppCompatButton mStartBtnLed;
    @BindView(R.id.reverseColor_led)
    ImageView mReverseColorLed;
    @BindView(R.id.spinner_magicstyle_led)
    AppCompatSpinner mCompatSpinner;
    @BindView(R.id.tv_lines_led)
    TextView mLinesTextView;
    @BindView(R.id.lines_seekbar_led)
    AppCompatSeekBar mlinesSeekbar;
    public int mBgColor;
    public int mFontColor;
    public int mProgress;
    public int mShowStyle;
    public int mMagicStyle;


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_led);
    }

    @Override
    public void initView() {
        initToolbar();
        initViewEvent();
    }

    private void initViewEvent() {
        mShowstyleRadiogroupLed.check(R.id.single_radiobtn_led);
        mShowStyle = R.id.single_radiobtn_led;
        if (!mAdaptiveRadiobtnLed.isChecked()) {
            mlinesSeekbar.setEnabled(false);
        }

        mCompatSpinner.setSelection(0);
        mMagicStyle = 0;
        mRollspeedSeekbarLed.setOnSeekBarChangeListener(this);
        mShowstyleRadiogroupLed.setOnCheckedChangeListener(this);
        mCompatSpinner.setOnItemSelectedListener(this);
        mlinesSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLinesTextView.setText(String.valueOf(progress+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initToolbar() {
        mToolbarLed.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbarLed.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initPresenter() {

    }


    @OnClick({R.id.fontcolor_btn_led, R.id.bgcolor_btn_led, R.id.start_btn_led, R.id.recommendcolor_btn_led,R.id.reverseColor_led})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fontcolor_btn_led:
                showColorPicker(true);
            case R.id.bgcolor_btn_led:
                showColorPicker(false);
                break;
            case R.id.recommendcolor_btn_led:
                showRecommendColorDialog();
                break;
            case R.id.reverseColor_led:
                if (mBgColor != 0 && mFontColor != 0) {
                    int bgColor = mBgColor;
                    changePreviewBgColor(mFontColor);
                    changePreviewFontColor(bgColor);
                }
                break;
            case R.id.start_btn_led:
                startShowLed();
                break;
        }
    }

    private void startShowLed() {
        Editable mContentLedText = mContentLed.getText();
        if (TextUtils.isEmpty(mContentLedText)) {
            mContentLed.setError("请填写要显示的内容");
            return;
        }
        if (mFontColor == 0) {
            mFontColor = mPreviewLed.getCurrentTextColor();
        }
        if (mBgColor == 0) {
            mBgColor = 0xffffffff;
        }

        Bundle bundle = new Bundle();
        bundle.putString(Const.LED_CONTENT,mContentLedText.toString());
        bundle.putInt(Const.LED_BG_COLOR,mBgColor);
        bundle.putInt(Const.LED_FONT_COLOR,mFontColor);

        switch(mShowStyle){
            case R.id.single_radiobtn_led:
                bundle.putInt(Const.LED_ROLL_SPEED,mProgress);
                bundle.putBoolean(Const.LED_SINGLE_ISH,true);
                startSingleLED(bundle);
                break;
            case R.id.single_toss_radiobtn_led:
                bundle.putInt(Const.LED_ROLL_SPEED,mProgress);
                bundle.putBoolean(Const.LED_SINGLE_ISH,false);
                startSingleLED(bundle);
                break;
            case R.id.adaptive_radiobtn_led:
                bundle.putString(Const.LED_LINES,mLinesTextView.getText().toString());
                startAdaptiveLED(bundle);
                break;
            case R.id.magic_radiobtn_led:
                bundle.putString(Const.LED_MAGIC_STYLE,
                        getResources().getStringArray(R.array.arrays_led_magicstyle)[mMagicStyle]
                );
                startMagicLED(bundle);
                break;
            default:
                break;
        }
    }

    private void startMagicLED(Bundle bundle) {
        if(!mContentLed.getText().toString().contains("#")){
            mContentLed.setError("至少输入两句话，用'#'分隔");
            return;
        }
        startActivity(new Intent(this,LEDMagicActivity.class).putExtras(bundle));

    }

    private void startAdaptiveLED(Bundle bundle) {
        startActivity(new Intent(this,LEDAutoActivity.class).putExtras(bundle));
    }

    private void startSingleLED(Bundle bundle) {
        startActivity(new Intent(this,LEDSingleActivity.class).putExtras(bundle));

    }

    private void showRecommendColorDialog() {

        final List<LEDRecommendColor> ledRecommendColors = LEDRecommendColorManager.getInstance().getLEDRecommendColors();
        new AlertDialog.Builder(this)
                .setTitle("选择颜色组合")
                .setItems(LEDRecommendColorManager.colorName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        LEDRecommendColor ledRecommendColor = ledRecommendColors.get(which);
                        changePreviewBgColor(ledRecommendColor.getBackgroundColor());
                        changePreviewFontColor(ledRecommendColor.getFontColor());
                    }
                })
                .show();

    }

    private void showColorPicker(final boolean isFontColor) {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("选择颜色")
                .initialColor(0xffff0000)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("确定", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        if (isFontColor) {
                            changePreviewFontColor(selectedColor);
                        } else {
                            changePreviewBgColor(selectedColor);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    private void changePreviewBgColor(int selectedColor) {
        mPreviewLed.setBackgroundColor(selectedColor);
        mBgColor = selectedColor;
    }

    private void changePreviewFontColor(int selectedColor) {
        mPreviewLed.setTextColor(selectedColor);
        mFontColor = selectedColor;
    }

    /**
     * 对滚动速度进度条 的监听
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mProgress = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * 对 显示样式 的单选按钮的事件监听
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        mShowStyle = checkedId;
        if (checkedId == R.id.single_radiobtn_led || checkedId==R.id.single_toss_radiobtn_led) {
            if (!mRollspeedSeekbarLed.isEnabled())
                mRollspeedSeekbarLed.setEnabled(true);
        } else {
            if (mRollspeedSeekbarLed.isEnabled())
                mRollspeedSeekbarLed.setEnabled(false);
        }

        if (checkedId != R.id.magic_radiobtn_led) {
            if (mCompatSpinner.isEnabled()) {
                mCompatSpinner.setEnabled(false);
            }
        }else {
            if (!mCompatSpinner.isEnabled()) {
                mCompatSpinner.setEnabled(true);
            }
        }

        if (checkedId == R.id.adaptive_radiobtn_led) {
            if (!mlinesSeekbar.isEnabled()) {
                mlinesSeekbar.setEnabled(true);
            }
        }else {
            if (mlinesSeekbar.isEnabled()) {
                mlinesSeekbar.setEnabled(false);
            }
        }
    }

    /**
     * 对魔法动效 的动效样式的选择的监听
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mMagicStyle = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
//        mMagicStyle = 0;
    }
}
