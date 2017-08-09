package com.ocnyang.qbox.app.model.entities;

import java.util.List;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/16 10:11.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class NewsItem {

    /**
     * reason : 成功的返回
     * result : {"stat":"1","data":[{"uniquekey":"bc720bfa92d8b2696db53b5070155e4c","title":"李小璐贾静雯殷旭 娱乐圈里的\u201c炫女狂魔\u201d","date":"2017-02-17 16:29","category":"头条","author_name":"腾讯娱乐","url":"http://mini.eastday.com/mobile/170217162916749.html","thumbnail_pic_s":"http://05.imgmini.eastday.com/mobile/20170217/20170217162916_d6261bca7e12168d04264ed81fc7dc79_1_mwpm_03200403.jpeg","thumbnail_pic_s02":"http://05.imgmini.eastday.com/mobile/20170217/20170217162916_d6261bca7e12168d04264ed81fc7dc79_2_mwpm_03200403.jpeg","thumbnail_pic_s03":"http://05.imgmini.eastday.com/mobile/20170217/20170217162916_d6261bca7e12168d04264ed81fc7dc79_3_mwpm_03200403.jpeg"},{"uniquekey":"537460c489254bd5bf68c65261decb2b","title":"工信部谈钢铁去产能：去年钢铁企业利润同比增2.02倍","date":"2017-02-17 17:17","category":"头条","author_name":"中国新闻网","url":"http://mini.eastday.com/mobile/170217171736444.html","thumbnail_pic_s":"http://07.imgmini.eastday.com/mobile/20170217/20170217171736_f011a039101c623569fb0a6f6310697c_1_mwpm_03200403.jpeg"}]}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

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
         * stat : 1
         * data : [{"uniquekey":"bc720bfa92d8b2696db53b5070155e4c","title":"李小璐贾静雯殷旭 娱乐圈里的\u201c炫女狂魔\u201d","date":"2017-02-17 16:29","category":"头条","author_name":"腾讯娱乐","url":"http://mini.eastday.com/mobile/170217162916749.html","thumbnail_pic_s":"http://05.imgmini.eastday.com/mobile/20170217/20170217162916_d6261bca7e12168d04264ed81fc7dc79_1_mwpm_03200403.jpeg","thumbnail_pic_s02":"http://05.imgmini.eastday.com/mobile/20170217/20170217162916_d6261bca7e12168d04264ed81fc7dc79_2_mwpm_03200403.jpeg","thumbnail_pic_s03":"http://05.imgmini.eastday.com/mobile/20170217/20170217162916_d6261bca7e12168d04264ed81fc7dc79_3_mwpm_03200403.jpeg"},{"uniquekey":"537460c489254bd5bf68c65261decb2b","title":"工信部谈钢铁去产能：去年钢铁企业利润同比增2.02倍","date":"2017-02-17 17:17","category":"头条","author_name":"中国新闻网","url":"http://mini.eastday.com/mobile/170217171736444.html","thumbnail_pic_s":"http://07.imgmini.eastday.com/mobile/20170217/20170217171736_f011a039101c623569fb0a6f6310697c_1_mwpm_03200403.jpeg"}]
         */

        private String stat;
        private List<DataBean> data;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * uniquekey : bc720bfa92d8b2696db53b5070155e4c
             * title : 李小璐贾静雯殷旭 娱乐圈里的“炫女狂魔”
             * date : 2017-02-17 16:29
             * category : 头条
             * author_name : 腾讯娱乐
             * url : http://mini.eastday.com/mobile/170217162916749.html
             * thumbnail_pic_s : http://05.imgmini.eastday.com/mobile/20170217/20170217162916_d6261bca7e12168d04264ed81fc7dc79_1_mwpm_03200403.jpeg
             * thumbnail_pic_s02 : http://05.imgmini.eastday.com/mobile/20170217/20170217162916_d6261bca7e12168d04264ed81fc7dc79_2_mwpm_03200403.jpeg
             * thumbnail_pic_s03 : http://05.imgmini.eastday.com/mobile/20170217/20170217162916_d6261bca7e12168d04264ed81fc7dc79_3_mwpm_03200403.jpeg
             */

            private String uniquekey;
            private String title;
            private String date;
            private String category;
            private String author_name;
            private String url;
            private String thumbnail_pic_s;
            private String thumbnail_pic_s02;
            private String thumbnail_pic_s03;

            public String getUniquekey() {
                return uniquekey;
            }

            public void setUniquekey(String uniquekey) {
                this.uniquekey = uniquekey;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getAuthor_name() {
                return author_name;
            }

            public void setAuthor_name(String author_name) {
                this.author_name = author_name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getThumbnail_pic_s() {
                return thumbnail_pic_s;
            }

            public void setThumbnail_pic_s(String thumbnail_pic_s) {
                this.thumbnail_pic_s = thumbnail_pic_s;
            }

            public String getThumbnail_pic_s02() {
                return thumbnail_pic_s02;
            }

            public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
                this.thumbnail_pic_s02 = thumbnail_pic_s02;
            }

            public String getThumbnail_pic_s03() {
                return thumbnail_pic_s03;
            }

            public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
                this.thumbnail_pic_s03 = thumbnail_pic_s03;
            }
        }
    }
}
