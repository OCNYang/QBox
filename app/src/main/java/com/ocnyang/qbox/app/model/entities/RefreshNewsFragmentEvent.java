package com.ocnyang.qbox.app.model.entities;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/24 09:35.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class RefreshNewsFragmentEvent {
    private int mark_code;

    public int getMark_code() {
        return mark_code;
    }

    public void setMark_code(int mark_code) {
        this.mark_code = mark_code;
    }

    public RefreshNewsFragmentEvent(int mark_code) {
        this.mark_code = mark_code;
    }

    public RefreshNewsFragmentEvent() {
    }
}

