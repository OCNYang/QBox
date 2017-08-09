package com.ocnyang.qbox.app.model.entities;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/21 10:38.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class RefreshConstellationEvent {
    String constellation;

    public RefreshConstellationEvent() {
    }

    public RefreshConstellationEvent(String constellation) {
        this.constellation = constellation;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }
}
