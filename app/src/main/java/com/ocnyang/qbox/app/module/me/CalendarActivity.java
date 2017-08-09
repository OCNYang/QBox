package com.ocnyang.qbox.app.module.me;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.model.HolidaysManager;
import com.ocnyang.qbox.app.module.me.calendar.EventDecorator_Holiday;
import com.ocnyang.qbox.app.module.me.calendar.EventDecorator_Workday;
import com.ocnyang.qbox.app.module.me.calendar.HighlightWeekendsDecorator;
import com.ocnyang.qbox.app.module.me.calendar.LunarDecorator;
import com.ocnyang.qbox.app.module.me.calendar.TodayDecorator;
import com.ocnyang.qbox.app.utils.DateUtils;
import com.ocnyang.qbox.app.utils.Lunar;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class CalendarActivity extends BaseCommonActivity implements OnDateSelectedListener, OnMonthChangedListener {

    @BindView(R.id.calendarView_calendar)
    MaterialCalendarView widget;
    @BindView(R.id.toolbar_calendar)
    Toolbar mToolbar;
    @BindView(R.id.animals_year_calendar)
    TextView mAnimalsYearCalendar;
    @BindView(R.id.distance_todaynumber_calendar)
    TextView mDistanceTodaynumberCalendar;
    @BindView(R.id.nongli_date_calendar)
    TextView mNongliDateCalendar;
    @BindView(R.id.cyclical_calendar)
    TextView mCyclical;
    @BindView(R.id.beforeorback_calendar)
    TextView mBeforeOrBack;

    Lunar mLunar;
    @BindView(R.id.back_today_calendar)
    TextView mBackTodayCalendar;
    @BindView(R.id.month_view_calendar)
    TextView mMonthViewCalendar;
    @BindView(R.id.week_view_calendar)
    TextView mWeekViewCalendar;
    @BindView(R.id.holidayname_date_calendar)
    TextView mHolidayName;
    private CalendarDay today;
    HolidaysManager holidaysManager;

    public String year;
    public String month;
    public LunarDecorator mLunarDecorator;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_calendar);
    }

    @Override
    public void initView() {
        today = CalendarDay.today();
        //给设置标识
        holidaysManager = new HolidaysManager();
        initToolbar();
        initTodayInfo(today.getDate(), today.getDate());

        year = DateUtils.date2String(today.getDate(), "yyyy");
        month = DateUtils.date2String(today.getDate(), "MM");
        mLunarDecorator = new LunarDecorator(year,month);


        widget.setCurrentDate(today);

        //在头和尾显示其他月的日期
        widget.setShowOtherDates(MaterialCalendarView.SHOW_DEFAULTS);
        //当点击头和尾处的其他月的日期时，跳转到其他月
        widget.setAllowClickDaysOutsideCurrentMonth(true);
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);


        widget.addDecorators(new TodayDecorator(),//当前日期的标志
                mLunarDecorator,//显示农历
//                new MySelectorDecorator(this),//点击日期的标志
                new HighlightWeekendsDecorator(),//周末日期的标志
                new EventDecorator_Holiday(holidaysManager.getHolidays()),
                new EventDecorator_Workday(holidaysManager.getHolidays())
        );
    }

    private void initTodayInfo(Date nowdate, Date selectDate) {
        String holidayName = holidaysManager.containsDate(HolidaysManager.formatDate(selectDate));
        mHolidayName.setText(TextUtils.isEmpty(holidayName)?"":holidayName);
        mLunar = new Lunar(selectDate);
        mAnimalsYearCalendar.setText(mLunar.animalsYear() + "年");
        mNongliDateCalendar.setText(mLunar.toString());
        mCyclical.setText(mLunar.cyclical());
        int distanceDay = (int) ((selectDate.getTime() - nowdate.getTime()) / 86400000L);
        String distanceDayStr = null;
        if (distanceDay == 0) {
            mBeforeOrBack.setText("\b\b");
            distanceDayStr = "今";
        } else if (distanceDay > 0) {
            mBeforeOrBack.setText("后");
            distanceDayStr = String.valueOf(distanceDay);
        } else if (distanceDay < 0) {
            mBeforeOrBack.setText("前");
            distanceDayStr = String.valueOf(-distanceDay);
        }
        mDistanceTodaynumberCalendar.setText(distanceDayStr);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.year_view:
                break;
            case R.id.skip_to_day:
                skipToDay();
                break;
            case R.id.holidays:
                showHolidays();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void skipToDay() {
        Calendar cal = today.getCalendar();
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
                CalendarDay calendarDay = CalendarDay.from(year, month, day);
                widget.setCurrentDate(calendarDay);
                widget.setSelectedDate(calendarDay);
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

    @Override
    public void initPresenter() {
    }

    //当选择
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //If you change a decorate, you need to invalidate decorators
//        oneDayDecorator.setDate(date.getDate());
//        widget.invalidateDecorators();
        initTodayInfo(today.getDate(), date.getDate());
    }

    @OnClick({R.id.back_today_calendar, R.id.month_view_calendar, R.id.week_view_calendar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_today_calendar:
                widget.setCurrentDate(today);
                break;
            case R.id.month_view_calendar:
                widget.state().edit()
                        .setCalendarDisplayMode(CalendarMode.MONTHS)
                        .commit();
                break;
            case R.id.week_view_calendar:
                widget.state().edit()
                        .setCalendarDisplayMode(CalendarMode.WEEKS)
                        .commit();
                break;
        }
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        mLunarDecorator.setYear(DateUtils.date2String(date.getDate(), "yyyy"));
        mLunarDecorator.setMonth(DateUtils.date2String(date.getDate(), "MM"));
        widget.invalidateDecorators();
    }

    private void showHolidays() {
        View view = LayoutInflater.from(this)
                .inflate(R.layout.holiday_list, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.holidays_recy);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        HolidayAdapter adapter = new HolidayAdapter();
        recyclerView.setAdapter(adapter);

        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
        adapter.setOnItemClickListener(new HolidayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
//                Toast.makeText(CalendarActivity.this, text, Toast.LENGTH_SHORT).show();
                widget.setCurrentDate(CalendarDay.from(2017,mHolidayDate[position][0]-1,mHolidayDate[position][1]));
                widget.setSelectedDate(CalendarDay.from(2017,mHolidayDate[position][0]-1,mHolidayDate[position][1]));
                dialog.dismiss();
            }
        });

    }

    static String [] mHolidayNameArray = {"元旦","春节","清明节","劳动节","端午节","中秋国庆"};
    static String [] mHolidayInfoArray = {"1月1日放假，1月2日（星期一）补休。",
            "1月27日至2月2日放假调休，共7天。1月22日（星期日）、2月4日（星期六）上班。",
            "4月2日至4日放假调休，共3天。4月1日（星期六）上班。",
            "5月1日放假，与周末连休。",
            "5月28日至30日放假调休，共3天。5月27日（星期六）上班。",
            "10月1日至8日放假调休，共8天。9月30日（星期六）上班。"};
    static int[][]  mHolidayDate={{1,1},{1,27},{4,2},{5,1},{5,28},{10,1}};

    static class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.Holder> {

        private OnItemClickListener mItemClickListener;


        public void setOnItemClickListener(OnItemClickListener li) {
            mItemClickListener = li;
        }

        @Override
        public HolidayAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.holiday_list_item, parent, false);
            return new Holder(item);
        }

        @Override
        public void onBindViewHolder(final HolidayAdapter.Holder holder, int position) {
            holder.holiday_name.setText(mHolidayNameArray[position]);
            holder.holiday_info.setText(mHolidayInfoArray[position]);
            if(mItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClick(holder.getLayoutPosition(),
                                holder.holiday_name.getText().toString());
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mHolidayNameArray.length;
        }

        class Holder extends RecyclerView.ViewHolder {
            TextView holiday_name;
            TextView holiday_info;
            public Holder(View itemView) {
                super(itemView);
                holiday_name = (TextView) itemView.findViewById(R.id.name_holiday_item);
                holiday_info = (TextView) itemView.findViewById(R.id.info_holiday_item);
            }
        }

        interface OnItemClickListener {
            void onItemClick(int position, String text);
        }
    }

}
