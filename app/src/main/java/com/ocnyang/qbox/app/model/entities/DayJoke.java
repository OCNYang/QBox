package com.ocnyang.qbox.app.model.entities;

import java.util.List;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/3/3 15:21.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class DayJoke extends JvHeBaseBean {

    /**
     * result : {"data":[{"content":"入夜，跟老婆一起回忆热恋时的时光。　　老婆：那个时候我在你心里什么印象啊？　　我：一个浑身发光长着翅膀的---天使。　　老婆：那现在呢？还发光吗？还长着翅膀吗？　　我：嗯。　　老婆一脸的幸福。　　我：不过不是天使了，而是----萤火虫！　　老婆：@#%$&","hashId":"7577b53c0f5f28127b68ec44fe9af45a","unixtime":1488525230,"updatetime":"2017-03-03 15:13:50"}]}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * content : 入夜，跟老婆一起回忆热恋时的时光。　　老婆：那个时候我在你心里什么印象啊？　　我：一个浑身发光长着翅膀的---天使。　　老婆：那现在呢？还发光吗？还长着翅膀吗？　　我：嗯。　　老婆一脸的幸福。　　我：不过不是天使了，而是----萤火虫！　　老婆：@#%$&
             * hashId : 7577b53c0f5f28127b68ec44fe9af45a
             * unixtime : 1488525230
             * updatetime : 2017-03-03 15:13:50
             */

            private String content;
            private String hashId;
            private int unixtime;
            private String updatetime;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getHashId() {
                return hashId;
            }

            public void setHashId(String hashId) {
                this.hashId = hashId;
            }

            public int getUnixtime() {
                return unixtime;
            }

            public void setUnixtime(int unixtime) {
                this.unixtime = unixtime;
            }

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }
        }
    }
}
