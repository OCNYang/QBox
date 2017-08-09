package com.ocnyang.qbox.app.model.entities.textjoke;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/17 17:32.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class QueryQQ{

    /**
     * error_code : 0
     * reason : success
     * result : {"data":{"conclusion":"[中吉]中吉之数，进退保守，生意安稳，成就可期","analysis":"温和平安艺才高，努力前途福运来，文笔奇绝仁德高，务实稳健掌门人。"}}
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
         * data : {"conclusion":"[中吉]中吉之数，进退保守，生意安稳，成就可期","analysis":"温和平安艺才高，努力前途福运来，文笔奇绝仁德高，务实稳健掌门人。"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * conclusion : [中吉]中吉之数，进退保守，生意安稳，成就可期
             * analysis : 温和平安艺才高，努力前途福运来，文笔奇绝仁德高，务实稳健掌门人。
             */

            private String conclusion;
            private String analysis;

            public String getConclusion() {
                return conclusion;
            }

            public void setConclusion(String conclusion) {
                this.conclusion = conclusion;
            }

            public String getAnalysis() {
                return analysis;
            }

            public void setAnalysis(String analysis) {
                this.analysis = analysis;
            }

            @Override
            public String toString() {
                return "查询结果：" +"\n\n"+
                        "结论：" + conclusion + "\n\n" +
                        "分析：" + analysis;
            }
        }
    }
}
