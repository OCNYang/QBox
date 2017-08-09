package com.ocnyang.qbox.app.module.me.weather.weather.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.ocnyang.qbox.app.module.me.weather.weather.WeatherActivity;

public class FontTextView extends android.support.v7.widget.AppCompatTextView{

	public FontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(isInEditMode()){
			return ;
		}
//		setIncludeFontPadding(false);
		setTypeface(WeatherActivity.getTypeface(context));
	}

}
