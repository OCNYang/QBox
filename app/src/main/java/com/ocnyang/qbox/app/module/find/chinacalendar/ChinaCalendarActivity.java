package com.ocnyang.qbox.app.module.find.chinacalendar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.orhanobut.logger.Logger;
import com.robertlevonyan.views.chip.Chip;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.model.entities.ChinaCalendar;
import com.ocnyang.qbox.app.network.Network;
import com.ocnyang.qbox.app.utils.DateUtils;
import com.ocnyang.qbox.app.utils.SPUtils;
import com.ocnyang.qbox.app.utils.webviewutils.ObjectSaveManager;

import java.util.Calendar;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChinaCalendarActivity extends BaseCommonActivity {

    public static final String CHINA_CALENDAR = "chinacalendar";

    @BindView(R.id.lunar1_ccheader)
    TextView mLunar1Ccheader;
    @BindView(R.id.date1_ccheader)
    TextView mDate1Ccheader;
    @BindView(R.id.chinayear1_ccheader)
    TextView mChinayear1Ccheader;
    @BindView(R.id.week1_ccheader)
    TextView mWeek1Ccheader;
    @BindView(R.id.lunar2_ccheader)
    TextView mLunar2Ccheader;
    @BindView(R.id.chinayear2_ccheader)
    TextView mChinayear2Ccheader;
    @BindView(R.id.date2_ccheader)
    TextView mDate2Ccheader;
    @BindView(R.id.animals1_ccheader)
    TextView mAnimals1Ccheader;
    @BindView(R.id.animals2_ccheader)
    TextView mAnimals2Ccheader;
    @BindView(R.id.week2_ccheader)
    TextView mWeek2Ccheader;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.flexboxlayout_china_calendar)
    FlexboxLayout mFlexboxlayoutChinaCalendar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_china_calendar)
    TextView mTitle;
    private Subscription mSubscription;
    private String mDate;
    ObjectSaveManager objectSaveManager;

    Observer<ChinaCalendar> mObserver = new Observer<ChinaCalendar>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Logger.e("onError:" + e.getMessage());
        }

        @Override
        public void onNext(ChinaCalendar chinaCalendar) {
            if (chinaCalendar.getError_code() == 0) {
                Logger.e(chinaCalendar.getResult().getData().toString() + "");

//                initDateView(chinaCalendar.getResult().getData());
                saveObject(chinaCalendar.getResult().getData());
//                EventBus.getDefault().post(chinaCalendar.getResult().getData());
                initDateView(chinaCalendar.getResult().getData());
            }else {
                Toast.makeText(ChinaCalendarActivity.this, "请求数据失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void saveObject(ChinaCalendar.ResultBean.DataBean data) {
        SPUtils.put(this, CHINA_CALENDAR, data.getDate());
        objectSaveManager.saveObject(CHINA_CALENDAR + mDate, data);

    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_china_calendar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initView() {
        objectSaveManager = ObjectSaveManager.getInstance(this.getApplicationContext());

//        EventBus.getDefault().register(this);
        initToolbar();
        initFAB();
        initDate();
//        requestStarData();
    }

    private void initDate() {
        String nowDate = new StringBuffer()
                .append(DateUtils.getCurrYear()).append("-")
                .append(DateUtils.getCurrMonth()).append("-")
                .append(DateUtils.getCurrDay())
                .toString();

        if (SPUtils.contains(this, CHINA_CALENDAR)) {
            mDate = nowDate;
            ChinaCalendar.ResultBean.DataBean data = (ChinaCalendar.ResultBean.DataBean) objectSaveManager.getObject(CHINA_CALENDAR + nowDate);
            if (data == null) {
                requestStarData();
            }else {
                initDateView(data);
            }

//            String oldDate = (String) SPUtils.get(this, CHINA_CALENDAR, "1993-3-15");
//            int yearDiff = DateUtils.yearDiff(oldDate, nowDate);
//            if (yearDiff > 0) {
//                //重新请求数据
////                SPUtils.put(this, CHINA_CALENDAR, nowDate);
//                requestStarData();
//            } else {
//                //取出本地数据
//                ChinaCalendar.ResultBean.DataBean data = (ChinaCalendar.ResultBean.DataBean) objectSaveManager.getObject(CHINA_CALENDAR + oldDate);
//                if (data == null) {
//                    requestStarData();
//                } else {
//                    initDateView(data);
//                }
//            }
        } else {
//            SPUtils.put(this, CHINA_CALENDAR, nowDate);
            mDate = nowDate;
            requestStarData();
        }
    }

    /**
     * 填充数据
     *
     * @param data
     */
    private void initDateView(ChinaCalendar.ResultBean.DataBean data) {

//        getSupportActionBar().setTitle(mDate);
        mTitle.setText(data.getDate()+"");

        String avoid = getStringFormat(data.getAvoid());
        String animalsYear = getStringFormat(data.getAnimalsYear());
        String weekday = getStringFormat(data.getWeekday());
        String suit = getStringFormat(data.getSuit());
        String lunarYear = getStringFormat(data.getLunarYear());
        String lunar = getStringFormat(data.getLunar());
        String date = getStringFormat(data.getDate());

        mAnimals1Ccheader.setText(animalsYear + "年");
        mAnimals2Ccheader.setText(animalsYear + "年");
        mWeek1Ccheader.setText(weekday);
        mWeek2Ccheader.setText(weekday);
        mChinayear1Ccheader.setText(lunarYear);
        mChinayear2Ccheader.setText(lunarYear);
        mLunar1Ccheader.setText(lunar);
        mLunar2Ccheader.setText(lunar);
        mDate1Ccheader.setText(date);
        mDate2Ccheader.setText(date);


        String[] avoidArr = avoid.split("\\.", 0);
        String[] suitArr = suit.split("\\.", 0);

        if (mFlexboxlayoutChinaCalendar.getChildCount() != 0) {
            mFlexboxlayoutChinaCalendar.removeAllViewsInLayout();
        }

        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int flag = 0;

        int[] itemColors = getResources().getIntArray(R.array.itemcolor);

        for (int i = 0; i < avoidArr.length; i++) {
            final Chip avoidChip = new Chip(this);
            avoidChip.setHasIcon(true);
            avoidChip.setTextColor(Color.WHITE);
            avoidChip.changeBackgroundColor(itemColors[flag % itemColors.length]);

            avoidChip.setChipText(avoidArr[i]);
            avoidChip.setChipIcon(getResources().getDrawable(R.drawable.ji));
            //Chip 这个自定义控件的问题所在，当更改设置一些属性时，不会自动刷新
            Handler avoidhandler = new Handler();
            avoidhandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    avoidChip.requestLayout();
                }
            }, 250);
            mFlexboxlayoutChinaCalendar.addView(avoidChip, flag++, layoutParams);
        }
        for (int i = 0; i < suitArr.length; i++) {
            final Chip suitChip = new Chip(this);
            suitChip.setHasIcon(true);
            suitChip.setTextColor(Color.WHITE);
            suitChip.changeBackgroundColor(itemColors[flag % itemColors.length]);

            suitChip.setChipText(suitArr[i]);
            suitChip.setChipIcon(getResources().getDrawable(R.drawable.yi));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    suitChip.requestLayout();
                }
            }, 250);
            mFlexboxlayoutChinaCalendar.addView(suitChip, flag++, layoutParams);
        }

        mFlexboxlayoutChinaCalendar.requestLayout();
        mFlexboxlayoutChinaCalendar.invalidate();


    }

    public String getStringFormat(String string) {
        return TextUtils.isEmpty(string) ? "" : string;
    }

    private void initFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipToDay();
            }
        });
    }

    private void skipToDay() {
        Calendar cal = Calendar.getInstance();
        final DatePickerDialog mDialog = new DatePickerDialog(this, null,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        //手动设置按钮
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = mDialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                String skipDate = new StringBuffer()
                        .append(year).append("-")
                        .append(month+1).append("-")
                        .append(day)
                        .toString();
                mDate = skipDate;

                ChinaCalendar.ResultBean.DataBean data = (ChinaCalendar.ResultBean.DataBean)
                        objectSaveManager.getObject(CHINA_CALENDAR + skipDate);
                if (data == null) {
                    requestStarData();
                }else {
                    initDateView(data);
                }
                Logger.e("调整日期："+skipDate+mDate);

//                requestStarData();
            }
        });
        //取消按钮，如果不需要直接不设置即可
        mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        mDialog.show();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void requestStarData() {
        unsubscribe();
        mSubscription = Network.getChinaCalendarApi()
                .getChinaCalendar("3f95b5d789fbc83f5d2f6d2479850e7e", mDate)
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
    public void initPresenter() {
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void OnRefreshDataEvent(ChinaCalendar.ResultBean.DataBean data) {
//        initDateView(data);
//    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }
}
