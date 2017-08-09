package com.ocnyang.qbox.app.module.me;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseFragment;
import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.model.entities.RefreshMeFragmentEvent;
import com.ocnyang.qbox.app.module.me.weather.weather.WeatherActivity;
import com.ocnyang.qbox.app.module.setting.SettingActivity;
import com.ocnyang.qbox.app.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MeFragment extends BaseFragment {

    @BindView(R.id.homebg_me)
    ImageView mHomebgMe;
    @BindView(R.id.motto_me)
    TextView mMottoMe;
    @BindView(R.id.userhead_me)
    CircleImageView mUserheadMe;
    @BindView(R.id.username_me)
    TextView mUsernameMe;
    @BindView(R.id.rili_me)
    LinearLayout mRiliMe;
    @BindView(R.id.tianqi_me)
    LinearLayout mTianqiMe;
    @BindView(R.id.led_me)
    LinearLayout mLedMe;
    @BindView(R.id.sdt_me)
    LinearLayout mSdtMe;
    @BindView(R.id.erweima_me)
    LinearLayout mErweimaMe;
    @BindView(R.id.setting_me)
    LinearLayout mSettingMe;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    public MeFragment() {
    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_me_new;
    }

    @Override
    public void initView() {
        //订阅事件
        EventBus.getDefault().register(this);

        initUserInfo();

    }

    private void initUserInfo() {
        String username = (String) SPUtils.get(getContext(), Const.USER_NAME, "");
        String userhader = (String) SPUtils.get(getContext(), Const.USER_HEADER, "");
        String usergeyan = (String) SPUtils.get(getContext(), Const.USER_GEYAN, "我愿做你世界里的太阳，给你温暖。");
        if (!TextUtils.isEmpty(username)) {
            mUsernameMe.setText(username);
        }
        if (!TextUtils.isEmpty(userhader)) {
            Glide.with(getContext()).load(new File(userhader)).into(mUserheadMe);
        }
        if (!TextUtils.isEmpty(usergeyan)) {
            mMottoMe.setText(usergeyan);
        }
    }

    @Override
    protected void managerArguments() {
    }


    @OnClick({R.id.rili_me, R.id.tianqi_me, R.id.led_me, R.id.sdt_me, R.id.erweima_me, R.id.setting_me, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rili_me:
                startActivity(new Intent(getContext(),CalendarActivity.class));
                break;
            case R.id.tianqi_me:
                startActivity(new Intent(getContext(), WeatherActivity.class));
                break;
            case R.id.led_me:
                startActivity(new Intent(getContext(),LEDActivity.class));
                break;
            case R.id.sdt_me:
                startActivity(new Intent(getContext(),FlashActivity.class));
                break;
            case R.id.erweima_me:
                startActivity(new Intent(getContext(),ZxingActivity.class));
                break;
            case R.id.setting_me:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.fab:
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        mUserheadMe,
                        getString(R.string.transition_userhead)
                );

                ActivityCompat.startActivity((Activity) getContext(),intent,optionsCompat.toBundle());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnRefreshUserInfo(RefreshMeFragmentEvent event) {
        if (event.getMark_code() == 0x1000) {
            initUserInfo();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Override
    public String getUmengFragmentName() {
        return getContext().getClass().getSimpleName()+"-"
                +this.getClass().getSimpleName();
    }

}
