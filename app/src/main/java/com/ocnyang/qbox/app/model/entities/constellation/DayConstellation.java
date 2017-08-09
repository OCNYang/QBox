package com.ocnyang.qbox.app.model.entities.constellation;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/20 11:21.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class DayConstellation extends BaseConstellation{
    /**
     * date : 20170321
     * datetime : 2017年03月21日
     * all : 60%
     * color : 黄
     * number : 5
     * QFriend : 金牛
     * summary : 今天的工作有点繁重，并且不能非常直白的解决，需要你费一点心思。同时有的时候会没来由的发脾气或者情绪低落。正财运不错，会有可能赚一笔钱。你的领导或者朋友都能帮助你赚钱。
     */


    private String datetime;
    private String all;
    private String color;
    private int number;
    private String QFriend;
    private String summary;


    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getQFriend() {
        return QFriend;
    }

    public void setQFriend(String QFriend) {
        this.QFriend = QFriend;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return
                ", datetime='" + datetime + '\'' +
                ", all='" + all + '\'' +
                ", color='" + color + '\'' +
                ", number=" + number +
                ", QFriend='" + QFriend + '\'' +
                ", summary='" + summary + '\'';
    }
}
