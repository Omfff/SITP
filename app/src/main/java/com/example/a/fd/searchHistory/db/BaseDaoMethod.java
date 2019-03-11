package com.example.a.fd.searchHistory.db;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Package: com.example.a.fd.searchHistory.db
 * @ClassName: BaseDaoMethod
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/22 18:15
 */

public class BaseDaoMethod<T>{
    public static final String TAG = BaseDaoMethod.class.getSimpleName();
    public static final boolean DUBUG = true;

    public BaseDaoMethod(Context context) {
        //manager = DaoManager.getInstance();
        //manager.init(context);
        //daoSession = manager.getDaoSession();
        //manager.setDebug(DUBUG);
    }

    /**************************数据库插入操作***********************/
    /**
     * 插入单个对象
     * @param object
     * @return
     */
    public  boolean insertObject(T object){
        boolean flag = false;
        try {
            flag = DaoManager.getInstance().getDaoSession().insert(object) != -1 ? true:false;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return flag;
    }

    /**
     * 插入多个对象，并开启新的线程
     * @param objects
     * @return
     */
    public boolean insertMultObject(final List<T> objects){
        boolean flag = false;
        if (null == objects || objects.isEmpty()){
            return false;
        }
        try {
            DaoManager.getInstance().getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (T object : objects) {
                        DaoManager.getInstance().getDaoSession().insertOrReplace(object);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            flag = false;
        }finally {
//            manager.CloseDataBase();
        }
        return flag;
    }

    /**************************数据库更新操作***********************/
    /**
     * 以对象形式进行数据修改
     * 其中必须要知道对象的主键ID
     * @param object
     * @return
     */
    public void  updateObject(T object){

        if (null == object){
            return ;
        }
        try {
            DaoManager.getInstance().getDaoSession().update(object);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 批量更新数据
     * @param objects
     * @return
     */
    public void updateMultObject(final List<T> objects, Class clss){
        if (null == objects || objects.isEmpty()){
            return;
        }
        try {

            DaoManager.getInstance().getDaoSession().getDao(clss).updateInTx(new Runnable() {
                @Override
                public void run() {
                    for(T object:objects){
                        DaoManager.getInstance().getDaoSession().update(object);
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    /**************************数据库删除操作***********************/
    /**
     * 删除某个数据库表
     * @param clss
     * @return
     */
    public boolean deleteAll(Class clss){
        boolean flag = false;
        try {
            DaoManager.getInstance().getDaoSession().deleteAll(clss);
            flag = true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            flag = false;
        }
        return flag;
    }

    /**
     * 删除某个对象
     * @param object
     * @return
     */
    public void deleteObject(T object){
        try {
            DaoManager.getInstance().getDaoSession().delete(object);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 异步批量删除数据
     * @param objects
     * @return
     */
    public boolean deleteMultObject(final List<T> objects, Class clss){
        boolean flag = false;
        if (null == objects || objects.isEmpty()){
            return false;
        }
        try {

            DaoManager.getInstance().getDaoSession().getDao(clss).deleteInTx(new Runnable() {
                @Override
                public void run() {
                    for(T object:objects){
                        DaoManager.getInstance().getDaoSession().delete(object);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            flag = false;
        }
        return flag;
    }

    /**************************数据库查询操作***********************/

    /**
     * 获得某个表名
     * @return
     */
    public String getTablename(Class object){
        return  DaoManager.getInstance().getDaoSession().getDao(object).getTablename();
    }

    /**
     * 查询某个ID的对象是否存在
     * @param
     * @return
     */
    public boolean isExitObject(long id,Class object){
        QueryBuilder<T> qb = (QueryBuilder<T>)  DaoManager.getInstance().getDaoSession().getDao(object).queryBuilder();
        qb.where( DaoManager.getInstance().getDaoSession().getDao(object).getPkProperty().eq(id));
        long length = qb.buildCount().count();
        return length>0 ? true:false;
    }

    /**
     * 根据主键ID来查询
     * @param id
     * @return
     */
    public T QueryById(long id,Class object){
        return (T)  DaoManager.getInstance().getDaoSession().getDao(object).loadByRowId(id);
    }

    /**
     * 查询某条件下的对象
     * @param object
     * @return
     */
    public List<T> QueryObject(Class object,String where,String...params){
        Object obj = null;
        List<T> objects = null;
        try {
            obj =  DaoManager.getInstance().getDaoSession().getDao(object);
            if (null == obj){
                return null;
            }
            objects =  DaoManager.getInstance().getDaoSession().getDao(object).queryRaw(where,params);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return objects;
    }
    /**
     * 查询所有对象
     * @param object
     * @return
     */
    public List<T> QueryAll(Class object){
        List<T> objects = null;
        try {
            objects = (List<T>)  DaoManager.getInstance().getDaoSession().getDao(object).loadAll();
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
        return objects;
    }

    /***************************关闭数据库*************************/
    /**
     * 关闭数据库一般在Odestory中使用
     */
    public void CloseDataBase(){
       //DaoManager.closeDataBase();
    }


}

