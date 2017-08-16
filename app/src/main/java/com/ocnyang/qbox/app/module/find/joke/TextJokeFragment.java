package com.ocnyang.qbox.app.module.find.joke;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.orhanobut.logger.Logger;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseFragment;
import com.ocnyang.qbox.app.model.entities.textjoke.BaseJokeBean;
import com.ocnyang.qbox.app.model.entities.textjoke.NewTextJokeBean;
import com.ocnyang.qbox.app.model.entities.textjoke.RandomTextJoke;
import com.ocnyang.qbox.app.model.entities.textjoke.RefreshJokeStyleEvent;
import com.ocnyang.qbox.app.model.entities.textjoke.TextJokeBean;
import com.ocnyang.qbox.app.network.Network;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;




public class TextJokeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String JOKE_KEY ="39094c8b40b831b8e7b7a19a20654ed7";

    //每页请求的 item 数量
    public final int mPs = 20;
    public int mPageMark = 1;
    public boolean mRefreshMark;

    int jokestyle;

    private View notDataView;
    private View errorView;

    TextJokeAdapter textJokeAdapter;

    @BindView(R.id.recy_textjoke)
    RecyclerView mRecyTextjoke;
    @BindView(R.id.swiper_textjoke)
    SwipeRefreshLayout mSwiperTextjoke;

    private String mParam1;
    private String mParam2;
    public List<TextJokeBean> mJokeBeanArrayList;

    private Subscription mSubscription;


    Observer<BaseJokeBean> mObserver = new Observer<BaseJokeBean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Logger.e(e.getMessage());
            onErrorView();
            if (mSwiperTextjoke.isRefreshing()) {
                mSwiperTextjoke.setRefreshing(false);
            }
            if (mSwiperTextjoke.isEnabled()) {
                mSwiperTextjoke.setEnabled(false);
            }
        }

        @Override
        public void onNext(BaseJokeBean baseJokeBean) {
            //先强转baseJokeBean
            setNewDataAddList(baseJokeBean);
        }
    };

    private void setNewDataAddList(BaseJokeBean baseJokeBean) {
        if (baseJokeBean!=null&&baseJokeBean.getError_code() == 0){
            mPageMark++;
            List<TextJokeBean> data = null;
            switch (jokestyle) {
                case JokeActivity.JOKESTYLE_NEW:
                    data = ((NewTextJokeBean) baseJokeBean).getResult().getData();
                    break;
                case JokeActivity.JOKESTYLE_RANDOM:
                    data = ((RandomTextJoke) baseJokeBean).getResult();
                    break;
                default:
                    break;
            }
            TextJokeBean textJokeBean = new TextJokeBean();
            textJokeBean.setItemType(TextJokeBean.MORE);
            Logger.e("数据条数"+data.size());
            data.add(textJokeBean);
            if (mRefreshMark) {
                textJokeAdapter.setNewData(data);
                mRefreshMark = false;
            } else {
                textJokeAdapter.addData(data);
            }

            if (mSwiperTextjoke.isRefreshing()) {
                mSwiperTextjoke.setRefreshing(false);
            }
            if (!mSwiperTextjoke.isEnabled()) {
                mSwiperTextjoke.setEnabled(true);
            }
            if (textJokeAdapter.isLoading()) {
                textJokeAdapter.loadMoreComplete();
            }
            if (!textJokeAdapter.isLoadMoreEnable()) {
                textJokeAdapter.setEnableLoadMore(true);
            }
        }else {
            if (textJokeAdapter.isLoading()) {
                Toast.makeText(getContext(), R.string.load_more_error, Toast.LENGTH_SHORT).show();
                textJokeAdapter.loadMoreFail();
            } else {
                textJokeAdapter.setEmptyView(notDataView);
                if (mSwiperTextjoke.isRefreshing()) {
                    mSwiperTextjoke.setRefreshing(false);
                }
                if (mSwiperTextjoke.isEnabled()) {
                    mSwiperTextjoke.setEnabled(false);
                }
            }
        }
    }

    private void onErrorView() {
        textJokeAdapter.setEmptyView(errorView);
    }

    public TextJokeFragment() {
        // Required empty public constructor
    }


    public static TextJokeFragment newInstance(String param1, String param2) {
        TextJokeFragment fragment = new TextJokeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_text_joke;
    }

    @Override
    public void initView() {
        jokestyle = ((JokeActivity) getActivity()).getJokestyle();
        EventBus.getDefault().register(this);

        initSwiper();
        initEmptyView();
        initRecy();
        onLoading();
        requestData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnRefreshNewsFragmentEvent(RefreshJokeStyleEvent event) {
        jokestyle = event.getJokeStyle();
        onRefresh();
    }

    private void onLoading() {
        textJokeAdapter.setEmptyView(R.layout.loading_view,
                (ViewGroup) mRecyTextjoke.getParent());
    }


    private void requestData() {
        unsubscribe();
        switch (jokestyle) {
            case JokeActivity.JOKESTYLE_NEW:
                mSubscription = Network.getNewTextJokeApi()
                        .getNewTextJokeJoke(JOKE_KEY, mPageMark, mPs)//key,页码,每页条数
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mObserver);
                break;
            case JokeActivity.JOKESTYLE_RANDOM:
                mSubscription = Network.getRandomTextJokeApi()
                        .getRandomTextJoke(JOKE_KEY)//key,页码,每页条数
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mObserver);
                break;
            default:
                break;
        }

    }

    private void initRecy() {

        if (mJokeBeanArrayList == null) {
            mJokeBeanArrayList = new ArrayList<>();
        }

        textJokeAdapter = new TextJokeAdapter(mJokeBeanArrayList);
        textJokeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
//        textJokeAdapter.setOnLoadMoreListener(this);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyTextjoke.setLayoutManager(staggeredGridLayoutManager);
        mRecyTextjoke.setAdapter(textJokeAdapter);
        mRecyTextjoke.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (((TextJokeBean) adapter.getData().get(position)).getItemType() == TextJokeBean.MORE) {
                    //加载更多
                    adapter.remove(position);
                    onLoadMoreRequested();
                }
            }
        });
        mRecyTextjoke.addOnItemTouchListener(new OnItemLongClickListener() {
            @Override
            public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                TextJokeBean textJokeBean = (TextJokeBean) adapter.getData().get(position);
                if (textJokeBean.getItemType() == TextJokeBean.JOKE) {
                    showShare(textJokeBean.getContent());
                }
            }
        });
    }

    private void showShare(String title) {
//        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("笑话一则");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(title);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("笑话一则："+title+"\n-来自：小秋魔盒");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(title);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("太搞笑了，太逗了");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://ocnyang.com");

        // 启动分享GUI
        oks.show(getContext());
    }

    private void initSwiper() {
        mSwiperTextjoke.setOnRefreshListener(this);
        mSwiperTextjoke.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwiperTextjoke.setEnabled(false);
    }

    @Override
    protected void managerArguments() {
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
    }

    private void initEmptyView() {
        /**
         * 网络请求失败没有数据
         */
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyTextjoke.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();

            }
        });

        /**
         * 网络请求错误 | 没有网络
         */
        errorView = getActivity().getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRecyTextjoke.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        textJokeAdapter.setEnableLoadMore(false);
        mRefreshMark = true;
        mPageMark = 1;
        onLoading();
        requestData();
    }


    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }


    public void onLoadMoreRequested() {
        mSwiperTextjoke.setEnabled(false);
        requestData();
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
