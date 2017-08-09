package com.ocnyang.qbox.app.module.me.weather.weather;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.app.BaseApplication;
import com.ocnyang.qbox.app.module.me.weather.android.util.MxxNetworkUtil;
import com.ocnyang.qbox.app.module.me.weather.android.util.TimingLogger;
import com.ocnyang.qbox.app.module.me.weather.android.util.UiUtil;
import com.ocnyang.qbox.app.module.me.weather.dynamicweather.BaseDrawer;
import com.ocnyang.qbox.app.module.me.weather.dynamicweather.BaseDrawer.Type;
import com.ocnyang.qbox.app.module.me.weather.weather.api.ApiManager;
import com.ocnyang.qbox.app.module.me.weather.weather.api.entity.HeWeatherDataService30;
import com.ocnyang.qbox.app.module.me.weather.weather.api.entity.Weather;
import com.ocnyang.qbox.app.module.me.weather.weather.widget.AqiView;
import com.ocnyang.qbox.app.module.me.weather.weather.widget.AstroView;
import com.ocnyang.qbox.app.module.me.weather.weather.widget.DailyForecastView;
import com.ocnyang.qbox.app.module.me.weather.weather.widget.HourlyForecastView;
import com.ocnyang.qbox.app.module.me.weather.weather.widget.PullRefreshLayout;

import java.util.ArrayList;

public class WeatherFragment extends BaseWeatherFragment {


    private View mRootView;
    private Weather mWeather;
    private DailyForecastView mDailyForecastView;
    private PullRefreshLayout pullRefreshLayout;
    private HourlyForecastView mHourlyForecastView;
    private AqiView mAqiView;
    private AstroView mAstroView;
    private ApiManager.Area mArea;
    private ScrollView mScrollView;
    private BaseDrawer.Type mDrawerType = BaseDrawer.Type.UNKNOWN_D;

    public BaseDrawer.Type getDrawerType() {
        // if(mDrawerType == null){
        // if(mWeather != null){
        // mDrawerType = ApiManager.convertWeatherType(mWeather);
        // }
        // }
        return this.mDrawerType;
    }

    private static final String BUNDLE_EXTRA_AREA = "BUNDLE_EXTRA_AREA";
    private static final String BUNDLE_EXTRA_WEATHER = "BUNDLE_EXTRA_WEATHER";

    // private static final String BUNDLE_SAVED_TYPE = "BUNDLE_SAVED_TYPE";

    public static WeatherFragment makeInstance(@NonNull ApiManager.Area area, Weather weather) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_EXTRA_AREA, area);
        if (weather != null) {
            bundle.putSerializable(BUNDLE_EXTRA_WEATHER, weather);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    private void fetchArguments() {
        if (this.mArea == null) {
            try {
                this.mArea = (ApiManager.Area) getArguments().getSerializable(BUNDLE_EXTRA_AREA);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.mWeather == null) {
            try {
                this.mWeather = (Weather) getArguments().getSerializable(BUNDLE_EXTRA_WEATHER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_weather, null);
            mDailyForecastView = (DailyForecastView) mRootView.findViewById(R.id.w_dailyForecastView);
            pullRefreshLayout = (PullRefreshLayout) mRootView.findViewById(R.id.w_PullRefreshLayout);
            mHourlyForecastView = (HourlyForecastView) mRootView.findViewById(R.id.w_hourlyForecastView);
            mAqiView = (AqiView) mRootView.findViewById(R.id.w_aqi_view);
            mAstroView = (AstroView) mRootView.findViewById(R.id.w_astroView);
            mScrollView = (ScrollView) mRootView.findViewById(R.id.w_WeatherScrollView);

            pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    ApiManager.updateWeather(getActivity(), mArea.id, new ApiManager.ApiListener() {
                        @Override
                        public void onUpdateError() {
                            pullRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onReceiveWeather(Weather weather, boolean updated) {
                            pullRefreshLayout.setRefreshing(false);
                            if (updated) {
                                if (ApiManager.acceptWeather(weather)) {
                                    WeatherFragment.this.mWeather = weather;
                                    updateWeatherUI();
                                }
                            }
                        }
                    });
                }
            });
            debug();
            if (mWeather != null) {
                updateWeatherUI();
            }
        } else {
//			UiUtil.toastDebug(getActivity(), "mRootView is not null, just use it");
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.scrollTo(0, 0);
                }
            });

        }
        return mRootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchArguments();
        UiUtil.logDebug("ocnyang", "onCreate");
    }

    public String getCityName() {
        fetchArguments();
        if (this.mArea != null) {
            return mArea.name_cn;
        } else {
            return "Error";
        }
    }

    private void updateDrawerTypeAndNotify() {
        final BaseDrawer.Type curType = ApiManager.convertWeatherType(mWeather);
        // if(this.mDrawerType != curType){
        this.mDrawerType = curType;
        notifyActivityUpdate();
        // }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchArguments();
        UiUtil.logDebug("FUCK", "onActivityCreated");
        if (this.mArea == null) {
            return;
        }
        TimingLogger logger = new TimingLogger("WeatherFragment.onActivityCreated");
        if (this.mWeather == null) {
            this.mWeather = ApiManager.loadWeather(getActivity(), mArea.id);
            logger.addSplit("loadWeather");
            updateWeatherUI();
            logger.addSplit("updateWeatherUI");
        }
        logger.dumpToLog();
        if (mWeather == null) {
            postRefresh();
        }
    }

    private void debug() {
        // DEBUG///////////////
        if (BaseApplication.DEBUG) {
            mRootView.findViewById(R.id.w_WeatherLinearLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    ArrayList<String> strs = new ArrayList<String>();
                    for (BaseDrawer.Type t : BaseDrawer.Type.values()) {
                        strs.add(t.toString());
                    }
                    int index = 0;
                    for (int i = 0; i < Type.values().length; i++) {
                        if (Type.values()[i] == mDrawerType) {
                            index = i;
                            break;
                        }
                    }
                    builder.setSingleChoiceItems(strs.toArray(new String[]{}), index,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mDrawerType = Type.values()[which];
                                    notifyActivityUpdate();
                                    dialog.dismiss();
                                    // Toast.makeText(getActivity(), "onClick->"
                                    // + which, Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, null);
                    builder.create().show();
                }
            });
        }
					mDailyForecastView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							ApiManager.loadAreaData(getActivity(), new ApiManager.LoadAreaDataListener() {
								@Override
								public void onLoaded(final ApiManager.AreaData areaData) {
									AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
									builder.setTitle("AreaList");
									builder.setAdapter(new ArrayAdapter<ApiManager.Area>(getActivity(),
											android.R.layout.simple_list_item_1, areaData.list),
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int which) {
													final ApiManager.Area a = areaData.list.get(which);
													mArea = a;
													postRefresh();
												}
											});
									builder.setPositiveButton("ok", null);
									builder.create().show();
									toast("size->" + areaData.list.size());
								}

								@Override
								public void onError() {

								}
							});
						}
					});
    }

    private void postRefresh() {
        if (pullRefreshLayout != null) {
//			UiUtil.toastDebug(getActivity(), mArea.name_cn + "postRefresh");
            Activity activity = getActivity();
            if (activity != null) {
                if (MxxNetworkUtil.isNetworkAvailable(activity))
                    pullRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pullRefreshLayout.setRefreshing(true, true);
                        }
                    }, 100);
            }

        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            checkRefresh();
        }
//		else{
//			if(mDailyForecastView!=null){
//				mDailyForecastView.resetAnimation();
//			}
//		}

    }

    private void checkRefresh() {
        if (mArea == null) {
            return;
        }
        // toast(mArea.name_cn + "checkRefresh");
        if (getUserVisibleHint()) {
            // updateDrawerTypeAndNotify();
            if (ApiManager.isNeedUpdate(mWeather)) {
                postRefresh();
            }
        }
    }

    private void updateWeatherUI() {
//		UiUtil.toastDebug(getActivity(),getCityName()+ "updateWeatherUI");
        if (!ApiManager.acceptWeather(mWeather)) {
            return;
        }
        try {
            final Weather weather = mWeather;
            updateDrawerTypeAndNotify();

            HeWeatherDataService30 w = weather.get();

            mDailyForecastView.setData(weather);
            mHourlyForecastView.setData(weather);
            mAqiView.setData(w.aqi);
            mAstroView.setData(weather);
            // setTextViewString(R.id.w_city, w.basic.city);
            final String tmp = w.now.tmp;
            try {
                final int tmp_int = Integer.valueOf(tmp);
                if (tmp_int < 0) {
                    setTextViewString(R.id.w_now_tmp, String.valueOf(-tmp_int));
                    mRootView.findViewById(R.id.w_now_tmp_minus).setVisibility(View.VISIBLE);
                } else {
                    setTextViewString(R.id.w_now_tmp, tmp);
                    mRootView.findViewById(R.id.w_now_tmp_minus).setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                setTextViewString(R.id.w_now_tmp, tmp);
                mRootView.findViewById(R.id.w_now_tmp_minus).setVisibility(View.GONE);
            }

            setTextViewString(R.id.w_now_cond_text, w.now.cond.txt);

            if (ApiManager.isToday(w.basic.update.loc)) {
                setTextViewString(R.id.w_basic_update_loc, w.basic.update.loc.substring(11) + " 发布");
            } else {
                setTextViewString(R.id.w_basic_update_loc, w.basic.update.loc.substring(5) + " 发布");
            }

            setTextViewString(R.id.w_todaydetail_bottomline, w.now.cond.txt + "  " + mWeather.getTodayTempDescription());
            setTextViewString(R.id.w_todaydetail_temp, w.now.tmp + "°");
//			try {
//				((ImageView)mRootView.findViewById(R.id.w_todaydetail_cond_imageview))
//					.setImageResource(getCondIconDrawableId(mWeather));
//			} catch (Exception e) {
//			}

            setTextViewString(R.id.w_now_fl, w.now.fl + "°");
            setTextViewString(R.id.w_now_hum, w.now.hum + "%");// 湿度
            setTextViewString(R.id.w_now_vis, w.now.vis + "km");// 能见度
            // setTextViewString(R.id.w_now_wind_dir, w.now.wind.dir);
            // setTextViewString(R.id.w_now_wind_sc, w.now.wind.sc);
            // setTextViewString(R.id.w_now_pres, w.now.pres);
            setTextViewString(R.id.w_now_pcpn, w.now.pcpn + "mm"); // 降雨量

            if (weather.hasAqi()) {
                setTextViewString(R.id.w_aqi_text, w.aqi.city.qlty);

                setTextViewString(R.id.w_aqi_detail_text, w.aqi.city.qlty);
                setTextViewString(R.id.w_aqi_pm25, w.aqi.city.pm25 + "μg/m³");
                setTextViewString(R.id.w_aqi_pm10, w.aqi.city.pm10 + "μg/m³");
                setTextViewString(R.id.w_aqi_so2, w.aqi.city.so2 + "μg/m³");
                setTextViewString(R.id.w_aqi_no2, w.aqi.city.no2 + "μg/m³");

            } else {
                setTextViewString(R.id.w_aqi_text, "");
            }
            if (w.suggestion != null) {
                setTextViewString(R.id.w_suggestion_comf, w.suggestion.comf.txt);
                setTextViewString(R.id.w_suggestion_cw, w.suggestion.cw.txt);
                setTextViewString(R.id.w_suggestion_drsg, w.suggestion.drsg.txt);
                setTextViewString(R.id.w_suggestion_flu, w.suggestion.flu.txt);
                setTextViewString(R.id.w_suggestion_sport, w.suggestion.sport.txt);
                setTextViewString(R.id.w_suggestion_tarv, w.suggestion.trav.txt);
                setTextViewString(R.id.w_suggestion_uv, w.suggestion.uv.txt);

                setTextViewString(R.id.w_suggestion_comf_brf, w.suggestion.comf.brf);
                setTextViewString(R.id.w_suggestion_cw_brf, w.suggestion.cw.brf);
                setTextViewString(R.id.w_suggestion_drsg_brf, w.suggestion.drsg.brf);
                setTextViewString(R.id.w_suggestion_flu_brf, w.suggestion.flu.brf);
                setTextViewString(R.id.w_suggestion_sport_brf, w.suggestion.sport.brf);
                setTextViewString(R.id.w_suggestion_tarv_brf, w.suggestion.trav.brf);
                setTextViewString(R.id.w_suggestion_uv_brf, w.suggestion.uv.brf);
            }

        } catch (Exception e) {
            e.printStackTrace();
            toast(mArea.name_cn + " Error\n" + e.toString());

        }

    }

    @Override
    public void onDestroy() {
        // textviews.clear();
        super.onDestroy();
    }

    // private SparseArray<TextView> textviews = new SparseArray<TextView>();

    private void setTextViewString(int textViewId, String str) {
        // TextView tv = textviews.get(textViewId);
        // if(tv == null){
        // tv = (TextView) mRootView.findViewById(textViewId);
        // textviews.put(textViewId, tv);
        // }
        // tv.setText(str);
        TextView tv = (TextView) mRootView.findViewById(textViewId);
        if (tv != null) {
            tv.setText(str);
        } else {
            toast("Error NOT found textView id->" + Integer.toHexString(textViewId));
        }
    }

    @Override
    public String getTitle() {
        return getCityName();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkRefresh();
    }

    @Override
    public void onSelected() {
        // checkRefresh();
    }

    public static int getCondIconDrawableId(final Weather weather) {
        if (weather == null || !weather.isOK()) {
            return R.drawable.cond_icon_na;
        }
        final boolean isNight = ApiManager.isNight(weather);
        try {
            final int w = Integer.valueOf(weather.get().now.cond.code);
            switch (w) {
                case 100:
                    return isNight ? R.drawable.cond_icon_sun_night : R.drawable.cond_icon_sun;
                case 101://多云
                case 102://少云
                case 103://晴间多云
                    return isNight ? R.drawable.cond_icon_cloudy_night : R.drawable.cond_icon_cloudy;
                case 104://阴
                    return R.drawable.cond_icon_overcast;
                //200 - 213是风
//			case 200:
//			case 201:
//			case 202:
//			case 203:
//			case 204:
//			case 205:
//			case 206:
//			case 207:
//			case 208:
//			case 209:
//			case 210:
//			case 211:
//			case 212:
//			case 213:
//				return isNight ? Type.WIND_N : Type.WIND_D;

                case 300://阵雨Shower Rain
                case 305://小雨	Light Rain
                case 309://毛毛雨/细雨	Drizzle Rain
                    return R.drawable.cond_icon_lightrain;
                case 302://雷阵雨	Thundershower
                case 303://强雷阵雨	Heavy Thunderstorm
                    return R.drawable.cond_icon_thundershower;
                case 304://雷阵雨伴有冰雹	Hail
                    return R.drawable.cond_icon_hail;
                case 306://中雨	Moderate Rain
                    return R.drawable.cond_icon_moderaterain;
                case 307://大雨	Heavy Rain
                case 301://强阵雨	Heavy Shower Rain
                case 308://极端降雨	Extreme Rain
                case 310://暴雨	Storm
                case 311://大暴雨	Heavy Storm
                case 312://特大暴雨	Severe Storm
                    return R.drawable.cond_icon_heavyrain;
                case 313://冻雨	Freezing Rain
                    return R.drawable.cond_icon_icerain;
                case 400://小雪 Light Snow
                case 401://中雪 Moderate Snow
                case 407://阵雪 Snow Flurry
                    return R.drawable.cond_icon_lightsnow;
                case 402://大雪 Heavy Snow
                case 403://暴雪 Snowstorm
                    return R.drawable.cond_icon_snowstorm;
                case 404://雨夹雪 Sleet
                case 405://雨雪天气 Rain And Snow
                case 406://阵雨夹雪 Shower Snow
                    return R.drawable.cond_icon_sleet;
                case 500://薄雾
                case 501://雾
                    return R.drawable.cond_icon_foggy;
                case 502://霾
                case 504://浮尘
                    return R.drawable.cond_icon_haze;
                case 503://扬沙
                case 506://火山灰
                case 507://沙尘暴
                case 508://强沙尘暴
                    return R.drawable.cond_icon_sand;
                default:
                    return R.drawable.cond_icon_na;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.drawable.cond_icon_na;
    }
}
