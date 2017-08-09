package com.ocnyang.qbox.app.module.me.weather.weather.api.entity;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ocnyang.qbox.app.module.me.weather.weather.api.ApiManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Weather implements Serializable{

	private static final long serialVersionUID = -821374811106598097L;
	
	@SerializedName("HeWeather5")
	@Expose
	public List<com.ocnyang.qbox.app.module.me.weather.weather.api.entity.HeWeatherDataService30> HeWeatherDataService30 = new ArrayList<com.ocnyang.qbox.app.module.me.weather.weather.api.entity.HeWeatherDataService30>(1);

	public boolean isOK() {
		if (this.HeWeatherDataService30.size() > 0) {
			final HeWeatherDataService30 dataService30 = this.HeWeatherDataService30.get(0);
			return TextUtils.equals(dataService30.status, "ok");
		}
		return false;
	}

	public boolean hasAqi() {
		if (isOK()) {
			final HeWeatherDataService30 h = get();
			return h.aqi != null && h.aqi.city != null;
		}
		return true;
	}

	public HeWeatherDataService30 get() {
		// if(this.HeWeatherDataService30.size() > 0){
		return this.HeWeatherDataService30.get(0);
		// }
		// return null;
	}
	
	
	/**
	 * 出错返回-1
	 * @returnTodayDailyForecastIndex
	 */
	public int getTodayDailyForecastIndex(){
		int todayIndex = -1;
		if(!isOK()){
			return todayIndex;
		}
		final HeWeatherDataService30 w = get();
		for(int i = 0;i< w.dailyForecast.size() ; i++){
			if(ApiManager.isToday(w.dailyForecast.get(i).date)){
				todayIndex = i;
				break;
			}
		}
		return todayIndex;
	}
	/**
	 * 出错返回null
	 * @return Today DailyForecast
	 */
	public DailyForecast getTodayDailyForecast(){
		final int todayIndex = getTodayDailyForecastIndex();
		if(todayIndex != -1){
			DailyForecast forecast = get().dailyForecast.get(todayIndex);
			return forecast;
		}
		return null;
	}
	/**
	 * 今天的气温 6~16°
	 * @return
	 */
	public String getTodayTempDescription(){
		final int todayIndex = getTodayDailyForecastIndex();
		if(todayIndex != -1){
			DailyForecast forecast = get().dailyForecast.get(todayIndex);
			return forecast.tmp.min + "~" + forecast.tmp.max + "°";
		}
		return "";
	}
	
	public static String prettyUpdateTime(HeWeatherDataService30 w){
		try {
			if (ApiManager.isToday(w.basic.update.loc)) {
				return w.basic.update.loc.substring(11) + " 发布";
			} else {
				return  w.basic.update.loc.substring(5) + " 发布";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "? 发布";
	}
}
