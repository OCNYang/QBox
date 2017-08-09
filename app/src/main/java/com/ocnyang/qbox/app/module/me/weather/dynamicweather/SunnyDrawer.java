package com.ocnyang.qbox.app.module.me.weather.dynamicweather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;

import java.util.ArrayList;

/**
 * 晴天
 */
public class SunnyDrawer extends BaseDrawer {
	static final String TAG = SunnyDrawer.class.getSimpleName();

	private GradientDrawable drawable;
	private ArrayList<SunnyHolder> holders = new ArrayList<SunnyHolder>();
//	private SunnyHolder holder;
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public SunnyDrawer(Context context) {
		super(context, false);
//		drawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[] { 0x10ffffff, 0x20ffffff });
		drawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[] { 0x20ffffff, 0x10ffffff });
		drawable.setShape(GradientDrawable.OVAL);
		drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
//		drawable.setGradientRadius((float) (Math.sqrt(2) * 60));
		paint.setColor(0x33ffffff);
	}

	@Override
	public boolean drawWeather(Canvas canvas,float alpha) {
		final float size = width * centerOfWidth;
			for (SunnyHolder holder : holders) {
				holder.updateRandom(drawable, alpha);
				drawable.draw(canvas);
			}
		paint.setColor(Color.argb((int) (alpha * 0.18f * 255f), 0xff, 0xff, 0xff));
		canvas.drawCircle(size, size, width * 0.12f, paint);
		return true;
	}

	private static final int SUNNY_COUNT = 3;
	private final float centerOfWidth = 0.02f;
	//private static final float SUNNY_MIN_SIZE = 60f;// dp
	//private static final float SUNNY_MAX_SIZE = 500f;// dp

	@Override
	protected void setSize(int width, int height) {
		super.setSize(width, height);
		if (this.holders.size() == 0) {
			final float minSize = width * 0.16f;//dp2px(SUNNY_MIN_SIZE);
			final float maxSize = width * 1.5f;//dp2px(SUNNY_MAX_SIZE);
			final float center = width * centerOfWidth;
			float deltaSize = (maxSize - minSize) / SUNNY_COUNT;
			for (int i = 0; i < SUNNY_COUNT; i++) {
				final float curSize = maxSize - i * deltaSize * getRandom(0.9f, 1.1f);
				SunnyHolder holder = new SunnyHolder(center, center, curSize, curSize);
				holders.add(holder);
			}
		}
//		if(this.holder == null){
//			final float center = width * 0.25f;
//			final float size = width * 0.3f;
//			holder = new SunnyHolder(center, center, size, size);
//		}
	}

	public static class SunnyHolder {
		public float x;
		public float y;
		public float w;
		public float h;
		public final float maxAlpha = 1f;
		public float curAlpha;// [0,1]
		public boolean alphaIsGrowing = true;
		private final float minAlpha = 0.5f;

		public SunnyHolder(float x, float y, float w, float h) {
			super();
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.curAlpha = getRandom(minAlpha, maxAlpha);
		}

		public void updateRandom(GradientDrawable drawable,float alpha) {
			// curAlpha += getRandom(-0.01f, 0.01f);
			// curAlpha = Math.max(0f, Math.min(maxAlpha, curAlpha));
			final float delta = getRandom(0.002f * maxAlpha, 0.005f * maxAlpha);
			if (alphaIsGrowing) {
				curAlpha += delta;
				if (curAlpha > maxAlpha) {
					curAlpha = maxAlpha;
					alphaIsGrowing = false;
				}
			} else {
				curAlpha -= delta;
				if (curAlpha < minAlpha) {
					curAlpha = minAlpha;
					alphaIsGrowing = true;
				}
			}

			final int left = Math.round(x - w / 2f);
			final int right = Math.round(x + w / 2f);
			final int top = Math.round(y - h / 2f);
			final int bottom = Math.round(y + h / 2f);
			drawable.setBounds(left, top, right, bottom);
			drawable.setGradientRadius(w / 2.2f);
			drawable.setAlpha((int) (255 * curAlpha * alpha));
		}
	}
}
