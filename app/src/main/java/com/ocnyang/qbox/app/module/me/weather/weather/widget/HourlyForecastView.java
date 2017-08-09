package com.ocnyang.qbox.app.module.me.weather.weather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.ocnyang.qbox.app.module.me.weather.weather.WeatherActivity;
import com.ocnyang.qbox.app.module.me.weather.weather.api.ApiManager;
import com.ocnyang.qbox.app.module.me.weather.weather.api.entity.HourlyForecast;
import com.ocnyang.qbox.app.module.me.weather.weather.api.entity.Weather;

import java.util.ArrayList;


/**
 * 一天24h预报
 * 12行
 * 12 * 12 = 144dp
 *
 */
public class HourlyForecastView extends View {

	private int width, height;
	// private float percent = 0f;;
	private final float density;
	private ArrayList<HourlyForecast> forecastList;
	private Path tmpPath = new Path();
	private Path goneTmpPath = new Path();
	private Data[] datas;
	private final int full_data_count = 9;//理论上有8个数据（从1：00到22:00每隔3小时共8个数据）(添加第一列显示行的名称)，但是api只会返回现在的时间之后的
	private final DashPathEffect dashPathEffect;
	
	private final TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

	public class Data {
		public float offsetPercent;// , maxOffsetPercent;// 差值%
		public int tmp;// , tmp_min;
		/** 2015-11-05 04:00 **/
		public String date;
		public String wind_sc;
		/** 降水概率 **/
		public String pop;
	}

	public HourlyForecastView(Context context, AttributeSet attrs) {
		super(context, attrs);
		density = context.getResources().getDisplayMetrics().density;
		dashPathEffect = new DashPathEffect(new float[]{density * 3,density * 3}, 1);
		if (isInEditMode()) {
			return;
		}
		init(context);
	}

	private void init(Context context) {
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(1f * density);
		paint.setTextSize(12f * density);
		paint.setStyle(Style.FILL);
		paint.setTextAlign(Align.CENTER);
		paint.setTypeface(WeatherActivity.getTypeface(context));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		if (isInEditMode()) {
			return;
		}

		paint.setStyle(Style.FILL);

		// 一共需要 顶部文字2(+图占4行)+底部文字0 + 【间距1 + 日期1 + 间距0.5 +　晴1 + 间距0.5f + 微风1 +
		// 底部边距1f 】 = 12行
		// 12 13 14 14.5 15.5 16 17 18 都-6

		final float textSize = this.height / 12f;
		paint.setTextSize(textSize);
		final float textOffset = getTextPaintOffset(paint);
		final float dH = textSize * 4f;
		final float dCenterY = textSize * 4f;
		if (datas == null || datas.length <= 1) {
			canvas.drawLine(0, dCenterY, this.width, dCenterY, paint);// 没有数据的情况下只画一条线
			return;
		}
		final float dW = this.width * 1f / full_data_count;//datas.length;

		tmpPath.reset();
		goneTmpPath.reset();
		final int length = datas.length;
		float[] x = new float[length];
		float[] y = new float[length];

		final float textPercent = 1f;//(percent >= 0.6f) ? ((percent - 0.6f) / 0.4f) : 0f;
		final float pathPercent = 1f;//(percent >= 0.6f) ? 1f : (percent / 0.6f);

		final float smallerHeight = 4 * textSize;
		final float smallerPercent = 1 - smallerHeight / 2f / dH;
		paint.setAlpha((int) (255 * textPercent));
		final int data_length_offset = Math.max(0, full_data_count - length);
		for (int i = 0; i < length; i++) {
			final Data d = datas[i];
			final int index = i + data_length_offset;
			x[i] = index * dW + dW / 2f;
			y[i] = dCenterY - d.offsetPercent * dH * smallerPercent;
			// ///draw the froecast data'text
			canvas.drawText(d.tmp + "°", x[i], y[i] - textSize + textOffset, paint);
			
			// 降水概率Java字符'\ue612'xml字符&#xe612;
			if(i == 0){
				final float i0_x = dW/2f;
				canvas.drawText("时间", i0_x, textSize * 7.5f + textOffset, paint);// 日期d.date.substring(5)
				canvas.drawText("降水率",  i0_x, textSize * 9f + textOffset, paint);
				canvas.drawText("风力", i0_x, textSize * 10.5f + textOffset, paint);// 微风
			}
			canvas.drawText(d.date.substring(11), x[i], textSize * 7.5f + textOffset, paint);// 日期d.date.substring(5)
			canvas.drawText( d.pop + "%", x[i], textSize * 9f + textOffset, paint);
			canvas.drawText(d.wind_sc, x[i], textSize * 10.5f + textOffset, paint);// 微风
		}
		paint.setAlpha(255);
		paint.setStyle(Style.STROKE);
		final float data_x0 = data_length_offset * dW;
		
		//draw gone tmp path
		goneTmpPath.moveTo(0,  y[0]);
		goneTmpPath.lineTo(data_x0, y[0]);
		paint.setPathEffect(dashPathEffect);
		canvas.drawPath(goneTmpPath, paint);
		
		for (int i = 0; i < (length - 1); i++) {
			final float midX = (x[i] + x[i + 1]) / 2f;
			final float midY = (y[i] + y[i + 1]) / 2f;
			if (i == 0) {
				tmpPath.moveTo(data_x0, y[i]);
			}
			tmpPath.cubicTo(x[i] - 1, y[i], x[i], y[i], midX, midY);

			if (i == (length - 2)) {
				tmpPath.cubicTo(x[i + 1] - 1, y[i + 1], x[i + 1], y[i + 1], this.width, y[i + 1]);
			}
		}
		// draw tmp path
		
		final boolean needClip = pathPercent < 1f;
		if (needClip) {
			canvas.save();
			canvas.clipRect(0, 0, this.width * pathPercent, this.height);
		}
		paint.setPathEffect(null);
		canvas.drawPath(tmpPath, paint);
		if (needClip) {
			canvas.restore();
		}
//		if (percent < 1) {
//			percent += 1f / 40f;
//			percent = Math.min(percent, 1f);
//			ViewCompat.postInvalidateOnAnimation(this);
//		}

	}

	public void setData(Weather weather) {
		if (weather == null || !weather.isOK()) {
			return;
		}

		if (this.forecastList == weather.get().hourlyForecast) {
//			percent = 0f;
			invalidate();
			return;
		}
		try {
			final ArrayList<HourlyForecast> w_hourlyForecast = weather.get().hourlyForecast;
			if (w_hourlyForecast.size() == 0) {// 有可能为空
				return;
			}
			if (!ApiManager.isToday(w_hourlyForecast.get(0).date)) {// 不是今天的数据
				return;
			}
			this.forecastList = w_hourlyForecast;
			if (forecastList == null && forecastList.size() == 0) {
				return;
			}
			// this.points = new PointF[forecastList.size()];
			datas = new Data[forecastList.size()];
			int all_max = Integer.MIN_VALUE;
			int all_min = Integer.MAX_VALUE;
			for (int i = 0; i < forecastList.size(); i++) {
				HourlyForecast forecast = forecastList.get(i);
				int tmp = Integer.valueOf(forecast.tmp);
				if (all_max < tmp) {
					all_max = tmp;
				}
				if (all_min > tmp) {
					all_min = tmp;
				}
				final Data data = new Data();
				data.tmp = tmp;
				data.date = forecast.date;
				data.wind_sc = forecast.wind.sc;
				data.pop = forecast.pop;
				datas[i] = data;
			}
			float all_distance = Math.abs(all_max - all_min);
			float average_distance = (all_max + all_min) / 2f;
			// toast("all->" + all_distance + " aver->" + average_distance);
			for (Data d : datas) {
				d.offsetPercent = (d.tmp - average_distance) / all_distance;
			}
//			percent = 0f;
		} catch (Exception e) {
			e.printStackTrace();
		}
		invalidate();
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		this.width = w;
		this.height = h;
	}

	public static float getTextPaintOffset(Paint paint) {
		FontMetrics fontMetrics = paint.getFontMetrics();
		return -(fontMetrics.bottom - fontMetrics.top) / 2f - fontMetrics.top;
	}

}
