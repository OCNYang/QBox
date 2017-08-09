package com.ocnyang.qbox.app.module.me.weather.dynamicweather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import java.util.ArrayList;

public class SandDrawer extends BaseDrawer{

	public SandDrawer(Context context, boolean isNight) {
		super(context, isNight);
		paint.setStyle(Style.STROKE);
	}
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	final int count = 30;
	private ArrayList<ArcHolder> holders = new ArrayList<ArcHolder>();
	
	@Override
	protected void setSize(int width, int height) {
		super.setSize(width, height);
		if(this.holders.size() == 0){
			final float cx = -width * 0.3f;
			final float cy = -width * 1.5f;
			for (int i= 0; i<count;i++){
				float radiusWidth = getRandom(width * 1.3f, width * 3.0f);
				float radiusHeight = radiusWidth * getRandom(0.92f, 0.96f);//getRandom(width * 0.02f,  width * 1.6f);
				float strokeWidth = dp2px(getDownRandFloat(1f, 2.5f));
				float sizeDegree = getDownRandFloat(8f, 15f);
				this.holders.add(new ArcHolder(cx, cy, radiusWidth,radiusHeight, strokeWidth, 30f, 99f, sizeDegree,isNight? 0x99a59056: 0xbba59056));
			}
		}
		
	}
	@Override
	public boolean drawWeather(Canvas canvas, float alpha) {
		for(ArcHolder holder : this.holders){
			holder.updateAndDraw(canvas, paint, alpha);
		}
		return true;
	}
	
	public static class ArcHolder{
		private final float cx, cy, radiusWidth, radiusHeight,strokeWidth, fromDegree,endDegree, sizeDegree;
		private final int color;
		private float curDegree;
		private final float stepDegree;
		private RectF rectF = new RectF();
		public ArcHolder(float cx, float cy, float radiusWidth,float radiusHeight, float strokeWidth, float fromDegree, float endDegree,
				float sizeDegree, int color) {
			super();
			this.cx = cx;
			this.cy = cy;
			this.radiusWidth = radiusWidth;
			this.radiusHeight = radiusHeight;
			this.strokeWidth = strokeWidth;
			this.fromDegree = fromDegree;
			this.endDegree = endDegree;
			this.sizeDegree = sizeDegree;
			this.color = color;
			this.curDegree = getRandom(fromDegree, endDegree);
			this.stepDegree = getRandom(0.4f, 0.8f);
		}
		public void updateAndDraw(Canvas canvas, Paint paint, float alpha){
			paint.setColor(convertAlphaColor(alpha * (Color.alpha(color) / 255f), color));
			paint.setStrokeWidth(strokeWidth);
			curDegree += stepDegree * getRandom(0.8f, 1.2f);
			if(curDegree > (endDegree -sizeDegree)){
				curDegree = fromDegree - sizeDegree;
			}
			float startAngle = curDegree;
			float sweepAngle = sizeDegree;
			rectF.left=cx - radiusWidth;
			rectF.top = cy - radiusHeight;
			rectF.right = cx + radiusWidth;
			rectF.bottom = cy + radiusHeight;
			canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);
		}
	}
	
	@Override
	protected int[] getSkyBackgroundGradient() {
		return isNight ? SkyBackground.SAND_N : SkyBackground.SAND_D;
	}
	
	
	
	
	
}
