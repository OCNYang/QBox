package com.ocnyang.qbox.app.module.me.calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.LineBackgroundSpan;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/23 13:06.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class EventSpan_Holiday implements LineBackgroundSpan{
    @Override
    public void drawBackground(Canvas c, Paint p,
                               int left, int right, int top, int baseline, int bottom,
                               CharSequence text, int start, int end, int lnum) {


        Paint paint = new Paint();

        paint.setColor(Color.parseColor("#FFFF4081"));
        RectF rectF = new RectF(0,(-(right-bottom)/2), CircleBackGroundSpan.dip2px(18),(-(right-bottom)/2)+ CircleBackGroundSpan.dip2px(18));
        c.drawRoundRect(rectF,0,0,paint);

        paint.setTextSize(CircleBackGroundSpan.dip2px(14));
        paint.setColor(Color.WHITE);
        c.drawColor(Color.parseColor("#22FF4081"));
        c.drawText("休", CircleBackGroundSpan.dip2px(2),(-(right-bottom)/2)+ CircleBackGroundSpan.dip2px(14), paint);
    }

}
