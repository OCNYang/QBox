package com.ocnyang.qbox.app.module.recommend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ocnyang.qbox.app.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/*************************************************************
 * Created by OCN.Yang           * * * *   * * * *   *     * *
 * Time:2016/11/28 15:10         *     *   *         * *   * *
 * Email address:yangocn@163.com *     *   *         *   * * *
 * Web site:www.ocnyang.com      * * * *   * * * *   *     * *
 *************************************************************/


public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_LEFT_ITEM = 0;
    private static final int TYPE_RIGHT_ITEM = 3;
    private View sView;
    private Context sContext;
    private List<String> sStringList;

    public RecommendAdapter(Context context, List<String> stringList) {
        sContext = context;
        sStringList = Arrays.asList("a","f","ddd","dse","aeg","aege","aetg","ah");
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LEFT_ITEM) {
            sView = LayoutInflater.from(sContext).inflate(R.layout.recommend_recommenditem_left, parent, false);
            LeftViewHolder leftViewHolder = new LeftViewHolder(sView);
            return leftViewHolder;
        } else if (viewType == TYPE_RIGHT_ITEM) {
            sView = LayoutInflater.from(sContext).inflate(R.layout.recommend_recommenditem_right, parent, false);
            RightViewHolder rightViewHolder = new RightViewHolder(sView);
            return rightViewHolder;

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LeftViewHolder) {

        } else if (holder instanceof RightViewHolder) {

        }

    }

    @Override
    public int getItemCount() {
        return sStringList != null ? sStringList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_LEFT_ITEM;
        }else {
            return TYPE_RIGHT_ITEM;
        }
    }

    public static class LeftViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_item_recommend)
        TextView mTitleItemRecommend;
        @BindView(R.id.time_item_recommend)
        TextView mTimeItemRecommend;
        @BindView(R.id.howlong_item_recommend)
        TextView mHowlongItemRecommend;
        @BindView(R.id.looknum_item_recommend)
        TextView mLooknumItemRecommend;
        @BindView(R.id.site_item_recommend)
        TextView mSiteItemRecommend;
        @BindView(R.id.userheaderimg_item_recommend)
        CircleImageView mUserheaderimgItemRecommend;
        @BindView(R.id.username_item_recommend)
        TextView mUsernameItemRecommend;

        public LeftViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class RightViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_item_recommend)
        TextView mTitleItemRecommend;
        @BindView(R.id.time_item_recommend)
        TextView mTimeItemRecommend;
        @BindView(R.id.howlong_item_recommend)
        TextView mHowlongItemRecommend;
        @BindView(R.id.looknum_item_recommend)
        TextView mLooknumItemRecommend;
        @BindView(R.id.site_item_recommend)
        TextView mSiteItemRecommend;
        @BindView(R.id.userheaderimg_item_recommend)
        CircleImageView mUserheaderimgItemRecommend;
        @BindView(R.id.username_item_recommend)
        TextView mUsernameItemRecommend;

        public RightViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
