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


public class EventSpan_Workday implements LineBackgroundSpan{
    /**
     * 参数简介：坐标系是文本显示的横向区域
     * 即：文本横向是充满父区域，高度自适应
     * 所以：原点是：中间显示区域的左上角
     * @param c
     * @param p
     * @param left
     * @param right
     * @param top
     * @param baseline
     * @param bottom
     * @param text
     * @param start
     * @param end
     * @param lnum
     */
    @Override
    public void drawBackground(Canvas c, Paint p,
                               int left, int right, int top, int baseline, int bottom,
                               CharSequence text, int start, int end, int lnum) {

        Paint paint = new Paint();

        paint.setColor(Color.parseColor("#FF212121"));
        RectF rectF = new RectF(0,(-(right-bottom)/2), CircleBackGroundSpan.dip2px(18),(-(right-bottom)/2)+ CircleBackGroundSpan.dip2px(18));
        c.drawRoundRect(rectF,0,0,paint);

        paint.setTextSize(CircleBackGroundSpan.dip2px(14));
        paint.setColor(Color.WHITE);
        c.drawColor(Color.parseColor("#22212121"));
        c.drawText("班", CircleBackGroundSpan.dip2px(2),(-(right-bottom)/2)+ CircleBackGroundSpan.dip2px(14), paint);
    }

}
