package com.ocnyang.qbox.app.module.find;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.model.entities.textjoke.QueryIDCard;
import com.ocnyang.qbox.app.model.entities.textjoke.QueryQQ;
import com.ocnyang.qbox.app.model.entities.textjoke.QueryTel;
import com.ocnyang.qbox.app.network.Network;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class QueryInfoActivity extends BaseCommonActivity {

    public static final String QUERY_IDCARD_KEY = "220f329adbf071c81b4b3011e0439cad";
    public static final String QUERY_QQ_KEY = "27100330526ba52940ecc6846436c398";
    public static final String QUERY_TEL_KEY = "576f995fdbc2c0e9db5fa785da34efd6";

    public static final String QUERY_STYLE="style";
    public static final int QUERY_TEL = 1;
    public static final int QUERY_QQ =2;
    public static final int QUERY_IDCARD = 3;

    public static final String[] sQueryStyleName = {"手机号查询","QQ吉凶查询","身份证号查询"};

    @BindView(R.id.toolbar_queryinfo)
    Toolbar mToolbarQueryinfo;
    @BindView(R.id.inputtext_queryinfo)
    TextInputEditText mInputtextQueryinfo;
    @BindView(R.id.inputlayout_queryinfo)
    TextInputLayout mInputlayoutQueryinfo;
    @BindView(R.id.result_queryinfo)
    TextView mResultQueryinfo;
    @BindView(R.id.find_queryinfo)
    FloatingActionButton mFindQueryinfo;
    public int mQueryStyle;

    private Subscription mSubscription;


    Observer<Object> mObserver = new Observer<Object>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Object object) {
            try{
                switch (mQueryStyle) {
                    case QUERY_IDCARD:
                        QueryIDCard queryIDCard = (QueryIDCard) object;
                        showResult(queryIDCard.getResult().toString());
                        break;
                    case QUERY_QQ:
                        QueryQQ queryQQ = (QueryQQ) object;
                        showResult(queryQQ.getResult().getData().toString());
                        break;
                    case QUERY_TEL:
                        QueryTel queryTel = (QueryTel) object;
                        showResult(queryTel.getResult().toString());
                        break;
                    default:
                        break;
                }
            }catch (Exception exception){
                showResult(null);
            }
        }
    };

    private void showResult(String s) {
        Logger.e("查询结果："+s);
        if (TextUtils.isEmpty(s)) {
            mResultQueryinfo.setText("查询结果有误，请检查输入号码是否有误！");
        }else {
            mResultQueryinfo.setText(s);
        }
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_query_info);
    }

    @Override
    public void initView() {
        initgetIntent();
        initToolbar();
        initEditText();
        initFAB();

    }

    private void initEditText() {
        switch(mQueryStyle){
            case QUERY_IDCARD:
                mInputtextQueryinfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
                break;
            case QUERY_QQ:
                mInputtextQueryinfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                break;
            case QUERY_TEL:
                mInputtextQueryinfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                break;
            default:
                break;
        }

    }

    private void initgetIntent() {
        Intent intent = getIntent();
        mQueryStyle = intent.getIntExtra(QUERY_STYLE,0);
        if (mQueryStyle == 0) {
            Toast.makeText(this, "页面入口有误", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void initFAB() {
        mFindQueryinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mInputtextQueryinfo.getText())) {
                    mInputlayoutQueryinfo.setError("请输入要查询的内容");
                }else {
                    requestData();
                }
            }
        });
    }

    @Override
    public void initPresenter() {

    }

    private void initToolbar() {
        mToolbarQueryinfo.setTitle(sQueryStyleName[mQueryStyle-1]);
        mToolbarQueryinfo.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbarQueryinfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void requestData() {
        unsubscribe();
        switch(mQueryStyle){
            case QUERY_IDCARD:
                mSubscription = Network.getQueryIDCardApi()
                        .getIDCardInfo(QUERY_IDCARD_KEY,mInputtextQueryinfo.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mObserver);
                break;
            case QUERY_QQ:
                mSubscription = Network.getQueryQQApi()
                        .getQQInfo(QUERY_QQ_KEY,mInputtextQueryinfo.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mObserver);
                break;
            case QUERY_TEL:
                mSubscription = Network.getQueryTelApi()
                        .getTelInfo(QUERY_TEL_KEY,mInputtextQueryinfo.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mObserver);
                break;
            default:
                break;
        }
    }

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}
