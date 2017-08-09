package com.ocnyang.qbox.app.module.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.model.entities.City;
import com.ocnyang.qbox.app.model.entities.RefreshMeFragmentEvent;
import com.ocnyang.qbox.app.module.me.weather.weather.api.ApiManager;
import com.ocnyang.qbox.app.utils.SPUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.ocnyang.qbox.app.module.me.weather.weather.api.ApiManager.KEY_SELECTED_AREA;


public class UserInfoActivity extends BaseCommonActivity implements Toolbar.OnMenuItemClickListener, TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.toolbar_userinfo)
    Toolbar mToolbarUserinfo;
    @BindView(R.id.userhead_img_userinfo)
    CircleImageView mUserheadImgUserinfo;
    @BindView(R.id.userheader_userinfo)
    FrameLayout mUserheaderUserinfo;
    @BindView(R.id.user_name_userinfo)
    TextInputEditText mUserNameUserinfo;
    @BindView(R.id.radiobutton_man_userinfo)
    RadioButton mRadiobuttonManUserinfo;
    @BindView(R.id.radiobutton_woman_userinfo)
    RadioButton mRadiobuttonWomanUserinfo;
    @BindView(R.id.user_geyan_userinfo)
    TextInputEditText mUserGeyanUserinfo;
    @BindView(R.id.radiogroup_sex_userinfo)
    RadioGroup mRadiogroupSexUserinfo;
    @BindView(R.id.starspinner_userinfo)
    Spinner mStarspinnerUserinfo;
    @BindView(R.id.user_address_userinfo)
    TextInputEditText mUserAddressUserinfo;


    City.HeWeather5Bean.BasicBean cityBean = null;
    public final static int mMessageFlag = 0x1010;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    public String mHeaderAbsolutePath;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case mMessageFlag:
                    Glide.with(UserInfoActivity.this).load(((File) msg.obj)).into(mUserheadImgUserinfo);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_user_info);
    }

    @Override
    public void initView() {
        initToolbar();
        initInfo();
    }


    private void initInfo() {
        String userName = (String) SPUtils.get(this, Const.USER_NAME, "");
        boolean usergender = (boolean) SPUtils.get(this, Const.USER_SEX, true);
        String usergeyan = (String) SPUtils.get(this, Const.USER_GEYAN, "");
        String userstar = (String) SPUtils.get(this, Const.USER_STAR, "");
        String useraddress = (String) SPUtils.get(this, Const.USER_ADDRESS, "");
        String userheader = (String) SPUtils.get(this, Const.USER_HEADER, "");
        if (!TextUtils.isEmpty(userName)) {
            mUserNameUserinfo.setText(userName);
        }
        mRadiogroupSexUserinfo.check(usergender == true ? R.id.radiobutton_man_userinfo : R.id.radiobutton_woman_userinfo);
        if (!TextUtils.isEmpty(usergeyan)) {
            mUserGeyanUserinfo.setText(usergeyan);
        }
        if (!TextUtils.isEmpty(useraddress)) {
            mUserAddressUserinfo.setText(useraddress);
        }
        if (!TextUtils.isEmpty(userstar)) {
            int indexOf = Arrays.asList(getResources().getStringArray(R.array.arrays_constellation)).indexOf(userstar);
            mStarspinnerUserinfo.setSelection(indexOf);
        }
        if (!TextUtils.isEmpty(userheader)) {
            File file = new File(userheader);
            Glide.with(this).load(file).into(mUserheadImgUserinfo);
        }


        mUserNameUserinfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 16)
                    mUserNameUserinfo.setError("已达到最大长度");
            }
        });

        mUserGeyanUserinfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 128) {
                    mUserGeyanUserinfo.setError("已达到最大长度");
                }
            }
        });

    }

    /**
     * 获取星座列表的选择项
     *
     * @return
     */
    private String getStarSelect() {
        long selectedItemId = mStarspinnerUserinfo.getSelectedItemId();
        String[] stringArray = getResources().getStringArray(R.array.arrays_constellation);
        return stringArray[(int) selectedItemId];
    }

    private void initToolbar() {
        mToolbarUserinfo.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbarUserinfo.inflateMenu(R.menu.userinfo_menu);
        mToolbarUserinfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolbarUserinfo.setOnMenuItemClickListener(this);
    }

    @Override
    public void initPresenter() {

    }

    /**
     * 保存按钮的 点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.save) {

            Editable textName = mUserNameUserinfo.getText();
            if (TextUtils.isEmpty(textName)) {
                mUserNameUserinfo.setError("姓名不应为空");
                return false;
            }

            if (textName.length() >= 3) {
                SPUtils.put(this, Const.USER_NAME, textName.toString());
            } else {
                mUserNameUserinfo.setError("长度不应小于3个字符");
                return false;
            }

            Editable textGeyan = mUserGeyanUserinfo.getText();
            if (!TextUtils.isEmpty(textGeyan)) {
                SPUtils.put(this, Const.USER_GEYAN, textGeyan.toString());
            } else {
                SPUtils.remove(this, Const.USER_GEYAN);
            }

            SPUtils.put(this, Const.USER_SEX, mRadiogroupSexUserinfo.getCheckedRadioButtonId()
                    == R.id.radiobutton_man_userinfo ? true : false);
            SPUtils.put(this, Const.USER_STAR, getStarSelect());
            if (!TextUtils.isEmpty(mUserAddressUserinfo.getText())) {
                SPUtils.put(this, Const.USER_ADDRESS, mUserAddressUserinfo.getText().toString());
                /**
                 * city : 北京
                 * cnty : 中国
                 * id : CN101010100
                 * lat : 39.904000
                 * lon : 116.391000
                 * prov : 直辖市
                 */
                if (cityBean != null) {
                    Logger.e("地点："+cityBean.toString());

                    List<ApiManager.Area> areas  = new ArrayList<>();
                    String s = (String) SPUtils.get(this, KEY_SELECTED_AREA, "");

                    Logger.e("取出的地点：+s");

                    if (!TextUtils.isEmpty(s)) {
                        ApiManager.Area[] aa = new Gson().fromJson(s, ApiManager.Area[].class);
                        if (aa != null) {
                            Collections.addAll(areas, aa);
                        }
                    }

                    String nowAddress = (String) SPUtils.get(this, Const.USER_ADDRESS_ID, "");

                    if (!TextUtils.isEmpty(nowAddress)) {
                        for (ApiManager.Area area : areas) {
                            if (nowAddress.equals(area.getId())) {
                                area.setId(cityBean.getId());
                                area.setCity(cityBean.getCity());
                                area.setName_cn(cityBean.getCity());
                                area.setProvince(cityBean.getProv());
                                break;
                            }
                        }
                    }else {
                        ApiManager.Area e = new ApiManager.Area();
                        e.setCity(cityBean.getCity());
                        e.setProvince(cityBean.getProv());
                        e.setId(cityBean.getId());
                        e.setName_cn(cityBean.getCity());
                        areas.add(e);
                    }

                    SPUtils.put(this,KEY_SELECTED_AREA,new Gson().toJson(areas));

                    SPUtils.put(this, Const.USER_ADDRESS_CITY, cityBean.getCity());
                    SPUtils.put(this, Const.USER_ADDRESS_CNTY, cityBean.getCnty());
                    SPUtils.put(this, Const.USER_ADDRESS_ID, cityBean.getId());
                    SPUtils.put(this, Const.USER_ADDRESS_LAT, cityBean.getLat());
                    SPUtils.put(this, Const.USER_ADDRESS_LON, cityBean.getLon());
                    SPUtils.put(this, Const.USER_ADDRESS_PROV, cityBean.getProv());



                }
            } else {
                SPUtils.remove(this, Const.USER_ADDRESS);
                SPUtils.remove(this, Const.USER_ADDRESS_CNTY);
                SPUtils.remove(this, Const.USER_ADDRESS_CITY);
                SPUtils.remove(this, Const.USER_ADDRESS_ID);
                SPUtils.remove(this, Const.USER_ADDRESS_LAT);
                SPUtils.remove(this, Const.USER_ADDRESS_LON);
                SPUtils.remove(this, Const.USER_ADDRESS_PROV);
            }
            if (TextUtils.isEmpty(mHeaderAbsolutePath)) ;
            else {
                SPUtils.put(this, Const.USER_HEADER, mHeaderAbsolutePath);
            }
            EventBus.getDefault().post(new RefreshMeFragmentEvent(0x1000));
            finish();
        }
        return false;
    }


    @OnClick({R.id.userheader_userinfo, R.id.user_address_userinfo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userheader_userinfo:
                showTakePhotoDialog();
                break;
            case R.id.user_address_userinfo:
                startActivityForResult(new Intent(UserInfoActivity.this, AddressActivity.class),
                        Const.REQUEST_CODE_ADDRESS);
                break;
            default:
                break;
        }
    }

    private void showTakePhotoDialog() {
        final String items[] = {"拍照", "相册"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择头像");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                takeOrPickPhoto(which == 0 ? true : false);
            }
        });

        builder.create().show();
    }

    private void takeOrPickPhoto(boolean isTakePhoto) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        TakePhoto takePhoto = getTakePhoto();

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);

        if (isTakePhoto) {//拍照
            if (true) {//是否裁剪
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
            } else {
                takePhoto.onPickFromCapture(imageUri);
            }
        } else {//选取图片
            int limit = 1;//选择图片的个数。
            if (limit > 1) {
                //当选择图片大于1个时是否裁剪
                if (true) {
                    takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
                } else {
                    takePhoto.onPickMultiple(limit);
                }
                return;
            }
            //是否从文件中选取图片
            if (false) {
                if (true) {//是否裁剪
                    takePhoto.onPickFromDocumentsWithCrop(imageUri, getCropOptions());
                } else {
                    takePhoto.onPickFromDocuments();
                }
                return;
            } else {
                if (true) {//是否裁剪
                    takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                } else {
                    takePhoto.onPickFromGallery();
                }
            }
        }
    }

    /**
     * 拍照相关的配置
     *
     * @param takePhoto
     */
    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        //是否使用Takephoto自带的相册
        if (false) {
            builder.setWithOwnGallery(true);
        }
        //纠正拍照的旋转角度
        if (true) {
            builder.setCorrectImage(true);
        }
        takePhoto.setTakePhotoOptions(builder.create());
    }

    /**
     * 配置 压缩选项
     *
     * @param takePhoto
     */
    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 102400;
        int width = 800;
        int height = 800;
        //是否显示 压缩进度条
        boolean showProgressBar = true;
        //压缩后是否保存原图
        boolean enableRawFile = true;
        CompressConfig config;
        if (false) {
            //使用自带的压缩工具
            config = new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        } else {
            //使用开源的鲁班压缩工具
            LubanOptions option = new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    /**
     * 配置 裁剪选项
     *
     * @return
     */
    private CropOptions getCropOptions() {
        int height = 100;
        int width = 100;

        CropOptions.Builder builder = new CropOptions.Builder();

        //按照宽高比例裁剪
        builder.setAspectX(width).setAspectY(height);
        //是否使用Takephoto自带的裁剪工具
        builder.setWithOwnCrop(false);
        return builder.create();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Const.REQUEST_CODE_ADDRESS && resultCode == Const.RESULTES_CODE_ADDRESS) {
            //添加地点
            cityBean = (City.HeWeather5Bean.BasicBean) data.getParcelableExtra("data");
            Logger.e(cityBean.getProv() + "-" + cityBean.getCity());
            mUserAddressUserinfo.setText(cityBean.getProv() + "-" + cityBean.getCity());

        } else {
            //添加头像
            getTakePhoto().onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }


    //选取图片或拍照成功
    @Override
    public void takeSuccess(TResult result) {
        File file = new File(result.getImages().get(0).getCompressPath());
        mHeaderAbsolutePath = file.getAbsolutePath();

        //需要返回到UI线程 刷新头像
        Message msg = mHandler.obtainMessage();
        msg.what = mMessageFlag;
        msg.obj = file;
        mHandler.sendMessage(msg);
    }


    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

}
