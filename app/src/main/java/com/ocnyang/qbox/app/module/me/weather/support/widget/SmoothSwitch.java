/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.ocnyang.qbox.app.module.me.weather.support.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CompoundButton;

/**
 * SwitchCompat is a version of the Switch widget which on devices back to API
 * v7. It does not make any attempt to use the platform provided widget on those
 * devices which it is available normally.
 * <p>
 * A Switch is a two-state toggle switch widget that can select between two
 * options. The user may drag the "thumb" back and forth to choose the selected
 * option, or simply tap to toggle as if it were a checkbox. The
 * {@link #setText(CharSequence) text} property controls the text displayed in
 * the label for the switch, whereas the {@link #setTextOff(CharSequence) off}
 * and {@link #setTextOn(CharSequence) on} text controls the text on the thumb.
 * Similarly, the {@link #setTextAppearance(Context, int)
 * textAppearance} and the related setTypeface() methods control the typeface
 * and style of label text, whereas the
 * {@link #setSwitchTextAppearance(Context, int)
 * switchTextAppearance} and the related seSwitchTypeface() methods control that
 * of the thumb.
 */
@SuppressLint("NewApi")
public class SmoothSwitch extends CompoundButton {
	private static final int THUMB_ANIMATION_DURATION = 250;

	private static final int TOUCH_MODE_IDLE = 0;
	private static final int TOUCH_MODE_DOWN = 1;
	private static final int TOUCH_MODE_DRAGGING = 2;

	private static final int[] TEXT_APPEARANCE_ATTRS = { android.R.attr.textColor, android.R.attr.textSize, };

	// Enum for the "typeface" XML parameter.
//	private static final int SANS = 1;
//	private static final int SERIF = 2;
//	private static final int MONOSPACE = 3;

	private GradientDrawable mThumbDrawable;
	private GradientDrawable mTrackDrawable;
	private int mThumbTextPadding;
	private int mSwitchMinWidth;
	private int mSwitchPadding;
//	private boolean mSplitTrack;
	private CharSequence mTextOn;
	private CharSequence mTextOff;
	private boolean mShowText;

	private int mTouchMode;
	private int mTouchSlop;
	private float mTouchX;
	private float mTouchY;
	private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
	private int mMinFlingVelocity;

	private float mThumbPosition;
	
	private int mThumbColorOn,mThumbColorOff,mTrackColorOn,mTrackColorOff;

	/**
	 * Width required to draw the switch track and thumb. Includes padding and
	 * optical bounds for both the track and thumb.
	 */
	private int mSwitchWidth;

	/**
	 * Height required to draw the switch track and thumb. Includes padding and
	 * optical bounds for both the track and thumb.
	 */
	private int mSwitchHeight;

	/**
	 * Width of the thumb's content region. Does not include padding or optical
	 * bounds.
	 */
	private int mThumbWidth;

	/** Left bound for drawing the switch track and thumb. */
	private int mSwitchLeft;

	/** Top bound for drawing the switch track and thumb. */
	private int mSwitchTop;

	/** Right bound for drawing the switch track and thumb. */
	private int mSwitchRight;

	/** Bottom bound for drawing the switch track and thumb. */
	private int mSwitchBottom;

	private TextPaint mTextPaint;
	private ColorStateList mTextColors;
	private Layout mOnLayout;
	private Layout mOffLayout;
	private TransformationMethod mSwitchTransformationMethod;
	private Animation mPositionAnimator;

//	@SuppressWarnings("hiding")
	private final Rect mTempRect = new Rect();

	// private final TintManager mTintManager;

	private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };

	/**
	 * Construct a new Switch with default styling.
	 * 
	 * @param context
	 *            The Context that will determine this widget's theming.
	 */
	public SmoothSwitch(Context context) {
		this(context, null);
	}

	/**
	 * Construct a new Switch with default styling, overriding specific style
	 * attributes as requested.
	 * 
	 * @param context
	 *            The Context that will determine this widget's theming.
	 * @param attrs
	 *            Specification of attributes that should deviate from default
	 *            styling.
	 */
	public SmoothSwitch(Context context, AttributeSet attrs) {
		this(context, attrs,0);// R.attr.switchStyle);
	}
	
	static class Default {
		static final int DEFAULT_SIZE_DP = 20;//高度
		static final int DEFAULT_THUMB_OFF_COLOR = 0xffececec;// Color.parseColor("#E3E3E3");
		static final int DEFAULT_THUMB_ON_COLOR = 0xff009688;// 0xff43d95d;//#02BFE7
		static final int DEFAULT_TRACK_OFF_COLOR = 0x4cececec;// getThemeAttrColor(R.attr.colorForeground, 0.3f);
		static final int DEFAULT_TRACK_ON_COLOR = 0x4c009688;//alpha=0.3f=0x4c getThemeAttrColor(R.attr.colorControlActivated, 0.3f);
//		static final int DEFAULT_OFF_DISABLED_COLOR = 0x88ececec;
//		static final int DEFAULT_ON_DISABLED_COLOR = 0x88009688;
		static final float DEFAULT_RADIUS_PERCENT_OF_SIZE = 0.5f;//标准的material=0.5f，也就是圆形
		static final float DEFAULT_THUMB_PADDING_PERCENT_OF_SIZE = 0.0f;//标准的material=0f，thumb不要边距
		static final float DEFAULT_TRACK_PADDING_PERCENT_OF_SIZE = 0.3f;// 标准的material风格设置为0.3f左右，thumb高22dp，track高15dp
		
		static final float DEFAULT_MEASURE_FACTOR = 2f;// 2f;// 高 ： 宽
	}

	/**
	 * Construct a new Switch with a default style determined by the given theme
	 * attribute, overriding specific style attributes as requested.
	 * 
	 * @param context
	 *            The Context that will determine this widget's theming.
	 * @param attrs
	 *            Specification of attributes that should deviate from the
	 *            default styling.
	 * @param defStyleAttr
	 *            An attribute in the current theme that contains a reference to
	 *            a style resource that supplies default values for the view.
	 *            Can be 0 to not look for defaults.
	 */
	public SmoothSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

		final Resources res = getResources();
		final float density = res.getDisplayMetrics().density;
		mTextPaint.density = density;//res.getDisplayMetrics().density;

		final int thumbSize = (int)(Default.DEFAULT_SIZE_DP * density + 0.5f);// truckSize = 15dp 
		final int thumbWidth = thumbSize;//a.getDimensionPixelSize(R.styleable.SwitchCompat_thumbWidth, thumbSize);
		mThumbColorOn = Default.DEFAULT_THUMB_ON_COLOR;//a.getColor(R.styleable.SwitchCompat_colorOn, Default.DEFAULT_THUMB_ON_COLOR);
		mThumbColorOff =  Default.DEFAULT_THUMB_OFF_COLOR;//a.getColor(R.styleable.SwitchCompat_colorOff, Default.DEFAULT_THUMB_OFF_COLOR);
		mTrackColorOn= Default.DEFAULT_TRACK_ON_COLOR;//a.getColor(R.styleable.SwitchCompat_trackColorOn, Default.DEFAULT_TRACK_ON_COLOR);
		mTrackColorOff= Default.DEFAULT_TRACK_OFF_COLOR;//a.getColor(R.styleable.SwitchCompat_trackColorOff, Default.DEFAULT_TRACK_OFF_COLOR);
		
		final float radius = thumbSize * Default.DEFAULT_RADIUS_PERCENT_OF_SIZE;//a.getFloat(R.styleable.SwitchCompat_radiusPercentOfThumbSize,
//				Default.DEFAULT_RADIUS_PERCENT_OF_SIZE);
		final int thumbPadding = (int) (thumbSize * Default.DEFAULT_THUMB_PADDING_PERCENT_OF_SIZE + 0.5f);//thumbSize * a.getFloat(R.styleable.SwitchCompat_thumbPaddingPercentOfThumbSize,
//				Default.DEFAULT_THUMB_PADDING_PERCENT_OF_SIZE) + 0.5f);
		final int trackPadding = (int) (thumbSize * Default.DEFAULT_TRACK_PADDING_PERCENT_OF_SIZE + 0.5f);//thumbSize * a.getFloat(R.styleable.SwitchCompat_trackPaddingPercentOfThumbSize,
//				Default.DEFAULT_TRACK_PADDING_PERCENT_OF_SIZE) + 0.5f);
		
		final int curThumbColor = isChecked() ? mThumbColorOn : mThumbColorOff;
		final int curTrackColor = isChecked() ? mTrackColorOn : mTrackColorOff;
		mThumbDrawable = makeThumbDrawable(curThumbColor,thumbWidth, thumbSize, radius, thumbPadding);
		mTrackDrawable = makeTrackDrawable(curTrackColor,thumbWidth, thumbSize, radius, trackPadding);
//		final int onDisableColor = getDisableColor(onColor);
//		final int offDisableColor = getDisableColor(offColor);
//		final StateListDrawable thumbDrawable = new StateListDrawable();
//		thumbDrawable.addState(new int[] { -android.R.attr.state_enabled, android.R.attr.state_checked},
//				makeThumbDrawable(onDisableColor, thumbSize));// Disabled ON
//		thumbDrawable.addState(new int[] { -android.R.attr.state_enabled, -android.R.attr.state_checked},
//				makeThumbDrawable(offDisableColor, thumbSize));// Disabled OFF
//		thumbDrawable.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_checked },
//				makeThumbDrawable(onColor, thumbSize));
//		thumbDrawable.addState(new int[] {}, makeThumbDrawable(offColor, thumbSize));
//		mThumbDrawable = thumbDrawable;
//
//		final StateListDrawable trackDrawable = new StateListDrawable();
//		trackDrawable.addState(new int[] { -android.R.attr.state_enabled, android.R.attr.state_checked},
//				makeTrackDrawable(onDisableColor, thumbSize));// Disabled ON
//		trackDrawable.addState(new int[] { -android.R.attr.state_enabled, -android.R.attr.state_checked},
//				makeTrackDrawable(offDisableColor, thumbSize));// Disabled OFF
//		trackDrawable.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_checked },
//				makeTrackDrawable(onColor, thumbSize));
//		trackDrawable.addState(new int[] {}, makeTrackDrawable(offColor, thumbSize));
//		mTrackDrawable = trackDrawable;

//		mTextOn = a.getText(R.styleable.SwitchCompat_android_textOn);
//		mTextOff = a.getText(R.styleable.SwitchCompat_android_textOff);
		mShowText = false;//a.getBoolean(R.styleable.SwitchCompat_showText, false);
		mThumbTextPadding = 0;//a.getDimensionPixelSize(R.styleable.SwitchCompat_thumbTextPadding, 0);
//		mSwitchMinWidth = a.getDimensionPixelSize(R.styleable.SwitchCompat_switchMinWidth, 0);
		mSwitchMinWidth = Math.max(mSwitchMinWidth, thumbWidth * 2);
		mSwitchPadding = 0;//a.getDimensionPixelSize(R.styleable.SwitchCompat_switchPadding, 0);
//		mSplitTrack = a.getBoolean(R.styleable.SwitchCompat_splitTrack, false);

//		final int appearance = a.getResourceId(R.styleable.SwitchCompat_switchTextAppearance, 0);
//		if (appearance != 0) {
//			setSwitchTextAppearance(context, appearance);
//		}else{
//			ColorStateList colors = a.getColorStateList(R.styleable.SwitchCompat_switchTextColor);
//			if (colors != null) {
//				mTextColors = colors;
//			} else {
//				// If no color set in TextAppearance, default to the view's textColor
//				mTextColors = getTextColors();
//			}
//			float ts = a.getDimension(R.styleable.SwitchCompat_switchTextSize, 0);
//			if (ts != 0) {
//				if (ts != mTextPaint.getTextSize()) {
//					mTextPaint.setTextSize(ts);
//					requestLayout();
//				}
//			}
//		}

		// mTintManager = a.getTintManager();

//		a.recycle();

		final ViewConfiguration config = ViewConfiguration.get(context);
		mTouchSlop = config.getScaledTouchSlop();
		mMinFlingVelocity = config.getScaledMinimumFlingVelocity();

		// Refresh display with current params
		refreshDrawableState();
		setChecked(isChecked());
	}

	private GradientDrawable makeTrackDrawable(int color, int thumbWidth,int thumbHeight,float radius, int padding) {
		GradientDrawable trackDrawable = new GradientDrawable();
		if(padding > 0){
			trackDrawable.setStroke(padding , Color.TRANSPARENT);
		}
		trackDrawable.setCornerRadius(radius);
		trackDrawable.setSize((int) (thumbWidth * Default.DEFAULT_MEASURE_FACTOR), thumbHeight);
		trackDrawable.setColor(getTrackColor(color));
		return trackDrawable;
	}

	private GradientDrawable makeThumbDrawable(int color, int thumbWidth,int thumbHeight, float radius, int padding) {
		GradientDrawable thumbDrawable = new GradientDrawable();
		if(padding > 0){
			thumbDrawable.setStroke(padding , Color.TRANSPARENT);//make a transparent stroke ,just like padding
		}
		thumbDrawable.setCornerRadius(radius);
		thumbDrawable.setSize(thumbWidth, thumbHeight);
		thumbDrawable.setColor(color);
		return thumbDrawable;
	}

	private int getTrackColor(int color) {
		return getAlphaColor(color, 0.3f);
	}
	private int getDisableColor(int color){
		return getAlphaColor(color, 0.5f);
	}
	private int getPressedColor(int color){
		float dark = 0.6f;
		return Color.argb(Color.alpha(color), (int) (Color.red(color) * dark), 
				(int) (Color.green(color) * dark), (int) (Color.blue(color) * dark));
	}

	private int getAlphaColor(int originalColor, float alpha) {
		final int originalAlpha = Color.alpha(originalColor);
		// Return the color, multiplying the original alpha by the disabled
		// value
		return (originalColor & 0x00ffffff) | (Math.round(originalAlpha * alpha) << 24);
	}

	

	// private ColorStateList getSwitchThumbColorStateList() {
	// final int[][] states = new int[3][];
	// final int[] colors = new int[3];
	// int i = 0;
	// // Disabled state
	// states[i] = new int[] { -android.R.attr.state_enabled };
	// colors[i] = Default.DEFAULT_THUMB_DISABLED_COLOR;//
	// getDisabledThemeAttrColor(R.attr.colorSwitchThumbNormal);
	// i++;
	// states[i] = new int[] { android.R.attr.state_checked };
	// colors[i] = Default.DEFAULT_THUMB_COLOR;//
	// getThemeAttrColor(R.attr.colorControlActivated);
	// i++;
	// // Default enabled state
	// states[i] = new int[0];
	// colors[i] =
	// Default.DEFAULT_THUMB_COLOR;//getThemeAttrColor(R.attr.colorSwitchThumbNormal);
	// i++;
	// return new ColorStateList(states, colors);
	// }

	/**
	 * Sets the switch text color, size, style, hint color, and highlight color
	 * from the specified TextAppearance resource.
	 */
	@SuppressWarnings("ResourceType")
	public void setSwitchTextAppearance(Context context, int resid) {
		TypedArray appearance = context.obtainStyledAttributes(resid, TEXT_APPEARANCE_ATTRS);

		ColorStateList colors;
		int ts;

		colors = appearance.getColorStateList(0);
		if (colors != null) {
			mTextColors = colors;
		} else {
			// If no color set in TextAppearance, default to the view's
			// textColor
			mTextColors = getTextColors();
		}

		ts = appearance.getDimensionPixelSize(1, 0);
		if (ts != 0) {
			if (ts != mTextPaint.getTextSize()) {
				mTextPaint.setTextSize(ts);
				requestLayout();
			}
		}

		// boolean allCaps = appearance.getBoolean(2, false);
		// if (allCaps) {
		// mSwitchTransformationMethod = new
		// AllCapsTransformationMethod(getContext());
		// } else {
		// mSwitchTransformationMethod = null;
		// }

		appearance.recycle();
	}

	/**
	 * Sets the typeface and style in which the text should be displayed on the
	 * switch, and turns on the fake bold and italic bits in the Paint if the
	 * Typeface that you provided does not have all the bits in the style that
	 * you specified.
	 */
	public void setSwitchTypeface(Typeface tf, int style) {
		if (style > 0) {
			if (tf == null) {
				tf = Typeface.defaultFromStyle(style);
			} else {
				tf = Typeface.create(tf, style);
			}

			setSwitchTypeface(tf);
			// now compute what (if any) algorithmic styling is needed
			int typefaceStyle = tf != null ? tf.getStyle() : 0;
			int need = style & ~typefaceStyle;
			mTextPaint.setFakeBoldText((need & Typeface.BOLD) != 0);
			mTextPaint.setTextSkewX((need & Typeface.ITALIC) != 0 ? -0.25f : 0);
		} else {
			mTextPaint.setFakeBoldText(false);
			mTextPaint.setTextSkewX(0);
			setSwitchTypeface(tf);
		}
	}

	/**
	 * Sets the typeface in which the text should be displayed on the switch.
	 * Note that not all Typeface families actually have bold and italic
	 * variants, so you may need to use
	 * {@link #setSwitchTypeface(Typeface, int)} to get the appearance that you
	 * actually want.
	 */
	public void setSwitchTypeface(Typeface tf) {
		if (mTextPaint.getTypeface() != tf) {
			mTextPaint.setTypeface(tf);

			requestLayout();
			invalidate();
		}
	}

	/**
	 * Set the amount of horizontal padding between the switch and the
	 * associated text.
	 * 
	 * @param pixels
	 *            Amount of padding in pixels
	 */
	@SuppressWarnings("ResourceType")
	public void setSwitchPadding(int pixels) {
		mSwitchPadding = pixels;
		requestLayout();
	}

	/**
	 * Get the amount of horizontal padding between the switch and the
	 * associated text.
	 * 
	 * @return Amount of padding in pixels
	 */
	public int getSwitchPadding() {
		return mSwitchPadding;
	}

	/**
	 * Set the minimum width of the switch in pixels. The switch's width will be
	 * the maximum of this value and its measured width as determined by the
	 * switch drawables and text used.
	 * 
	 * @param pixels
	 *            Minimum width of the switch in pixels
	 */
	public void setSwitchMinWidth(int pixels) {
		mSwitchMinWidth = pixels;
		requestLayout();
	}

	/**
	 * Get the minimum width of the switch in pixels. The switch's width will be
	 * the maximum of this value and its measured width as determined by the
	 * switch drawables and text used.
	 * 
	 * @return Minimum width of the switch in pixels
	 */
	public int getSwitchMinWidth() {
		return mSwitchMinWidth;
	}

	/**
	 * Set the horizontal padding around the text drawn on the switch itself.
	 * 
	 * @param pixels
	 *            Horizontal padding for switch thumb text in pixels
	 */
	public void setThumbTextPadding(int pixels) {
		mThumbTextPadding = pixels;
		requestLayout();
	}

	/**
	 * Get the horizontal padding around the text drawn on the switch itself.
	 * 
	 * @return Horizontal padding for switch thumb text in pixels
	 */
	public int getThumbTextPadding() {
		return mThumbTextPadding;
	}

	/**
	 * Set the drawable used for the track that the switch slides within.
	 * 
	 * @param track
	 *            Track drawable
	 */
//	public void setTrackDrawable(Drawable track) {
//		mTrackDrawable = track;
//		requestLayout();
//	}

	/**
	 * Set the drawable used for the track that the switch slides within.
	 * 
	 * @param resId
	 *            Resource ID of a track drawable
	 */
	// public void setTrackResource(int resId) {
	// setTrackDrawable(mTintManager.getDrawable(resId));
	// }

	/**
	 * Get the drawable used for the track that the switch slides within.
	 * 
	 * @return Track drawable
	 */
	public Drawable getTrackDrawable() {
		return mTrackDrawable;
	}

	/**
	 * Set the drawable used for the switch "thumb" - the piece that the user
	 * can physically touch and drag along the track.
	 * 
	 * @param thumb
	 *            Thumb drawable
	 */
//	public void setThumbDrawable(Drawable thumb) {
//		mThumbDrawable = thumb;
//		requestLayout();
//	}

	/**
	 * Set the drawable used for the switch "thumb" - the piece that the user
	 * can physically touch and drag along the track.
	 * 
	 * @param resId
	 *            Resource ID of a thumb drawable
	 */
	// public void setThumbResource(int resId) {
	// setThumbDrawable(mTintManager.getDrawable(resId));
	// }

	/**
	 * Get the drawable used for the switch "thumb" - the piece that the user
	 * can physically touch and drag along the track.
	 * 
	 * @return Thumb drawable
	 */
	public Drawable getThumbDrawable() {
		return mThumbDrawable;
	} 

	/**
	 * Specifies whether the track should be split by the thumb. When true, the
	 * thumb's optical bounds will be clipped out of the track drawable, then
	 * the thumb will be drawn into the resulting gap.
	 * 
	 * @param splitTrack
	 *            Whether the track should be split by the thumb
	 */
//	public void setSplitTrack(boolean splitTrack) {
//		mSplitTrack = splitTrack;
//		invalidate();
//	}

	/**
	 * Returns whether the track should be split by the thumb.
	 */
//	public boolean getSplitTrack() {
//		return mSplitTrack;
//	}

	/**
	 * Returns the text displayed when the button is in the checked state.
	 */
	public CharSequence getTextOn() {
		return mTextOn;
	}

	/**
	 * Sets the text displayed when the button is in the checked state.
	 */
	public void setTextOn(CharSequence textOn) {
		mTextOn = textOn;
		requestLayout();
	}

	/**
	 * Returns the text displayed when the button is not in the checked state.
	 */
	public CharSequence getTextOff() {
		return mTextOff;
	}

	/**
	 * Sets the text displayed when the button is not in the checked state.
	 */
	public void setTextOff(CharSequence textOff) {
		mTextOff = textOff;
		requestLayout();
	}

	/**
	 * Sets whether the on/off text should be displayed.
	 * 
	 * @param showText
	 *            {@code true} to display on/off text
	 */
	public void setShowText(boolean showText) {
		if (mShowText != showText) {
			mShowText = showText;
			requestLayout();
		}
	}

	/**
	 * @return whether the on/off text should be displayed
	 */
	public boolean getShowText() {
		return mShowText;
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mShowText) {
			if (mOnLayout == null) {
				mOnLayout = makeLayout(mTextOn);
			}

			if (mOffLayout == null) {
				mOffLayout = makeLayout(mTextOff);
			}
		}

		final Rect padding = mTempRect;
		final int thumbWidth;
		final int thumbHeight;
		if (mThumbDrawable != null) {
			// Cached thumb width does not include padding.
			mThumbDrawable.getPadding(padding);
			thumbWidth = mThumbDrawable.getIntrinsicWidth() - padding.left - padding.right;
			thumbHeight = mThumbDrawable.getIntrinsicHeight();
		} else {
			thumbWidth = 0;
			thumbHeight = 0;
		}

		final int maxTextWidth;
		if (mShowText) {
			maxTextWidth = Math.max(mOnLayout.getWidth(), mOffLayout.getWidth()) + mThumbTextPadding * 2;
		} else {
			maxTextWidth = 0;
		}

		mThumbWidth = Math.max(maxTextWidth, thumbWidth);

		final int trackHeight;
		if (mTrackDrawable != null) {
			mTrackDrawable.getPadding(padding);
			trackHeight = mTrackDrawable.getIntrinsicHeight();
		} else {
			padding.setEmpty();
			trackHeight = 0;
		}

		// Adjust left and right padding to ensure there's enough room for the
		// thumb's padding (when present).
		int paddingLeft = padding.left;
		int paddingRight = padding.right;

		final int switchWidth = Math.max(mSwitchMinWidth, 2 * mThumbWidth + paddingLeft + paddingRight);
		final int switchHeight = Math.max(trackHeight, thumbHeight);
		mSwitchWidth = switchWidth;
		mSwitchHeight = switchHeight;

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int measuredHeight = getMeasuredHeight();
		if (measuredHeight < switchHeight) {
			setMeasuredDimension(ViewCompat.getMeasuredWidthAndState(this), switchHeight);
		}
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
		super.onPopulateAccessibilityEvent(event);

		final CharSequence text = isChecked() ? mTextOn : mTextOff;
		if (text != null) {
			event.getText().add(text);
		}
	}

	private Layout makeLayout(CharSequence text) {
		final CharSequence transformed = (mSwitchTransformationMethod != null) ? mSwitchTransformationMethod
				.getTransformation(text, this) : text;

		return new StaticLayout(transformed, mTextPaint, (int) Math.ceil(Layout
				.getDesiredWidth(transformed, mTextPaint)), Layout.Alignment.ALIGN_NORMAL, 1.f, 0, true);
	}

	/**
	 * @return true if (x, y) is within the target area of the switch thumb
	 */
	private boolean hitThumb(float x, float y) {
		// Relies on mTempRect, MUST be called first!
		final int thumbOffset = getThumbOffset();

		mThumbDrawable.getPadding(mTempRect);
		final int thumbTop = mSwitchTop - mTouchSlop;
		final int thumbLeft = mSwitchLeft + thumbOffset - mTouchSlop;
		final int thumbRight = thumbLeft + mThumbWidth + mTempRect.left + mTempRect.right + mTouchSlop;
		final int thumbBottom = mSwitchBottom + mTouchSlop;
		return x > thumbLeft && x < thumbRight && y > thumbTop && y < thumbBottom;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mVelocityTracker.addMovement(ev);
		final int action = MotionEventCompat.getActionMasked(ev);
		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			final float x = ev.getX();
			final float y = ev.getY();
			if (isEnabled() && hitThumb(x, y)) {
				getParent().requestDisallowInterceptTouchEvent(true);
				mTouchMode = TOUCH_MODE_DOWN;
				mTouchX = x;
				mTouchY = y;
			}
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			switch (mTouchMode) {
			case TOUCH_MODE_IDLE:
				// Didn't target the thumb, treat normally.
				break;

			case TOUCH_MODE_DOWN: {
				final float x = ev.getX();
				final float y = ev.getY();
				if (Math.abs(x - mTouchX) > mTouchSlop || Math.abs(y - mTouchY) > mTouchSlop) {
					mTouchMode = TOUCH_MODE_DRAGGING;
					getParent().requestDisallowInterceptTouchEvent(true);
					mTouchX = x;
					mTouchY = y;
					return true;
				}
				break;
			}

			case TOUCH_MODE_DRAGGING: {
				final float x = ev.getX();
				final int thumbScrollRange = getThumbScrollRange();
				final float thumbScrollOffset = x - mTouchX;
				float dPos;
				if (thumbScrollRange != 0) {
					dPos = thumbScrollOffset / thumbScrollRange;
				} else {
					// If the thumb scroll range is empty, just use the
					// movement direction to snap on or off.
					dPos = thumbScrollOffset > 0 ? 1 : -1;
				}
				if (ViewUtils.isLayoutRtl(this)) {
					dPos = -dPos;
				}
				final float newPos = constrain(mThumbPosition + dPos, 0, 1);
				if (newPos != mThumbPosition) {
					mTouchX = x;
					setThumbPosition(newPos);
				}
				return true;
			}
			}
			break;
		}

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL: {
			if (mTouchMode == TOUCH_MODE_DRAGGING) {
				stopDrag(ev);
				// Allow super class to handle pressed state, etc.
				super.onTouchEvent(ev);
				return true;
			}
			mTouchMode = TOUCH_MODE_IDLE;
			mVelocityTracker.clear();
			break;
		}
		}

		return super.onTouchEvent(ev);
	}

	private void cancelSuperTouch(MotionEvent ev) {
		MotionEvent cancel = MotionEvent.obtain(ev);
		cancel.setAction(MotionEvent.ACTION_CANCEL);
		super.onTouchEvent(cancel);
		cancel.recycle();
	}

	/**
	 * Called from onTouchEvent to end a drag operation.
	 * 
	 * @param ev
	 *            Event that triggered the end of drag mode - ACTION_UP or
	 *            ACTION_CANCEL
	 */
	private void stopDrag(MotionEvent ev) {
		mTouchMode = TOUCH_MODE_IDLE;

		// Commit the change if the event is up and not canceled and the switch
		// has not been disabled during the drag.
		final boolean commitChange = ev.getAction() == MotionEvent.ACTION_UP && isEnabled();
		final boolean newState;
		if (commitChange) {
			mVelocityTracker.computeCurrentVelocity(1000);
			final float xvel = mVelocityTracker.getXVelocity();
			if (Math.abs(xvel) > mMinFlingVelocity) {
				newState = ViewUtils.isLayoutRtl(this) ? (xvel < 0) : (xvel > 0);
			} else {
				newState = getTargetCheckedState();
			}
		} else {
			newState = isChecked();
		}

		setChecked(newState);
		cancelSuperTouch(ev);
	}

	private void animateThumbToCheckedState(boolean newCheckedState) {
		final float startPosition = mThumbPosition;
		final float targetPosition = newCheckedState ? 1 : 0;
		final float diff = targetPosition - startPosition;

		mPositionAnimator = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				setThumbPosition(startPosition + (diff * interpolatedTime));
			}
		};
		mPositionAnimator.setDuration(THUMB_ANIMATION_DURATION);
		startAnimation(mPositionAnimator);
	}

	private void cancelPositionAnimator() {
		if (mPositionAnimator != null) {
			clearAnimation();
			mPositionAnimator = null;
		}
	}

	private boolean getTargetCheckedState() {
		return mThumbPosition > 0.5f;
	}

	/**
	 * Sets the thumb position as a decimal value between 0 (off) and 1 (on).
	 * 
	 * @param position
	 *            new position between [0,1]
	 */
	private void setThumbPosition(float position) {
		mThumbPosition = position;
//		if(isPressed()){
//			if(mThumbDrawable != null){
//				mThumbDrawable.setColor(getPressedColor(convertColorOfPostion(mThumbPosition, mThumbColorOff, mThumbColorOn)));
//			}
//			if(mTrackDrawable != null){
//				mTrackDrawable.setColor((convertColorOfPostion(mThumbPosition, mTrackColorOff, mTrackColorOn)));
//			}
//		}else{
			if(mThumbDrawable != null){
				mThumbDrawable.setColor(convertColorOfPostion(position, mThumbColorOff, mThumbColorOn));
			}
			if(mTrackDrawable != null){
				mTrackDrawable.setColor(convertColorOfPostion(position, mTrackColorOff, mTrackColorOn));
			}
//		}
		
		invalidate();
	}
	
	private int convertColorOfPostion(float position, int color0, int color1){
		int a0 = Color.alpha(color0);
		int r0 = Color.red(color0);
		int g0 = Color.green(color0);
		int b0 = Color.blue(color0);
		
		int a1 = Color.alpha(color1);
		int r1 = Color.red(color1);
		int g1 = Color.green(color1);
		int b1 = Color.blue(color1);
		int a = (int) (a0 + (a1 - a0) * position);
		int r = (int) (r0 + (r1 - r0) * position);
		int g = (int) (g0 + (g1 - g0) * position);
		int b = (int) (b0 + (b1 - b0) * position);
		return Color.argb(a, r, g, b);
	}

	@Override
	public void toggle() {
		setChecked(!isChecked());
	}

	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);

		// Calling the super method may result in setChecked() getting called
		// recursively with a different value, so load the REAL value...
		checked = isChecked();

		if (getWindowToken() != null) {
			animateThumbToCheckedState(checked);
		} else {
			// Immediately move the thumb to the new position.
			cancelPositionAnimator();
			setThumbPosition(checked ? 1 : 0);
		}
	}
	
	public void setChecked(boolean checked, boolean animate){
		if(!animate){
			super.setChecked(checked);
			checked = isChecked();
			cancelPositionAnimator();
			setThumbPosition(checked ? 1 : 0);
		}else{
			setChecked(checked);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		int opticalInsetLeft = 0;
		int opticalInsetRight = 0;
		if (mThumbDrawable != null) {
			final Rect trackPadding = mTempRect;
			if (mTrackDrawable != null) {
				mTrackDrawable.getPadding(trackPadding);
			} else {
				trackPadding.setEmpty();
			}

			opticalInsetLeft = 0;
			opticalInsetRight = 0;
		}

		final int switchRight;
		final int switchLeft;
		if (ViewUtils.isLayoutRtl(this)) {
			switchLeft = getPaddingLeft() + opticalInsetLeft;
			switchRight = switchLeft + mSwitchWidth - opticalInsetLeft - opticalInsetRight;
		} else {
			switchRight = getWidth() - getPaddingRight() - opticalInsetRight;
			switchLeft = switchRight - mSwitchWidth + opticalInsetLeft + opticalInsetRight;
		}

		final int switchTop;
		final int switchBottom;
		switch (getGravity() & Gravity.VERTICAL_GRAVITY_MASK) {
		default:
		case Gravity.TOP:
			switchTop = getPaddingTop();
			switchBottom = switchTop + mSwitchHeight;
			break;

		case Gravity.CENTER_VERTICAL:
			switchTop = (getPaddingTop() + getHeight() - getPaddingBottom()) / 2 - mSwitchHeight / 2;
			switchBottom = switchTop + mSwitchHeight;
			break;

		case Gravity.BOTTOM:
			switchBottom = getHeight() - getPaddingBottom();
			switchTop = switchBottom - mSwitchHeight;
			break;
		}

		mSwitchLeft = switchLeft;
		mSwitchTop = switchTop;
		mSwitchBottom = switchBottom;
		mSwitchRight = switchRight;
	}

	@Override
	public void draw(Canvas c) {
		final Rect padding = mTempRect;
		final int switchLeft = mSwitchLeft;
		final int switchTop = mSwitchTop;
		final int switchRight = mSwitchRight;
		final int switchBottom = mSwitchBottom;

		int thumbInitialLeft = switchLeft + getThumbOffset();

		// Layout the track.
		if (mTrackDrawable != null) {
			mTrackDrawable.getPadding(padding);

			// Adjust thumb position for track padding.
			thumbInitialLeft += padding.left;

			// If necessary, offset by the optical insets of the thumb asset.
			int trackLeft = switchLeft;
			int trackTop = switchTop;
			int trackRight = switchRight;
			int trackBottom = switchBottom;
			mTrackDrawable.setBounds(trackLeft, trackTop, trackRight, trackBottom);
		}

		// Layout the thumb.
		if (mThumbDrawable != null) {
			mThumbDrawable.getPadding(padding);

			final int thumbLeft = thumbInitialLeft - padding.left;
			final int thumbRight = thumbInitialLeft + mThumbWidth + padding.right;
			mThumbDrawable.setBounds(thumbLeft, switchTop, thumbRight, switchBottom);

			final Drawable background = getBackground();
			if (background != null) {
				DrawableCompat.setHotspotBounds(background, thumbLeft, switchTop, thumbRight, switchBottom);
			}
		}

		// Draw the background.
		super.draw(c);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(!isEnabled()){//wangbin added this line
			if(mThumbDrawable != null){
				mThumbDrawable.setColor(getDisableColor(isChecked() ? mThumbColorOn : mThumbColorOff));
			}
			if(mTrackDrawable != null){
				mTrackDrawable.setColor(getDisableColor(isChecked() ? mTrackColorOn : mTrackColorOff));
			}
		}
		final Rect padding = mTempRect;
		final Drawable trackDrawable = mTrackDrawable;
		if (trackDrawable != null) {
			trackDrawable.getPadding(padding);
		} else {
			padding.setEmpty();
		}

		final int switchTop = mSwitchTop;
		final int switchBottom = mSwitchBottom;
		final int switchInnerTop = switchTop + padding.top;
		final int switchInnerBottom = switchBottom - padding.bottom;

		final Drawable thumbDrawable = mThumbDrawable;
		if (trackDrawable != null) {
			trackDrawable.draw(canvas);
		}

		final int saveCount = canvas.save();

		if (thumbDrawable != null) {
			thumbDrawable.draw(canvas);
		}

		final Layout switchText = getTargetCheckedState() ? mOnLayout : mOffLayout;
		if (switchText != null) {
			final int drawableState[] = getDrawableState();
			if (mTextColors != null) {
				mTextPaint.setColor(mTextColors.getColorForState(drawableState, 0));
			}
			mTextPaint.drawableState = drawableState;

			final int cX;
			if (thumbDrawable != null) {
				final Rect bounds = thumbDrawable.getBounds();
				cX = bounds.left + bounds.right;
			} else {
				cX = getWidth();
			}

			final int left = cX / 2 - switchText.getWidth() / 2;
			final int top = (switchInnerTop + switchInnerBottom) / 2 - switchText.getHeight() / 2;
			canvas.translate(left, top);
			switchText.draw(canvas);
		}

		canvas.restoreToCount(saveCount);
	}

	@Override
	public int getCompoundPaddingLeft() {
		if (!ViewUtils.isLayoutRtl(this)) {
			return super.getCompoundPaddingLeft();
		}
		int padding = super.getCompoundPaddingLeft() + mSwitchWidth;
		if (!TextUtils.isEmpty(getText())) {
			padding += mSwitchPadding;
		}
		return padding;
	}

	@Override
	public int getCompoundPaddingRight() {
		if (ViewUtils.isLayoutRtl(this)) {
			return super.getCompoundPaddingRight();
		}
		int padding = super.getCompoundPaddingRight() + mSwitchWidth;
		if (!TextUtils.isEmpty(getText())) {
			padding += mSwitchPadding;
		}
		return padding;
	}

	/**
	 * Translates thumb position to offset according to current RTL setting and
	 * thumb scroll range. Accounts for both track and thumb padding.
	 * 
	 * @return thumb offset
	 */
	private int getThumbOffset() {
		final float thumbPosition;
		if (ViewUtils.isLayoutRtl(this)) {
			thumbPosition = 1 - mThumbPosition;
		} else {
			thumbPosition = mThumbPosition;
		}
		return (int) (thumbPosition * getThumbScrollRange() + 0.5f);
	}

	private int getThumbScrollRange() {
		if (mTrackDrawable != null) {
			final Rect padding = mTempRect;
			mTrackDrawable.getPadding(padding);
			return mSwitchWidth - mThumbWidth - padding.left - padding.right;
		} else {
			return 0;
		}
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (isChecked()) {
			mergeDrawableStates(drawableState, CHECKED_STATE_SET);
		}
		return drawableState;
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();

		final int[] myDrawableState = getDrawableState();

		if (mThumbDrawable != null) {
			mThumbDrawable.setState(myDrawableState);
		}

		if (mTrackDrawable != null) {
			mTrackDrawable.setState(myDrawableState);
		}

		invalidate();
	}

	@Override
	public void drawableHotspotChanged(float x, float y) {
		super.drawableHotspotChanged(x, y);

		if (mThumbDrawable != null) {
			DrawableCompat.setHotspot(mThumbDrawable, x, y);
		}

		if (mTrackDrawable != null) {
			DrawableCompat.setHotspot(mTrackDrawable, x, y);
		}
	}

	@Override
	protected boolean verifyDrawable(Drawable who) {
		return super.verifyDrawable(who) || who == mThumbDrawable || who == mTrackDrawable;
	}

	@Override
	public void jumpDrawablesToCurrentState() {
		if (Build.VERSION.SDK_INT >= 11) {
			super.jumpDrawablesToCurrentState();

			if (mThumbDrawable != null) {
				mThumbDrawable.jumpToCurrentState();
			}

			if (mTrackDrawable != null) {
				mTrackDrawable.jumpToCurrentState();
			}

			if (mPositionAnimator != null && mPositionAnimator.hasStarted() && !mPositionAnimator.hasEnded()) {
				clearAnimation();
				mPositionAnimator = null;
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
		super.onInitializeAccessibilityEvent(event);
		event.setClassName(SmoothSwitch.class.getName());
	}

	@Override
	public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
		if (Build.VERSION.SDK_INT >= 14) {
			super.onInitializeAccessibilityNodeInfo(info);
			info.setClassName(SmoothSwitch.class.getName());
			CharSequence switchText = isChecked() ? mTextOn : mTextOff;
			if (!TextUtils.isEmpty(switchText)) {
				CharSequence oldText = info.getText();
				if (TextUtils.isEmpty(oldText)) {
					info.setText(switchText);
				} else {
					StringBuilder newText = new StringBuilder();
					newText.append(oldText).append(' ').append(switchText);
					info.setText(newText);
				}
			}
		}
	}

	/**
	 * Taken from android.util.MathUtils
	 */
	private static float constrain(float amount, float low, float high) {
		return amount < low ? low : (amount > high ? high : amount);
	}
	
	
//	static class TintManager{
//		private ColorStateList getSwitchTrackColorStateList() {
//	        if (mSwitchTrackStateList == null) {
//	            final int[][] states = new int[3][];
//	            final int[] colors = new int[3];
//	            int i = 0;
//
//	            // Disabled state
//	            states[i] = new int[] { -android.R.attr.state_enabled };
//	            colors[i] = getThemeAttrColor(android.R.attr.colorForeground, 0.1f);
//	            i++;
//
//	            states[i] = new int[] { android.R.attr.state_checked };
//	            colors[i] = getThemeAttrColor(R.attr.colorControlActivated, 0.3f);
//	            i++;
//
//	            // Default enabled state
//	            states[i] = new int[0];
//	            colors[i] = getThemeAttrColor(android.R.attr.colorForeground, 0.3f);
//	            i++;
//
//	            mSwitchTrackStateList = new ColorStateList(states, colors);
//	        }
//	        return mSwitchTrackStateList;
//	    }
//
//	    private ColorStateList getSwitchThumbColorStateList() {
//	        if (mSwitchThumbStateList == null) {
//	            final int[][] states = new int[3][];
//	            final int[] colors = new int[3];
//	            int i = 0;
//
//	            // Disabled state
//	            states[i] = new int[] { -android.R.attr.state_enabled };
//	            colors[i] = getDisabledThemeAttrColor(R.attr.colorSwitchThumbNormal);
//	            i++;
//
//	            states[i] = new int[] { android.R.attr.state_checked };
//	            colors[i] = getThemeAttrColor(R.attr.colorControlActivated);
//	            i++;
//
//	            // Default enabled state
//	            states[i] = new int[0];
//	            colors[i] = getThemeAttrColor(R.attr.colorSwitchThumbNormal);
//	            i++;
//
//	            mSwitchThumbStateList = new ColorStateList(states, colors);
//	        }
//	        return mSwitchThumbStateList;
//	    }
//
//	    int getThemeAttrColor(int attr) {
//	        if (mContext.getTheme().resolveAttribute(attr, mTypedValue, true)) {
//	            if (mTypedValue.type >= TypedValue.TYPE_FIRST_INT
//	                    && mTypedValue.type <= TypedValue.TYPE_LAST_INT) {
//	                return mTypedValue.data;
//	            } else if (mTypedValue.type == TypedValue.TYPE_STRING) {
//	                return mResources.getColor(mTypedValue.resourceId);
//	            }
//	        }
//	        return 0;
//	    }
//
//	    int getThemeAttrColor(int attr, float alpha) {
//	        final int color = getThemeAttrColor(attr);
//	        final int originalAlpha = Color.alpha(color);
//
//	        // Return the color, multiplying the original alpha by the disabled value
//	        return (color & 0x00ffffff) | (Math.round(originalAlpha * alpha) << 24);
//	    }
//
//	    int getDisabledThemeAttrColor(int attr) {
//	        // Now retrieve the disabledAlpha value from the theme
//	        mContext.getTheme().resolveAttribute(android.R.attr.disabledAlpha, mTypedValue, true);
//	        final float disabledAlpha = mTypedValue.getFloat();
//
//	        return getThemeAttrColor(attr, disabledAlpha);
//	    }
//	}
}