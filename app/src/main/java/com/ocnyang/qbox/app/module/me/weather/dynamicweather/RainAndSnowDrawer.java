package com.ocnyang.qbox.app.module.me.weather.dynamicweather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;

import com.ocnyang.qbox.app.module.me.weather.dynamicweather.RainDrawer.RainDrawable;
import com.ocnyang.qbox.app.module.me.weather.dynamicweather.RainDrawer.RainHolder;
import com.ocnyang.qbox.app.module.me.weather.dynamicweather.SnowDrawer.SnowHolder;

import java.util.ArrayList;
/**
 * 雨夹雪
 */
public class RainAndSnowDrawer extends BaseDrawer {
	static final String TAG = RainAndSnowDrawer.class.getSimpleName();

	private GradientDrawable snowDrawable;
	private RainDrawer.RainDrawable rainDrawable;
	private ArrayList<SnowHolder> snowHolders = new ArrayList<SnowHolder>();
	private ArrayList<RainHolder> rainHolders = new ArrayList<RainHolder>();

	private static final int SNOW_COUNT = 15;
	private static final int RAIN_COUNT = 30;
	private static final float MIN_SIZE = 6f;// dp
	private static final float MAX_SIZE = 14f;// dp

	public RainAndSnowDrawer(Context context, boolean isNight) {
		super(context, isNight);
		snowDrawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[] { 0x99ffffff, 0x00ffffff });
		snowDrawable.setShape(GradientDrawable.OVAL);
		snowDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
		rainDrawable = new RainDrawable();
	}

	@Override
	public boolean drawWeather(Canvas canvas, float alpha) {
			for (SnowHolder holder : snowHolders) {
				holder.updateRandom(snowDrawable, alpha);
				snowDrawable.draw(canvas);
			}
			for (RainHolder holder : rainHolders) {
				holder.updateRandom(rainDrawable, alpha);
				rainDrawable.draw(canvas);
			}
		return true;
	}

	@Override
	protected void setSize(int width, int height) {
		super.setSize(width, height);
		if (this.snowHolders.size() == 0) {
			final float minSize = dp2px(MIN_SIZE);
			final float maxSize = dp2px(MAX_SIZE);
			final float speed = dp2px(200);// 40当作中雪
			for (int i = 0; i < SNOW_COUNT; i++) {
				final float size = getRandom(minSize, maxSize);
				SnowHolder holder = new SnowHolder(getRandom(0, width), size, height, speed);
				snowHolders.add(holder);
			}
		}
		if (this.rainHolders.size() == 0) {
			final float rainWidth =  dp2px(2);//*(1f -  getDownRandFloat(0, 1));
			final float minRainHeight = dp2px(8);
			final float maxRainHeight = dp2px(14);
			final float speed = dp2px(360);
			for (int i = 0; i < RAIN_COUNT; i++) {
				float x = getRandom(0f, width);
				RainHolder holder = new RainHolder(x, rainWidth, minRainHeight, maxRainHeight, height,speed);
				rainHolders.add(holder);
			}
		}
	}

	@Override
	protected int[] getSkyBackgroundGradient() {
		return isNight ? SkyBackground.RAIN_N : SkyBackground.RAIN_D;
	}
}
