package com.ocnyang.qbox.app.model.entities;

import java.util.List;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/3/3 13:07.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class FindBg {

    /**
     * images : [{"startdate":"20170302","fullstartdate":"201703021600","enddate":"20170303","url":"/az/hprichbg/rb/SpringbokHerd_ZH-CN11603112082_1920x1080.jpg","urlbase":"/az/hprichbg/rb/SpringbokHerd_ZH-CN11603112082","copyright":"在南非卡拉哈里沙漠地区的一群雄性跳羚(© Minden Pictures/Masterfile)","copyrightlink":"http://www.bing.com/search?q=%E8%B7%B3%E7%BE%9A&form=hpcapt&mkt=zh-cn","quiz":"/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20170302_SpringbokHerd%22&FORM=HPQUIZ","wp":true,"hsh":"5dd7d323df4e79f5f1c383876d027bb1","drk":1,"top":1,"bot":1,"hs":[]},{"startdate":"20170301","fullstartdate":"201703011600","enddate":"20170302","url":"/az/hprichbg/rb/Shiprock_ZH-CN11237156651_1920x1080.jpg","urlbase":"/az/hprichbg/rb/Shiprock_ZH-CN11237156651","copyright":"纳瓦霍族保留地内的船岩，新墨西哥州 (© Wild Horizon/Getty Images)","copyrightlink":"http://www.bing.com/search?q=%E8%88%B9%E5%B2%A9&form=hpcapt&mkt=zh-cn","quiz":"/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20170301_Shiprock%22&FORM=HPQUIZ","wp":true,"hsh":"29502c464d6552ef862b911fbf0480f4","drk":1,"top":1,"bot":1,"hs":[]},{"startdate":"20170228","fullstartdate":"201702281600","enddate":"20170301","url":"/az/hprichbg/rb/SommeBay_ZH-CN11043403486_1920x1080.jpg","urlbase":"/az/hprichbg/rb/SommeBay_ZH-CN11043403486","copyright":"索姆河河口，法国 (© Philippe Frutier/plainpicture)","copyrightlink":"http://www.bing.com/search?q=%E7%B4%A2%E5%A7%86%E6%B2%B3&form=hpcapt&mkt=zh-cn","quiz":"/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20170228_SommeBay%22&FORM=HPQUIZ","wp":true,"hsh":"4889485c73bb4fb52f9b6fc432a07ac0","drk":1,"top":1,"bot":1,"hs":[]},{"startdate":"20170227","fullstartdate":"201702271600","enddate":"20170228","url":"/az/hprichbg/rb/BrassBandTrumpet_ZH-CN8703910231_1920x1080.jpg","urlbase":"/az/hprichbg/rb/BrassBandTrumpet_ZH-CN8703910231","copyright":"一位铜管乐队成员正在为国王嘉年华活动吹奏小号，新奥尔良 (© Gerald Herbert/AP Photo)","copyrightlink":"http://www.bing.com/search?q=King's+Day+Celebration&form=hpcapt&mkt=zh-cn","quiz":"/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20170227_BrassBandTrumpet%22&FORM=HPQUIZ","wp":false,"hsh":"4cee43606858bd1d1975a12e8be41b1a","drk":1,"top":1,"bot":1,"hs":[]},{"startdate":"20170226","fullstartdate":"201702261600","enddate":"20170227","url":"/az/hprichbg/rb/RiverOtters_ZH-CN9287285757_1920x1080.jpg","urlbase":"/az/hprichbg/rb/RiverOtters_ZH-CN9287285757","copyright":"黄石国家公园内的北美水獭，美国怀俄明州 (© mlharing/istock/Getty Images)","copyrightlink":"http://www.bing.com/search?q=%E5%8C%97%E7%BE%8E%E6%B0%B4%E7%8D%AD&form=hpcapt&mkt=zh-cn","quiz":"/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20170226_RiverOtters%22&FORM=HPQUIZ","wp":false,"hsh":"ad618a120991d903dca5c4bb5499e075","drk":1,"top":1,"bot":1,"hs":[]},{"startdate":"20170225","fullstartdate":"201702251600","enddate":"20170226","url":"/az/hprichbg/rb/GriffithPark_ZH-CN9871772537_1920x1080.jpg","urlbase":"/az/hprichbg/rb/GriffithPark_ZH-CN9871772537","copyright":"加利福尼亚州洛杉矶格里菲斯天文台，美国 (© Walter Bibikow/Getty Images)","copyrightlink":"http://www.bing.com/search?q=%E6%A0%BC%E9%87%8C%E8%8F%B2%E6%96%AF%E5%A4%A9%E6%96%87%E5%8F%B0&form=hpcapt&mkt=zh-cn","quiz":"/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20170225_GriffithPark%22&FORM=HPQUIZ","wp":false,"hsh":"05951cee899cb75f4f465710d1824b85","drk":1,"top":1,"bot":1,"hs":[]},{"startdate":"20170224","fullstartdate":"201702241600","enddate":"20170225","url":"/az/hprichbg/rb/Hoatzin_ZH-CN6642664963_1920x1080.jpg","urlbase":"/az/hprichbg/rb/Hoatzin_ZH-CN6642664963","copyright":"两只麝雉栖息枝头，巴西 (© Morten Ross/500px)","copyrightlink":"http://www.bing.com/search?q=%E9%BA%9D%E9%9B%89&form=hpcapt&mkt=zh-cn","quiz":"/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20170224_Hoatzin%22&FORM=HPQUIZ","wp":true,"hsh":"fe1371a1dcc3167b54e28a5c5556874d","drk":1,"top":1,"bot":1,"hs":[]},{"startdate":"20170223","fullstartdate":"201702231600","enddate":"20170224","url":"/az/hprichbg/rb/ShengshanIsland_ZH-CN14229927013_1920x1080.jpg","urlbase":"/az/hprichbg/rb/ShengshanIsland_ZH-CN14229927013","copyright":"浙江嵊山岛被遗弃的渔村 (© VCG/Getty Images)","copyrightlink":"http://www.bing.com/search?q=%E8%A2%AB%E9%81%97%E5%BC%83%E7%9A%84%E7%BB%9D%E7%BE%8E%E6%99%AF%E7%82%B9&form=hpcapt&mkt=zh-cn","quiz":"/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20170223_ShengshanIsland%22&FORM=HPQUIZ","wp":true,"hsh":"afc114b2604fefccad514121c3de1b33","drk":1,"top":1,"bot":1,"hs":[]}]
     * tooltips : {"loading":"正在加载...","previous":"上一个图像","next":"下一个图像","walle":"此图片不能下载用作壁纸。","walls":"下载今日美图。仅限用作桌面壁纸。"}
     */

    private List<ImagesBean> images;

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
        /**
         * startdate : 20170302
         * fullstartdate : 201703021600
         * enddate : 20170303
         * url : /az/hprichbg/rb/SpringbokHerd_ZH-CN11603112082_1920x1080.jpg
         * urlbase : /az/hprichbg/rb/SpringbokHerd_ZH-CN11603112082
         * copyright : 在南非卡拉哈里沙漠地区的一群雄性跳羚(© Minden Pictures/Masterfile)
         * copyrightlink : http://www.bing.com/search?q=%E8%B7%B3%E7%BE%9A&form=hpcapt&mkt=zh-cn
         * quiz : /search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20170302_SpringbokHerd%22&FORM=HPQUIZ
         * wp : true
         * hsh : 5dd7d323df4e79f5f1c383876d027bb1
         * drk : 1
         * top : 1
         * bot : 1
         * hs : []
         */

        private String startdate;
        private String fullstartdate;
        private String enddate;
        private String url;
        private String urlbase;
        private String copyright;
        private String copyrightlink;
        private String quiz;

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getFullstartdate() {
            return fullstartdate;
        }

        public void setFullstartdate(String fullstartdate) {
            this.fullstartdate = fullstartdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlbase() {
            return urlbase;
        }

        public void setUrlbase(String urlbase) {
            this.urlbase = urlbase;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getCopyrightlink() {
            return copyrightlink;
        }

        public void setCopyrightlink(String copyrightlink) {
            this.copyrightlink = copyrightlink;
        }

        public String getQuiz() {
            return quiz;
        }

        public void setQuiz(String quiz) {
            this.quiz = quiz;
        }

    }
}
