package com.ocnyang.qbox.app.model.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/29 10:19.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class ChinaCalendar {

    /**
     * error_code : 0
     * reason : Success
     * result : {"data":{"holiday":"元旦","avoid":"破土.安葬.行丧.开生坟.","animalsYear":"马","desc":"1月1日至3日放假调休，共3天。1月4日（星期日）上班。","weekday":"星期四","suit":"订盟.纳采.造车器.祭祀.祈福.出行.安香.修造.动土.上梁.开市.交易.立券.移徙.入宅.会亲友.安机械.栽种.纳畜.造屋.起基.安床.造畜椆栖.","lunarYear":"甲午年","lunar":"十一月十一","year-month":"2015-1","date":"2015-1-1"}}
     */

    private int error_code;
    private String reason;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * data : {"holiday":"元旦","avoid":"破土.安葬.行丧.开生坟.","animalsYear":"马","desc":"1月1日至3日放假调休，共3天。1月4日（星期日）上班。","weekday":"星期四","suit":"订盟.纳采.造车器.祭祀.祈福.出行.安香.修造.动土.上梁.开市.交易.立券.移徙.入宅.会亲友.安机械.栽种.纳畜.造屋.起基.安床.造畜椆栖.","lunarYear":"甲午年","lunar":"十一月十一","year-month":"2015-1","date":"2015-1-1"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean implements Serializable{
            /**
             * holiday : 元旦
             * avoid : 破土.安葬.行丧.开生坟.
             * animalsYear : 马
             * desc : 1月1日至3日放假调休，共3天。1月4日（星期日）上班。
             * weekday : 星期四
             * suit : 订盟.纳采.造车器.祭祀.祈福.出行.安香.修造.动土.上梁.开市.交易.立券.移徙.入宅.会亲友.安机械.栽种.纳畜.造屋.起基.安床.造畜椆栖.
             * lunarYear : 甲午年
             * lunar : 十一月十一
             * year-month : 2015-1
             * date : 2015-1-1
             */

            private String holiday;
            private String avoid;
            private String animalsYear;
            private String desc;
            private String weekday;
            private String suit;
            private String lunarYear;
            private String lunar;
            @SerializedName("year-month")
            private String yearmonth;
            private String date;

            public String getHoliday() {
                return holiday;
            }

            public void setHoliday(String holiday) {
                this.holiday = holiday;
            }

            public String getAvoid() {
                return avoid;
            }

            public void setAvoid(String avoid) {
                this.avoid = avoid;
            }

            public String getAnimalsYear() {
                return animalsYear;
            }

            public void setAnimalsYear(String animalsYear) {
                this.animalsYear = animalsYear;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getWeekday() {
                return weekday;
            }

            public void setWeekday(String weekday) {
                this.weekday = weekday;
            }

            public String getSuit() {
                return suit;
            }

            public void setSuit(String suit) {
                this.suit = suit;
            }

            public String getLunarYear() {
                return lunarYear;
            }

            public void setLunarYear(String lunarYear) {
                this.lunarYear = lunarYear;
            }

            public String getLunar() {
                return lunar;
            }

            public void setLunar(String lunar) {
                this.lunar = lunar;
            }

            public String getYearmonth() {
                return yearmonth;
            }

            public void setYearmonth(String yearmonth) {
                this.yearmonth = yearmonth;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            @Override
            public String toString() {
                return "DataBean{" +
                        "holiday='" + holiday + '\'' +
                        ", avoid='" + avoid + '\'' +
                        ", animalsYear='" + animalsYear + '\'' +
                        ", desc='" + desc + '\'' +
                        ", weekday='" + weekday + '\'' +
                        ", suit='" + suit + '\'' +
                        ", lunarYear='" + lunarYear + '\'' +
                        ", lunar='" + lunar + '\'' +
                        ", yearmonth='" + yearmonth + '\'' +
                        ", date='" + date + '\'' +
                        '}';
            }
        }
    }
}
