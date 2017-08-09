package com.ocnyang.qbox.app.module.me.calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.ocnyang.qbox.app.utils.Lunar;

import java.util.Calendar;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/23 13:11.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class LunarSpan implements LineBackgroundSpan {
    private String year;
    private String month;

    public LunarSpan(String year, String month) {
        this.year = year;
        this.month = month;
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Integer.valueOf(year),Integer.valueOf(month)-1,Integer.valueOf(text.toString()));

        Lunar lunar = new Lunar(calendar.getTime());
        String chinaDayString = lunar.getChinaDayString();
        Paint paint = new Paint();
        paint.setTextSize(CircleBackGroundSpan.dip2px(10));
        paint.setColor(Color.parseColor("#cccccc"));
        c.drawText(chinaDayString, (right - left) / 2 - CircleBackGroundSpan.dip2px(10), (bottom - top) / 2 + CircleBackGroundSpan.dip2px(17), paint);
    }
}