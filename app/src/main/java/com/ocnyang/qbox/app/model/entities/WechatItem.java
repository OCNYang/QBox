package com.ocnyang.qbox.app.model.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/24 13:10.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/

public class WechatItem implements Parcelable {

    /**
     * msg : success
     * result : {}],"total":4846}
     * retCode : 200
     */

    private String msg;
    private ResultBean result;
    private String retCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public static class ResultBean implements Parcelable {
        /**
         * curPage : 1
         * list : [{}]
         * total : 4846
         */

        private int curPage;
        private int total;
        private List<ListBean> list;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements MultiItemEntity, Parcelable {
            /**
             * cid : 8
             * hitCount : 51111
             * id : FDA1C88F4814BA4D3D17B31B5E92D0EB
             * pubTime : 2017-07-18
             * sourceUrl : http://mp.weixin.qq.com/s?__biz=MjM5ODA0NTc4MA==&mid=2652749935&idx=1&sn=70152649d7b0788aa17d9a3090917d3d&chksm=bd39d1548a4e584241a24b99c4b7a8db77cbb5ba6b3ef5a6fed333d6f5e1f6c3c1fee5b4a147&scene=27#wechat_redirect
             * subTitle : 如果有人敢这么掰我的嘴，我绝对一口咬回去。
             * thumbnails : http://mmbiz.qpic.cn/mmbiz_jpg/HhorckbERhjeOvXRm684W3wvzUPu9j1c6sDibXIMZJaEqn9OAiamTMFPUFgUVs1dLB9gcdRxZFBC25vnxXRI3ZBg/0?wx_fmt=jpeg
             * title : 原创 你挑媳妇的样子，就像在挑牲口
             */

            public static final int STYLE_BIG = 1;
            public static final int STYLE_SMALL = 0;

            public static final int STYLE_SMALL_SPAN_SIZE = 1;
            public static final int STYLE_BIG_SPAN_SIZE = 2;

            private String cid;
            private String hitCount;
            private String id;
            private String pubTime;
            private String sourceUrl;
            private String subTitle;
            private String thumbnails;
            private String title;

            private int itemType = 0;
            private int spansize = 1;

            public int getSpansize() {
                return spansize;
            }

            public void setSpansize(int spansize) {
                this.spansize = spansize;
            }

            @Override
            public int getItemType() {
                return itemType;
            }

            public void setItemType(int itemType) {
                if (itemType == 1 || itemType == 0) {
                    this.itemType = itemType;
                } else {
                    this.itemType = 0;
                }
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getHitCount() {
                return hitCount;
            }

            public void setHitCount(String hitCount) {
                this.hitCount = hitCount;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPubTime() {
                return pubTime;
            }

            public void setPubTime(String pubTime) {
                this.pubTime = pubTime;
            }

            public String getSourceUrl() {
                return sourceUrl;
            }

            public void setSourceUrl(String sourceUrl) {
                this.sourceUrl = sourceUrl;
            }

            public String getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }

            public String getThumbnails() {
                if ((!TextUtils.isEmpty(thumbnails)) && thumbnails.contains("$")) {
                    for (String string : thumbnails.split("\\$", 3)) {
                        if (!TextUtils.isEmpty(string)) {
                            return string;
                        }
                    }
                }
                return thumbnails;
            }

            public void setThumbnails(String thumbnails) {
                this.thumbnails = thumbnails;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.cid);
                dest.writeString(this.hitCount);
                dest.writeString(this.id);
                dest.writeString(this.pubTime);
                dest.writeString(this.sourceUrl);
                dest.writeString(this.subTitle);
                dest.writeString(this.thumbnails);
                dest.writeString(this.title);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.cid = in.readString();
                this.hitCount = in.readString();
                this.id = in.readString();
                this.pubTime = in.readString();
                this.sourceUrl = in.readString();
                this.subTitle = in.readString();
                this.thumbnails = in.readString();
                this.title = in.readString();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.curPage);
            dest.writeInt(this.total);
            dest.writeList(this.list);
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.curPage = in.readInt();
            this.total = in.readInt();
            this.list = new ArrayList<ListBean>();
            in.readList(this.list, ListBean.class.getClassLoader());
        }

        public static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel source) {
                return new ResultBean(source);
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg);
        dest.writeParcelable(this.result, flags);
        dest.writeString(this.retCode);
    }

    public WechatItem() {
    }

    protected WechatItem(Parcel in) {
        this.msg = in.readString();
        this.result = in.readParcelable(ResultBean.class.getClassLoader());
        this.retCode = in.readString();
    }

    public static final Parcelable.Creator<WechatItem> CREATOR = new Parcelable.Creator<WechatItem>() {
        @Override
        public WechatItem createFromParcel(Parcel source) {
            return new WechatItem(source);
        }

        @Override
        public WechatItem[] newArray(int size) {
            return new WechatItem[size];
        }
    };
}