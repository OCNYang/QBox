package com.ocnyang.qbox.app.network.api;

import com.ocnyang.qbox.app.model.entities.constellation.DayConstellation;
import com.ocnyang.qbox.app.model.entities.constellation.MonthConstellation;
import com.ocnyang.qbox.app.model.entities.constellation.WeekConstellation;
import com.ocnyang.qbox.app.model.entities.constellation.YearConstellation;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/3/3 15:45.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public interface ConstellationsApi {
    @GET("constellation/getAll")
    Observable<DayConstellation> getDayConstellation(
            @Query("consName") String consName,
            @Query("type") String type,
            @Query("key") String key);

    @GET("constellation/getAll")
    Observable<WeekConstellation> getWeekConstellation(
            @Query("consName") String consName,
            @Query("type") String type,
            @Query("key") String key);

    @GET("constellation/getAll")
    Observable<MonthConstellation> getMonthConstellation(
            @Query("consName") String consName,
            @Query("type") String type,
            @Query("key") String key);

    @GET("constellation/getAll")
    Observable<YearConstellation> getYearConstellation(
            @Query("consName") String consName,
            @Query("type") String type,
            @Query("key") String key);
}
