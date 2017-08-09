/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ocnyang.qbox.app.module.me.weather.mxxedgeeffect.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.ocnyang.qbox.app.R;

import java.lang.ref.WeakReference;

/**
 * PagerTitleStrip is a non-interactive indicator of the current, next, and
 * previous pages of a {@ ViewPager}. It is intended to be used as a child
 * view of a ViewPager widget in your XML layout. Add it as a child of a
 * ViewPager in your layout file and set its android:layout_gravity to TOP or
 * BOTTOM to pin it to the top or bottom of the ViewPager. The title from each
 * page is supplied by the method {@link PagerAdapter#getPageTitle(int)} in the
 * adapter supplied to the ViewPager.
 * 
 * <p>
 * For an interactive indicator, see {@ PagerTabStrip}.
 * </p>
 */
public class MxxPagerTitleStrip extends ViewGroup implements MxxViewPager.Decor {
	private static final String TAG = "PagerTitleStrip";

	MxxViewPager mPager;
	TextView mPrevText;
	TextView mCurrText;
	TextView mNextText;

	private int mLastKnownCurrentPage = -1;
	private float mLastKnownPositionOffset = -1;
	private int mScaledTextSpacing;
	private int mGravity;

	private boolean mUpdatingText;
	private boolean mUpdatingPositions;

	private final PageListener mPageListener = new PageListener();

	private WeakReference<PagerAdapter> mWatchingAdapter;

	private static final int[] ATTRS = new int[] { android.R.attr.textAppearance, android.R.attr.textSize,
			android.R.attr.textColor, android.R.attr.gravity };

	private static final int[] TEXT_ATTRS = new int[] { 0x0101038c // android.R.attr.textAllCaps
	};

	private static final float SIDE_ALPHA = 0.6f;// wangbin changed this
	private static final int TEXT_SPACING = 0;// //16; // dip 这个是每个TextView的间隔
												// wangbin changed this

	private int mNonPrimaryAlpha;
	int mTextColor;
	
	private CircleIndicator mCircleIndicator;

	interface PagerTitleStripImpl {
		void setSingleLineAllCaps(TextView text);
	}

	static class PagerTitleStripImplBase implements PagerTitleStripImpl {
		public void setSingleLineAllCaps(TextView text) {
			text.setSingleLine();
		}
	}

	// static class PagerTitleStripImplIcs implements PagerTitleStripImpl {
	// public void setSingleLineAllCaps(TextView text) {
	// PagerTitleStripIcs.setSingleLineAllCaps(text);
	// }
	// }

	// private static final PagerTitleStripImpl IMPL;
	// static {
	// if (android.os.Build.VERSION.SDK_INT >= 14) {
	// IMPL = new PagerTitleStripImplIcs();
	// } else {
	// IMPL = new PagerTitleStripImplBase();
	// }
	// }

	private static void setSingleLineAllCaps(TextView text) {
		// IMPL.setSingleLineAllCaps(text);
	}

	public MxxPagerTitleStrip(Context context) {
		this(context, null);
	}

	public MxxPagerTitleStrip(Context context, AttributeSet attrs) {
		super(context, attrs);

		
		addView(mPrevText = new TextView(context));
		addView(mCurrText = new TextView(context));
		addView(mNextText = new TextView(context));

		final TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
		final int textAppearance = a.getResourceId(0, 0);
		if (textAppearance != 0) {
			mPrevText.setTextAppearance(context, textAppearance);
			mCurrText.setTextAppearance(context, textAppearance);
			mNextText.setTextAppearance(context, textAppearance);
		}
		final int textSize = a.getDimensionPixelSize(1, 0);
		if (textSize != 0) {
			setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		}

		setSomeTextColor(a);

		mGravity = a.getInteger(3, Gravity.BOTTOM);
		a.recycle();

		mTextColor = mCurrText.getTextColors().getDefaultColor();
		setNonPrimaryAlpha(SIDE_ALPHA);

		mPrevText.setEllipsize(TruncateAt.END);
		mCurrText.setEllipsize(TruncateAt.END);
		mNextText.setEllipsize(TruncateAt.END);

		boolean allCaps = false;
		if (textAppearance != 0) {
			final TypedArray ta = context.obtainStyledAttributes(textAppearance, TEXT_ATTRS);
			allCaps = ta.getBoolean(0, false);
			ta.recycle();
		}

		if (allCaps) {
			setSingleLineAllCaps(mPrevText);
			setSingleLineAllCaps(mCurrText);
			setSingleLineAllCaps(mNextText);
		} else {
			mPrevText.setSingleLine();
			mCurrText.setSingleLine();
			mNextText.setSingleLine();
		}

		final float density = context.getResources().getDisplayMetrics().density;
		mScaledTextSpacing = (int) (TEXT_SPACING * density);
		mCircleIndicator = new CircleIndicator(context, attrs);
		addView(mCircleIndicator);
	}

	@SuppressWarnings("ResourceType")
	private void setSomeTextColor(TypedArray a) {
		if (a.hasValue(2)) {
			final int textColor = a.getColor(2, 0);
			mPrevText.setTextColor(textColor);
			mCurrText.setTextColor(textColor);
			mNextText.setTextColor(textColor);
		}
	}

	private class CircleIndicator extends View implements MxxViewPager.OnPageChangeListener{

		// FOR circleIndicator config//////////
		private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		private int mCurItemPosition;
		private float mCurItemPositionOffset;
		private float mIndicatorRadius;
		private float mIndicatorMargin;
		private int mIndicatorBackground;
		private int mIndicatorSelectedBackground;

		// circleIndicator default value
		private final int DEFAULT_INDICATOR_RADIUS = 10;
		private final int DEFAULT_INDICATOR_MARGIN = 40;
		private final int DEFAULT_INDICATOR_BACKGROUND = Color.BLUE;
		private final int DEFAULT_INDICATOR_SELECTED_BACKGROUND = Color.RED;

		// private final int DEFAULT_INDICATOR_LAYOUT_GRAVITY =
		// Gravity.CENTER.ordinal();
		private int mItemCount = 0;

		public CircleIndicator(Context context, AttributeSet attrs) {
			super(context, attrs);
			final float density = context.getResources().getDisplayMetrics().density;
			mPaint.setStrokeWidth(1f * density);
			if (attrs == null)
				return;
			TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MxxPagerTitleStrip);
			mIndicatorRadius = typedArray.getDimensionPixelSize(R.styleable.MxxPagerTitleStrip_pts_radius,
					DEFAULT_INDICATOR_RADIUS);
			mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.MxxPagerTitleStrip_pts_margin,
					DEFAULT_INDICATOR_MARGIN);
			mIndicatorBackground = typedArray.getColor(R.styleable.MxxPagerTitleStrip_pts_background,
					DEFAULT_INDICATOR_BACKGROUND);
			mIndicatorSelectedBackground = typedArray.getColor(R.styleable.MxxPagerTitleStrip_pts_selected_background,
					DEFAULT_INDICATOR_SELECTED_BACKGROUND);
			typedArray.recycle();
			post(new Runnable() {
				@Override
				public void run() {
					hideSelf();
				}
			});
		}
		
		public void setItemCount(int itemCount){
			mItemCount = itemCount;
			invalidate();
		}

		
		
		public int geIndicatorHeight() {
			return (int) (mIndicatorRadius * 2f + 0.5f);
		}
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//			final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//			final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
			final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
			setMeasuredDimension(widthSize, geIndicatorHeight());
		}
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			trigger(position, positionOffset);
		}
		@Override
		public void onPageSelected(int position) {
			trigger(position, 0);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
//			Log.e("CircleIndicator", "onPageScrollStateChanged()->" + state );
			switch (state) {
			case MxxViewPager.SCROLL_STATE_IDLE:
				hideSelf();
				break;
			default:
				showSelf();
				break;
			}
		}
		private void trigger(int position, float positionOffset) {
			CircleIndicator.this.mCurItemPosition = position;
			CircleIndicator.this.mCurItemPositionOffset = positionOffset;
			invalidate();
		}
		
		final int fadeDuration = 400;
		boolean isShowing = true;

		private void hideSelf() {
			if (isShowing) {
//				this.animate().cancel();
				this.animate().alpha(0).setStartDelay(fadeDuration * 3).setDuration(fadeDuration).start();
				isShowing = false;
			}
		}

		private void showSelf() {
			if (!isShowing) {
//				this.animate().cancel();
				this.animate().alpha(1).setStartDelay(0).setDuration(fadeDuration / 2).start();
				isShowing = true;
			}
		}
		@Override
		protected void onDraw(Canvas canvas) {
			 
			super.onDraw(canvas);
			canvas.save();
//			int sc = canvas
//					.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
//							| Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
//							| Canvas.CLIP_TO_LAYER_SAVE_FLAG);
			if (this.mItemCount <= 0) {
				return;
			}
			final int width = getWidth();
			final int height = getHeight();
//			Log.d("CircleIndicator", "onDraw()->" + mItemCount + " w->" + width + " h->" + height + " left" + getLeft() + " right" + getRight() +" t" + getTop() + " b" + getBottom());
			final float oneCircleWidth = mIndicatorRadius * 2f + mIndicatorMargin ;
			final float circleWidth = mItemCount * oneCircleWidth -  mIndicatorMargin;
			canvas.translate((width - circleWidth) / 2f, height / 2f);
			//画所有的圆形  
			mPaint.setStyle(Style.FILL);
			mPaint.setColor(mIndicatorBackground);
			for (int i = 0; i < mItemCount; i++) {
				canvas.drawCircle(mIndicatorRadius + i * oneCircleWidth, 0, mIndicatorRadius,
						mPaint);
			}
			//画+号
//			mPaint.setStyle(Style.STROKE);
//			final float firstDotX = 0;
//			canvas.drawLine(firstDotX, 0, firstDotX + mIndicatorRadius*2f, 0, mPaint);
//			canvas.drawLine(firstDotX+ mIndicatorRadius, -mIndicatorRadius,
//					firstDotX + mIndicatorRadius, mIndicatorRadius, mPaint);
			
			//画当前的圆
			mPaint.setStyle(Style.FILL);
			mPaint.setColor(mIndicatorSelectedBackground);
			canvas.drawCircle(mIndicatorRadius + (mCurItemPosition + mCurItemPositionOffset)
					* oneCircleWidth, 0, mIndicatorRadius, mPaint);
//			canvas.restoreToCount(sc);
			canvas.restore();
		}

	}

	/**
	 * Set the required spacing between title segments.
	 * 
	 * @param spacingPixels
	 *            Spacing between each title displayed in pixels
	 */
	public void setTextSpacing(int spacingPixels) {
		mScaledTextSpacing = spacingPixels;
		requestLayout();
	}

	/**
	 * @return The required spacing between title segments in pixels
	 */
	public int getTextSpacing() {
		return mScaledTextSpacing;
	}

	/**
	 * Set the alpha value used for non-primary page titles.
	 * 
	 * @param alpha
	 *            Opacity value in the range 0-1f
	 */
	public void setNonPrimaryAlpha(float alpha) {
		// mNonPrimaryAlpha = (int) (alpha * 255) & 0xFF;
		// final int transparentColor = (mNonPrimaryAlpha << 24) | (mTextColor &
		// 0xFFFFFF);
		// mPrevText.setTextColor(transparentColor);
		// mNextText.setTextColor(transparentColor);
	}

	/**
	 * Set the color value used as the base color for all displayed page titles.
	 * Alpha will be ignored for non-primary page titles. See
	 * {@link #setNonPrimaryAlpha(float)}.
	 * 
	 * @param color
	 *            Color hex code in 0xAARRGGBB format
	 */
	public void setTextColor(int color) {
		mTextColor = color;
		mCurrText.setTextColor(color);
		final int transparentColor = (mNonPrimaryAlpha << 24) | (mTextColor & 0xFFFFFF);
		mPrevText.setTextColor(transparentColor);
		mNextText.setTextColor(transparentColor);
	}

	/**
	 * Set the default text size to a given unit and value. See
	 * {@link TypedValue} for the possible dimension units.
	 * 
	 * <p>
	 * Example: to set the text size to 14px, use
	 * setTextSize(TypedValue.COMPLEX_UNIT_PX, 14);
	 * </p>
	 * 
	 * @param unit
	 *            The desired dimension unit
	 * @param size
	 *            The desired size in the given units
	 */
	public void setTextSize(int unit, float size) {
		mPrevText.setTextSize(unit, size);
		mCurrText.setTextSize(unit, size);
		mNextText.setTextSize(unit, size);
	}

	/**
	 * Set the {@link Gravity} used to position text within the title strip.
	 * Only the vertical gravity component is used.
	 * 
	 * @param gravity
	 *            {@link Gravity} constant for positioning title text
	 */
	public void setGravity(int gravity) {
		mGravity = gravity;
		requestLayout();
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		final ViewParent parent = getParent();
		if (!(parent instanceof MxxViewPager)) {
			throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
		}

		final MxxViewPager pager = (MxxViewPager) parent;
		final PagerAdapter adapter = pager.getAdapter();

		pager.setInternalPageChangeListener(mPageListener);
		pager.setOnAdapterChangeListener(mPageListener);
		mPager = pager;
		updateAdapter(mWatchingAdapter != null ? mWatchingAdapter.get() : null, adapter);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mPager != null) {
			updateAdapter(mPager.getAdapter(), null);
			mPager.setInternalPageChangeListener(null);
			mPager.setOnAdapterChangeListener(null);
			mPager = null;
		}
	}

	void updateText(int currentItem, PagerAdapter adapter) {
		final int itemCount = adapter != null ? adapter.getCount() : 0;
		mUpdatingText = true;

		CharSequence text = null;
		if (currentItem >= 1 && adapter != null) {
			text = adapter.getPageTitle(currentItem - 1);
		}
		mPrevText.setText(text);

		mCurrText.setText(adapter != null && currentItem < itemCount ? adapter.getPageTitle(currentItem) : null);

		text = null;
		if (currentItem + 1 < itemCount && adapter != null) {
			text = adapter.getPageTitle(currentItem + 1);
		}
		mNextText.setText(text);

		// Measure everything
		final int width = getWidth() - getPaddingLeft() - getPaddingRight();
		final int childHeight = getHeight() - getPaddingTop() - getPaddingBottom();
		final int childWidthSpec = MeasureSpec.makeMeasureSpec((int) (width * 0.8f), MeasureSpec.AT_MOST);
		final int childHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST);
		mPrevText.measure(childWidthSpec, childHeightSpec);
		mCurrText.measure(childWidthSpec, childHeightSpec);
		mNextText.measure(childWidthSpec, childHeightSpec);

		mLastKnownCurrentPage = currentItem;

		if (!mUpdatingPositions) {
			updateTextPositions(currentItem, mLastKnownPositionOffset, false);
		}

		mUpdatingText = false;
	}

	@Override
	public void requestLayout() {
		if (!mUpdatingText) {
			super.requestLayout();
		}
	}

	void updateAdapter(PagerAdapter oldAdapter, PagerAdapter newAdapter) {
		if (oldAdapter != null) {
			oldAdapter.unregisterDataSetObserver(mPageListener);
			mWatchingAdapter = null;
		}
		if (newAdapter != null) {
			newAdapter.registerDataSetObserver(mPageListener);
			mWatchingAdapter = new WeakReference<PagerAdapter>(newAdapter);
			mCircleIndicator.setItemCount(newAdapter.getCount());
		}
		if (mPager != null) {
			mLastKnownCurrentPage = -1;
			mLastKnownPositionOffset = -1;
			updateText(mPager.getCurrentItem(), newAdapter);
			requestLayout();
		}
		
	}

	private int convertTextColor(float percent) {
		int alpha = (int) (percent * 255) & 0xFF;
		return (alpha << 24) | (mTextColor & 0xFFFFFF);
	}

	void updateTextPositions(int position, float positionOffset, boolean force) {
		// position是已经确定了的当前item,也就是currText
		// onPageScrolled中positionOffset>0.5f时position会+1
		if (position != mLastKnownCurrentPage) {
			updateText(position, mPager.getAdapter());
		} else if (!force && positionOffset == mLastKnownPositionOffset) {
			return;
		}

		// ///////////////
		if (positionOffset == 0f) {
			mPrevText.setTextColor(convertTextColor(0f));
			mCurrText.setTextColor(convertTextColor(1f));
			mNextText.setTextColor(convertTextColor(0f));
		} else {
//			Log.d(TAG, "position->" + position + "  positionOffset->" + positionOffset);
			float currPercent = positionOffset;// positionOffset=0.6
												// 则currOffset=0.1
			// boolean isOffsetAdding = (positionOffset -
			// mLastKnownPositionOffset)>0f;//offset在增大
			if (currPercent <= 0.5f) {// 边的在出现
				mPrevText.setTextColor(convertTextColor(0f));
				mCurrText.setTextColor(convertTextColor((1f - currPercent)));
				// if(isOffsetAdding){

				mNextText.setTextColor(convertTextColor(currPercent));
				// }

			} else {// 边的在出现
					// currPercent -= 0.5f;
				mPrevText.setTextColor(convertTextColor(1f - currPercent));
				mCurrText.setTextColor(convertTextColor(currPercent));
				mNextText.setTextColor(convertTextColor(0));
			}
			// if (currPercent > 0.5f) {
			// currPercent -= 0.5f;
			// }
			// mPrevText.setTextColor(convertTextColor(currPercent));

		}

		// mNextText.setTextColor(transparentColor);

		mUpdatingPositions = true;

		final int prevWidth = mPrevText.getMeasuredWidth();
		final int currWidth = mCurrText.getMeasuredWidth();
		final int nextWidth = mNextText.getMeasuredWidth();
		final int halfCurrWidth = 0;// currWidth / 2;

		final int stripWidth = getWidth();
		final int stripHeight = getHeight();
		final int paddingLeft = getPaddingLeft();
		final int paddingRight = getPaddingRight();
		final int paddingTop = getPaddingTop();
		final int paddingBottom = getPaddingBottom();
//		final int textPaddedLeft = paddingLeft + halfCurrWidth;
//		final int textPaddedRight = paddingRight + halfCurrWidth;
//		final int contentWidth = prevWidth + currWidth + nextWidth;// stripWidth
																	// -
																	// textPaddedLeft
																	// -
																	// textPaddedRight;

		float currOffset = positionOffset + 0.5f;// positionOffset=0.6
													// 则currOffset=0.1
		if (currOffset > 1.f) {
			currOffset -= 1.f;
		}
		// float currOffset = positionOffset;
		// if (currOffset > 1.f) {
		// currOffset -= 1.f;
		// }
		final int currCenter = stripWidth / 2 - (int) (currWidth * (currOffset - 0.5f));// stripWidth
																						// -
																						// textPaddedRight
																						// -
																						// (int)
																						// (contentWidth
																						// *
																						// currOffset);

		final int currLeft = currCenter - currWidth / 2;
		final int currRight = currLeft + currWidth;

		final int prevBaseline = mPrevText.getBaseline();
		final int currBaseline = mCurrText.getBaseline();
		final int nextBaseline = mNextText.getBaseline();
		final int maxBaseline = Math.max(Math.max(prevBaseline, currBaseline), nextBaseline);
		final int prevTopOffset = maxBaseline - prevBaseline;
		final int currTopOffset = maxBaseline - currBaseline;
		final int nextTopOffset = maxBaseline - nextBaseline;
		final int alignedPrevHeight = prevTopOffset + mPrevText.getMeasuredHeight();
		final int alignedCurrHeight = currTopOffset + mCurrText.getMeasuredHeight();
		final int alignedNextHeight = nextTopOffset + mNextText.getMeasuredHeight();
		final int maxTextHeight = Math.max(Math.max(alignedPrevHeight, alignedCurrHeight), alignedNextHeight);

		final int vgrav = mGravity & Gravity.VERTICAL_GRAVITY_MASK;

		int prevTop;
		int currTop;
		int nextTop;
		switch (vgrav) {
		default:
		case Gravity.TOP:
			prevTop = paddingTop + prevTopOffset;
			currTop = paddingTop + currTopOffset;
			nextTop = paddingTop + nextTopOffset;
			break;
		case Gravity.CENTER_VERTICAL:
			final int paddedHeight = stripHeight - paddingTop - paddingBottom;
			final int centeredTop = (paddedHeight - maxTextHeight) / 2;
			prevTop = centeredTop + prevTopOffset;
			currTop = centeredTop + currTopOffset;
			nextTop = centeredTop + nextTopOffset;
			break;
		case Gravity.BOTTOM:
			final int bottomGravTop = stripHeight - paddingBottom - maxTextHeight;
			prevTop = bottomGravTop + prevTopOffset;
			currTop = bottomGravTop + currTopOffset;
			nextTop = bottomGravTop + nextTopOffset;
			break;
		}

		mCurrText.layout(currLeft, currTop, currRight, currTop + mCurrText.getMeasuredHeight());

		final int prevLeft = currLeft - mScaledTextSpacing - prevWidth;// Math.min(paddingLeft,
																		// currLeft
																		// -
																		// mScaledTextSpacing
																		// -
																		// prevWidth);
		mPrevText.layout(prevLeft, prevTop, prevLeft + prevWidth, prevTop + mPrevText.getMeasuredHeight());

		final int nextLeft = currRight + mScaledTextSpacing;// Math.max(stripWidth
															// - paddingRight -
															// nextWidth,
		// currRight + mScaledTextSpacing);
		mNextText.layout(nextLeft, nextTop, nextLeft + nextWidth, nextTop + mNextText.getMeasuredHeight());

		mLastKnownPositionOffset = positionOffset;
		mUpdatingPositions = false;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("Must measure with an exact width");
		}

		int childHeight = heightSize;
		int minHeight = getMinHeight();
		int padding = 0;
		padding = getPaddingTop() + getPaddingBottom();
		childHeight -= padding;

		final int childWidthSpec = MeasureSpec.makeMeasureSpec((int) (widthSize * 0.8f), MeasureSpec.AT_MOST);
		final int childHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST);

		mPrevText.measure(childWidthSpec, childHeightSpec);
		mCurrText.measure(childWidthSpec, childHeightSpec);
		mNextText.measure(childWidthSpec, childHeightSpec);

		mCircleIndicator.measure(widthMeasureSpec, childHeightSpec);
		if (heightMode == MeasureSpec.EXACTLY) {
			setMeasuredDimension(widthSize, heightSize);
		} else {
			int textHeight = mCurrText.getMeasuredHeight();
			setMeasuredDimension(widthSize, Math.max(minHeight, textHeight + padding));
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (mPager != null) {
			final float offset = mLastKnownPositionOffset >= 0 ? mLastKnownPositionOffset : 0;
			updateTextPositions(mLastKnownCurrentPage, offset, true);
		}
		final int indicatorHeight = mCircleIndicator.geIndicatorHeight();
		mCircleIndicator.layout(0,  b - t -  indicatorHeight * 2, r - l, b - t -indicatorHeight);//indicator底部留有indicatorHeight的边距
	}

	int getMinHeight() {
		int minHeight = 0;
		final Drawable bg = getBackground();
		if (bg != null) {
			minHeight = bg.getIntrinsicHeight();
		}
		return minHeight;
	}

	private class PageListener extends DataSetObserver implements MxxViewPager.OnPageChangeListener,
			MxxViewPager.OnAdapterChangeListener {
		private int mScrollState;

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			mCircleIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
			if (positionOffset > 0.5f) {
				// Consider ourselves to be on the next page when we're 50% of
				// the way there.
				position++;
			}
			updateTextPositions(position, positionOffset, false);
		}

		@Override
		public void onPageSelected(int position) {
			if (mScrollState == MxxViewPager.SCROLL_STATE_IDLE) {
				// Only update the text here if we're not dragging or settling.
				updateText(mPager.getCurrentItem(), mPager.getAdapter());

				final float offset = mLastKnownPositionOffset >= 0 ? mLastKnownPositionOffset : 0;
				updateTextPositions(mPager.getCurrentItem(), offset, true);
			}
			mCircleIndicator.onPageSelected(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			mScrollState = state;
			mCircleIndicator.onPageScrollStateChanged(state);
		}

		@Override
		public void onAdapterChanged(PagerAdapter oldAdapter, PagerAdapter newAdapter) {
			updateAdapter(oldAdapter, newAdapter);
		}

		@Override
		public void onChanged() {
			updateText(mPager.getCurrentItem(), mPager.getAdapter());

			final float offset = mLastKnownPositionOffset >= 0 ? mLastKnownPositionOffset : 0;
			updateTextPositions(mPager.getCurrentItem(), offset, true);
		}
	}
}
