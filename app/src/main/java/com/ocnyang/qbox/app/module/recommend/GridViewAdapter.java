package com.ocnyang.qbox.app.module.recommend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ocnyang.qbox.app.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/*************************************************************
 * Created by OCN.Yang           * * * *   * * * *   *     * *
 * Time:2016/11/25 16:32         *     *   *         * *   * *
 * Email address:yangocn@163.com *     *   *         *   * * *
 * Web site:www.ocnyang.com      * * * *   * * * *   *     * *
 *************************************************************/


public class GridViewAdapter extends BaseAdapter {
    private Context sContext;
    private List<String> sStringList;

    public GridViewAdapter(Context context, List<String> stringList) {
        sContext = context;
        sStringList = stringList;
    }

    @Override
    public int getCount() {
        return sStringList == null ? 0 : sStringList.size();
    }

    @Override
    public Object getItem(int position) {
        return sStringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(sContext).inflate(R.layout.item_gridview_recommend, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.sImgItemGridviewRecommend.setImageResource(R.drawable.bg1);
        viewHolder.sImgnameItemGridviewRecommend.setText("ocnyang");
        viewHolder.sTitleItemGridviewRecommend.setText("来看大家看来大家考虑建档立卡几分可怜的两款发动机了空间发的考虑离开的考虑的快乐离开的房间了肯定放假快乐的房间打开了放得开了疯狂的弗兰克的");
        viewHolder.sUserheaderimgItemGridviewRecommend.setImageResource(R.mipmap.ic_launcher);
        viewHolder.sUsernameItemGridviewRecommend.setText(sStringList.get(position));
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.img_item_gridview_recommend)
        ImageView sImgItemGridviewRecommend;
        @BindView(R.id.imgname_item_gridview_recommend)
        TextView sImgnameItemGridviewRecommend;
        @BindView(R.id.title_item_gridview_recommend)
        TextView sTitleItemGridviewRecommend;
        @BindView(R.id.userheaderimg_item_gridview_recommend)
        CircleImageView sUserheaderimgItemGridviewRecommend;
        @BindView(R.id.username_item_gridview_recommend)
        TextView sUsernameItemGridviewRecommend;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
