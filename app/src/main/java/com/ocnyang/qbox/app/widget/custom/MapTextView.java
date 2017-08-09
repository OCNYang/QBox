package com.ocnyang.qbox.app.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.ocnyang.qbox.app.R;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/28 11:27.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class MapTextView extends View {

//    int width = 400;
//    int height = 400;
//    int fontsize = width / 4;
//    int fontsizeBottom = 40;
    public boolean mIsUniformColor;
    public int mCircleColor;
    public int mKeyBackGroundColor;
    public int mKeyTextColor;
    public int mValueTextColor;
    public float mKeyTextSize;
    public float mValueTextSize;
    public int mViewSize;
    public int mWidth;
    public int mHight;
    public String mValueText;
    public String mKeyText;

    @Override
    public String toString() {
        return "MapTextView{" +
                "mIsUniformColor=" + mIsUniformColor +
                ", mCircleColor=" + mCircleColor +
                ", mKeyBackGroundColor=" + mKeyBackGroundColor +
                ", mKeyTextColor=" + mKeyTextColor +
                ", mValueTextColor=" + mValueTextColor +
                ", mKeyTextSize=" + mKeyTextSize +
                ", mValueTextSize=" + mValueTextSize +
                ", mViewSize=" + mViewSize +
                ", mWidth=" + mWidth +
                ", mHight=" + mHight +
                ", mValueText='" + mValueText + '\'' +
                ", mKeyText='" + mKeyText + '\'' +
                '}';
    }

    public MapTextView(Context context) {
        super(context);
    }

    public MapTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MapTextView);

        mValueText = typedArray.getString(R.styleable.MapTextView_valueText);
        mKeyText = typedArray.getString(R.styleable.MapTextView_keyText);

        mIsUniformColor = ((boolean) typedArray.getBoolean(R.styleable.MapTextView_isUniformColor, true));
        int colorAccent = context.getResources().getColor(R.color.colorAccent);
        mCircleColor = typedArray.getColor(R.styleable.MapTextView_circleColor,
                colorAccent);
        if (mIsUniformColor){
            mKeyBackGroundColor = mCircleColor;
            mKeyTextColor = Color.WHITE;
            mValueTextColor = mCircleColor;
        }else {
            mKeyBackGroundColor = typedArray.getColor(R.styleable.MapTextView_keyBackgroundColor, colorAccent);
            mKeyTextColor = typedArray.getColor(R.styleable.MapTextView_keyTextColor, Color.WHITE);
            mValueTextColor = typedArray.getColor(R.styleable.MapTextView_valueTextColor, colorAccent);
        }
        mKeyTextSize = typedArray.getDimension(R.styleable.MapTextView_keyText_size,0);
        mValueTextSize = typedArray.getDimension(R.styleable.MapTextView_valueText_size,0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewSize = Math.min(w,h);
        mWidth = w;
        mHight = h;
        if (mKeyTextSize == 0.0) {
            mKeyTextSize = mViewSize/10;
        }
        if (mValueTextSize == 0.0) {
            mValueTextSize = mViewSize/4;
        }
        super.onSizeChanged(w, h, oldw, oldh);

    }

    public MapTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.e("maptextview"+toString());
        canvas.translate(mWidth/2,mHight/2);
        Paint paint = new Paint();

        paint.setColor(mCircleColor);
        paint.setStyle(Paint.Style.STROKE);
        int radius = mViewSize / 2;
        canvas.drawCircle(0, 0, radius, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mKeyBackGroundColor);

        RectF rectF = new RectF(-radius, -radius, radius, radius);
//        double start = 90 - (Math.cos((double) ((radius / 4) / (radius / 2))));
//        double end = 180 - start * 2;
        canvas.drawArc(rectF, 20, 140, false, paint);

        float pathStartX = (float) Math.sqrt(radius * radius - mValueTextSize * mValueTextSize);

//        canvas.translate(radius, radius);
        canvas.translate(-pathStartX, 0);

        float pathEndX = pathStartX * 2;

        Path path = new Path();
        path.lineTo(pathEndX, 0);

        paint.setTextSize(mValueTextSize);
        paint.setColor(mValueTextColor);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawTextOnPath(TextUtils.isEmpty(mValueText)?"love":mValueText, path, 0, 0, paint);

        double sin20 = Math.sin(Math.PI * 20 / 180);

        double radiusDistance = sin20 * radius;
        double v = (radius - radiusDistance) / 2;
        double distanceX = Math.sqrt(radius * radius - (v + radiusDistance) * (v + radiusDistance));

        canvas.translate(pathStartX, 0);
        canvas.translate(0, ((float) (v + radiusDistance)));
        canvas.translate((float) -distanceX, 0);

        Path path_bottomText = new Path();
        path_bottomText.lineTo((float) (distanceX * 2), 0);

        paint.setColor(mKeyTextColor);
        paint.setTextSize((float) Math.min(mKeyTextSize, v));
        canvas.drawTextOnPath(TextUtils.isEmpty(mKeyText)?"小秋魔盒":mKeyText, path_bottomText, 0, 0, paint);
    }

    public void setValueText(String valueText) {
        mValueText = valueText;
        invalidate();
    }

    public void setKeyText(String keyText) {
        mKeyText = keyText;
        invalidate();
    }
}
