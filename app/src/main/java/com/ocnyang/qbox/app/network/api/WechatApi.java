package com.ocnyang.qbox.app.network.api;

import com.ocnyang.qbox.app.model.entities.WechatItem;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/24 14:53.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public interface WechatApi {
    @GET("wx/article/search?key=1cc099ede9137")
    Observable<WechatItem> getWechat(@Query("cid") String cid,
                                     @Query("page") int page,
                                     @Query("size") int size);
}
