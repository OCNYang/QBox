package com.ocnyang.qbox.app.module.find.constellation;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.model.entities.RefreshConstellationEvent;
import com.ocnyang.qbox.app.module.find.joke.SectionsPagerAdapter;
import com.ocnyang.qbox.app.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstellationActivity extends BaseCommonActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    private List<String> mTitleStrings;
    private List<Fragment> mFragmentList;
    Toolbar toolbar;
    private String mSelectConstellation;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_constellation);

    }

    @Override
    public void initView() {

        initToolbar();
//        initFAB();
        initViewPager();
    }

    private void initViewPager() {
        mFragmentList = new ArrayList<>();
        mTitleStrings = Arrays.asList("今天", "明天", "本周", "下周", "本月", "今年");

        for (String fragmentname : mTitleStrings) {
            switch (fragmentname) {
                case "今天":
                    mFragmentList.add(DayConstellationFragment.newInstance
                            (mSelectConstellation,DayConstellationFragment.DAY_TODAY));
                    break;
                case "明天":
                    mFragmentList.add(DayConstellationFragment.newInstance
                            (mSelectConstellation,DayConstellationFragment.DAY_TOMORROW));
                    break;
                case "本周":
                    mFragmentList.add(WeekAndMonthConstellationFragment.newInstance
                            (WeekAndMonthConstellationFragment.WEEK,mSelectConstellation));
                    break;
                case "下周":
                    mFragmentList.add(WeekAndMonthConstellationFragment.newInstance
                            (WeekAndMonthConstellationFragment.NEXTWEEK,mSelectConstellation));
                    break;
                case "本月":
                    mFragmentList.add(WeekAndMonthConstellationFragment.newInstance
                            (WeekAndMonthConstellationFragment.MONTH,mSelectConstellation));
                    break;
                case "今年":
                    mFragmentList.add(YearConstellationFragment.newInstance(mSelectConstellation,""));
                    break;
                default:

                    break;
            }
        }


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this, mFragmentList, mTitleStrings);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void initToolbar() {
        mSelectConstellation = (String) SPUtils.get(this, Const.USER_STAR, "水瓶座");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setSubtitle(mSelectConstellation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initPresenter() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_constellation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Logger.e(item.getOrder()+"");
        Logger.e(item.getTitle()+"title");
        Logger.e(item.getTitleCondensed()+"titlecondensed");

        if (android.R.id.home == id) {
            finish();
        }else {
            String chengConstellation = item.getTitle().toString();
            getSupportActionBar().setSubtitle(mSelectConstellation = chengConstellation);
            EventBus.getDefault().post(new RefreshConstellationEvent(chengConstellation));
        }
        return true;
    }

}
