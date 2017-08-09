package com.ocnyang.qbox.app.model.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/28 16:45.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/

@Entity
public class FunctionBean {

    /**
     * id : 1  //标记元素顺序
     * mark : 1  //标记元素类别
     * name : 万年历
     * code : calendar  //元素图标的名称
     */

    @Id(autoincrement = true)
    private Long functionId;

    private int id;
    private int mark;
    private String name;
    private String code;
    private boolean notOpen;

    @Generated(hash = 1500686151)
    public FunctionBean(Long functionId, int id, int mark, String name, String code,
            boolean notOpen) {
        this.functionId = functionId;
        this.id = id;
        this.mark = mark;
        this.name = name;
        this.code = code;
        this.notOpen = notOpen;
    }

    @Generated(hash = 1500552263)
    public FunctionBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getFunctionId() {
        return this.functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public boolean getNotOpen() {
        return this.notOpen;
    }

    public void setNotOpen(boolean notOpen) {
        this.notOpen = notOpen;
    }
}
