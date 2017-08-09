
package com.ocnyang.qbox.app.module.me.weather.weather.api.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

 
public class HeWeatherDataService30 implements Serializable{

	private static final long serialVersionUID = 1206164576046726422L;
	@SerializedName("aqi")
    @Expose
    public Aqi aqi;
    @SerializedName("basic")
    @Expose
    public Basic basic;
    @SerializedName("daily_forecast")
    @Expose
    public ArrayList<DailyForecast> dailyForecast = new ArrayList<DailyForecast>();
    @SerializedName("hourly_forecast")
    @Expose
    public ArrayList<HourlyForecast> hourlyForecast = new ArrayList<HourlyForecast>();
    @SerializedName("now")
    @Expose
    public Now now;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("suggestion")
    @Expose
    public Suggestion suggestion;

}
