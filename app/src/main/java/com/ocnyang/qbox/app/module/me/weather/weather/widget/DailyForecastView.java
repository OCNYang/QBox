package com.ocnyang.qbox.app.module.me.weather.weather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.ocnyang.qbox.app.module.me.weather.weather.WeatherActivity;
import com.ocnyang.qbox.app.module.me.weather.weather.api.ApiManager;
import com.ocnyang.qbox.app.module.me.weather.weather.api.entity.DailyForecast;
import com.ocnyang.qbox.app.module.me.weather.weather.api.entity.Weather;

import java.util.ArrayList;


/**
 * 一周天气预报
 * 按文字算高度18行
 * 文字设置为12，高度是216dp
 *	
 */
public class DailyForecastView extends View {

	private int width, height;
	private float percent = 0f;;
	private final float density;
	private ArrayList<DailyForecast> forecastList;
	private Path tmpMaxPath = new Path();
	private Path tmpMinPath = new Path();
//	private PathMeasure tmpMaxPathMeasure = new PathMeasure(tmpMaxPath, false);
	// private PointF[] points ;
	private Data[] datas;
//	private final float textSize;

	private final TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

	public class Data {
		public float minOffsetPercent, maxOffsetPercent;// 差值%
		public int tmp_max, tmp_min;
		public String date;
		public String wind_sc;
		public String cond_txt_d;
	}

	public DailyForecastView(Context context, AttributeSet attrs) {
		super(context, attrs);
		density = context.getResources().getDisplayMetrics().density;
		if(isInEditMode()){
			return ;
		}
		init(context);
	}
	
	public void resetAnimation(){
		percent = 0f;
		invalidate();
	}

	private void init(Context context) {
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(1f * density);
		paint.setTextSize(12f * density);
		paint.setStyle(Style.FILL);
		paint.setTextAlign(Align.CENTER);
		paint.setTypeface(WeatherActivity.getTypeface(context));
	}
	//220dp 18hang
	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		if(isInEditMode()){
			return ;
		}
		paint.setStyle(Style.FILL);
		//一共需要 顶部文字2(+图占8行)+底部文字2 + 【间距1 + 日期1 + 间距0.5 +　晴1 + 间距0.5f + 微风1 + 底部边距1f 】 = 18行
		//                                  12     13       14      14.5    15.5      16      17       18      
		final float textSize = this.height / 18f;
		paint.setTextSize(textSize); 
		final float textOffset = getTextPaintOffset(paint);
		final float dH = textSize * 8f;
		final float dCenterY = textSize * 6f ;
		if (datas == null || datas.length <= 1) {
			canvas.drawLine(0, dCenterY, this.width, dCenterY, paint);//没有数据的情况下只画一条线
			return;
		}
		final float dW = this.width * 1f / datas.length;
//		for (int i = 0; i < (datas.length - 1); i++) {
//			final Data leftD = datas[i];
//			final Data rightD = datas[i+1];
//			///////////////max tmp line///////////
//			final float startX = i * dW + dW / 2f;
//			final float startY = dCenterY - leftD.maxOffsetPercent * dH;
//			final float stopX = startX + dW;
//			final float stopY = dCenterY - rightD.maxOffsetPercent * dH;
//			canvas.drawLine(startX, startY, stopX, stopY, paint);
//			
//			///////////////min tmp line///////////////
//			final float startX1 = startX;
//			final float startY1 = dCenterY - leftD.minOffsetPercent * dH;
//			final float stopX1 = stopX;
//			final float stopY1 = dCenterY - rightD.minOffsetPercent * dH;
//			canvas.drawLine(startX1, startY1, stopX1, stopY1, paint);
//			
//		}
		tmpMaxPath.reset();
		tmpMinPath.reset();
		final int length = datas.length;
		float[] x = new float[length];
		float[] yMax = new float[length];
		float[] yMin = new float[length];
		
		final float textPercent = (percent >= 0.6f) ? ((percent - 0.6f) / 0.4f) : 0f;
		final float pathPercent = (percent >= 0.6f) ? 1f : (percent / 0.6f);
		
		//画底部的三行文字和标注最高最低温度
		paint.setAlpha((int) (255 * textPercent));
		for (int i = 0; i < length; i++) {
			final Data d = datas[i];
			x[i] = i * dW + dW / 2f;;
			yMax[i] = dCenterY - d.maxOffsetPercent * dH;
			yMin[i] = dCenterY - d.minOffsetPercent * dH;
			
			canvas.drawText(d.tmp_max + "°", x[i], yMax[i] - textSize + textOffset, paint);// - textSize 
			canvas.drawText(d.tmp_min + "°", x[i], yMin[i] + textSize  + textOffset, paint);
			canvas.drawText(ApiManager.prettyDate(d.date), x[i], textSize * 13.5f + textOffset, paint);//日期d.date.substring(5)
			canvas.drawText(d.cond_txt_d + "", x[i], textSize * 15f + textOffset, paint);//“晴"
			canvas.drawText(d.wind_sc, x[i],textSize * 16.5f + textOffset, paint);//微风
		
		}
		paint.setAlpha(255);
		
		for (int i = 0; i < (length - 1); i++) {
			final float midX = (x[i] + x[i + 1]) / 2f;
			final float midYMax = (yMax[i] + yMax[i + 1]) / 2f;
			final float midYMin = (yMin[i] + yMin[i + 1]) / 2f;
			if(i == 0){
				tmpMaxPath.moveTo(0, yMax[i]);
				tmpMinPath.moveTo(0, yMin[i]);
			}
			tmpMaxPath.cubicTo(x[i]-1, yMax[i],x[i], yMax[i], midX, midYMax);
//			tmpMaxPath.quadTo(x[i], yMax[i], midX, midYMax);
			tmpMinPath.cubicTo(x[i]-1, yMin[i],x[i], yMin[i], midX, midYMin);
//			tmpMinPath.quadTo(x[i], yMin[i], midX, midYMin);
			
			if(i == (length - 2)){
				tmpMaxPath.cubicTo(x[i + 1]-1, yMax[i + 1], x[i + 1], yMax[i + 1], this.width, yMax[i + 1]);
				tmpMinPath.cubicTo(x[i + 1]-1, yMin[i + 1], x[i + 1], yMin[i + 1], this.width, yMin[i + 1]);
			}
		}
		//draw max_tmp and min_tmp path
		paint.setStyle(Style.STROKE);
		final boolean needClip = pathPercent < 1f;
		if(needClip){
			canvas.save();
			canvas.clipRect( 0 , 0, this.width * pathPercent, this.height);
			//canvas.drawColor(0x66ffffff);
		}
		canvas.drawPath(tmpMaxPath, paint);
		canvas.drawPath(tmpMinPath, paint);
		if(needClip){
			canvas.restore();
		}
		if(percent < 1){
			percent += 0.025f;// 0.025f;
			percent = Math.min(percent, 1f);
			ViewCompat.postInvalidateOnAnimation(this);
		}
		
		
	}
	//private Rect rect = new Rect();

	public void setData(Weather weather) {
		if(weather==null || !weather.isOK()){
			return ;
		}
		
		if(this.forecastList == weather.get().dailyForecast){
			percent = 0f;
			invalidate();
			return ;
		}
		this.forecastList = weather.get().dailyForecast;
		if (forecastList == null && forecastList.size() == 0) {
			return;
		}
		// this.points = new PointF[forecastList.size()];
		datas = new Data[forecastList.size()];
		try {
			int all_max = Integer.MIN_VALUE;
			int all_min = Integer.MAX_VALUE;
			for (int i = 0; i < forecastList.size(); i++) {
				DailyForecast forecast = forecastList.get(i);
				int max = Integer.valueOf(forecast.tmp.max);
				int min = Integer.valueOf(forecast.tmp.min);
				if (all_max < max) {
					all_max = max;
				}
				if (all_min > min) {
					all_min = min;
				}
				final Data data = new Data();
				data.tmp_max = max;
				data.tmp_min = min;
				data.date = forecast.date;
				data.wind_sc = forecast.wind.sc;
				data.cond_txt_d = forecast.cond.txtD;
				datas[i] = data;
			}
			float all_distance = Math.abs(all_max - all_min);
			float average_distance = (all_max + all_min) / 2f;
//		toast("all->" + all_distance + " aver->" + average_distance);
			for (Data d : datas) {
				d.maxOffsetPercent = (d.tmp_max - average_distance) / all_distance;
				d.minOffsetPercent = (d.tmp_min - average_distance) / all_distance;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		getGlobalVisibleRect(rect);
//		if(rect.isEmpty()){
//			percent = 1f;
//		}else{
			percent = 0f;
//		}
		invalidate();
	}
//	private void toast(String msg){
//		Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
//	}

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
