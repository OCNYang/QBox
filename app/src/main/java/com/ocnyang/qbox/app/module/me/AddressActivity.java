package com.ocnyang.qbox.app.module.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.orhanobut.logger.Logger;
import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;
import com.robertlevonyan.views.chip.OnSelectClickListener;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.model.entities.City;
import com.ocnyang.qbox.app.network.Network;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddressActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;
    @BindView(R.id.toolbar_container)
    FrameLayout mToolbarContainer;
    @BindView(R.id.context_address)
    LinearLayout mContextAddress;
    @BindView(R.id.hotcity_grid)
    GridView mHotCityGrid;

    private Subscription mSubscription;

    Observer<City> mObserver = new Observer<City>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Logger.e("onError:" + e.getMessage());
        }

        @Override
        public void onNext(City city) {
            showAddress(city.getHeWeather5());
        }
    };

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_address);

    }

    @Override
    public void initView() {
        initToolbar();
        initGridView();
        initSearchView();

    }

    private void initGridView() {
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, getData(), R.layout.item_address_activity,
                new String[]{"address"}, new int[]{R.id.item_address});
        mHotCityGrid.setAdapter(simpleAdapter);
        mHotCityGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchText = ((TextView) view).getText().toString();
                requestData(searchText);
            }
        });
    }

    private ArrayList<HashMap<String, String>> getData() {
        String[] stringArray = getResources().getStringArray(R.array.arrays_address);
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = null;
        for (int i = 1; i < stringArray.length; i++) {
            map = new HashMap<String, String>();
            map.put("address", stringArray[i]);
            list.add(map);
        }
        return list;
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initSearchView() {
        mSearchView.setVoiceSearch(false);
        mSearchView.setCursorDrawable(R.drawable.color_cursor_white);
        mSearchView.setSuggestions(getResources().getStringArray(R.array.arrays_address));
        mSearchView.setSuggestionIcon(getResources().getDrawable(R.drawable.ic_add_location_black_24dp));

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                requestData(query);
                Logger.e("search-submit:" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                Logger.e("search-change:" + newText);
                return false;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    private void showAddress(List<City.HeWeather5Bean> query) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (mContextAddress.getChildCount() != 0) {
            mContextAddress.removeAllViews();
        }
        for (int i = 0; i < query.size(); i++) {
            City.HeWeather5Bean.BasicBean basic = null;
            if ("ok".equals(query.get(i).getStatus())) {
                basic = query.get(i).getBasic();
                Logger.e(basic.getId());
            } else {
                continue;
            }
            final Chip avoidChip = new Chip(this);
            avoidChip.setHasIcon(true);
            avoidChip.setTextColor(Color.WHITE);
//            avoidChip.changeBackgroundColor(getResources().getColor(R.color.colorAccent));

            avoidChip.setChipText(basic.getProv() + "-" + basic.getCity());
            avoidChip.setChipIcon(getResources().getDrawable(R.drawable.ic_add_location_black_24dp));
            avoidChip.setSelectable(true);
            final City.HeWeather5Bean.BasicBean finalBasic = basic;
            avoidChip.setOnChipClickListener(new OnChipClickListener() {
                @Override
                public void onChipClick(View v) {
                    Toast.makeText(AddressActivity.this, finalBasic.toString() + "\n\n点击勾选即可确定选择", Toast.LENGTH_SHORT).show();
                }
            });
            avoidChip.setOnSelectClickListener(new OnSelectClickListener() {
                @Override
                public void onSelectClick(View v, boolean selected) {
                    if (selected) {
                        Intent intent = new Intent();
                        intent.putExtra("data", finalBasic);
                        setResult(Const.RESULTES_CODE_ADDRESS, intent);
                        EventBus.getDefault().post(finalBasic);
                        finish();
                    }
                }
            });
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    avoidChip.requestLayout();
                }
            }, 250);
            mContextAddress.addView(avoidChip);
        }
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.address, menu);

        MenuItem item = menu.findItem(R.id.search_address);
        mSearchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }


    private void requestData(String query) {
        unsubscribe();
        mSubscription = Network.getCityApi()
                .getCity("def9a507328e4cd395d983fe2589586e", query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
