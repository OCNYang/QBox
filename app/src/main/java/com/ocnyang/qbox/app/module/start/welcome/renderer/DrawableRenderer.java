/*
 *   The MIT License (MIT)
 *
 *   Copyright (c) 2015 Cleveroad
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */
package com.ocnyang.qbox.app.module.start.welcome.renderer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatDrawableManager;

import com.cleveroad.slidingtutorial.Renderer;
import com.ocnyang.qbox.app.R;
import com.orhanobut.logger.Logger;

/**
 * 圆形  对号圆形 轮播图形
 * {@link Renderer} implementation for drawing indicators with bitmap.
 */
@SuppressWarnings("WeakerAccess")
public class DrawableRenderer implements Renderer {

    private Drawable mDrawableActive;
    private Drawable mDrawable;

    public static DrawableRenderer create(@NonNull Context context) {
        return new DrawableRenderer(context);
    }

    private DrawableRenderer(@NonNull Context context) {
        //解决 vector “资源未找到” 错误
        try {
            mDrawableActive = AppCompatDrawableManager.get()
                    .getDrawable(context, R.drawable.vec_checkbox_fill_circle_outline);
            mDrawable = AppCompatDrawableManager.get()
                    .getDrawable(context, R.drawable.vec_checkbox_blank_circle_outline);
        } catch (Resources.NotFoundException notFoundException) {
            Logger.e(notFoundException.getMessage());
        } catch (Exception e) {
            Logger.e(e.getMessage());
        } finally {
            mDrawableActive = context.getResources().getDrawable(R.drawable.vec_checkbox_fill_circle);
            mDrawable = context.getResources().getDrawable(R.drawable.vec_checkbox_empty_circle);
        }

    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull RectF elementBounds, @NonNull Paint paint, boolean isActive) {
        Drawable drawable = isActive ? mDrawableActive : mDrawable;
        drawable.setBounds((int) elementBounds.left, (int) elementBounds.top,
                (int) elementBounds.right, (int) elementBounds.bottom);
        drawable.draw(canvas);
    }
}
