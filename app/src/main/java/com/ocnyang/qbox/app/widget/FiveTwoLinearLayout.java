package com.ocnyang.qbox.app.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ocnyang.qbox.app.utils.PixelUtil;


/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/7/12 17:16.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class FiveTwoLinearLayout extends LinearLayout {
    public FiveTwoLinearLayout(Context context) {
        this(context, null);
    }

    public FiveTwoLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FiveTwoLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    /**
     * 这里的作用是能够使占总宽度1/3的图片的宽高比能够达到3/4
     * 40和20是内边距，这里写得不严谨，没有放到dimens.xml里
     */
    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int fourThreeHeight = MeasureSpec.makeMeasureSpec(
                ((MeasureSpec.getSize(widthSpec) - PixelUtil.dp2px(40)) / 4) + PixelUtil.dp2px(20),
                MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, fourThreeHeight);
    }
}
