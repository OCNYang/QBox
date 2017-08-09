package com.ocnyang.qbox.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ocnyang.qbox.app.R;

/*************************************************************
 * Created by OCN.Yang           * * * *   * * * *   *     * *
 * Time:2016/11/24 10:46         *     *   *         * *   * *
 * Email address:yangocn@163.com *     *   *         *   * * *
 * Web site:www.ocnyang.com      * * * *   * * * *   *     * *
 *************************************************************/


public class TabBar_Mains extends LinearLayout {

    private String sName;
    private Drawable sIcon;
    private ImageView sIconImgView;
    private TextView sNameTv;

    public TabBar_Mains(Context context) {
        this(context, null);
    }

    public TabBar_Mains(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.tabbar_mains, this, true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabBar_Attr);
        sName = typedArray.getString(R.styleable.TabBar_Attr_name);
        sIcon = typedArray.getDrawable(R.styleable.TabBar_Attr_icon);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        sIconImgView = (ImageView) findViewById(R.id.icon_tabbar);
        sNameTv = (TextView) findViewById(R.id.name_tabbar);
        if (TextUtils.isEmpty(sName)) ;
        else setName(sName);
        if (sIcon != null)
            setIcon(sIcon);
    }

    public void setName(String name) {
        sNameTv.setText(name);

    }

    public void setIcon(Drawable icon) {
        sIconImgView.setImageDrawable(icon);
    }
}
