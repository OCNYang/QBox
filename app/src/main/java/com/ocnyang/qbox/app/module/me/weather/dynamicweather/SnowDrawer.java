package com.ocnyang.qbox.app.module.me.weather.dynamicweather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;

import java.util.ArrayList;

/**
 * 下雪
 */
public class SnowDrawer extends BaseDrawer {
	static final String TAG = SnowDrawer.class.getSimpleName();

	private GradientDrawable drawable;
	private ArrayList<SnowHolder> holders = new ArrayList<SnowHolder>();

	private static final int COUNT = 30;
	private static final float MIN_SIZE = 12f;// dp
	private static final float MAX_SIZE = 30f;// dp

	public SnowDrawer(Context context, boolean isNight) {
		super(context, isNight);
		drawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[] { 0x99ffffff, 0x00ffffff });
		drawable.setShape(GradientDrawable.OVAL);
		drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
	}

	@Override
	public boolean drawWeather(Canvas canvas, float alpha) {
		for (SnowHolder holder : holders) {
			holder.updateRandom(drawable, alpha);
			drawable.draw(canvas);
		}
		return true;
	}

	@Override
	protected void setSize(int width, int height) {
		super.setSize(width, height);
		if (this.holders.size() == 0) {
			final float minSize = dp2px(MIN_SIZE);
			final float maxSize = dp2px(MAX_SIZE);
			final float speed = dp2px(80);// 40当作中雪80
			for (int i = 0; i < COUNT; i++) {
				final float size = getRandom(minSize, maxSize);
				SnowHolder holder = new SnowHolder(getRandom(0, width), size, height, speed);
				holders.add(holder);
			}
		}
	}
	@Override
	protected int[] getSkyBackgroundGradient() {
		return isNight ? SkyBackground.SNOW_N : SkyBackground.SNOW_D;
	}

	public static class SnowHolder {
		public float x;
		// public float y;//y 表示雨滴底部的y坐标,由curTime求得
		public final float snowSize;
		public final float maxY;// [0,1]
		public float curTime;// [0,1]
		public final float v;// 速度

		/**
		 * @param x
		 * @param snowSize
		 * @param maxY
		 * @param averageSpeed
		 */
		public SnowHolder(float x, float snowSize, float maxY, float averageSpeed) {
			super();
			this.x = x;
			this.snowSize = snowSize;
			this.maxY = maxY;
			this.v = averageSpeed * getRandom(0.85f, 1.15f);
			final float maxTime = maxY / this.v;
			this.curTime = getRandom(0, maxTime);
		}

		public void updateRandom(GradientDrawable drawable, float alpha) {
			curTime += 0.025f;
			float curY = curTime * this.v;
			if ((curY - this.snowSize) > this.maxY) {
				this.curTime = 0f;
			}
			final int left = Math.round(x - snowSize / 2f);
			final int right = Math.round(x + snowSize / 2f);
			final int top = Math.round(curY - snowSize);
			final int bottom = Math.round(curY);
			drawable.setBounds(left, top, right, bottom);
			drawable.setGradientRadius(snowSize / 2.2f);
			drawable.setAlpha((int) (255 * alpha));
		}
	}
}
