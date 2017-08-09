package com.ocnyang.qbox.app.module.setting;

import android.content.Intent;
import android.graphics.Canvas;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.google.gson.Gson;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.model.entities.City;
import com.ocnyang.qbox.app.module.me.AddressActivity;
import com.ocnyang.qbox.app.module.me.weather.weather.AreaSetAdapter;
import com.ocnyang.qbox.app.module.me.weather.weather.api.ApiManager;
import com.ocnyang.qbox.app.module.me.weather.weather.api.ApiManager.Area;
import com.ocnyang.qbox.app.utils.SPUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ocnyang.qbox.app.module.me.weather.weather.api.ApiManager.KEY_SELECTED_AREA;

public class WeatherAddressActivity extends BaseCommonActivity {

    @BindView(R.id.recy_area_set)
    RecyclerView mRecyAreaSet;
    @BindView(R.id.fab_area_set)
    FloatingActionButton mFabAreaSet;

    AreaSetAdapter mAreaSetAdapter;
    ArrayList<ApiManager.Area> mSelectedAreas;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_weather_address);
    }

    @Override
    public void initView() {
        initToolbar();
        EventBus.getDefault().register(this);

        initRecy();
    }

    private void initRecy() {
        mSelectedAreas = ApiManager.loadSelectedArea(this);
        Logger.e("ms"+mSelectedAreas.size());
        mRecyAreaSet.setLayoutManager(new LinearLayoutManager(this));
        mAreaSetAdapter = new AreaSetAdapter(mSelectedAreas, getResources().getStringArray(R.array.bgimg));

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAreaSetAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyAreaSet);
        // 开启滑动删除
        mAreaSetAdapter.enableSwipeItem();
        mAreaSetAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Area area = (Area) mSelectedAreas.get(pos);
                Logger.e(area.getCity());
                removeAddress(area);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });

        mRecyAreaSet.setAdapter(mAreaSetAdapter);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_weatheraddress);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeAddress(Area area) {
        List<Area> areas = new ArrayList<>();
        String s = (String) SPUtils.get(this, KEY_SELECTED_AREA, "");

        if (!TextUtils.isEmpty(s)) {
            ApiManager.Area[] aa = new Gson().fromJson(s, ApiManager.Area[].class);
            if (aa != null) {
                Collections.addAll(areas, aa);
            }
        }

        for (ApiManager.Area areaitem : areas) {
            if (area.getId().equals(areaitem.getId())) {
                areas.remove(areaitem);
                break;
            }
        }
        SPUtils.put(getContext(), KEY_SELECTED_AREA, new Gson().toJson(areas));
    }

    @Override
    public void initPresenter() {

    }

    @OnClick(R.id.fab_area_set)
    public void onViewClicked() {
        if (mAreaSetAdapter.getData().size() < 3) {
            startActivity(new Intent(this, AddressActivity.class));
        } else {
            Toast.makeText(this, "对不起！最多支持添加3个地点。", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEventCome(City.HeWeather5Bean.BasicBean BasicBean) {

        List<ApiManager.Area> areas = new ArrayList<>();
        String s = (String) SPUtils.get(this, KEY_SELECTED_AREA, "");
        if (!TextUtils.isEmpty(s)) {
            ApiManager.Area[] aa = new Gson().fromJson(s, ApiManager.Area[].class);
            if (aa != null) {
                Collections.addAll(areas, aa);
            }
        }

        boolean flag = false;
        for (ApiManager.Area area : areas) {
            if (BasicBean.getId().equals(area.getId())) {
                flag = true;
                break;
            }
        }

        ApiManager.Area e = new ApiManager.Area();

        if (!flag) {
            e.setCity(BasicBean.getCity());
            e.setProvince(BasicBean.getProv());
            e.setId(BasicBean.getId());
            e.setName_cn(BasicBean.getCity());
            areas.add(e);
        }

        SPUtils.put(this, KEY_SELECTED_AREA, new Gson().toJson(areas));
        initRecy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
