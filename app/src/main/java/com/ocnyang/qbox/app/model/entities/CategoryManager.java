package com.ocnyang.qbox.app.model.entities;

import android.content.Context;
import android.content.res.Resources;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.module.news_category.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/23 15:53.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class CategoryManager {
    Context mContext;

    public CategoryManager(Context context) {
        mContext = context;
    }

    public List<CategoryEntity> getAllCategory() {
        List<CategoryEntity> categoryEntityList = new ArrayList<>();

        Resources resources = mContext.getResources();
        String[] nameArray = resources.getStringArray(R.array.category_name);
        String[] typeArray = resources.getStringArray(R.array.category_type);

        for (int i = 0; i < (nameArray.length > typeArray.length ? typeArray.length : nameArray.length); i++) {
            CategoryEntity categoryEntity = new CategoryEntity(null, nameArray[i], typeArray[i],i);
            categoryEntityList.add(categoryEntity);
        }

        return categoryEntityList;
    }
}
