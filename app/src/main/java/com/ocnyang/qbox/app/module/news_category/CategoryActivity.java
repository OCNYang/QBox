package com.ocnyang.qbox.app.module.news_category;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.database.CategoryDao;
import com.ocnyang.qbox.app.model.entities.AllCategoryBean;
import com.ocnyang.qbox.app.model.entities.CategoryManager;
import com.ocnyang.qbox.app.module.news_category.draghelper.ItemDragHelperCallback;
import com.ocnyang.qbox.app.network.Network;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CategoryActivity extends BaseCommonActivity {

    private RecyclerView mRecy;
    private Toolbar mToolbar;
    List<CategoryEntity> items;
    List<CategoryEntity> otherItems;
    private Subscription mSubscription;
    CategoryAdapter adapter;

    Observer<AllCategoryBean> mObserver = new Observer<AllCategoryBean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Logger.e("onError:" + e.getMessage());
            setAdapter(null);
        }

        @Override
        public void onNext(AllCategoryBean wechatItem) {
            if (wechatItem != null && "200".equals(wechatItem.getRetCode())) {
                List<CategoryEntity> categoryEntityList = new ArrayList<>();
                List<AllCategoryBean.ResultBean> result = wechatItem.getResult();
                for (int i = 0; i < result.size(); i++) {
                    AllCategoryBean.ResultBean resultBean = result.get(i);
                    CategoryEntity categoryEntity = new CategoryEntity(null, resultBean.getName(), resultBean.getCid(), i);
                    categoryEntityList.add(categoryEntity);
                }
                setAdapter(categoryEntityList);
            } else {
                setAdapter(null);
            }
        }
    };

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_category);
    }

    @Override
    public void initView() {
        mRecy = ((RecyclerView) findViewById(R.id.recy));
        initToolbar();
        initRecy();
    }

    private void initToolbar() {
        mToolbar = ((Toolbar) findViewById(R.id.toolbar));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (CategoryEntity categ :
                        items) {
                    String name = categ.getName();
                    Logger.e(name);
                }
                finish();
            }
        });
        mToolbar.inflateMenu(R.menu.news_category);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                saveNewDataToDB();
                Toast.makeText(CategoryActivity.this, R.string.set_success, Toast.LENGTH_SHORT).show();

                setResult(Const.NEWSFRAGMENT_CATEGORYACTIVITY_RESULTCODE, null);
                finish();
                return false;
            }


        });
    }

    /**
     * 更新数据库的存储
     */
    private void saveNewDataToDB() {
        CategoryDao categoryDao = new CategoryDao(getApplicationContext());
        categoryDao.deleteAllCategory();
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setOrder(i);
        }
        categoryDao.insertCategoryList(items);
    }

    private void initRecy() {
        items = getCategoryFromDB();
        otherItems = new ArrayList<>();

        if (items == null) {
            items = new ArrayList<>();
        }

        requestCategory();

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecy.setLayoutManager(manager);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == CategoryAdapter.TYPE_MY || viewType == CategoryAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
    }

    private void setAdapter(List<CategoryEntity> categoryEntityList) {

        List<CategoryEntity> allCategory = categoryEntityList == null ? new CategoryManager(this).getAllCategory() : categoryEntityList;

        for (CategoryEntity entity : allCategory) {
            if (!items.contains(entity)) {
                otherItems.add(entity);
            }
        }

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecy);

        adapter = new CategoryAdapter(this, helper, items, otherItems);
        mRecy.setAdapter(adapter);

        adapter.setOnMyChannelItemClickListener(new CategoryAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(CategoryActivity.this, "请点击编辑或者长按进行操作！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestCategory() {
        unsubscribe();
        mSubscription = Network.getAllCategoryApi()
                .getAllCategory()//key,页码,每页条数
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    /**
     * 获取本地数据库中 序列化的新闻类别
     *
     * @return
     */
    private List<CategoryEntity> getCategoryFromDB() {
        CategoryDao categoryDao = new CategoryDao(getApplicationContext());
        return categoryDao.queryCategoryList();
    }

    @Override
    public void initPresenter() {

    }

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}
