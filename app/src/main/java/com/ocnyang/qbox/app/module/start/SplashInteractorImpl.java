package com.ocnyang.qbox.app.module.start;

import android.os.Handler;

/*************************************************************
 * Created by OCN.Yang           * * * *   * * * *   *     * *
 * Time:2016/10/11 17:56         *     *   *         * *   * *
 * Email address:yangocn@163.com *     *   *         *   * * *
 * Web site:www.ocnyang.com      * * * *   * * * *   *     * *
 *************************************************************/


public class SplashInteractorImpl implements SplashInteractor{
    @Override
    public void enterInto(boolean isFirstOpen, final OnEnterIntoFinishListener listener) {
        if (!isFirstOpen){
            listener.isFirstOpen();
        }else {
            listener.showContentView();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    listener.isNotFirstOpen();
                }
            }, 2000);
        }
    }
}
