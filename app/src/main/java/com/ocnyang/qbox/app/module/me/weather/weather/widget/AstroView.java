package com.ocnyang.qbox.app.module.me.weather.weather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.ocnyang.qbox.app.module.me.weather.android.util.UiUtil;
import com.ocnyang.qbox.app.module.me.weather.weather.WeatherActivity;
import com.ocnyang.qbox.app.module.me.weather.weather.api.ApiManager;
import com.ocnyang.qbox.app.module.me.weather.weather.api.entity.DailyForecast;
import com.ocnyang.qbox.app.module.me.weather.weather.api.entity.Now;
import com.ocnyang.qbox.app.module.me.weather.weather.api.entity.Weather;

import java.util.Calendar;


/**
 * 太阳和风速
 * 
 */
public class AstroView extends View {

	private int width, height;
	private final float density;
	private final DashPathEffect dashPathEffect;
	private Path sunPath = new Path();
	private RectF sunRectF = new RectF();
	private Path fanPath = new Path();// 旋转的风扇的扇叶
	private Path fanPillarPath = new Path();// 旋转的风扇的柱子
	private float fanPillerHeight;
	private final TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
	private float paintTextOffset;
	final float offsetDegree = 15f;
	private float curRotate;// 旋转的风扇的角度
	private DailyForecast todayForecast;
	private Now now;
//	private PorterDuffXfermode clearfXfermode = new PorterDuffXfermode(Mode.CLEAR);
	private float sunArcHeight, sunArcRadius;

	private Rect visibleRect = new Rect();

	public void setData(Weather weather) {
		try {
			if (ApiManager.acceptWeather(weather)) {
				this.now = weather.get().now;
				final DailyForecast forecast = weather.getTodayDailyForecast();
				if (forecast != null) {
					todayForecast = forecast;
				}
				if (this.now != null || this.todayForecast != null) {
					invalidate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public AstroView(Context context, AttributeSet attrs) {
		super(context, attrs);
		density = context.getResources().getDisplayMetrics().density;
		dashPathEffect = new DashPathEffect(new float[] { density * 3, density * 3 }, 1);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(density);
		paint.setTextAlign(Align.CENTER);
//		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		if(isInEditMode()){
			return;
		}
		paint.setTypeface(WeatherActivity.getTypeface(context));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawColor(0x33ffffff);
		if (this.todayForecast == null || this.now == null) {
			return;
		}
		paint.setColor(Color.WHITE);
		float textSize = paint.getTextSize();
		try {
			paint.setStrokeWidth(density);
			paint.setStyle(Style.STROKE);
			// draw sun path
			paint.setColor(0x55ffffff);
			paint.setPathEffect(dashPathEffect);
			canvas.drawPath(sunPath, paint);
			paint.setPathEffect(null);
			paint.setColor(Color.WHITE);
			int saveCount = canvas.save();
			canvas.translate(width / 2f - fanPillerHeight * 1f, textSize + sunArcHeight - fanPillerHeight);
			// draw wind text
			paint.setStyle(Style.FILL);
			paint.setTextAlign(Align.LEFT);
			final float fanHeight = textSize * 2f;
			canvas.drawText("风速", fanHeight + textSize, -textSize, paint);
			canvas.drawText(now.wind.spd + "km/h " + now.wind.dir, fanHeight + textSize, 0, paint);
			// draw fan and fanPillar
			paint.setStyle(Style.STROKE);
			canvas.drawPath(fanPillarPath, paint);
			canvas.rotate(curRotate * 360f);
			float speed = 0f;
			try {
				speed = Float.valueOf(now.wind.spd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			speed = Math.max(speed, 0.75f);
			curRotate += 0.001f * speed;
			if (curRotate > 1f) {
				curRotate = 0f;
			}
			paint.setStyle(Style.FILL);
			canvas.drawPath(fanPath, paint);
			canvas.rotate(120f);
			canvas.drawPath(fanPath, paint);
			canvas.rotate(120f);
			canvas.drawPath(fanPath, paint);
			canvas.restoreToCount(saveCount);

			// draw bottom line
			paint.setStyle(Style.STROKE);
			//paint.setColor(0x55ffffff);
			final float lineLeft = width / 2f - sunArcRadius;
			canvas.drawLine(lineLeft, sunArcHeight + textSize, width - lineLeft, sunArcHeight + textSize, paint);

			// draw pressure info
			//paint.setColor(Color.WHITE);
			paint.setStyle(Style.FILL);
			paint.setTextAlign(Align.RIGHT);
			final float pressureTextRight = width / 2f + sunArcRadius - textSize * 2.5f;
			canvas.drawText("气压 " + now.pres + "hpa", pressureTextRight, sunArcHeight + paintTextOffset, paint);
			// draw astor info
			final float textLeft = width / 2f - sunArcRadius;// sunArcSize;
			paint.setTextAlign(Align.CENTER);
			canvas.drawText("日出 " + todayForecast.astro.sr, textLeft, textSize * 10.5f + paintTextOffset, paint);
			canvas.drawText(todayForecast.astro.ss + " 日落", width - textLeft, textSize * 10.5f + paintTextOffset, paint);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			// draw the sun
			String[] sr = todayForecast.astro.sr.split(":");// 日出
			int srTime = Integer.valueOf(sr[0]) * 60 * 60 + Integer.valueOf(sr[1]) * 60 + 0;// 精确到秒
			String[] ss = todayForecast.astro.ss.split(":");// 日落
			int ssTime = Integer.valueOf(ss[0]) * 60 * 60 + Integer.valueOf(ss[1]) * 60 + 0;
			Calendar c = Calendar.getInstance();
			int curTime = c.get(Calendar.HOUR_OF_DAY) * 60 * 60 + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.MINUTE);
			if (curTime >= srTime && curTime <= ssTime) {// 说明是在白天，需要画太阳
				canvas.save();
				canvas.translate(width / 2f, sunArcRadius + textSize);// 先平移到圆心
				float percent = (curTime - srTime) / ((float) (ssTime - srTime));
				float degree = 15f + 150f * percent;
				final float sunRadius = density * 4f;
				canvas.rotate(degree);// 旋转到太阳的角度

				paint.setStyle(Style.FILL);
				// canvas.drawLine(-1000, 0, 1000, 0, paint);//测试
				paint.setStrokeWidth(density * 1.333f);// 宽度是2对应半径是6
				canvas.translate(-sunArcRadius, 0);// 平移到太阳应该在的位置
				canvas.rotate(-degree);// 转正方向。。。
				// 尝试清除太阳周围的一圈，但是没有成功
				// final float clearRadius =sunRadius * 1.2f;
				// int layer = canvas.saveLayer(-clearRadius, -clearRadius,
				// clearRadius, clearRadius, paint,Canvas.ALL_SAVE_FLAG);
				// paint.setColor(Color.TRANSPARENT);
				// paint.setXfermode(clearfXfermode);
				// canvas.drawCircle(0, 0, sunRadius * 1.2f, paint);
				// canvas.restoreToCount(layer);
				// paint.setColor(Color.WHITE);
				// paint.setXfermode(null);
				canvas.drawCircle(0, 0, sunRadius, paint);
				// paint.setStrokeJoin(Paint.Join.ROUND);
				// paint.setStrokeCap(Paint.Cap.ROUND);
				paint.setStyle(Style.STROKE);
				final int light_count = 8;
				for (int i = 0; i < light_count; i++) {// 画刻度
					double radians = Math.toRadians(i * (360 / light_count));
					float x1 = (float) (Math.cos(radians) * sunRadius * 1.6f);
					float y1 = (float) (Math.sin(radians) * sunRadius * 1.6f);
					float x2 = x1 * (1f + 0.4f * 1f);
					float y2 = y1 * (1f + 0.4f * 1f);
					canvas.drawLine(0 + x1, y1, 0 + x2, y2, paint);
				}
				canvas.restore();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		getGlobalVisibleRect(visibleRect);
		if (!visibleRect.isEmpty()) {
			ViewCompat.postInvalidateOnAnimation(this);
			// Log.d(AstroView.class.getSimpleName(),
			// "postInvalidateOnAnimation");
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 间距1 图8.5行 间距0.5 字1行 间距1 = 12;
		// 9.5 10 11 12
		this.width = w;
		this.height = h;

		try {
			final float textSize = height / 12f;
			paint.setTextSize(textSize);
			paintTextOffset = UiUtil.getTextPaintOffset(paint);

			sunPath.reset();

			sunArcHeight = textSize * 8.5f;
			sunArcRadius = (float) (sunArcHeight / (1f - Math.sin(Math.toRadians(offsetDegree))));
			final float sunArcLeft = width / 2f - sunArcRadius;
			sunRectF.left = sunArcLeft;
			sunRectF.top = textSize;
			sunRectF.right = width - sunArcLeft;
			sunRectF.bottom = sunArcRadius * 2f + textSize;
			sunPath.addArc(sunRectF, -165, +150);// 圆形的最右端点为0，顺时针sweepAngle

			// fanPath和fanPillarPath的中心点在扇叶圆形的中间
			fanPath.reset();
			final float fanSize = textSize * 0.2f;// 风扇底部半圆的半径
			final float fanHeight = textSize * 2f;
			final float fanCenterOffsetY = fanSize * 1.6f;
			// fanPath.moveTo(fanSize, -fanCenterOffsetY);
			// 也可以用arcTo
			// 从右边到底部到左边了的弧
			fanPath.addArc(new RectF(-fanSize, -fanSize - fanCenterOffsetY, fanSize, fanSize - fanCenterOffsetY), 0,
					180);
			// fanPath.lineTo(0, -fanHeight - fanCenterOffsetY);
			fanPath.quadTo(-fanSize * 1f, -fanHeight * 0.5f - fanCenterOffsetY, 0, -fanHeight - fanCenterOffsetY);
			fanPath.quadTo(fanSize * 1f, -fanHeight * 0.5f - fanCenterOffsetY, fanSize, -fanCenterOffsetY);
			fanPath.close();

			fanPillarPath.reset();
			final float fanPillarSize = textSize * 0.25f;// 柱子的宽度
			fanPillarPath.moveTo(0, 0);
			fanPillerHeight = textSize * 4f;// 柱子的宽度
			fanPillarPath.lineTo(fanPillarSize, fanPillerHeight);
			fanPillarPath.lineTo(-fanPillarSize, fanPillerHeight);
			fanPillarPath.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
