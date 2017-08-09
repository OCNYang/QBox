package com.ocnyang.qbox.app.base;

import android.text.TextUtils;

/**
 * 公共Presenter,所有Presenter继承自此类
 * 因为Presenter层一般用于校验数据正确性,故该类用于封装常用的数据校验方法
 */
public abstract class BasePresenter {

    /**
     * 校验指定的字符串是否为空,如果为空则弹出指定内容的Toast
     *
     * @param verifData
     * @param view
     * @param showMessage
     * @return
     */
    public boolean isEmpty(String verifData, IBaseView view, String showMessage) {
        if (TextUtils.isEmpty(verifData)) {
            view.showToast(showMessage);
            return true;
        }

        return false;
    }
}
