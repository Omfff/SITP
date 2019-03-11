package com.example.a.fd.searchHistory.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.a.fd.model.SearchKeyword;

import org.greenrobot.greendao.database.StandardDatabase;

/**
 * @Package: com.example.a.fd.searchHistory.db
 * @ClassName: MyOpenHelper
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/22 21:25
 */
public class MyOpenHelper extends DaoMaster.OpenHelper {
    String TAG = "GreenDAOHelper Operation";


    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory){
        super(context,name,factory);
    }
    public MyOpenHelper(Context context, String name){
        super(context,name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        if(oldVersion == newVersion){
            Log.d(TAG, "onUpgrade: 数据库是最新版本,无需升级");
            return;
        }
        Log.d(TAG, "onUpgrade: 数据库从" + oldVersion +"升级到"+newVersion+"版本");
        MigrationHelper.migrate(new StandardDatabase(db),
                FoodDao.class,
                SearchKeywordDao.class,
                FoodRecordDao.class);
        // OR you can use it like this (Dont use both it is example of 2 different usages)
    }
}