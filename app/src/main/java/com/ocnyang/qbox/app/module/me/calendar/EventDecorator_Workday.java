package com.ocnyang.qbox.app.module.me.calendar;

import android.text.TextUtils;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.ocnyang.qbox.app.model.HolidaysManager;

import java.util.Map;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/23 15:46.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class EventDecorator_Workday implements DayViewDecorator {
    private Map<String, String> mDateStringMap;

    public EventDecorator_Workday(Map<String,String> dateStringMap) {
        this.mDateStringMap = dateStringMap;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        boolean b = mDateStringMap.containsKey(HolidaysManager.formatDate(day.getDate()));

        if (b){
            String s = mDateStringMap.get(HolidaysManager.formatDate(day.getDate()));
            if (TextUtils.isEmpty(s)) {
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new EventSpan_Workday());
    }
}
