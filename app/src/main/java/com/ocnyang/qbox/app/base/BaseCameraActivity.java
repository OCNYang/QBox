package com.ocnyang.qbox.app.base;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.ocnyang.qbox.app.utils.CommonUtils;
import com.ocnyang.qbox.app.utils.SdCardUtil;

import java.io.File;

/**
 * 带拍照及选择相册图片的Activity
 */
public abstract class BaseCameraActivity extends BaseActivity {

    public static final int ACTION_CAMERA = 0;
    public static final int ACTION_ALBUM = 1;
    public static final int ACTION_ZOOM = 2;
    private static final String SUFFIX_NAME = ".jpg";

    private int width;
    private int height;
    private String picName;
    private String savePath = SdCardUtil.getNormalSDCardPath();

    /**
     * 打开摄像头拍照
     *
     * @param picName 照片存储名称
     */
    public void openCamera(String picName, int width, int height) {
        this.picName = picName;
        this.width = width;
        this.height = height;

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(savePath, picName + SUFFIX_NAME)));
        startActivityForResult(intent, ACTION_CAMERA);
    }

    /**
     * 打开相册选取图片
     *
     * @param width  图片宽度
     * @param height 图片高度
     */
    public void openAlbum(int width, int height) {
        this.width = width;
        this.height = height;

        Intent albumIntent = new Intent();
        albumIntent.setType("image/*");
        albumIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(albumIntent, ACTION_ALBUM);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String picPath = "";
        switch (requestCode) {
            case ACTION_CAMERA:
                picPath = new File(savePath, picName + SUFFIX_NAME).getPath();
                break;
            case ACTION_ALBUM:
                if (data == null) {
                    return;
                }

                Uri uri = data.getData();
                picPath = getPath(this, uri);
                break;
            case ACTION_ZOOM:
                if (data != null) {
                    Uri zoomImaUri = data.getData();
                    if(zoomImaUri != null){
                        String zoomImaUriPath = CommonUtils.getRealFilePath(this, zoomImaUri);
                        picSelectSuccess(zoomImaUriPath);
                    }
                }
                break;
        }

        if (!TextUtils.isEmpty(picPath)) {
            startPhotoZoom(Uri.fromFile(new File(picPath)));
        }
    }

    /**
     * 获取图片名称
     * @return
     */
    public String getPicName() {
        return picName;
    }

    /**
     * 图片选择完毕
     *
     * @param path 图片路径
     */
    protected abstract void picSelectSuccess(String path);

    /**
     * 裁剪图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, ACTION_ZOOM);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }
}
