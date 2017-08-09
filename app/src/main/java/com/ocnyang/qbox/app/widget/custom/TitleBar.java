package com.ocnyang.qbox.app.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ocnyang.qbox.app.R;


/**
 * 自定义标题栏
 */
public class TitleBar extends LinearLayout {
    private LayoutInflater mInflater;
    private View mHeader;
    private LinearLayout mLayoutLeftContainer;
    private LinearLayout mLayoutRightContainer;
    private LinearLayout mLayoutCenterContainer;
    private LinearLayout mHeaderTitle;
    private TextView mTvTitle;
    private ImageButton mLeftImageButton;
    private ImageButton mRightImageButton;

    public TitleBar(Context context) {
        super(context);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mHeader = mInflater.inflate(R.layout.common_header, null);
        addView(mHeader);
        initViews();
    }

    /**
     * 初始化布局
     */
    private void initViews() {
        mLayoutLeftContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_leftview_container);
        mLayoutRightContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_rightview_container);

        mHeaderTitle = (LinearLayout) findViewByHeaderId(R.id.header_title);
        mTvTitle = (TextView) findViewByHeaderId(R.id.header_htv_subtitle);

        mLayoutCenterContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_middleview_container);

        initLeftView();
        initRightView();

        mLayoutLeftContainer.setVisibility(View.INVISIBLE);
        mLayoutRightContainer.setVisibility(View.INVISIBLE);

        mHeaderTitle.setVisibility(View.VISIBLE);
        mTvTitle.setVisibility(View.VISIBLE);

        mLayoutCenterContainer.setVisibility(View.GONE);
    }

    /**
     * 初始化左侧按钮
     */
    private void initLeftView() {
        View mleftImageButtonView = mInflater.inflate(
                R.layout.common_header_leftbutton, null);
        mLayoutLeftContainer.addView(mleftImageButtonView);
        mLeftImageButton = (ImageButton) mleftImageButtonView
                .findViewById(R.id.ib_titlebar_left);
    }

    /**
     * 初始化右侧按钮
     */
    private void initRightView() {
        View mRightImageButtonView = mInflater.inflate(
                R.layout.common_header_rightbutton, null);
        mLayoutRightContainer.addView(mRightImageButtonView);
        mRightImageButton = (ImageButton) mRightImageButtonView
                .findViewById(R.id.ib_titlebar_right);
    }

    /**
     * 在TitleBar中查找指定控件
     */
    public View findViewByHeaderId(int id) {
        return mHeader.findViewById(id);
    }

    /**
     * 设置TitleBar的标题
     */
    public void setTitle(CharSequence title) {
        mTvTitle.setText(title);
        mHeaderTitle.setVisibility(View.VISIBLE);
        mLayoutCenterContainer.setVisibility(View.GONE);
    }

    /**
     * 设置TitleBar的标题
     *
     * @param res
     */
    public void setTitle(int res) {
        mTvTitle.setText(res);
        mHeaderTitle.setVisibility(View.VISIBLE);
        mLayoutCenterContainer.setVisibility(View.GONE);
    }

    /**
     * 自定义左侧视图
     */
    public void setLeftView(View view) {
        mLayoutLeftContainer.removeAllViews();
        mLayoutLeftContainer.addView(view);
        mLayoutLeftContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 自定义居中视图
     */
    public void setCenterView(View view) {
        mHeaderTitle.setVisibility(View.GONE);
        mLayoutCenterContainer.removeAllViews();
        mLayoutCenterContainer.addView(view);
        mLayoutCenterContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 自定义右侧视图
     */
    public void setRightView(View view) {
        mLayoutRightContainer.removeAllViews();
        mLayoutRightContainer.addView(view);
        mLayoutRightContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 设置文字标题以及左右的View
     *
     * @param title
     * @param leftView
     * @param rightView
     */
    public void setTitleBar(CharSequence title, View leftView, View rightView) {
        setTitle(title);
        setLeftView(leftView);
        setRightView(rightView);
    }

    /**
     * 设置文字标题以及左右的View
     *
     * @param title
     * @param leftView
     * @param rightView
     */
    public void setTitleBar(int title, View leftView, View rightView) {
        setTitle(title);
        setLeftView(leftView);
        setRightView(rightView);
    }
}
