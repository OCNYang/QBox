package com.ocnyang.qbox.app.database;

import android.content.Context;

import com.ocnyang.qbox.app.greendao.db.DaoMaster;
import com.ocnyang.qbox.app.greendao.db.DaoSession;
import com.ocnyang.qbox.app.greendao.db.FunctionBeanDao;
import com.ocnyang.qbox.app.model.entities.FunctionBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************
 * * * * *   * * * *   *     *       Created by OCN.Yang
 * *     *   *         * *   *       Time:2017/2/28 16:58.
 * *     *   *         *   * *       Email address:ocnyang@gmail.com
 * * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class FunctionDao {
    private DBManager mDBManager;

    public FunctionDao(Context context) {
        mDBManager = DBManager.getInstance(context);
    }

    //增删改查---------------------------------------

    /**
     * 插入一条
     *
     * @param functionBean
     */
    public void insertFunctionBean(FunctionBean functionBean){
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao functionBeanDao = daoSession.getFunctionBeanDao();
        functionBeanDao.insert(functionBean);
    }

    /**
     * 插入用户集合
     *
     * @param users
     */
    public void insertFunctionBeanList(List<FunctionBean> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao userDao = daoSession.getFunctionBeanDao();
        userDao.insertOrReplaceInTx(users);
    }

    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteFunctionBean(FunctionBean user) {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao userDao = daoSession.getFunctionBeanDao();
        userDao.delete(user);
    }

    /**
     * 删除所有记录
     */
    public void deleteAllFunctionBean(){
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao categoryEntityDao = daoSession.getFunctionBeanDao();
        categoryEntityDao.deleteAll();
    }

    /**
     * 更新一条记录
     *
     * @param user
     */
    public void updateFunctionBean(FunctionBean user) {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao userDao = daoSession.getFunctionBeanDao();
        userDao.update(user);
    }

    /**
     * 更新多条记录
     *
     * @param user
     */
    public void updateAllFunctionBean(List<FunctionBean> user) {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao userDao = daoSession.getFunctionBeanDao();
        userDao.updateInTx(user);
    }

    /**
     * 查询用户列表
     */
    public List<FunctionBean> queryFunctionBeanList() {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao userDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = userDao.queryBuilder();
        List<FunctionBean> list = qb.orderAsc(FunctionBeanDao.Properties.Id).list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<FunctionBean> queryFunctionBeanListSmall() {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao userDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = userDao.queryBuilder();
        List<FunctionBean> list = qb.where(FunctionBeanDao.Properties.Mark.eq(1))
                .orderAsc(FunctionBeanDao.Properties.Id).list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<FunctionBean> queryFunctionBeanListSmallNoMore() {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao userDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = userDao.queryBuilder();
        List<FunctionBean> list = qb.where(FunctionBeanDao.Properties.Mark.eq(1))
                .where(FunctionBeanDao.Properties.Name.notEq("更多"))
                .orderAsc(FunctionBeanDao.Properties.Id).list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<FunctionBean> queryFunctionBeanListSmallNeed() {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao userDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = userDao.queryBuilder();
        List<FunctionBean> list = qb.where(FunctionBeanDao.Properties.Mark.eq(1))
                .orderAsc(FunctionBeanDao.Properties.Id)
                .limit(6)
                .list();
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 查询某个功能是否开启
     */
    public boolean queryFunctionBeanListBigIsOpen(String string) {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao userDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = userDao.queryBuilder();
        FunctionBean functionBean = qb.where(FunctionBeanDao.Properties.Code.eq(string)).uniqueOrThrow();
        return !(functionBean.getNotOpen());
    }

    /**
     * 查询 更多 的位置
     */
    public int queryMoreFunctionBean() {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao userDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = userDao.queryBuilder();
        FunctionBean functionBean = qb.where(FunctionBeanDao.Properties.Name.eq("更多")).uniqueOrThrow();
        return functionBean.getId();
    }


    /**
     * 更新 更多 一条记录
     *
     *
     */
    public void updateMoreFunctionBean() {
        DaoMaster daoMaster = new DaoMaster(mDBManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FunctionBeanDao userDao = daoSession.getFunctionBeanDao();
        QueryBuilder<FunctionBean> qb = userDao.queryBuilder();
        FunctionBean functionBean = qb.where(FunctionBeanDao.Properties.Name.eq("更多")).uniqueOrThrow();
        functionBean.setId(6);
        userDao.update(functionBean);
    }
}
