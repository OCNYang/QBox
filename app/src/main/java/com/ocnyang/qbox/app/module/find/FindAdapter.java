package com.ocnyang.qbox.app.module.find;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.model.entities.FunctionBean;

import java.util.List;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/28 10:35.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class FindAdapter extends BaseItemDraggableAdapter<FunctionBean,BaseViewHolder> {
    public FindAdapter(List<FunctionBean> data) {
        super(R.layout.item_find,data);
    }

    public FindAdapter(int layoutResId, List<FunctionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FunctionBean item) {
        helper.setText(R.id.name_item_find,item.getName());
        ImageView view = (ImageView) helper.getView(R.id.icon_item_find);
        try {
            int camera = (Integer) R.drawable.class.getField(item.getCode()).get(null);
            view.setImageResource(camera);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
