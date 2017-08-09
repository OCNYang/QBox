package com.ocnyang.qbox.app.module.start;

/*************************************************************
 * Created by OCN.Yang           * * * *   * * * *   *     * *
 * Time:2016/10/11 17:52         *     *   *         * *   * *
 * Email address:yangocn@163.com *     *   *         *   * * *
 * Web site:www.ocnyang.com      * * * *   * * * *   *     * *
 *************************************************************/


public interface SplashInteractor {

    public interface OnEnterIntoFinishListener{
        void isFirstOpen();

        void isNotFirstOpen();

        void showContentView();

    }

    void enterInto(boolean isFirstOpen,OnEnterIntoFinishListener listener);
}
