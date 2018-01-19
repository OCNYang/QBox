package com.ocnyang.qbox.app.module.news;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseFragment;
import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.database.CategoryDao;
import com.ocnyang.qbox.app.model.entities.RefreshNewsFragmentEvent;
import com.ocnyang.qbox.app.module.me.MeFragment;
import com.ocnyang.qbox.app.module.news_category.CategoryEntity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.add_btn)
    ImageView mAddBtn;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.logo_news)
    ImageView mLogoNews;

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<CategoryEntity> mCategoryEntityList;


    public NewsFragment() {
        // Required empty public constructor
    }


    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (mLogoNews.getFitsSystemWindows() == false) {
                mLogoNews.setFitsSystemWindows(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    mLogoNews.requestApplyInsets();
                }
            }
        }
    }

    @Override
    public void initView() {
        mCategoryEntityList = getCategoryFromDB();
        if (mCategoryEntityList == null) {
            mCategoryEntityList = new ArrayList<>();
        }

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < mCategoryEntityList.size(); i++) {
            if (true) {
                DefaultStyleFragment meFragment = DefaultStyleFragment.newInstance(mCategoryEntityList.get(i).getKey(), "");
                fragments.add(meFragment);
            } else {
                MeFragment meFragment = new MeFragment();
                fragments.add(meFragment);
            }
        }
        mViewPager.setAdapter(new NewsPagerAdapter(getChildFragmentManager(), fragments));
        initMagicIndicator();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (mLogoNews != null) {
            if (hidden) {
                mLogoNews.setFitsSystemWindows(false);
            } else {
                mLogoNews.setFitsSystemWindows(true);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                mLogoNews.requestApplyInsets();
            }
        }
        super.onHiddenChanged(hidden);
    }

    private List<CategoryEntity> getCategoryFromDB() {
        CategoryDao categoryDao = new CategoryDao(getContext().getApplicationContext());
        return categoryDao.queryCategoryList();
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mCategoryEntityList == null ? 0 : mCategoryEntityList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mCategoryEntityList.get(index).getName());
                simplePagerTitleView.setNormalColor(Color.WHITE);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.colorAccent));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.WHITE);
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    @Override
    protected void managerArguments() {
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
    }

    @OnClick(R.id.add_btn)
    public void onClick() {
        EventBus.getDefault().post(new RefreshNewsFragmentEvent(Const.NEWSFRAGMENT_CATEGORYACTIVITY_REQUESTCODE));
    }

    @Override
    public String getUmengFragmentName() {
        return getContext().getClass().getSimpleName() + "-"
                + this.getClass().getSimpleName();
    }

}
