package com.example.a.fd.searchHistory.db;

import android.content.Context;

import com.example.a.fd.model.Food;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Package: com.example.a.fd.searchHistory.db
 * @ClassName: FoodMethod
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/25 22:12
 */
public class FoodMethod extends BaseDaoMethod<Food> {
    public FoodMethod(Context context){
        super(context);
    }
    public List<Food> queryByKeyWord(String keyword,int offset){
        QueryBuilder qb = DaoManager.getInstance().getDaoSession().getDao(Food.class).queryBuilder();
        qb.where(FoodDao.Properties.Name.like("%" + keyword + "%"));
        return qb.offset(offset * 10).limit(10).list();
    }
    public int queryResultPage(String keyword){
        QueryBuilder qb = DaoManager.getInstance().getDaoSession().getDao(Food.class).queryBuilder();
        qb.where(FoodDao.Properties.Name.like("%" + keyword + "%"));
        return qb.list().size();
    }
    public Food queryByName(String name){
        QueryBuilder qb = DaoManager.getInstance().getDaoSession().getDao(Food.class).queryBuilder();
        qb.where(FoodDao.Properties.Name.eq(name));
        return (Food) qb.unique();
    }
    public int queryCategoryPage(int category){
        QueryBuilder qb = DaoManager.getInstance().getDaoSession().getDao(Food.class).queryBuilder();
        qb.where(FoodDao.Properties.Type.eq(category));
        return qb.list().size();
    }
    public List<Food> queryCategoryList(int category,int offset){
        QueryBuilder qb = DaoManager.getInstance().getDaoSession().getDao(Food.class).queryBuilder();
        qb.where(FoodDao.Properties.Type.eq(category));
        return qb.offset(offset * 10).limit(10).list();
    }


}
