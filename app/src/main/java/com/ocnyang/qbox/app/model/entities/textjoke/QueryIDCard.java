package com.ocnyang.qbox.app.model.entities.textjoke;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/17 17:40.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class QueryIDCard {

    /**
     * resultcode : 200
     * reason : 成功的返回
     * result : {"area":"河南省驻马店地区汝南县","sex":"男","birthday":"1993年12月20日","verify":""}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private ResultBean result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
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

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * area : 河南省驻马店地区汝南县
         * sex : 男
         * birthday : 1993年12月20日
         * verify :
         */

        private String area;
        private String sex;
        private String birthday;
        private String verify;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getVerify() {
            return verify;
        }

        public void setVerify(String verify) {
            this.verify = verify;
        }


        @Override
        public String toString() {
            return "查询结果：" +"\n\n"+
                    "地区：" + area + "\n" +
                    "性别：" + sex + "\n" +
                    "出生日期：" + birthday + "\n" +
                    "校验：" + verify;
        }
    }
}
