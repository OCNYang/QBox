package com.ocnyang.qbox.app.network;

import android.os.Environment;
import android.util.Log;

import com.ocnyang.qbox.app.network.api.AllCategoryApi;
import com.ocnyang.qbox.app.network.api.ChinaCalendarApi;
import com.ocnyang.qbox.app.network.api.CityApi;
import com.ocnyang.qbox.app.network.api.ConstellationApi;
import com.ocnyang.qbox.app.network.api.ConstellationsApi;
import com.ocnyang.qbox.app.network.api.DayJokeApi;
import com.ocnyang.qbox.app.network.api.DemoApi;
import com.ocnyang.qbox.app.network.api.FindBgApi;
import com.ocnyang.qbox.app.network.api.ImgJokeApi;
import com.ocnyang.qbox.app.network.api.NewsApi;
import com.ocnyang.qbox.app.network.api.QueryInfoApi;
import com.ocnyang.qbox.app.network.api.TextJokeApi;
import com.ocnyang.qbox.app.network.api.WechatApi;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*************************************************************
 * Created by OCN.Yang           * * * *   * * * *   *     * *
 * Time:2016/10/10 16:49.        *     *   *         * *   * *
 * Email address:yangocn@163.com *     *   *         *   * * *
 * Web site:www.ocnyang.com      * * * *   * * * *   *     * *
 *************************************************************/


public class Network {

    public static final String ROOT_URL = "http://v.juhe.cn/";
    public static final String MOB_ROOT_URL = "http://apicloud.mob.com/";

    private static DemoApi demoApi;
    private static NewsApi sNewsApi;
    private static WechatApi mWechatApi;
    private static AllCategoryApi mAllCategoryApi;
    private static FindBgApi sFindBgApi;
    private static DayJokeApi sDayJokeApi;
    private static ConstellationApi sConstellationApi;
    private static TextJokeApi sTextJokeApi;
    private static TextJokeApi sRandomTextJokeApi;
    private static ImgJokeApi sRandomImgJokeApi;
    private static ImgJokeApi sNewImgJokeApi;

    private static QueryInfoApi sQueryTelApi;
    private static QueryInfoApi sQueryQQApi;
    private static QueryInfoApi sQueryIDCardApi;

    public static ConstellationsApi mDayConstellationsApi;
    public static ConstellationsApi mWeekConstellationsApi;
    public static ConstellationsApi mMonthConstellationsApi;
    public static ConstellationsApi mYearConstellationsApi;
    public static ChinaCalendarApi sChinaCalendarApi;
    public static CityApi sCityApi;

    private static final long cacheSize = 1024 * 1024 * 20;// 缓存文件最大限制大小20M
    private static String cacheDirectory = Environment.getExternalStorageDirectory() + "/okttpcaches"; // 设置缓存文件路径
    private static Cache cache = new Cache(new File(cacheDirectory), cacheSize);  //
    private static final OkHttpClient cacheClient;

    static {
        //如果无法生存缓存文件目录，检测权限使用已经加上，检测手机是否把文件读写权限禁止了
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(8, TimeUnit.SECONDS); // 设置连接超时时间
        builder.writeTimeout(8, TimeUnit.SECONDS);// 设置写入超时时间
        builder.readTimeout(8, TimeUnit.SECONDS);// 设置读取数据超时时间
        builder.retryOnConnectionFailure(true);// 设置进行连接失败重试
        builder.cache(cache);// 设置缓存
        cacheClient = builder.build();
    }

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static DemoApi getGankApi() {
        if (demoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            demoApi = retrofit.create(DemoApi.class);
        }
        return demoApi;
    }

    public static NewsApi getNewsApi() {
        if (sNewsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sNewsApi = retrofit.create(NewsApi.class);
        }
        Log.e("oooooo","getNewsApi");
        return sNewsApi;
    }

    public static AllCategoryApi getAllCategoryApi() {
        if (mAllCategoryApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(MOB_ROOT_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mAllCategoryApi = retrofit.create(AllCategoryApi.class);
        }
        return mAllCategoryApi;
    }

    public static WechatApi getWechatApi() {
        if (mWechatApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(MOB_ROOT_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mWechatApi = retrofit.create(WechatApi.class);
        }
        return mWechatApi;
    }

    public static FindBgApi getFindBgApi() {
        if (sFindBgApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://www.bing.com/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sFindBgApi = retrofit.create(FindBgApi.class);
        }
        Log.e("oooooo","getFindBgApi");
        return sFindBgApi;
    }

    public static DayJokeApi getDayJokeApi() {
        if (sDayJokeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://japi.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sDayJokeApi = retrofit.create(DayJokeApi.class);
        }
        Log.e("oooooo","getDayJokeApi");
        return sDayJokeApi;
    }

    public static ConstellationApi getConstellationApi(){
        if (sConstellationApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://web.juhe.cn:8080/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sConstellationApi = retrofit.create(ConstellationApi.class);
        }
        return sConstellationApi;

    }

    public static TextJokeApi getRandomTextJokeApi(){
        if (sRandomTextJokeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://v.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sRandomTextJokeApi = retrofit.create(TextJokeApi.class);
        }
        return sRandomTextJokeApi;

    }
    public static TextJokeApi getNewTextJokeApi(){
        if (sTextJokeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://japi.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sTextJokeApi = retrofit.create(TextJokeApi.class);
        }
        return sTextJokeApi;

    }

    public static ImgJokeApi getRandomImgJokeApi(){
        if (sRandomImgJokeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://v.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sRandomImgJokeApi = retrofit.create(ImgJokeApi.class);
        }
        return sRandomImgJokeApi;

    }
    public static ImgJokeApi getNewImgJokeApi(){
        if (sNewImgJokeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://japi.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sNewImgJokeApi = retrofit.create(ImgJokeApi.class);
        }
        return sNewImgJokeApi;

    }

    public static QueryInfoApi getQueryIDCardApi(){
        if (sQueryIDCardApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://apis.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sQueryIDCardApi = retrofit.create(QueryInfoApi.class);
        }
        return sQueryIDCardApi;
    }
    public static QueryInfoApi getQueryQQApi(){
        if (sQueryQQApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://japi.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sQueryQQApi = retrofit.create(QueryInfoApi.class);
        }
        return sQueryQQApi;
    }
    public static QueryInfoApi getQueryTelApi(){
        if (sQueryTelApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://apis.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sQueryTelApi = retrofit.create(QueryInfoApi.class);
        }
        return sQueryTelApi;
    }

    public static ConstellationsApi getDayConstellationsApi(){
        if (mDayConstellationsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://web.juhe.cn:8080/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mDayConstellationsApi = retrofit.create(ConstellationsApi.class);
        }
        return mDayConstellationsApi;
    }

    public static ConstellationsApi getmWeekConstellationsApi(){
        if (mWeekConstellationsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://web.juhe.cn:8080/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mWeekConstellationsApi = retrofit.create(ConstellationsApi.class);
        }
        return mWeekConstellationsApi;
    }

    public static ConstellationsApi getmMonthConstellationsApi(){
        if (mMonthConstellationsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://web.juhe.cn:8080/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mMonthConstellationsApi = retrofit.create(ConstellationsApi.class);
        }
        return mMonthConstellationsApi;
    }

    public static ConstellationsApi getmYearConstellationsApi(){
        if (mYearConstellationsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://web.juhe.cn:8080/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mYearConstellationsApi = retrofit.create(ConstellationsApi.class);
        }
        return mYearConstellationsApi;
    }

    public static ChinaCalendarApi getChinaCalendarApi(){
        if (sChinaCalendarApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://v.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sChinaCalendarApi = retrofit.create(ChinaCalendarApi.class);
        }
        return sChinaCalendarApi;
    }

    public static CityApi getCityApi(){
        if (sCityApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://api.heweather.com/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sCityApi = retrofit.create(CityApi.class);
        }
        return sCityApi;
    }

}
