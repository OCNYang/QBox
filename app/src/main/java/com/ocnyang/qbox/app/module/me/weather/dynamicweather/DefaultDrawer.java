package com.ocnyang.qbox.app.module.me.weather.dynamicweather;

import android.content.Context;
import android.graphics.Canvas;

public class DefaultDrawer extends BaseDrawer{
	

	public DefaultDrawer(Context context) {
		super(context, true);
	}

	@Override
	public boolean drawWeather(Canvas canvas, float alpha) {
		return false;
	}
//	@Override
//	protected void setSize(int width, int height) {
//		super.setSize(width, height);
//		defaultDrawable.setBounds(0, 0, width, height);
//	}
//	
//	@Override
//	protected void drawSkyBackground(Canvas canvas, float alpha) {
////		super.drawSkyBackground(canvas, alpha);
//		defaultDrawable.setAlpha(Math.round(alpha * 255f));
//		defaultDrawable.draw(canvas);
//	}

	@Override
	protected int[] getSkyBackgroundGradient() {
		// TODO Auto-generated method stub
		return SkyBackground.BLACK;
	}
	

}
