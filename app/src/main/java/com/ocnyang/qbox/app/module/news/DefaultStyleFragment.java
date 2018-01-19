package com.ocnyang.qbox.app.module.news;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseFragment;
import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.model.entities.NewsItem;
import com.ocnyang.qbox.app.model.entities.WechatItem;
import com.ocnyang.qbox.app.module.news_details.NewsDetailsActivity;
import com.ocnyang.qbox.app.network.Network;
import com.ocnyang.qbox.app.utils.PixelUtil;
import com.ocnyang.qbox.app.utils.SPUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DefaultStyleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefaultStyleFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    //    private static final String ARG_PARAM2 = "param2";
    private static final String JUHE_NEWS_APP_KEY = "509e1319694893a9df49d9b6ea7b2ed0";

    // 当前列表的item个数
    int mCurrentCounter;
    int mPage = 1;
    // 最多加载的条目个数
    private static final int TOTAL_COUNTER = 30;

    @BindView(R.id.news_list)
    RecyclerView mNewsList;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private View notDataView;
    private View errorView;

    // Rename and change types of parameters
    private String mParam1;
//    private String mParam2;

    private Subscription mSubscription;
    private List<NewsItem.ResultBean.DataBean> mNewsItemList;
    BaseQuickAdapter baseQuickAdapter;
    private int delayMillis = 1000;

    public DefaultStyleFragment() {
        // Required empty public constructor
    }

    Observer<WechatItem> mObserver = new Observer<WechatItem>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Logger.e("onError" + e.getMessage());
            onErrorView();
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            if (mSwipeRefreshLayout.isEnabled()) {
                mSwipeRefreshLayout.setEnabled(false);
            }
        }

        @Override
        public void onNext(WechatItem newsItem) {
            setNewDataAddList(newsItem);
        }
    };

    private void setNewDataAddList(WechatItem newsItem) {
        if (newsItem != null && "200".equals(newsItem.getRetCode())) {
            baseQuickAdapter.setNewData(newsItem.getResult().getList());
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            if (!mSwipeRefreshLayout.isEnabled()) {
                mSwipeRefreshLayout.setEnabled(true);
            }

            if (!baseQuickAdapter.isLoadMoreEnable()) {
                baseQuickAdapter.setEnableLoadMore(true);
            }
        } else {
            baseQuickAdapter.setEmptyView(notDataView);
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }


    public static DefaultStyleFragment newInstance(String param1, String param2) {
        DefaultStyleFragment fragment = new DefaultStyleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_default_style;
    }

    @Override
    public void initView() {
        initSwipeRefresh();
        initEmptyView();
        initRecyclerView();
        onLoading();

        requestNews();

    }

    private void initRecyclerView() {
        mNewsList.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));

        mNewsItemList = new ArrayList<>();
        boolean isNotLoad = (boolean) SPUtils.get(getContext(), Const.SLLMS, false);
        int imgWidth = (PixelUtil.getWindowWidth() - PixelUtil.dp2px(40)) / 3;
        int imgHeight = imgWidth / 4 * 3;
        baseQuickAdapter = new DefaultStyleItemAdapter(R.layout.news_item_default,
                isNotLoad,
                imgWidth, imgHeight);
        baseQuickAdapter.openLoadAnimation();
        baseQuickAdapter.setOnLoadMoreListener(this);
        mNewsList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<WechatItem.ResultBean.ListBean> data = adapter.getData();

                Bundle bundle = new Bundle();
                bundle.putString("url", data.get(position).getSourceUrl());
                bundle.putString("title", data.get(position).getTitle());
                Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        mNewsList.setAdapter(baseQuickAdapter);
        mCurrentCounter = baseQuickAdapter.getData().size();
    }

    private void initEmptyView() {
        /**
         * 网络请求失败没有数据
         */
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mNewsList.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRequestAgain();
            }
        });

        /**
         * 网络请求错误|没有网络
         */
        errorView = getActivity().getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mNewsList.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRequestAgain();
            }
        });
    }

    /**
     * 网络请求未完成前/重新请求数据，可以和刷新请求数据写在一起
     * <p>
     * 同时注意这个时候应该把刷新功能关闭。//或者设计成这个时候下拉刷新重新请求数据也行。
     */
    private void onRequestAgain() {
        mPage++;
        requestNews();
    }

    private void onLoading() {
        baseQuickAdapter.setEmptyView(R.layout.loading_view, (ViewGroup) mNewsList.getParent());
    }

    private void onErrorView() {
        baseQuickAdapter.setEmptyView(errorView);
    }

    private void initSwipeRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected void managerArguments() {
        mParam1 = getArguments().getString(ARG_PARAM1);
    }

    @Override
    public void onRefresh() {
        baseQuickAdapter.setEnableLoadMore(false);
        onRequestAgain();
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        mNewsList.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mCurrentCounter >= TOTAL_COUNTER) {
                    baseQuickAdapter.loadMoreEnd();
                } else {
                    baseQuickAdapter.addData(mNewsItemList);
                    mCurrentCounter = baseQuickAdapter.getData().size();
                    baseQuickAdapter.loadMoreComplete();//加载完成。
                }
                mSwipeRefreshLayout.setEnabled(true);
            }
        }, delayMillis);
    }

    /**
     * 发出网络请求
     */
    private void requestNews(/*String key*/) {
        unsubscribe();
        mSubscription = Network.getWechatApi()
                .getWechat(mParam1, mPage, TOTAL_COUNTER)
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
    public void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    @Override
    public String getUmengFragmentName() {
        return getContext().getClass().getSimpleName() + "-"
                + this.getClass().getSimpleName();
    }
}
