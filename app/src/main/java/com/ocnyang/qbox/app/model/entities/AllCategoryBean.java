package com.ocnyang.qbox.app.model.entities;

import java.util.List;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2018/1/19 16:33.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class AllCategoryBean {

    /**
     * msg : success
     * result : [{"cid":"1","name":"时尚"},{"cid":"2","name":"热点"},{"cid":"3","name":"健康"},{"cid":"5","name":"百科"},{"cid":"7","name":"娱乐"},{"cid":"8","name":"美文"},{"cid":"9","name":"旅行"},{"cid":"10","name":"媒体达人"},{"cid":"11","name":"搞笑"},{"cid":"12","name":"影视音乐"},{"cid":"13","name":"互联网"},{"cid":"14","name":"文史"},{"cid":"15","name":"金融"},{"cid":"16","name":"体育"},{"cid":"17","name":"游戏"},{"cid":"18","name":"两性"},{"cid":"19","name":"社交交友"},{"cid":"20","name":"女人"},{"cid":"23","name":"购物"},{"cid":"24","name":"美女"},{"cid":"25","name":"微信技巧"},{"cid":"26","name":"星座"},{"cid":"27","name":"美食"},{"cid":"28","name":"教育"},{"cid":"29","name":"职场"},{"cid":"30","name":"酷品"},{"cid":"31","name":"母婴"},{"cid":"32","name":"摄影"},{"cid":"33","name":"创投"},{"cid":"34","name":"典藏"},{"cid":"35","name":"家装"},{"cid":"36","name":"汽车"},{"cid":"37","name":"段子"}]
     * retCode : 200
     */

    private String msg;
    private String retCode;
    private List<ResultBean> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * cid : 1
         * name : 时尚
         */

        private String cid;
        private String name;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
