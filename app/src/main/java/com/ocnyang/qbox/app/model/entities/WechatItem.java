package com.ocnyang.qbox.app.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

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

    public static class ResultBean implements Parcelable {


        private int totalPage;
        private int ps;
        private int pno;
        private List<ListBean> list;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPs() {
            return ps;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }

        public int getPno() {
            return pno;
        }

        public void setPno(int pno) {
            this.pno = pno;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements MultiItemEntity, Parcelable {
            /**
             * id : wechat_20170222006600
             * title : 不死的基因
             * source : 大科技
             * firstImg : http://zxpic.gtimg.com/infonew/0/wechat_pics_-13427722.jpg/640
             * mark :
             * url : http://v.juhe.cn/weixin/redirect?wid=wechat_20170222006600
             */
            public static final int STYLE_BIG = 1;
            public static final int STYLE_SMALL = 0;

            public static final int STYLE_SMALL_SPAN_SIZE = 1;
            public static final int STYLE_BIG_SPAN_SIZE = 2;

            private String id;
            private String title;
            private String source;
            private String firstImg;
            private String mark;
            private String url;

            private int itemType = 0;
            private int spansize = 1;

            public int getSpansize() {
                return spansize;
            }

            public void setSpansize(int spansize) {
                this.spansize = spansize;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getFirstImg() {
                return firstImg;
            }

            public void setFirstImg(String firstImg) {
                this.firstImg = firstImg;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public int getItemType() {
                return itemType;
            }

            public void setItemType(int itemType) {
                if (itemType == 1 || itemType == 0) {
                    this.itemType = itemType;
                }else {
                    this.itemType = 0;
                }
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.title);
                dest.writeString(this.source);
                dest.writeString(this.firstImg);
                dest.writeString(this.mark);
                dest.writeString(this.url);
                dest.writeInt(this.itemType);
                dest.writeInt(this.spansize);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.id = in.readString();
                this.title = in.readString();
                this.source = in.readString();
                this.firstImg = in.readString();
                this.mark = in.readString();
                this.url = in.readString();
                this.itemType = in.readInt();
                this.spansize = in.readInt();
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
            dest.writeInt(this.totalPage);
            dest.writeInt(this.ps);
            dest.writeInt(this.pno);
            dest.writeList(this.list);
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.totalPage = in.readInt();
            this.ps = in.readInt();
            this.pno = in.readInt();
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
        dest.writeString(this.reason);
        dest.writeParcelable(this.result, flags);
        dest.writeInt(this.error_code);
    }

    public WechatItem() {
    }

    protected WechatItem(Parcel in) {
        this.reason = in.readString();
        this.result = in.readParcelable(ResultBean.class.getClassLoader());
        this.error_code = in.readInt();
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
