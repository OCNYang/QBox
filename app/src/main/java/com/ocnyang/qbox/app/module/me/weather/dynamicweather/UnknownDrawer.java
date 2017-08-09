package com.ocnyang.qbox.app.module.me.weather.dynamicweather;

import android.content.Context;
import android.graphics.Canvas;

public class UnknownDrawer extends BaseDrawer {

	public UnknownDrawer(Context context, boolean isNight) {
		super(context, isNight);
	}

	@Override
	public boolean drawWeather(Canvas canvas, float alpha) {
		return true;//这里返回false会出现有时候不刷新的问题
	}


}
