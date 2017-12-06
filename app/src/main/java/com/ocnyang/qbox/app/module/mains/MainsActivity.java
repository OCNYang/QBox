package com.ocnyang.qbox.app.module.mains;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.app.BaseApplication;
import com.ocnyang.qbox.app.base.BaseCustomActivity;
import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.config.StatusBarCompat;
import com.ocnyang.qbox.app.model.entities.RefreshNewsFragmentEvent;
import com.ocnyang.qbox.app.module.find.FindFragment;
import com.ocnyang.qbox.app.module.me.MeFragment;
import com.ocnyang.qbox.app.module.news.NewsFragment;
import com.ocnyang.qbox.app.module.news_category.CategoryActivity;
import com.ocnyang.qbox.app.module.wechat.WechatFragment;
import com.ocnyang.qbox.app.update.AppUtils;
import com.ocnyang.qbox.app.update.UpdateChecker;
import com.ocnyang.qbox.app.utils.SPUtils;
import com.ocnyang.qbox.app.utils.StateBarTranslucentUtils;
import com.ocnyang.qbox.app.utils.inputmethodmanager_leak.IMMLeaks;
import com.ocnyang.qbox.app.widget.TabBar_Mains;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainsActivity extends BaseCustomActivity {
    public static List<String> logList = new CopyOnWriteArrayList<String>();

    private static final String NEWS_FRAGMENT = "NEWS_FRAGMENT";
    private static final String WECHAT_FRAGMENT = "WECHAT_FRAGMENT";
    private static final String FIND_FRAGMENT = "FIND_FRAGMENT";
    public static final String ME_FRAGMENT = "ME_FRAGMENT";
    public static final String FROM_FLAG = "FROM_FLAG";

    public MeFragment mMeFragment;
    public NewsFragment mNewsFragment;
    public WechatFragment mWechatFragment;
    public FindFragment mFindFragment;

    @BindView(R.id.framelayout_mains)
    FrameLayout sFramelayoutMains;
    @BindView(R.id.recommend_mains)
    TabBar_Mains sRecommendMains;
    @BindView(R.id.cityfinder_mains)
    TabBar_Mains sCityfinderMains;
    @BindView(R.id.findtravel_mains)
    TabBar_Mains sFindtravelMains;
    @BindView(R.id.me_mains)
    TabBar_Mains sMeMains;

    private FragmentManager sBaseFragmentManager;
    /**
     * 存储当前Fragment的标记
     */
    private String mCurrentIndex;

//    小米推送常用操作
//    //设置别名，撤销别名（alias）
//    MiPushClient.setAlias(this, "demo1", null);
//    //MiPushClient.unsetAlias(this, "demo1", null);
//    //设置账号，撤销账号（account）
//    MiPushClient.setUserAccount(this, "user1", null);
//    //MiPushClient.unsetUserAccount(this, "user1", null);
//    //设置标签，撤销标签（topic：话题、主题）
//    MiPushClient.subscribe(this, "IT", null);
//    //MiPushClient.unsubscribe(this, "IT", null);
//    //设置接收时间（startHour, startMin, endHour, endMin）
//    MiPushClient.setAcceptTime(this, 7, 0, 23, 0, null);
//    //暂停和恢复推送 //MiPushClient.pausePush(this, null);
//    //MiPushClient.resumePush(this, null);

    @Override
    public void initContentView() {
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        setContentView(R.layout.activity_mains);
        BaseApplication.setMainActivity(this);
        StatusBarCompat.compat(this);
    }

    boolean isRestart = false;

    private void initByRestart(Bundle savedInstanceState) {

        mCurrentIndex = savedInstanceState.getString("mCurrentIndex");

        isRestart = true;
        Logger.e("恢复状态：" + mCurrentIndex);
        mMeFragment = (MeFragment) sBaseFragmentManager.findFragmentByTag(ME_FRAGMENT);
        if (sRecommendMains.getVisibility() == View.VISIBLE) {
            mNewsFragment = (NewsFragment) sBaseFragmentManager.findFragmentByTag(NEWS_FRAGMENT);
        }
        mWechatFragment = (WechatFragment) sBaseFragmentManager.findFragmentByTag(WECHAT_FRAGMENT);
        mFindFragment = (FindFragment) sBaseFragmentManager.findFragmentByTag(FIND_FRAGMENT);

        switchToFragment(mCurrentIndex);
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        sBaseFragmentManager = getBaseFragmentManager();

        String startPage = WECHAT_FRAGMENT;
        String s = (String) SPUtils.get(this, Const.OPENNEWS, "nomagic");
        if (s.equals("magicopen")) {
            sRecommendMains.setVisibility(View.VISIBLE);
            startPage = NEWS_FRAGMENT;
        }
        if (savedInstanceState != null) {
            initByRestart(savedInstanceState);
        } else {
            switchToFragment(startPage);
            mCurrentIndex = startPage;
        }

        int qbox_version = (int) SPUtils.get(this, Const.QBOX_NEW_VERSION, 0);
        if (qbox_version != 0 && qbox_version > AppUtils.getVersionCode(this)) {
            UpdateChecker.checkForNotification(this); //通知提示升级
        }

        //订阅事件
        EventBus.getDefault().register(this);
    }

    private void alreadyAtFragment(String mCurrentIndex) {
        switch (mCurrentIndex) {
            case NEWS_FRAGMENT:
                if (mNewsFragment != null) {
//                    sRecommendFragment.scrollToTop(true)
                }
                break;
            case ME_FRAGMENT:
                break;
            default:
                break;
        }

    }

    private void switchToFragment(String index) {
//        sFragmentTransaction = getFragmentTransaction();
        hideAllFragment();
        switch (index) {
            case NEWS_FRAGMENT:
                if (sRecommendMains.getVisibility() == View.VISIBLE) {
                    showNewsFragment();
                    Logger.e("newsopen:101");
                }
                break;
            case WECHAT_FRAGMENT:
                showWechatFragment();
                break;
            case FIND_FRAGMENT:
                showFindFragment();
                break;
            case ME_FRAGMENT:
                showMeFragment();
                break;
            default:

                break;
        }
        mCurrentIndex = index;
    }

    private void showMeFragment() {
        if (false == sMeMains.isSelected())
            sMeMains.setSelected(true);
        if (mMeFragment == null) {
            mMeFragment = MeFragment.newInstance();
            addFragment(R.id.framelayout_mains, mMeFragment, ME_FRAGMENT);
        } else if (isRestart = true) {
            getFragmentTransaction().show(mMeFragment).commit();
            isRestart = false;
        } else {
            showFragment(mMeFragment);
        }
    }

    private void showFindFragment() {
        if (false == sFindtravelMains.isSelected()) {
            sFindtravelMains.setSelected(true);
        }
        if (mFindFragment == null) {
            mFindFragment = FindFragment.newInstance("", "");
            addFragment(R.id.framelayout_mains, mFindFragment, FIND_FRAGMENT);
        } else if (isRestart = true) {
            isRestart = false;
            getFragmentTransaction().show(mFindFragment).commit();
        } else {
            showFragment(mFindFragment);
        }

    }

    private void showWechatFragment() {
        if (false == sCityfinderMains.isSelected()) {
            sCityfinderMains.setSelected(true);
        }
        if (mWechatFragment == null) {
            mWechatFragment = mWechatFragment.newInstance("", "");
            addFragment(R.id.framelayout_mains, mWechatFragment, WECHAT_FRAGMENT);
        } else if (isRestart = true) {
            isRestart = false;
            getFragmentTransaction().show(mWechatFragment).commit();
        } else {
            showFragment(mWechatFragment);
        }

    }

    private void showNewsFragment() {
        if (sRecommendMains.getVisibility() != View.VISIBLE) {
            return;
        }
        if (false == sRecommendMains.isSelected()) {
            sRecommendMains.setSelected(true);
        }
        if (mNewsFragment == null) {
            Logger.e("恢复状态：" + "null");
            mNewsFragment = NewsFragment.newInstance("a", "b");
            addFragment(R.id.framelayout_mains, mNewsFragment, NEWS_FRAGMENT);
        } else if (isRestart = true) {
            isRestart = false;
            getFragmentTransaction().show(mNewsFragment).commit();
        } else {
            showFragment(mNewsFragment);
        }

    }

    private void hideAllFragment() {
        if (mNewsFragment != null) {
            hideFragment(mNewsFragment);
        }
        if (mWechatFragment != null) {
            hideFragment(mWechatFragment);
        }
        if (mFindFragment != null) {
            hideFragment(mFindFragment);
        }
        if (mMeFragment != null) {
            hideFragment(mMeFragment);
        }
        if (sRecommendMains.getVisibility() == View.VISIBLE) {
            sRecommendMains.setSelected(false);
        }
        sFindtravelMains.setSelected(false);
        sCityfinderMains.setSelected(false);
        sMeMains.setSelected(false);
    }

    @Override
    public void initPresenter() {

    }

    @OnClick({R.id.recommend_mains, R.id.cityfinder_mains, R.id.findtravel_mains, R.id.me_mains})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recommend_mains:
                if (!mCurrentIndex.equals(NEWS_FRAGMENT))
                    switchToFragment(NEWS_FRAGMENT);
                break;
            case R.id.cityfinder_mains:
                if (!mCurrentIndex.equals(WECHAT_FRAGMENT))
                    switchToFragment(WECHAT_FRAGMENT);
                break;
            case R.id.findtravel_mains:
                if (!mCurrentIndex.equals(FIND_FRAGMENT))
                    switchToFragment(FIND_FRAGMENT);
                break;
            case R.id.me_mains:
                if (!mCurrentIndex.equals(ME_FRAGMENT))
                    switchToFragment(ME_FRAGMENT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Const.NEWSFRAGMENT_CATEGORYACTIVITY_REQUESTCODE
                && resultCode == Const.NEWSFRAGMENT_CATEGORYACTIVITY_RESULTCODE) {
            mNewsFragment.initView();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnRefreshNewsFragmentEvent(RefreshNewsFragmentEvent event) {
        startActivityForResult(new Intent(MainsActivity.this, CategoryActivity.class), event.getMark_code());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        for (Fragment fragment :
                getBaseFragmentManager().getFragments()) {
            getFragmentTransaction().remove(fragment);
        }
        super.onDestroy();
        BaseApplication.setMainActivity(null);
        IMMLeaks.fixFocusedViewLeak(getApplication());//解决 Android 输入法造成的内存泄漏问题。
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLogInfo();
    }

    public void refreshLogInfo() {
        String AllLog = "";
        for (String log : logList) {
            AllLog = AllLog + log + "\n\n";
        }
        Logger.e(AllLog);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mCurrentIndex", mCurrentIndex);
        Logger.e("保存状态");
    }

    /**
     * 监听用户按返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    private boolean mIsExit;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 用于优雅的退出程序(当从其他地方退出应用时会先返回到此页面再执行退出)
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(Const.TAG_EXIT, false);
            if (isExit) {
                finish();
            }
        }
    }

}
