package com.ocnyang.qbox.app.network.api;

import com.ocnyang.qbox.app.model.entities.DayJoke;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/3/3 15:24.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public interface DayJokeApi {
    @GET("joke/content/text.from")
    Observable<DayJoke> getDayJoke(@Query("key") String key);
}
