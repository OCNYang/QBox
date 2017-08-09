package com.ocnyang.qbox.app.module.recommend;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ocnyang.qbox.app.R;


/*************************************************************
 * Created by OCN.Yang           * * * *   * * * *   *     * *
 * Time:2016/11/28 17:27         *     *   *         * *   * *
 * Email address:yangocn@163.com *     *   *         *   * * *
 * Web site:www.ocnyang.com      * * * *   * * * *   *     * *
 *************************************************************/


public class Header_Recommend extends LinearLayout {
    public Header_Recommend(Context context) {
        super(context);
        init(context);
    }

    public Header_Recommend(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Header_Recommend(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.header_recommend,this);
    }
}
