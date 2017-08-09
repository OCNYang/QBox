package com.ocnyang.qbox.app.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/4/6 09:51.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class City implements Parcelable {

    private List<HeWeather5Bean> HeWeather5;

    public List<HeWeather5Bean> getHeWeather5() {
        return HeWeather5;
    }

    public void setHeWeather5(List<HeWeather5Bean> HeWeather5) {
        this.HeWeather5 = HeWeather5;
    }

    public static class HeWeather5Bean implements Parcelable {
        /**
         * basic : {"city":"北京","cnty":"中国","id":"CN101010100","lat":"39.904000","lon":"116.391000","prov":"直辖市"}
         * status : ok
         */

        private BasicBean basic;
        private String status;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public static class BasicBean implements Parcelable {
            /**
             * city : 北京
             * cnty : 中国
             * id : CN101010100
             * lat : 39.904000
             * lon : 116.391000
             * prov : 直辖市
             */

            private String city;
            private String cnty;
            private String id;
            private String lat;
            private String lon;
            private String prov;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getProv() {
                return prov;
            }

            public void setProv(String prov) {
                this.prov = prov;
            }

            @Override
            public String toString() {
                return "BasicBean{" +
                        "city='" + city + '\'' +
                        ", cnty='" + cnty + '\'' +
                        ", id='" + id + '\'' +
                        ", lat='" + lat + '\'' +
                        ", lon='" + lon + '\'' +
                        ", prov='" + prov + '\'' +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.city);
                dest.writeString(this.cnty);
                dest.writeString(this.id);
                dest.writeString(this.lat);
                dest.writeString(this.lon);
                dest.writeString(this.prov);
            }

            public BasicBean() {
            }

            protected BasicBean(Parcel in) {
                this.city = in.readString();
                this.cnty = in.readString();
                this.id = in.readString();
                this.lat = in.readString();
                this.lon = in.readString();
                this.prov = in.readString();
            }

            public static final Parcelable.Creator<BasicBean> CREATOR = new Parcelable.Creator<BasicBean>() {
                @Override
                public BasicBean createFromParcel(Parcel source) {
                    return new BasicBean(source);
                }

                @Override
                public BasicBean[] newArray(int size) {
                    return new BasicBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.basic, flags);
            dest.writeString(this.status);
        }

        public HeWeather5Bean() {
        }

        protected HeWeather5Bean(Parcel in) {
            this.basic = in.readParcelable(BasicBean.class.getClassLoader());
            this.status = in.readString();
        }

        public static final Parcelable.Creator<HeWeather5Bean> CREATOR = new Parcelable.Creator<HeWeather5Bean>() {
            @Override
            public HeWeather5Bean createFromParcel(Parcel source) {
                return new HeWeather5Bean(source);
            }

            @Override
            public HeWeather5Bean[] newArray(int size) {
                return new HeWeather5Bean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.HeWeather5);
    }

    public City() {
    }

    protected City(Parcel in) {
        this.HeWeather5 = in.createTypedArrayList(HeWeather5Bean.CREATOR);
    }

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
