package com.ocnyang.qbox.app.database;

import android.content.Context;

import com.ocnyang.qbox.app.greendao.db.CategoryEntityDao;
import com.ocnyang.qbox.app.greendao.db.DaoMaster;
import com.ocnyang.qbox.app.greendao.db.DaoSession;
import com.ocnyang.qbox.app.module.news_category.CategoryEntity;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/28 16:12.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class CategoryDao {

    public DBManager mDBManager;

    public CategoryDao(Context context) {
        mDBManager = DBManager.getInstance(context);
    }

    /**
     * 插入一条记录
     *
     * @param user
     */
    public void insertCategory(CategoryEntity user) {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
        userDao.insert(user);
    }

    /**
     * 插入用户集合
     *
     * @param users
     */
    public void insertCategoryList(List<CategoryEntity> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
        userDao.insertOrReplaceInTx(users);
    }

    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteCategory(CategoryEntity user) {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
        userDao.delete(user);
    }

    /**
     * 删除所有记录
     */
    public void deleteAllCategory(){
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CategoryEntityDao categoryEntityDao = daoSession.getCategoryEntityDao();
        categoryEntityDao.deleteAll();
    }

    /**
     * 更新一条记录
     *
     * @param user
     */
    public void updateCategory(CategoryEntity user) {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
        userDao.update(user);
    }

    /**
     * 查询用户列表
     */
    public List<CategoryEntity> queryCategoryList() {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CategoryEntityDao userDao = daoSession.getCategoryEntityDao();
        QueryBuilder<CategoryEntity> qb = userDao.queryBuilder();
        List<CategoryEntity> list = qb.orderAsc(CategoryEntityDao.Properties.Order).list();
        return list;
    }
}
