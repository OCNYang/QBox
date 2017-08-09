package com.ocnyang.qbox.app.module.find.constellation;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseFragment;
import com.ocnyang.qbox.app.model.entities.RefreshConstellationEvent;
import com.ocnyang.qbox.app.model.entities.constellation.BaseConstellation;
import com.ocnyang.qbox.app.model.entities.constellation.MonthConstellation;
import com.ocnyang.qbox.app.model.entities.constellation.WeekConstellation;
import com.ocnyang.qbox.app.network.Network;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class WeekAndMonthConstellationFragment extends BaseFragment {
    private static final String ARG_WEEKORMONTH = "weekOrMonth";
    private static final String ARG_CONSTELLATION = "constellation";
    public static final String WEEK = "week";
    public static final String NEXTWEEK = "nextweek";
    public static final String MONTH = "month";

    @BindView(R.id.allorwork_name_week)
    TextView mAllorworkNameWeek;
    @BindView(R.id.allorwork_text_week)
    TextView mAllorworkTextWeek;
    @BindView(R.id.health_text_week)
    TextView mHealthTextWeek;
    @BindView(R.id.love_text_week)
    TextView mLoveTextWeek;
    @BindView(R.id.money_text_week)
    TextView mMoneyTextWeek;
    @BindView(R.id.job_text_week)
    TextView mJobTextWeek;
    @BindView(R.id.date_week)
    TextView mDateWeek;

    private String mWeekOrMonth;
    private String mConstellation;
    private Subscription mSubscription;

    Observer<BaseConstellation> mObserver = new Observer<BaseConstellation>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Logger.e("onError:" + e.getMessage());
        }

        @Override
        public void onNext(BaseConstellation baseConstellation) {
            if (baseConstellation.getError_code() == 0)
                setDataToView(baseConstellation);
        }
    };

    private void setDataToView(BaseConstellation baseConstellation) {
        String allOrwork = null;
        String allOrworkText = null;
        String healthText = baseConstellation.getHealth();
        String jobText = baseConstellation.getWork();
        String loveText = baseConstellation.getLove();
        String moneyText = baseConstellation.getMoney();
        String date = baseConstellation.getDate();
        if (mWeekOrMonth.equals(WEEK) || mWeekOrMonth.equals(NEXTWEEK)) {
            WeekConstellation weekConstellation = (WeekConstellation) baseConstellation;
            allOrwork = "求职";
            allOrworkText = weekConstellation.getJob();
        } else if (mWeekOrMonth.equals(MONTH)) {
            MonthConstellation monthConstellation = (MonthConstellation) baseConstellation;
            allOrwork = "全部";
            allOrworkText = monthConstellation.getAll();
        }
        mAllorworkNameWeek.setText(allOrwork);
        mAllorworkTextWeek.setText(allOrworkText);
        mHealthTextWeek.setText(healthText);
        mJobTextWeek.setText(jobText);
        mLoveTextWeek.setText(loveText);
        mMoneyTextWeek.setText(moneyText);
        mDateWeek.setText(date);
    }


    public WeekAndMonthConstellationFragment() {
    }

    public static WeekAndMonthConstellationFragment newInstance(String param1, String param2) {
        WeekAndMonthConstellationFragment fragment = new WeekAndMonthConstellationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_WEEKORMONTH, param1);
        args.putString(ARG_CONSTELLATION, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_week_and_month;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        requestData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshConstellationEvent(RefreshConstellationEvent refreshConstellationEvent){
        mConstellation = refreshConstellationEvent.getConstellation();
        requestData();
    }

    private void requestData() {
        unsubscribe();
        if (mWeekOrMonth.equals(WEEK) || mWeekOrMonth.equals(NEXTWEEK)) {
            mSubscription = Network.getmWeekConstellationsApi()
                    .getWeekConstellation(TextUtils.isEmpty(mConstellation) ? "水瓶座" : mConstellation, mWeekOrMonth, "35de7ea555a8b5d58ce2d7e4f8cb7c9f")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mObserver);
        } else if (mWeekOrMonth.equals(MONTH)) {
            mSubscription = Network.getmMonthConstellationsApi()
                    .getMonthConstellation(TextUtils.isEmpty(mConstellation) ? "水瓶座" : mConstellation, mWeekOrMonth, "35de7ea555a8b5d58ce2d7e4f8cb7c9f")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mObserver);
        }
    }

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    protected void managerArguments() {
        mWeekOrMonth = getArguments().getString(ARG_WEEKORMONTH);
        mConstellation = getArguments().getString(ARG_CONSTELLATION);
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
