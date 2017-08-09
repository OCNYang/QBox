package com.ocnyang.qbox.app.model.entities.textjoke;

import java.util.List;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/13 13:39.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class RandomTextJoke extends BaseJokeBean{


    private List<TextJokeBean> result;

    public List<TextJokeBean> getResult() {
        return result;
    }

    public void setResult(List<TextJokeBean> result) {
        this.result = result;
    }

}
