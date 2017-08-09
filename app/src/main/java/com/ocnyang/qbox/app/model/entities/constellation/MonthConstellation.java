package com.ocnyang.qbox.app.model.entities.constellation;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/20 11:24.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class MonthConstellation extends BaseConstellation{


    /**
     * month : 3
     * all : 对钱财变得没有概念，模模糊糊，小心这段时间被洗脑。金星和木星在旅行、学习宫，开始反思学习的意义。与家人之间的沟通也会促使你思考。火星进入金牛之后，会有点懒，想在家呆着，或许是做好吃的，享受美食。

     * happyMagic :
     */

    private int month;
    private String all;
    private String happyMagic;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getHappyMagic() {
        return happyMagic;
    }

    public void setHappyMagic(String happyMagic) {
        this.happyMagic = happyMagic;
    }



    @Override
    public String toString() {
        return "month=" + month +
                ", all='" + all + '\'' +
                ", happyMagic='" + happyMagic + '\'';
    }
}
