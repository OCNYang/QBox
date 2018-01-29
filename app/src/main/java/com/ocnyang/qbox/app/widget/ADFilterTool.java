package com.ocnyang.qbox.app.widget;

import android.content.Context;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/21 09:35.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class ADFilterTool {
    public static boolean hasAd(Context context, String url) {
//开始采用这种辨别过滤广告前缀的方式，但是因为广告的提供地址太多不可能全部过滤，另一方面当添加的过滤地址过多时也会造成性能问题。
//        Resources res = context.getResources();
//        String[] adUrls = res.getStringArray(R.array.adBlockUrl);
//        for (String adUrl : adUrls) {
//            if (url.contains(adUrl)) {
//                return true;
//            }
//        }

//--------------------------------------------------------------------------------------------------

//如果是公司项目开发，直接判定是不是自己的域名，如果不是就不加载。
//        if (!url.contains("eastday.com")) {
//            return true;
//        } else {
//            if (url.contains("df888.eastday.com") ||
//                    url.contains("toutiao.eastday.com") ||
//                    url.contains("tt321.eastday.com") ||
//                    url.contains("eastday.com/toutiaoh5")
//                    ) {
//                return true;
//            }
//            return false;
//        }

//--------------------------------------------------------------------------------------------------

        //这里找到最初的病因，直接屏蔽掉这个就行了。
        if (url.contains("eastday.com/toutiaoh5")) {
            return true;
        }
        return false;
    }

}
