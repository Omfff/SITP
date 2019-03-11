package com.example.a.fd.searchHistory.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @Package: com.example.a.fd.searchHistory.db
 * @ClassName: DaoManager
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/22 17:57
 */
public class DaoManager {

    private static DaoManager mInstance;
    private static DaoSession daoSession;
    private static DaoMaster daoMaster;
    private static  final String  DB_NAME = "searchHistory.db";
    private static SQLiteDatabase db;
    private static MyOpenHelper mHelper;
    private static Context context;
    public static DaoManager getInstance(){
        if (mInstance == null){
            synchronized (DaoManager.class){
                if (mInstance == null){
                    mInstance = new DaoManager();
                }
            }
        }
        return mInstance;
    }
    public void init(Context context){
        this.context = context;
    }

    public DaoMaster getDaoMaster(){
        if (null == daoMaster){
            mHelper =  new MyOpenHelper(context,DB_NAME,null);
            daoMaster = new DaoMaster(mHelper.getWritableDatabase());
        }
        return daoMaster;
    }
    public DaoSession getDaoSession(){
        if (null == daoSession){
            if (null == daoMaster){
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public void closeDataBase(){
        closeHelper();
        closeDaoSession();
    }

    public void closeDaoSession() {
        if (null != daoSession) {
            daoSession.clear();
            daoSession = null;
        }

    }
    public  void  closeHelper(){
        if (mHelper!=null){
            mHelper.close();
            mHelper = null;
        }
    }


}
