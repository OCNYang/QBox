package com.ocnyang.qbox.app.module.setting;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseFragment;
import com.ocnyang.qbox.app.utils.SaveImageUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/4/7 14:09.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class AboutMeFragment extends BaseFragment {


    @BindView(R.id.my_mail)
    TextView mMyMail;
    @BindView(R.id.my_jianshu)
    TextView mMyJianshu;
    @BindView(R.id.my_github)
    TextView mMyGithub;
    @BindView(R.id.my_sinaweibo)
    TextView mMySinaweibo;
    @BindView(R.id.wechat_aboutme)
    View mWechat_aboutme;
    @BindView(R.id.weibo_aboutme)
    View mWeibo_aboutme;
    @BindView(R.id.alipay_aboutme)
    View mAlipay_aboutme;

    public AboutMeFragment() {
    }

    public static AboutMeFragment newInstance() {
        AboutMeFragment fragment = new AboutMeFragment();
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_about_me;
    }

    @Override
    public void initView() {
        initTextFont();
    }

    private void initTextFont() {
        mMyGithub.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        mMyMail.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        mMyJianshu.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        mMySinaweibo.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
    }

    public void openchrome(String url){
        //从其他浏览器打开
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(Intent.createChooser(intent, "请选择浏览器"));
    }

    public void sendMail(){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_EMAIL, new String[] {"ocnyang@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "对小秋魔盒的反馈");
        email.putExtra(Intent.EXTRA_TEXT, "请写出你对小秋魔盒改进的建议...");
        startActivity(Intent.createChooser(email, "选择邮箱客户端"));
    }

    @Override
    protected void managerArguments() {

    }

    @OnClick({R.id.my_mail, R.id.my_jianshu, R.id.my_github, R.id.my_sinaweibo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_mail:
                sendMail();
                break;
            case R.id.my_jianshu:
                openchrome(mMyJianshu.getText().toString());
                break;
            case R.id.my_github:
                openchrome(mMyGithub.getText().toString());
                break;
            case R.id.my_sinaweibo:
                openchrome("http://weibo.com/ocnyang");
                break;
        }
    }

    @OnLongClick({R.id.alipay_aboutme,R.id.weibo_aboutme,R.id.wechat_aboutme})
    public boolean onLongClick(View v) {
        boolean flag = false;
        switch (v.getId()){
            case R.id.weibo_aboutme:
                flag = SaveImageUtils.saveImageToGallery(getContext().getApplicationContext(), BitmapFactory.decodeResource(getResources(),R.drawable.ocnyang_sinaweibo));
                break;
            case R.id.alipay_aboutme:
                flag = SaveImageUtils.saveImageToGallery(getContext().getApplicationContext(),BitmapFactory.decodeResource(getResources(),R.drawable.ocnyang_zfb));
                break;
            case R.id.wechat_aboutme:
                flag = SaveImageUtils.saveImageToGallery(getContext().getApplicationContext(),BitmapFactory.decodeResource(getResources(),R.drawable.ocnyang_wechat));
                break;
        }
        if (flag){
            Toast.makeText(getContext(), "保存成功，请到图库中查看！", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "保存失败，请重新保存！", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public String getUmengFragmentName() {
        return getContext().getClass().getSimpleName()+"-"
                +this.getClass().getSimpleName();
    }
}
