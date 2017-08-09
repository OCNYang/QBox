package com.ocnyang.qbox.app.module.recommend;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecommendFragment extends BaseFragment {
    Context sContext;

    private int[] imgurl = new int[]{R.drawable.bg1,
            R.drawable.bg2, R.drawable.bg1, R.drawable.bg2};
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.slider_recommend)
    SliderLayout sSliderRecommend;
    @BindView(R.id.titlename_context_recommend)
    TextView sTitlenameContextRecommend;
    @BindView(R.id.title_context_recommend)
    LinearLayout sTitleContextRecommend;
    @BindView(R.id.gridview_recommend)
    GridView sGridviewRecommend;
    @BindView(R.id.swipeRefreshLayout_recommend)
    SwipeRefreshLayout sSwipeRefreshLayoutRecommend;
    @BindView(R.id.searchview_header_recommend)
    SearchView sSearchviewHeaderRecommend;
    @BindView(R.id.near_tv_header_recommend)
    TextView sNearTvHeaderRecommend;
    @BindView(R.id.loadingImg)
    ImageView sLoadingImg;
    @BindView(R.id.loading_view)
    RelativeLayout sLoadingView;
    @BindView(R.id.custom_indicator)
    PagerIndicator sCustomIndicator;

    private String mParam1;
    private String mParam2;
    private List<String> sStringList;

    public RecommendFragment() {
    }

    public static RecommendFragment newInstance(String param1, String param2) {
        RecommendFragment fragment = new RecommendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initView() {
        sGridviewRecommend.setFocusable(false);
        sContext = getContext();
        for (int aimgurl :
                imgurl) {
            TextSliderView textSliderView = new TextSliderView(sContext);
            textSliderView.image(aimgurl)
                    .description("www.ocnyang.com")
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sSliderRecommend.addSlider(textSliderView);
        }
        sSliderRecommend.setCustomAnimation(new DescriptionAnimation());
        sSliderRecommend.setDuration(3000);
        sSliderRecommend.setCustomIndicator(sCustomIndicator);

        initGridView();

    }

    

    private void initGridView() {
        sStringList = Arrays.asList("a","b","c","d");
        sGridviewRecommend.setAdapter(new GridViewAdapter(sContext,sStringList));
    }

    @Override
    protected void managerArguments() {
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public String getUmengFragmentName() {
        return getContext().getClass().getSimpleName()+"-"
                +this.getClass().getSimpleName();
    }
}
