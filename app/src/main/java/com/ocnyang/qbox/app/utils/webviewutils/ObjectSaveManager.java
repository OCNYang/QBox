package com.ocnyang.qbox.app.utils.webviewutils;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static android.content.Context.MODE_PRIVATE;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/29 11:00.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/

public class ObjectSaveManager {
    private static ObjectSaveManager mInstance;
    Context mContext;

    private ObjectSaveManager(Context context) {
        mContext = context;
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static ObjectSaveManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ObjectSaveManager.class) {
                if (mInstance == null) {
                    mInstance = new ObjectSaveManager(context);
                }
            }
        }
        return mInstance;
    }

    public void saveObject(String name, Object parcelable) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = mContext.openFileOutput(name, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(parcelable);
            Logger.e("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("保存失败："+e.getMessage()+"-"+e.getCause().toString());
            //这里是保存文件产生异常
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }

    public Object getObject(String name) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = mContext.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        //读取产生异常，返回null
        return null;
    }
}
