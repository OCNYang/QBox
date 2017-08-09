package com.ocnyang.qbox.app.module.find.joke;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.model.entities.textjoke.TextJokeBean;

import java.util.List;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/13 13:29.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class ImgJokeAdapter extends BaseMultiItemQuickAdapter<TextJokeBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ImgJokeAdapter(List<TextJokeBean> data) {
        super(data);
        addItemType(TextJokeBean.JOKE, R.layout.item_imgjoke_joke);
        addItemType(TextJokeBean.MORE, R.layout.item_textjoke_more);
    }

    @Override
    protected void convert(final BaseViewHolder helper, TextJokeBean item) {
        if (helper.getItemViewType() == TextJokeBean.JOKE) {
            helper.setText(R.id.tv_item_imgjoke,item.getContent());
            //遗留问题
            //这里的图片不再作GIF动画和普通图片的辨别加载
            //也不再使用宽度充满高度自适应的加载方式。
            //如果对上面两种情况做处理的话，会引起OOM,图片无法加载等一系列问题，现在还没找到这些问题的解决方法，所以暂时不做处理。
//            Glide.with(mContext)
//                    .load(item.getUrl())
//                    .asBitmap()
////                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                            ((ImageView) helper.getView(R.id.img_item_imgjoke)).setImageBitmap(resource);
//                        }
//                    });
            Glide.with(mContext)
                    .load(item.getUrl())
//                    .placeholder(R.drawable.lodingview)//Glide的占位图有时会造成一些问题，在这里尤其明显
                    .error(R.drawable.errorview)
                    .crossFade(500)
                    .into((ImageView) helper.getView(R.id.img_item_imgjoke));
        } else if (TextJokeBean.MORE == helper.getItemViewType()) {
            Glide.with(mContext)
                    .load(R.drawable.loadingjoke)
                    .into(((ImageView) helper.getView(R.id.img_item_morejoke)));
        }

    }
}
