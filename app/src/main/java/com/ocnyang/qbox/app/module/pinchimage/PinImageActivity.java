package com.ocnyang.qbox.app.module.pinchimage;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.utils.StateBarTranslucentUtils;
import com.ocnyang.qbox.app.widget.custom.PinchImageView;

import butterknife.BindView;

public class PinImageActivity extends BaseCommonActivity {

    public static final String IMG_URL = "IMG_URL";
    public static final String IMG_NAME = "IMG_NAME";

    @BindView(R.id.img_pinimg)
    PinchImageView mImgPinimg;
    @BindView(R.id.tv_pinimg)
    TextView mTvPinimg;
    public String mImgUrl;
    public String mImgName;


    @Override
    public void initContentView() {
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        setContentView(R.layout.activity_pin_image);
    }

    @Override
    public void initView() {
        initIntent();
        initImg();
        initImgName();
    }

    private void initImgName() {
        if (TextUtils.isEmpty(mImgName)) {
            if (mTvPinimg.getVisibility() == View.VISIBLE)
                mTvPinimg.setVisibility(View.GONE);
            return;
        }
        if (mTvPinimg.getVisibility() == View.GONE)
            mTvPinimg.setVisibility(View.VISIBLE);
        mTvPinimg.setText(mImgName);
    }

    private void initImg() {
        mImgPinimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollback();
            }
        });

        if (TextUtils.isEmpty(mImgUrl)) {
            return;
        }
        if (mImgUrl.toUpperCase().endsWith(".GIF")) {
            Glide.with(this)
                    .load(mImgUrl)
                    .asGif()
                    .placeholder(R.drawable.lodingview)
                    .error(R.drawable.errorview)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mImgPinimg);
        } else {
            Glide.with(this)
                    .load(mImgUrl)
                    .placeholder(R.drawable.lodingview)
                    .error(R.drawable.errorview)
                    .into(mImgPinimg);
        }

    }

    private void rollback() {
//        finish();
        onBackPressed();
    }

    private void initIntent() {
        Intent intent = getIntent();
        mImgUrl = intent.getStringExtra(IMG_URL);
        mImgName = intent.getStringExtra(IMG_NAME);
    }

    @Override
    public void initPresenter() {

    }


}
