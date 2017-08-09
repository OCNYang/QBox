package com.ocnyang.qbox.app.module.me.weather.dynamicweather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class CloudyDrawer extends BaseDrawer {

	// final ArrayList<CloudHolder> holders = new ArrayList<CloudHolder>();
	final ArrayList<CircleHolder> holders = new ArrayList<CircleHolder>();
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public CloudyDrawer(Context context, boolean isNight) {
		super(context, isNight);

	}

	@Override
	protected void setSize(int width, int height) {
		super.setSize(width, height);
		if (holders.size() == 0) {
			holders.add(new CircleHolder(0.20f * width, -0.30f * width, 0.06f * width, 0.022f * width, 0.56f * width,
					0.0015f, isNight ? 0x183c6b8c : 0x28ffffff));
			holders.add(new CircleHolder(0.58f * width, -0.35f * width, -0.15f * width, 0.032f * width, 0.6f * width,
					0.00125f, isNight ? 0x223c6b8c : 0x33ffffff));
			holders.add(new CircleHolder(0.9f * width, -0.19f * width, 0.08f * width, -0.015f * width, 0.44f * width,
					0.0025f, isNight ? 0x153c6b8c : 0x15ffffff));
		}
	}

	@Override
	public boolean drawWeather(Canvas canvas, float alpha) {
			for (CircleHolder holder : this.holders) {
				holder.updateAndDraw(canvas, paint, alpha);
			}
		return true;
	}

	@Override
	protected int[] getSkyBackgroundGradient() {
		return isNight ? SkyBackground.CLOUDY_N : SkyBackground.CLOUDY_D;
	}

	public static class CircleHolder {
		private final float cx, cy, dx, dy, radius;
		private final int color;
		private boolean isGrowing = true;
		private float curPercent = 0f;
		private final float percentSpeed;

		public CircleHolder(float cx, float cy, float dx, float dy, float radius, float percentSpeed, int color) {
			super();
			this.cx = cx;
			this.cy = cy;
			this.dx = dx;
			this.dy = dy;
			this.radius = radius;
			this.percentSpeed = percentSpeed;
			this.color = color;
		}

		public void updateAndDraw(Canvas canvas, Paint paint, float alpha) {
			float randomPercentSpeed = getRandom(percentSpeed * 0.7f, percentSpeed * 1.3f);
			if (isGrowing) {
				curPercent += randomPercentSpeed;
				if (curPercent > 1f) {
					curPercent = 1f;
					isGrowing = false;
				}
			} else {
				curPercent -= randomPercentSpeed;
				if (curPercent < 0f) {
					curPercent = 0f;
					isGrowing = true;
				}
			}
			float curCX = cx + dx * curPercent;
			float curCY = cy + dy * curPercent;
			int curColor = convertAlphaColor(alpha * (Color.alpha(color) / 255f), color);
			paint.setColor(curColor);
			canvas.drawCircle(curCX, curCY, radius, paint);

		}

	}

	public static class CloudHolder {
		private final Drawable drawable;
		private final float percentWidthPerframe;
		final float screenWidth;
		final float drawableWidth, drawableHeight;
		float curX;// screenWidth-drawableWidth ~ 0之间的 就是说drawable的宽度必须全部在屏幕内
		final float minX;
		final float maxAlpha;
		final boolean canLoop;

		/**
		 * drawableWidth 必须>ScreenWidth
		 * 
		 * @param drawable
		 * @param percentWidthPerframe
		 *            相对于drawableWidth每一帧移动的距离,screenWidth*2时大约 0.0002f
		 * @param screenWidth
		 * @param drawableWidth
		 * @param maxAlpha
		 * @param canLoop 是否可以循环移动
		 *            （如果左边正好接上右边就设置为true)
		 */
		public CloudHolder(Drawable drawable, float percentWidthPerframe, float screenWidth, float drawableWidth,
				float maxAlpha, final boolean canLoop) {
			this.drawable = drawable;
			this.percentWidthPerframe = percentWidthPerframe;
			this.screenWidth = screenWidth;
			if (drawableWidth < screenWidth) {
				drawableWidth = screenWidth * 1.1f;
			}
			float scale = drawableWidth / drawable.getIntrinsicWidth();
			this.drawableWidth = drawableWidth;
			this.drawableHeight = drawable.getIntrinsicHeight() * scale;
			minX = this.screenWidth - this.drawableWidth;
			this.curX = getRandom(minX, 0);
			this.maxAlpha = maxAlpha;
			this.canLoop = canLoop;
		}

		public void updateAndDraw(Canvas canvas, float alpha) {
			curX -= percentWidthPerframe * drawableWidth * getRandom(0.5f, 1);
			if (curX < minX) {
				curX = 0f;
			}
			float curAlpha = 1f;
			if (!canLoop) {
				float percent = Math.abs(curX / minX);
				curAlpha = (percent > 0.5f) ? ((1 - percent) / 0.5f) : (percent / 0.5f);
				curAlpha = fixAlpha(curAlpha) * maxAlpha;
			}
			drawable.setAlpha(Math.round(alpha * 255f * curAlpha));
			final int left = Math.round(curX);
			drawable.setBounds(left, 0, Math.round(left + drawableWidth), Math.round(drawableHeight));
			drawable.draw(canvas);

		}
	}

}
