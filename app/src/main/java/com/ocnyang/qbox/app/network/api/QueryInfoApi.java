package com.ocnyang.qbox.app.network.api;

import com.ocnyang.qbox.app.model.entities.textjoke.QueryIDCard;
import com.ocnyang.qbox.app.model.entities.textjoke.QueryQQ;
import com.ocnyang.qbox.app.model.entities.textjoke.QueryTel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/3/3 15:24.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public interface QueryInfoApi {

//    http://apis.juhe.cn/mobile/get   phone	int
    /*{
        "resultcode":"200",
            "reason":"Return Successd!",
            "result":{
        "province":"浙江",
                "city":"杭州",
                "areacode":"0571",
                "zip":"310000",
                "company":"联通",
                "card":""
    },
        "error_code":0
    }*/

    @GET("mobile/get")
    Observable<QueryTel> getTelInfo(@Query("key") String key, @Query("phone") String phone);


//    http://japi.juhe.cn/qqevaluate/qq  qq	string

    /*{
        "error_code":0,
            "reason":"success",
            "result":{
        "data":{
            "conclusion":"[中吉]中吉之数，进退保守，生意安稳，成就可期",
                    "analysis":"温和平安艺才高，努力前途福运来，文笔奇绝仁德高，务实稳健掌门人。"
        }
    }
    }*/

    @GET("qqevaluate/qq")
    Observable<QueryQQ> getQQInfo(@Query("key") String key, @Query("qq") String qq);


//    http://apis.juhe.cn/idcard/index  cardno	string

    /*{
        "resultcode":"200",
            "reason":"成功的返回",
            "result":{
        "area":"河南省驻马店地区汝南县",
                "sex":"男",
                "birthday":"1993年12月20日",
                "verify":""
    },
        "error_code":0
    }*/

    @GET("idcard/index")
    Observable<QueryIDCard> getIDCardInfo(@Query("key") String key, @Query("cardno") String cardno);

}
