package com.ocnyang.qbox.app.module.me.weather.weather;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ocnyang.qbox.app.module.me.weather.dynamicweather.BaseDrawer;

public abstract class BaseWeatherFragment extends Fragment{

	public abstract String getTitle();
	public abstract void onSelected();
	public abstract BaseDrawer.Type getDrawerType();
	
	protected void notifyActivityUpdate() {
		if (getUserVisibleHint()) {
			Activity activity = getActivity();
			if (activity != null) {
				((WeatherActivity) activity).updateCurDrawerType();
				//Toast.makeText(activity, getTitle() + " notifyActivityUpdate->" + getDrawerType().toString(), Toast.LENGTH_SHORT).show();
			}else{
				//toast(getTitle() + " notifyActivityUpdate getActivity() is NULL!");
			}
		}
	}
	protected void toast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}
}
