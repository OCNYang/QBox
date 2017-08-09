package com.ocnyang.qbox.app.module.me.weather.android.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UiUtil {
	
	public static int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
	
	public static float px2dip(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (px / scale);
	}
	
	public static int getStatusBarHeight() {
		final Resources res = Resources.getSystem();
		int id = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
		if (id > 0) {
			return res.getDimensionPixelSize(id);
		}
		return 0;
	}

	public static void setMIUIStatusBarDarkMode(boolean darkmode, Activity activity) {
		Class<? extends Window> clazz = activity.getWindow().getClass();
		try {
			int darkModeFlag = 0;
			Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
			Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
			darkModeFlag = field.getInt(layoutParams);
			Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
			extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void invalidateViewInViewGroup(View rootView){
		if(rootView == null){
			return ;
		}
		if(rootView instanceof ViewGroup){
			ViewGroup rootGroup = (ViewGroup) rootView;
			for(int i = 0 ; i < rootGroup.getChildCount() ;i++){
				invalidateViewInViewGroup(rootGroup.getChildAt(i));
			}
		}else{
			rootView.invalidate();
		}
	}
	public static void copyString(Context context,String text){
    	if(TextUtils.isEmpty(text)){
    		return ;
    	}
    	ClipboardManager clipboard = (ClipboardManager) context.getSystemService(
                Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(null, text));
    }
	
	public static float getTextPaintOffset(Paint paint) {
		FontMetrics fontMetrics = paint.getFontMetrics();
		return -(fontMetrics.bottom - fontMetrics.top) / 2f - fontMetrics.top;
	}
	
	public static void toastDebug(Context context,String msg){
		if(context == null){
			return ;
		}
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	public static void logDebug(String tag,String msg){
		Log.d(tag, msg);
	}
}
