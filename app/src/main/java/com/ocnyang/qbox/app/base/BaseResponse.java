package com.ocnyang.qbox.app.base;

import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * 公共响应参数
 *
 * @author Ht
 */
public abstract class BaseResponse {
    private boolean success;
    private String msg;
    private String data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * 解析单条数据
     *
     * @param clazz
     * @return
     * @throws IllegalArgumentException 参数异常(Response中data为空)
     * @throws JsonSyntaxException      Json解析异常
     */
    public abstract <T> T getBean(Class<T> clazz)
            throws IllegalArgumentException, JsonSyntaxException;

    /**
     * 解析数据列表
     *
     * @param typeOfT
     * @return
     * @throws IllegalArgumentException 参数异常(Response中data为空)
     * @throws JsonSyntaxException      Json解析异常
     */
    public abstract <T> T getBeanList(Type typeOfT)
            throws IllegalArgumentException, JsonSyntaxException;

}
