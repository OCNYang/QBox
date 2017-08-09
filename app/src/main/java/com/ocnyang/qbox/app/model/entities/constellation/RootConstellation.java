package com.ocnyang.qbox.app.model.entities.constellation;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/20 11:16.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class RootConstellation {
    /**
     * name : 水瓶座
     * weekth : 13
     * date : 2017年03月27日-2017年04月02日
     * health : 健康：本周水瓶人的健康运平稳。 作者：星言，forseiya
     * job : 求职：本周水瓶人在公司中会意外的从一些高层次的人物中得到一些关于新职位的消息。
     * love : 恋爱：本周单身的水瓶人更容易宅在家里，有伴的水瓶人本周可能不得不应付一些恼人的亲戚。
     * money : 财运：本周水瓶人的财运平稳，会从自己喜欢的嗜好中获得一笔收益。
     * work : 工作：本周水瓶人的工作上回容易说话比较直接，建议工作时候火气不要这么大。
     * resultcode : 200
     * error_code : 0
     */

    private String name;
    private String date;
    private String resultcode;
    private int error_code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    @Override
    public String toString() {
        return "查询结果：" +"\n\n"+
                "name='" + name + '\'' +
                ", date='" + date + '\'';
    }
}
