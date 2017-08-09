package com.ocnyang.qbox.app.model.entities.constellation;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/20 11:13.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class BaseConstellation extends RootConstellation{

    /**
     * weekth : 13
     * health : 健康：本周水瓶人的健康运平稳。 作者：星言，forseiya
     * job : 求职：本周水瓶人在公司中会意外的从一些高层次的人物中得到一些关于新职位的消息。
     * love : 恋爱：本周单身的水瓶人更容易宅在家里，有伴的水瓶人本周可能不得不应付一些恼人的亲戚。
     * money : 财运：本周水瓶人的财运平稳，会从自己喜欢的嗜好中获得一笔收益。
     * work : 工作：本周水瓶人的工作上回容易说话比较直接，建议工作时候火气不要这么大。
     */

    private String health;
    private String love;
    private String money;
    private String work;

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    @Override
    public String toString() {
        return  "health='" + health + '\'' +
                ", love='" + love + '\'' +
                ", money='" + money + '\'' +
                ", work='" + work + '\'';
    }
}
