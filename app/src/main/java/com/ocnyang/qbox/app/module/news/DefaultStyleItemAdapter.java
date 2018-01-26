package com.ocnyang.qbox.app.module.news;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.model.entities.WechatItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/16 10:15.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class DefaultStyleItemAdapter extends BaseQuickAdapter<WechatItem.ResultBean.ListBean, BaseViewHolder> {
    boolean isNotLoad;
    public int mImgWidth;
    public int mImgHeight;

    public DefaultStyleItemAdapter(int layoutResId) {
        super(layoutResId);
    }

    public DefaultStyleItemAdapter(int layoutResId, List<WechatItem.ResultBean.ListBean> data) {
        super(layoutResId, data);
    }

    public DefaultStyleItemAdapter(int layoutResId, boolean isNotLoadImg, int imgWidth, int imgHeight){
        super(layoutResId);
        isNotLoad = isNotLoadImg;
        mImgWidth = imgWidth;
        mImgHeight = imgHeight;
    }
    @Override
    protected void convert(BaseViewHolder helper, WechatItem.ResultBean.ListBean item) {
        helper.setText(R.id.title_news_item, item.getTitle())
                .setText(R.id.from_news_item, "小秋魔盒")
                .setText(R.id.time_news_item, onFormatTime(item.getPubTime()));

        if (!isNotLoad) {
            Glide.with(mContext)
                    .load(item.getThumbnails())
                    .override(mImgWidth,mImgHeight)
                    .centerCrop()
//                    .placeholder(R.drawable.loading)
                    .error(R.drawable.errorview)
                    .crossFade(1000)
                    .into((ImageView) helper.getView(R.id.img_news_item));
        }
    }

    private String onFormatTime(String date) {
        Date formatDate = null;
        if (TextUtils.isEmpty(date)) {
            return getNowTime("MM-dd");
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                formatDate = simpleDateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        if (formatDate == null) {
            return getNowTime("MM-dd");
        }
        return simpleDateFormat.format(formatDate);
    }

    private String getNowTime(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return simpleDateFormat.format(date);
    }
}
