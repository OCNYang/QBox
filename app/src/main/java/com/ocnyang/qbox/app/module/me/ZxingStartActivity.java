package com.ocnyang.qbox.app.module.me;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.orhanobut.logger.Logger;
import com.ocnyang.qbox.app.Html5Activity;
import com.ocnyang.qbox.app.R;
import com.ocnyang.qbox.app.base.BaseCommonActivity;
import com.ocnyang.qbox.app.config.Const;
import com.ocnyang.qbox.app.module.TakeOrPickPhotoManager;
import com.ocnyang.qbox.app.module.pinchimage.PinImageActivity;
import com.ocnyang.qbox.app.utils.SPUtils;

import java.io.File;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ZxingStartActivity extends BaseCommonActivity implements QRCodeView.Delegate, View.OnTouchListener, TakePhoto.TakeResultListener, InvokeListener {
    public static final int STYLE_ALL = 0;
    public static final String STYLE = "zxing_style";
    public static final int STYLE_TEXT = 1;
    public static final int STYLE_WEB = 2;
    public static final int STYLE_DOWNLOAD = 3;
    public static final int STYLE_IMG = 4;
    public final static int mMessageFlag = 0x1110;

    @BindView(R.id.zxingview)
    ZXingView mZxingview;
    @BindView(R.id.toolbar_zxingstart)
    Toolbar mToolbarZxingstart;
    @BindView(R.id.FAB_left_zxingstart)
    FloatingActionButton mFABLeftZxingstart;
    @BindView(R.id.FAB_right_zxingstart)
    FloatingActionButton mFABRightZxingstart;


    boolean mNotShowRect = false;
    public int mZxingStyle;
    public AlertDialog.Builder mDialogBuilder;
    public TakeOrPickPhotoManager mTakeOrPickPhotoManager;
    ;
    TakePhoto takePhoto;
    private InvokeParam invokeParam;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mZxingview.showScanRect();

            switch (msg.what) {
                case mMessageFlag:
                    String result = (String) msg.obj;
                    if (TextUtils.isEmpty(result)) {
                        Toast.makeText(ZxingStartActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
                    } else
                        showSuccessDialog(result);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_zxing_start);

    }

    @Override
    public void initView() {
        getStyle();
        initToolbar();
        initFAB();
        initZxingView();

    }

    private void getStyle() {
        Intent intent = getIntent();
        mZxingStyle = intent.getIntExtra(STYLE, 0);
    }

    private void initZxingView() {
        mZxingview.setDelegate(this);
        mZxingview.startSpot();
    }

    private void initFAB() {
        mFABLeftZxingstart.setOnTouchListener(this);
        mFABRightZxingstart.setOnTouchListener(this);
        boolean fab_location = (boolean) SPUtils.get(this, Const.FAB_LOCATION, false);
        changeFABLocation(fab_location);
    }

    /**
     * 改变浮动按钮的位置
     *
     * @param fab_location false表示按钮在左
     */
    private void changeFABLocation(boolean fab_location) {
        SPUtils.put(this, Const.FAB_LOCATION, fab_location);
        if (!fab_location) {
            if (mFABLeftZxingstart.getVisibility() == View.GONE) {
                mFABLeftZxingstart.setVisibility(View.VISIBLE);
            }
            if (mFABRightZxingstart.getVisibility() == View.VISIBLE) {
                mFABRightZxingstart.setVisibility(View.GONE);
            }
        } else {
            if (mFABLeftZxingstart.getVisibility() == View.VISIBLE) {
                mFABLeftZxingstart.setVisibility(View.GONE);
            }
            if (mFABRightZxingstart.getVisibility() == View.GONE) {
                mFABRightZxingstart.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void initPresenter() {

    }

    private void initToolbar() {
        mToolbarZxingstart.inflateMenu(R.menu.zxing_start_menu);
        mToolbarZxingstart.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbarZxingstart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbarZxingstart.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.open_photos:
                        openPhotos();
                        break;
                    case R.id.fab_location:
                        changeFABLocation(!((Boolean) SPUtils.get(ZxingStartActivity.this, Const.FAB_LOCATION, false)));
                        break;
                    case R.id.rect_mark:
                        changeRect(mNotShowRect = !mNotShowRect);
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void openPhotos() {
        mTakeOrPickPhotoManager = new TakeOrPickPhotoManager(getTakePhoto());

        //设置为选择图片不裁剪
        mTakeOrPickPhotoManager.setCrop(false);
        //选择图片
        mTakeOrPickPhotoManager.takeOrPickPhoto(false);
    }

    /**
     * 隐藏/显示 扫描框
     *
     * @param notShowRect
     */
    private void changeRect(boolean notShowRect) {
        if (notShowRect) {
            mZxingview.hiddenScanRect();
        } else {
            mZxingview.showScanRect();
        }
    }

    /**
     * 扫描二维码回调事件
     *
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {
        showSuccessDialog(result);
        vibrate();

    }

    RadioGroup radioGroup;
    public void showSuccessDialog(final String result) {
        mZxingview.stopSpot();
        mDialogBuilder = new AlertDialog.Builder(this);
        mDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mZxingview.startSpot();
            }
        });

        if (mZxingStyle == STYLE_ALL) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.selectspot_dialog_zxing, null);
            radioGroup = (RadioGroup) inflate.findViewById(R.id.radiogroup_dialog_zxing);
            radioGroup.check(R.id.rb_text);
            mDialogBuilder.setView(inflate);

        }
            mDialogBuilder.setTitle("扫取结果");
            mDialogBuilder.setMessage(result);

            View view = LayoutInflater.from(this).inflate(R.layout.headtitle_dialog_zxing, null);
            view.findViewById(R.id.copy_dialog_zxing).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCopyTextToClipboard(result);
                }
            });
            mDialogBuilder.setCustomTitle(view);



        mDialogBuilder.setNegativeButton(R.string.cancel, null);
        mDialogBuilder.setPositiveButton(R.string.imtrue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (mZxingStyle) {
                    case STYLE_ALL:
                        actionAll(result,radioGroup);
                        break;
                    case STYLE_DOWNLOAD:
                        break;
                    case STYLE_IMG:
                        actionImg(result);
                        break;
                    case STYLE_TEXT:
                        actionText();
                        break;
                    case STYLE_WEB:
                        actionWeb(result);
                        break;
                    default:
                        break;
                }
            }
        });

        mDialogBuilder.show();

    }

    private void actionAll(String result, RadioGroup radioGroup) {

        switch(radioGroup.getCheckedRadioButtonId()){
            case R.id.rb_download:
                break;
            case R.id.rb_img:
                actionImg(result);
                break;
            case R.id.rb_text:
                actionText();
                break;
            case R.id.rb_web:
                actionWeb(result);
                break;
            default:
                break;
        }
    }

    private void actionWeb(String result) {
        Intent intent = new Intent(getContext(), Html5Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url",result);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
        finish();
    }

    private void actionText() {
        finish();
    }

    private void actionImg(String result) {
        Intent intent = new Intent(getContext(), PinImageActivity.class);
        intent.putExtra(PinImageActivity.IMG_NAME,result);
        intent.putExtra(PinImageActivity.IMG_URL,result);
        startActivity(intent);
        finish();
    }

    /**
     * 复制到粘贴板
     *
     * @param string
     */
    private void onCopyTextToClipboard(String string) {
        if (!TextUtils.isEmpty(string)) {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("website", string);
            clipboardManager.setPrimaryClip(clip);
            Toast.makeText(this, R.string.copysuccess, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.copyfail, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Logger.e("打开相机出错");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZxingview.startCamera();
        mZxingview.showScanRect();
    }

    @Override
    protected void onStop() {
        mZxingview.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZxingview.onDestroy();
        super.onDestroy();
    }

    /**
     * 震动手机
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mZxingview.openFlashlight();
                break;
            case MotionEvent.ACTION_UP:
                mZxingview.closeFlashlight();
                break;
            default:

                break;
        }
        return false;
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
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public void takeSuccess(TResult result) {
        File file = new File(result.getImages().get(0).getCompressPath());
        String path = file.getAbsolutePath();

        //获取扫描图片结果的过程必须在异步线程中进行
        //在这里，因为获取图片的过程 是在异步线程中进行的，所以上面不必在开启新的异步线程了。
        String spotResult = QRCodeDecoder.syncDecodeQRCode(path);

        //需要返回到UI线程 刷新头像
        Message msg = mHandler.obtainMessage();
        msg.what = mMessageFlag;
        msg.obj = spotResult;
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
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this)
                    .bind(new TakePhotoImpl(this, this));

        }
        return takePhoto;
    }
}
