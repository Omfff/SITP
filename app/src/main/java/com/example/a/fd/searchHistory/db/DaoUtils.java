package com.example.a.fd.searchHistory.db;

import android.content.Context;

/**
 * @Package: com.example.a.fd.searchHistory.db
 * @ClassName: DaoUtils
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/22 22:28
 */

public class DaoUtils{
    private  static DaoManager daoManager;
    public  static Context context;

    public static void init(Context context){
        DaoUtils.context = context.getApplicationContext();
    }

    /**
     * 单列模式获取CustomerManager对象
     * @return
     */
    public static DaoManager getDaoManagerInstance(){
        if (daoManager == null) {
           daoManager = DaoManager.getInstance();
           daoManager.init(context);

        }
        return daoManager;
    }


}

