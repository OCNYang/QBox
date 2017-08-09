package com.ocnyang.qbox.app.model;


import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/23 15:49.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class HolidaysManager {
    Map<String, String> mDateMap;
    private Calendar calendar = Calendar.getInstance();

    public HolidaysManager() {
        mDateMap = new HashMap<>();
        //元旦
        mDateMap.put(getDate(2017, 01, 01), "元旦");
        mDateMap.put(getDate(2017, 01, 02), "");
        //春节
        mDateMap.put(getDate(2017, 01, 22), "");
        mDateMap.put(getDate(2017, 01, 27), "春节");
        mDateMap.put(getDate(2017, 01, 28), "春节");
        mDateMap.put(getDate(2017, 01, 29), "春节");
        mDateMap.put(getDate(2017, 01, 30), "春节");
        mDateMap.put(getDate(2017, 01, 31), "春节");
        mDateMap.put(getDate(2017, 02, 02), "春节");
        mDateMap.put(getDate(2017, 02, 04), "");
        //清明节
        mDateMap.put(getDate(2017, 04, 02), "清明节");
        mDateMap.put(getDate(2017, 04, 03), "清明节");
        mDateMap.put(getDate(2017, 04, 04), "清明节");
        mDateMap.put(getDate(2017, 04, 01), "");
        //劳动节
        mDateMap.put(getDate(2017, 05, 01), "劳动节");
        mDateMap.put(getDate(2017, 04, 29), "劳动节");
        mDateMap.put(getDate(2017, 04, 30), "劳动节");
        //端午节
        mDateMap.put(getDate(2017, 05, 27), "");
        mDateMap.put(getDate(2017, 05, 28), "端午节");
        mDateMap.put(getDate(2017, 05, 29), "端午节");
        mDateMap.put(getDate(2017, 05, 30), "端午节");
        //中秋节、国庆节
        mDateMap.put(getDate(2017, 10, 01), "国庆节");
        mDateMap.put(getDate(2017, 10, 02), "国庆节");
        mDateMap.put(getDate(2017, 10, 03), "国庆节");
        mDateMap.put(getDate(2017, 10, 04), "中秋节");
        mDateMap.put(getDate(2017, 10, 05), "中秋节");
        mDateMap.put(getDate(2017, 10, 06), "国庆节");
        mDateMap.put(getDate(2017, 10, 07), "国庆节");
        mDateMap.put(getDate(2017, 10, 8), "国庆节");
        mDateMap.put(getDate(2017, 9, 30), "");
    }


    private String getDate(int year, int month, int date) {
        calendar.set(year, month-1, date);
        Date time = calendar.getTime();
        return formatDate(time);
    }

    public Map<String, String> getHolidays() {
        return mDateMap;
    }

    public static String formatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(date);
        Logger.e("假期："+format);
        return format;
    }

    public String containsDate(String date) {
        String name = null;
        if (mDateMap.containsKey(date)) {
            name = mDateMap.get(date);
        }
        return name;
    }

    public String containsDate(int year, int month, int date) {
        return containsDate(getDate(year, month, date));
    }
}
