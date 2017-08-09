package com.ocnyang.qbox.app.module.news_details;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.widget.Html5WebView;

import butterknife.BindView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsDetailsActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar_news_details)
    Toolbar mToolbarNewsDetails;
    @BindView(R.id.nestedscrollview_news_details)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.activity_news_details)
    CoordinatorLayout mActivityNewsDetails;
    @BindView(R.id.title_news_datails)
    TextView mTitleNewsDatails;

    private String mUrl;
    private Html5WebView mHtml5WebView;
    private String mTitle;
    private String nowUrl;
    private String nowTitle;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_news_details);
    }

    //控件的相关事件绑定
    @Override
    public void initView() {
        ShareSDK.initSDK(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mUrl = extras.getString("url");
            mTitle = extras.getString("title");
            nowUrl = mUrl;
            nowTitle = mTitle;
        }
        registerForContextMenu(mTitleNewsDatails);
        initToolbar();
        initWebView();
    }

    private void initWebView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mHtml5WebView = new Html5WebView(getApplicationContext());
        mHtml5WebView.setLayoutParams(layoutParams);
        mNestedScrollView.addView(mHtml5WebView);
        mHtml5WebView.setWebsiteChangeListener(new Html5WebView.WebsiteChangeListener() {
            @Override
            public void onWebsiteChange(String title) {
                mTitleNewsDatails.setText(title);
                nowTitle = title;
            }

            @Override
            public void onUrlChange(String url) {
                nowUrl = url;
            }
        });
        mHtml5WebView.loadUrl(mUrl);
    }

    private void initToolbar() {
        mTitleNewsDatails.setText(mTitle);
        mToolbarNewsDetails.inflateMenu(R.menu.news_details_menu);
        mToolbarNewsDetails.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbarNewsDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mToolbarNewsDetails.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.open_on_browser:
                        onOpenBrowser(mHtml5WebView.getUrl());
                        break;
                    case R.id.share:
                        showShare();
                        break;
                    case R.id.refresh:
                        mHtml5WebView.reload();
                        break;
                    case R.id.copy_url:
                        onCopyTextToClipboard(mHtml5WebView.getUrl());
                        break;
                    default:

                        break;
                }
                return false;
            }
        });

    }

    private void showShare() {
//        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(nowTitle+"");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(nowUrl+"");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("文章标题："+nowTitle+"\n地址："+nowUrl+"\n-来自：小秋魔盒");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(nowUrl+"");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("很喜欢这篇文章，写的很不错。");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://ocnyang.com");

        // 启动分享GUI
        oks.show(this);
    }

    private void onCopyTextToClipboard(String string) {
        if (!TextUtils.isEmpty(string)){
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("website",string);
            clipboardManager.setPrimaryClip(clip);
            Toast.makeText(this, R.string.copysuccess, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, R.string.copyfail, Toast.LENGTH_SHORT).show();
        }
    }

    private void onOpenBrowser(String url) {
        //从其他浏览器打开
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, getResources().getString(R.string.select_browser)));
        }else{
            Toast.makeText(this, R.string.no_browser_choose, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initPresenter() {

    }

    private long mOldTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mOldTime < 1500) {
                mHtml5WebView.clearHistory();
                mHtml5WebView.loadUrl(mUrl);
            } else if (mHtml5WebView.canGoBack()) {
                mHtml5WebView.goBack();
            } else {
                NewsDetailsActivity.this.finish();
            }
            mOldTime = System.currentTimeMillis();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mHtml5WebView != null) {
            mHtml5WebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mHtml5WebView.clearHistory();

            ((ViewGroup) mHtml5WebView.getParent()).removeView(mHtml5WebView);
            mHtml5WebView.destroy();
            mHtml5WebView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.news_details_contextmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.copy_title:
                onCopyTextToClipboard(mTitleNewsDatails.getText().toString());
                break;
            case R.id.copy_websiteaddress:
                onCopyTextToClipboard(mHtml5WebView.getUrl());
                break;
            case R.id.go_back:
                if (mHtml5WebView.canGoBack()) {
                    mHtml5WebView.goBack();
                }else {
                    Toast.makeText(this, R.string.last_page, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_forward:
                if (mHtml5WebView.canGoForward()) {
                    mHtml5WebView.goForward();
                }else {
                    Toast.makeText(this, R.string.last_page, Toast.LENGTH_SHORT).show();
                }
                break;
            default:

                break;
        }
        return super.onContextItemSelected(item);
    }
}
