package com.ocnyang.qbox.app.widget.custom;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ocnyang.qbox.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScrollTextView extends SurfaceView implements SurfaceHolder.Callback {
	private final String TAG = "ScrollTextView";
	// surface Handle onto a raw buffer that is being managed by the screen compositor.
	private SurfaceHolder surfaceHolder;   //providing access and control over this SurfaceView's underlying surface.

	private Paint paint = null;
	private boolean bStop = false;          // stop scroll

	//Default value
	private boolean clickEnable = false;    // click to stop/start
	private boolean isHorizontal = true;    // horizontal｜V
	private int speed = 1;                  // scroll-speed
	private String text = "";               // scroll text
	private float textSize = 15f;           // text size
	private int textColor = Color.BLACK;    // text color
	private int times = Integer.MAX_VALUE;  // scroll times

	private int viewWidth = 0;
	private int viewHeight = 0;

	public int getSpeed() {
		return speed;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
		paint.setTextSize(textSize);
		requestLayout();
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
		paint.setColor(textColor);
	}

	public boolean isHorizontal() {
		return isHorizontal;
	}

	public void setHorizontal(boolean horizontal) {
		isHorizontal = horizontal;
	}

	private float textWidth = 0f;
	private float density = 1;
	private float textX = 0f;
	private float textY = 0f;
	private float viewWidth_plus_textLength = 0.0f;
	private int time = 0;                  //scroll times

	private ScheduledExecutorService scheduledExecutorService;


	/**
	 * constructs 1
	 *
	 * @param context you should know
	 */
	public ScrollTextView(Context context) {
		super(context);
	}

	/**
	 * constructs 2
	 *
	 * @param context
	 * @param attrs
	 */
	public ScrollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		surfaceHolder = this.getHolder();  //get The surface holder
		surfaceHolder.addCallback(this);
		paint = new Paint();
		TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.ScrollText);
		clickEnable = arr.getBoolean(R.styleable.ScrollText_clickEnable, clickEnable);
		isHorizontal = arr.getBoolean(R.styleable.ScrollText_isHorizontal, isHorizontal);
		speed = arr.getInteger(R.styleable.ScrollText_speed, speed);
		text = arr.getString(R.styleable.ScrollText_text);
		textColor = arr.getColor(R.styleable.ScrollText_textColor, textColor);
		textSize = arr.getDimension(R.styleable.ScrollText_textSize, textSize);
		times = arr.getInteger(R.styleable.ScrollText_times, times);

		time = times;
		paint.setColor(textColor);
		paint.setTextSize(textSize);

		setZOrderOnTop(true);  //Control whether the surface view's surface is placed on top of its window.
		getHolder().setFormat(PixelFormat.TRANSLUCENT);

		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
		density = metric.density;

		setFocusable(true);
	}

	/**
	 * measure text height width
	 *
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		viewWidth = MeasureSpec.getSize(widthMeasureSpec);
		viewHeight = MeasureSpec.getSize(heightMeasureSpec);
		if (isHorizontal) {
			textWidth = paint.measureText(text);
			viewWidth_plus_textLength = viewWidth + textWidth;
			textY = (viewHeight + getFontHeight(textSize / density)) / 2 + getPaddingTop() - getPaddingBottom() + 2;
			Log.e("TAG", "textY:" + textY);
		}
	}

	/**
	 * surfaceChanged
	 *
	 * @param arg0 arg0
	 * @param arg1 arg1
	 * @param arg2 arg1
	 * @param arg3 arg1
	 */
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		Log.d(TAG,"arg0:"+arg0.toString()+"  arg1:"+arg1+"  arg2:"+arg2+"  arg3:"+arg3);
	}

	/**
	 * surfaceCreated,init a new scroll thread.
	 * lockCanvas
	 * Draw somthing
	 * unlockCanvasAndPost
	 *
	 * @param holder holder
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		bStop = false;
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTextThread(), 1000, 10, TimeUnit.MILLISECONDS);
		Log.d(TAG, "ScrollTextTextView is created");
	}


	/**
	 * surfaceDestroyed
	 *
	 * @param arg0 SurfaceHolder
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// Auto-generated method stub
		bStop = true;
		scheduledExecutorService.shutdown();
		Log.d(TAG, "ScrollTextTextView is destroyed");
	}

	/**
	 * text height
	 *
	 * @param fontSize  fontsize
	 * @return  fontsize `s height
	 */
	public int getFontHeight(float fontSize) {
		Paint paint = new Paint();
		paint.setTextSize(fontSize);
		FontMetrics fm = paint.getFontMetrics();
		return (int) Math.ceil(fm.descent - fm.ascent);
	}

	/**
	 * set scroll times
	 *
	 * @param times scroll times
	 */
	public void setTimes(int times) {
		if (times <= 0) {
			this.times = Integer.MAX_VALUE;
		} else {
			this.times = times;
			time = times;
		}
	}

	/**
	 * set scroll text
	 *
	 * @param text  scroll text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * set scroll speed
	 *
	 * @param speed
	 */
	public void setSpeed(int speed) {
		if (speed > 10 || speed < 0) {
			throw new IllegalArgumentException("Speed was invalid integer, it must between 0 and 10");
		} else {
			this.speed = speed;
		}
	}

	/**
	 * touch to stop / start
	 *
	 */
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		if (!clickEnable) {
//			return true;
//		}
//		switch (event.getAction()) {
//			case MotionEvent.ACTION_DOWN:
//				bStop = !bStop;
//				if (!bStop && time == 0) {
//					time = times;
//				}
//				break;
//		}
//		return true;
//	}

	/**
	 * Draw text
	 *
	 * @param X X
	 * @param Y Y
	 */
	public synchronized void draw(float X, float Y) {
		Canvas canvas = surfaceHolder.lockCanvas();
		canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
		if (isHorizontal) {
			canvas.drawText(text, X, Y, paint);
		}
		surfaceHolder.unlockCanvasAndPost(canvas);
	}

	/**
	 * scroll text vertecl
	 *
	 */
	private void drawVerteclScroll() {
		List<String> strings = new ArrayList<>();
		int start=0,end=0;
		while(end<text.length()){
			while(paint.measureText(text.substring(start, end)) < viewWidth&&end<text.length()){
				end++;
			}
			if(end==text.length()){
				strings.add(text.substring(start,end));
				break;
			}else{
				end--;
				strings.add(text.substring(start,end));
				start=end;
			}
		}

		float fontHeight = getFontHeight(textSize / density);
		int GPoint = ((int) fontHeight + viewHeight) / 2;

		for (int n = 0; n < strings.size(); n++) {
			for (float i = viewHeight + fontHeight; i > -fontHeight; i = i - 3) {
				Canvas canvas = surfaceHolder.lockCanvas();
				canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
				canvas.drawText(strings.get(n), 0, i, paint);
				surfaceHolder.unlockCanvasAndPost(canvas);
				if (i - GPoint < 4 && i - GPoint > 0) {
					try {
						Thread.sleep(speed * 1000);
					} catch (InterruptedException e) {
						Log.e(TAG, e.toString());
					}
				}
			}
		}
	}

	/**
	 * Scroll thread
	 *
	 */
	class ScrollTextThread implements Runnable {
		@Override
		public void run() {
			//Auto-generated method stub
			while (!bStop) {
				if (isHorizontal) {
					draw(viewWidth - textX, textY);
					textX += speed;
					if (textX > viewWidth_plus_textLength) {
						textX = 0;
						--time;
					}
				} else {
					drawVerteclScroll();
					--time;
				}
				if (time <= 0) {
					bStop = true;
				}
			}
		}
	}
}
