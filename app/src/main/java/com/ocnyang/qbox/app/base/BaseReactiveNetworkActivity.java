package com.ocnyang.qbox.app.base;

import android.net.NetworkInfo;
import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.Connectivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/*************************************************************
 * Created by OCN.Yang           * * * *   * * * *   *     * *
 * Time:2016/10/13 17:10         *     *   *         * *   * *
 * Email address:yangocn@163.com *     *   *         *   * * *
 * Web site:www.ocnyang.com      * * * *   * * * *   *     * *
 *************************************************************/

/**
 * 适用于：
 *  网络变化时，布局自动改变的  有网络请求的布局。
 */
// TODO: 2016/10/13 自动显示网络错误布局的接口

public abstract class BaseReactiveNetworkActivity extends BaseActivity {
    private static final String TAG = "ReactiveNetwork";
    private Subscription networkConnectivitySubscription;
    private Subscription internetConnectivitySubscription;

    /**
     * 变化为 有网络
     *
     * @param state
     */
    public abstract void onConnectListener(NetworkInfo.State state);

    /**
     * 变化为 无网络
     *
     * @param state
     */
    public abstract void onDisConnectListener(NetworkInfo.State state);

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * NetworkInfo.State:CONNECTING, CONNECTED, SUSPENDED, DISCONNECTING, DISCONNECTED, UNKNOWN
         *       (正在连接,连接,暂停,正在断开,断开连接,不得而知)
         *
         * name:WIFI,NONE,MOBILE...
         */
        networkConnectivitySubscription =
                ReactiveNetwork.observeNetworkConnectivity(getApplicationContext())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Connectivity>() {
                            @Override
                            public void call(final Connectivity connectivity) {
                                Log.d(TAG, connectivity.toString());
                                final NetworkInfo.State state = connectivity.getState();
                                final String name = connectivity.getName();
//                                tvConnectivityStatus.setText(String.format("state: %s, name: %s", state, 0));
                                switch (name) {
                                    case "NONE":
                                        onDisConnectListener(state);
                                        break;
                                    case "WIFI"://设置一个接口，比如只在WiFi情况下显示图片等
                                        break;
                                    case "MOBILE"://和WiFi情况相反的方法逻辑
                                        break;
                                    default:
                                        break;
                                }
                                switch (state) {
                                    case CONNECTING:
                                        //正在连接中 的逻辑
                                        //break;
                                    case CONNECTED:
                                        onConnectListener(state);
                                        break;
                                    case SUSPENDED:
                                        break;
                                    case DISCONNECTING:
                                        //break;
                                    case DISCONNECTED:
                                        onDisConnectListener(state);
                                        break;
                                    case UNKNOWN:
                                        break;
                                    default:

                                        break;
                                }
                            }
                        });

        internetConnectivitySubscription =
                ReactiveNetwork.observeInternetConnectivity()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean isConnectedToInternet) {
//                                tvInternetStatus.setText(isConnectedToInternet.toString());

                            }
                        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        safelyUnsubscribe(networkConnectivitySubscription, internetConnectivitySubscription);
    }

    private void safelyUnsubscribe(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }
}
