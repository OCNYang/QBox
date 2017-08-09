package com.ocnyang.qbox.app.base;

import android.text.TextUtils;

import com.ocnyang.qbox.app.utils.CommonUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共请求
 */
public class BaseRequest {
    /**
     * 将实体类转换成请求参数,json字符串形式返回
     *
     * @return
     */
    public String getJsonParams() {
        String jsonStr = CommonUtils.getGson().toJson(this);
        if (TextUtils.isEmpty(jsonStr)) {
            jsonStr = "";
        }

        return jsonStr;
    }

    /**
     * 将实体类转换成请求参数,以map<k,v>形式返回
     *
     * @return
     */
    public Map<String, String> getMapParams() {
        Class<? extends BaseRequest> clazz = this.getClass();
        Class<? extends Object> superclass = clazz.getSuperclass();

        Field[] fields = clazz.getDeclaredFields();
        Field[] superFields = superclass.getDeclaredFields();

        if (fields == null || fields.length == 0) {
            return Collections.emptyMap();
        }

        Map<String, String> params = new HashMap<String, String>();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                params.put(field.getName(), String.valueOf(field.get(this)));
            }

            for (Field superField : superFields) {
                superField.setAccessible(true);
                params.put(superField.getName(), String.valueOf(superField.get(this)));
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return params;
    }
}
