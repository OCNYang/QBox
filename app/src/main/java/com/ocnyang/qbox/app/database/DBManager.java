package com.ocnyang.qbox.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ocnyang.qbox.app.greendao.db.DaoMaster;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/22 15:43.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class DBManager {
    private final static String dbName = "test_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    private DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    protected SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    protected SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }


    //增删改查-----------------------------------------------------------------------------
//
//    /**
//     * 插入一条记录
//     *
//     * @param user
//     */
//    public void insertCategory(CategoryEntity user) {
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
//        userDao.insert(user);
//    }
//
//    /**
//     * 插入用户集合
//     *
//     * @param users
//     */
//    public void insertCategoryList(List<CategoryEntity> users) {
//        if (users == null || users.isEmpty()) {
//            return;
//        }
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
//        userDao.insertOrReplaceInTx(users);
//    }
//
//    /**
//     * 删除一条记录
//     *
//     * @param user
//     */
//    public void deleteCategory(CategoryEntity user) {
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
//        userDao.delete(user);
//    }
//
//    /**
//     * 删除所有记录
//     */
//    public void deleteAllCategory(){
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        CategoryEntityDao categoryEntityDao = daoSession.getCategoryEntityDao();
//        categoryEntityDao.deleteAll();
//    }
//
//    /**
//     * 更新一条记录
//     *
//     * @param user
//     */
//    public void updateCategory(CategoryEntity user) {
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
//        userDao.update(user);
//    }
//
//    /**
//     * 查询用户列表
//     */
//    public List<CategoryEntity> queryCategoryList() {
//        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
//        QueryBuilder<CategoryEntity> qb = userDao.queryBuilder();
//        List<CategoryEntity> list = qb.orderAsc(CategoryEntityDao.Properties.Order).list();
//        return list;
//    }

    /**
     * 查询用户列表
     */
//    public List<CategoryEntity> queryUserList(int age) {
//        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
//        QueryBuilder<CategoryEntity> qb = userDao.queryBuilder();
//        qb.where(CategoryEntityDao.Properties.Age.gt(age)).orderAsc(CategoryEntityDao.Properties.Age);
//        List<CategoryEntity> list = qb.list();
//        return list;
//    }

}
