package com.ocnyang.qbox.app.network.api;

import com.ocnyang.qbox.app.model.entities.FindBg;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/3/3 13:12.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public interface FindBgApi {
    @GET("HPImageArchive.aspx")
    Observable<FindBg> getFindBg(@Query("format") String format, @Query("idx") int idx,@Query("n") int n);
}
