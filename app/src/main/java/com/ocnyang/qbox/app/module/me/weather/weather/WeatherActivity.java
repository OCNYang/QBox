package com.ocnyang.qbox.app.module.me.weather.weather;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.module.me.weather.android.util.TimingLogger;
import com.ocnyang.qbox.app.module.me.weather.android.util.UiUtil;
import com.ocnyang.qbox.app.module.me.weather.dynamicweather.DynamicWeatherView;
import com.ocnyang.qbox.app.module.me.weather.mxxedgeeffect.widget.MxxFragmentPagerAdapter;
import com.ocnyang.qbox.app.module.me.weather.mxxedgeeffect.widget.MxxViewPager;
import com.ocnyang.qbox.app.module.me.weather.weather.api.ApiManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeatherActivity extends FragmentActivity {

    public static Typeface typeface;
    public SimpleFragmentPagerAdapter mFragmentPagerAdapter;
    public List<BaseWeatherFragment> mFragmentList;


    public static Typeface getTypeface(Context context) {
        return typeface;
    }

    private DynamicWeatherView weatherView;
    private MxxViewPager viewPager;
    AlphaAnimation alphaAnimation;

    public MxxViewPager getViewPager() {
        return viewPager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimingLogger logger = new TimingLogger("WeatherActivity.onCreate");
//		typeface = Typeface.createFromAsset(getAssets(), "fonts/clock text typeface.ttf");
        if (typeface == null) {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/mxx_font2.ttf");
        }
        logger.addSplit("Typeface.createFromAsset");
        setContentView(R.layout.activity_weather);
        logger.addSplit("setContentView");

        viewPager = (MxxViewPager) findViewById(R.id.main_viewpager);
        if (Build.VERSION.SDK_INT >= 19) {
            viewPager.setPadding(0, UiUtil.getStatusBarHeight(), 0, 0);
        }
        alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(260);
        alphaAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                getWindow().setBackgroundDrawable(//getResources().getDrawable(R.drawable.window_frame_color));
                        new ColorDrawable(Color.BLACK));
//				WeatherNotificationService.startServiceWithNothing(this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        viewPager.setAnimation(alphaAnimation);
//		viewPager.setPageMargin(UiUtil.dp2px(this, 4));
        logger.addSplit("findViewPager");
        weatherView = (DynamicWeatherView) findViewById(R.id.main_dynamicweatherview);
        logger.addSplit("findWeatherView");
        loadAreaToViewPager();
        logger.addSplit("loadAreaToViewPager");
        logger.dumpToLog();

    }

    public void loadAreaToViewPager() {
        final ArrayList<ApiManager.Area> selectedAreas = ApiManager.loadSelectedArea(this);
        final BaseWeatherFragment[] fragments = new BaseWeatherFragment[selectedAreas.size()];
//		viewPager.setOffscreenPageLimit(fragments.length);
        for (int i = 0; i < selectedAreas.size(); i++) {
            final ApiManager.Area area = selectedAreas.get(i);
            fragments[i] = WeatherFragment.makeInstance(area, ApiManager.loadWeather(this, area.id));
        }
//        fragments[0] = SettingsFragment.makeInstance(selectedAreas);
        mFragmentList = Arrays.asList(fragments);
        mFragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(mFragmentPagerAdapter);
        viewPager.setOnPageChangeListener(new MxxViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                weatherView.setDrawerType(((SimpleFragmentPagerAdapter) viewPager.getAdapter()).getItem(
                        position).getDrawerType());
            }
        });
//        viewPager.setCurrentItem(1, false);
    }

    public void updateCurDrawerType() {
        weatherView.setDrawerType(((SimpleFragmentPagerAdapter) viewPager.getAdapter()).getItem(
                viewPager.getCurrentItem()).getDrawerType());
    }

    @Override
    protected void onResume() {
        super.onResume();
        weatherView.onResume();
    }

    @Override
    protected void onPause() {
        weatherView.onPause();
        viewPager.clearAnimation();
        alphaAnimation.cancel();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        weatherView.onDestroy();
        super.onDestroy();
    }

    public static class SimpleFragmentPagerAdapter extends MxxFragmentPagerAdapter {

        private List<BaseWeatherFragment> fragments;

        public SimpleFragmentPagerAdapter(FragmentManager fragmentManager, List<BaseWeatherFragment> fragments) {
            super(fragmentManager);
            this.fragments = fragments;
        }

        @Override
        public BaseWeatherFragment getItem(int position) {
            BaseWeatherFragment fragment = fragments.get(position);
            fragment.setRetainInstance(true);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((Fragment) object).getView());
            super.destroyItem(container, position, object);
        }

        public void addItem(BaseWeatherFragment baseWeatherFragment) {
            fragments.add(baseWeatherFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
