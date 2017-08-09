package com.ocnyang.qbox.app.module.me.calendar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.ocnyang.qbox.app.utils.DateUtils;

import java.util.Date;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/23 13:04.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class TodayDecorator implements DayViewDecorator{
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Date date = new Date();
        String dateStr = DateUtils.date2String(date, "yyyy-MM-dd");
        Date parse = DateUtils.string2Date(dateStr, "yyyy-MM-dd");
        if (day.getDate().equals(parse)) {
            return true;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new AnnulusSpan());
    }
}
