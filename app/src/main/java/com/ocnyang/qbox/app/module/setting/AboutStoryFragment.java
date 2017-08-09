package com.ocnyang.qbox.app.module.setting;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseFragment;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/4/7 14:09.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class AboutStoryFragment extends BaseFragment{


    public AboutStoryFragment() {
    }

    public static AboutStoryFragment newInstance() {
        AboutStoryFragment fragment = new AboutStoryFragment();
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_about_story;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return getContext().getClass().getSimpleName()+"-"
                +this.getClass().getSimpleName();
    }
}
