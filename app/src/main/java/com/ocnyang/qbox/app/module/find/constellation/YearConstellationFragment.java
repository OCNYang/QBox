package com.ocnyang.qbox.app.module.find.constellation;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseFragment;
import com.ocnyang.qbox.app.model.entities.RefreshConstellationEvent;
import com.ocnyang.qbox.app.model.entities.constellation.YearConstellation;
import com.ocnyang.qbox.app.network.Network;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class YearConstellationFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.title_year_star)
    TextView mTitleYearStar;
    @BindView(R.id.mima_info_year_star)
    TextView mMimaInfoYearStar;
    @BindView(R.id.mima_text_year_star)
    TextView mMimaTextYearStar;
    @BindView(R.id.love_year_star)
    TextView mLoveYearStar;
    @BindView(R.id.career_year_star)
    TextView mCareerYearStar;
    @BindView(R.id.finance_year_star)
    TextView mFinanceYearStar;
    @BindView(R.id.lucklystone_year_star)
    TextView mLucklystone;

    private String mSelectConstellationName;
    private String mParam2;
    private Subscription mSubscription;

    Observer<YearConstellation> mObserver = new Observer<YearConstellation>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Logger.e("onError:" + e.getMessage());
        }

        @Override
        public void onNext(YearConstellation yearConstellation) {
            if (yearConstellation.getError_code() == 0)
                setDataToView(yearConstellation);
        }
    };

    private void setDataToView(YearConstellation yearConstellation) {
        mTitleYearStar.setText(yearConstellation.getDate()+yearConstellation.getName()+"整体运势");

        mMimaInfoYearStar.setText(yearConstellation.getMima().getInfo()+"");
        mMimaTextYearStar.setText(yearConstellation.getMima().getText().get(0)+"");
        mLoveYearStar.setText(yearConstellation.getLove().get(0)+"");
        mCareerYearStar.setText(yearConstellation.getCareer().get(0)+"");
        mFinanceYearStar.setText(yearConstellation.getFinance().get(0)+"");

        mLucklystone.setText("幸运石："+yearConstellation.getLuckeyStone());
    }

    public YearConstellationFragment() {
    }

    public static YearConstellationFragment newInstance(String param1, String param2) {
        YearConstellationFragment fragment = new YearConstellationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_year_constellation;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        requestData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshConstellationEvent(RefreshConstellationEvent refreshConstellationEvent) {
        mSelectConstellationName = refreshConstellationEvent.getConstellation();
        requestData();
    }

    private void requestData() {
        unsubscribe();
        mSubscription = Network.getmYearConstellationsApi()
                .getYearConstellation(TextUtils.isEmpty(mSelectConstellationName) ? "水瓶座" : mSelectConstellationName,
                        "year", "35de7ea555a8b5d58ce2d7e4f8cb7c9f")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    protected void managerArguments() {
        mSelectConstellationName = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
    }

    @Override
    public String getUmengFragmentName() {
        return getContext().getClass().getSimpleName()+"-"
                +this.getClass().getSimpleName();
    }
}
