package com.ocnyang.qbox.app.widget;

import android.content.Context;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/21 09:32.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class NoAdWebViewClient extends WebViewClient {
    //    private String mHomeurl;
    private Context mContext;

    public NoAdWebViewClient(/*String homeurl,*/ Context context) {
//        mHomeurl = homeurl;
        mContext = context;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        url = url.toLowerCase();
//        if (!url.contains(mHomeurl)) {
        if (!ADFilterTool.hasAd(mContext, url)) {
            return super.shouldInterceptRequest(view, url);
        } else {
            return new WebResourceResponse(null, null, null);
        }
//        } else {
//            return super.shouldInterceptRequest(view, url);
//
//        }
    }

    /**
     * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        Log.d("Url:", url);
        return true;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            String url = request.getUrl().toString().toLowerCase();
            if (!ADFilterTool.hasAd(mContext, url)) {
                return super.shouldInterceptRequest(view, request);
            } else {
                return new WebResourceResponse(null, null, null);
            }
        } else {
            return super.shouldInterceptRequest(view, request);
        }
    }
}
