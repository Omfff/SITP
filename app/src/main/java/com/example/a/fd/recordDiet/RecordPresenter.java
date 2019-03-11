package com.example.a.fd.recordDiet;

import android.content.Context;

import com.example.a.fd.model.DietScore;
import com.example.a.fd.model.Food;
import com.example.a.fd.model.FoodRecord;
import com.example.a.fd.searchHistory.db.BaseDaoMethod;
import com.example.a.fd.searchHistory.db.DaoManager;
import com.example.a.fd.searchHistory.db.DietScoreDao;
import com.example.a.fd.searchHistory.db.FoodMethod;
import com.example.a.fd.searchHistory.db.FoodRecordDao;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Package: com.example.a.fd.recordDiet
 * @ClassName: RecordPresenter
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/3/3 20:06
 */
public class RecordPresenter {
    private static BaseDaoMethod<FoodRecord> foodRecordBaseDaoMethod ;
    private static BaseDaoMethod<DietScore> dietRecordBaseDaoMethod;
    private static FoodMethod foodMethod;
    public RecordPresenter(Context context){
        foodRecordBaseDaoMethod = new BaseDaoMethod<>(context);
        dietRecordBaseDaoMethod = new BaseDaoMethod<>(context);
        foodMethod = new FoodMethod(context);

    }
    public static void addRecordToDb(List<DietInfo> dietInfos,Date displayDate){
        if(dietInfos!=null){
            RecordPresenter.deleteRecordByDay(displayDate);
            for(DietInfo dietInfo :dietInfos){
                if(dietInfo.getItemType() == DietInfo.TYPE_DATA){
                    RecordPresenter.insertRecord(dietInfo,displayDate);
                }
            }
        }
    }
    public static void insertRecord(DietInfo dietInfo,Date date){
        Date newDate = setDateForm(date);
        if(dietInfo!=null) {
            FoodRecord foodRecord = new FoodRecord();
            foodRecord.setAmount(dietInfo.amount);
            foodRecord.setDate(newDate);
            foodRecord.setName(dietInfo.food.getName());
            foodRecord.setType(dietInfo.pinnedHeaderName);
            foodRecordBaseDaoMethod.insertObject(foodRecord);
        }
    }
    public static void insertMultiRecords(List<DietInfo> dietInfos,Date date){
        if(dietInfos!=null){
            for(DietInfo e : dietInfos){
                insertRecord(e,date);
            }
        }
    }
    public List<Date> getRecordByMonth(){
        //QueryBuilder qb = .queryBuilder();
        Query query = DaoManager.getInstance().getDaoSession().getDao(FoodRecord.class).queryBuilder().where( new
                WhereCondition.StringCondition("_ID IN " + "(SELECT * FROM FOOD_RECORD WHERE column = )")).build();
        return null;

    }
    public static void deleteRecordByDay(Date date){
        Date newDate = setDateForm(date);
        QueryBuilder qb = DaoManager.getInstance().getDaoSession().getDao(FoodRecord.class).queryBuilder();
        qb.where(FoodRecordDao.Properties.Date.eq(newDate)).buildDelete().executeDeleteWithoutDetachingEntities();

    }
    public static void deleteRecord(DietInfo dietInfo,Date date){
        Date newDate = setDateForm(date);
        QueryBuilder qb = DaoManager.getInstance().getDaoSession().getDao(FoodRecord.class).queryBuilder();
        qb.where(FoodRecordDao.Properties.Date.eq(newDate)
                ,FoodRecordDao.Properties.Name.eq(dietInfo.food.getName())
                ,FoodRecordDao.Properties.Type.eq(dietInfo.pinnedHeaderName)
                ).buildDelete().executeDeleteWithoutDetachingEntities();
        if(getRecords(date)==null||getRecords(date).size()==0){
            deleteScore(newDate);
        }
    }
    public static List<DietInfo> getRecords(Date date){
        Date newDate = setDateForm(date);
        QueryBuilder qb = DaoManager.getInstance().getDaoSession().getDao(FoodRecord.class).queryBuilder();
        qb.where(FoodRecordDao.Properties.Date.eq(newDate));
        List<FoodRecord> records = qb.list();
        List<DietInfo> dietInfos = new ArrayList<>();
        if(records!=null){
            for(FoodRecord e : records){
                DietInfo dietInfo = new DietInfo(DietInfo.TYPE_DATA);
                dietInfo.pinnedHeaderName = e.getType();
                dietInfo.amount = e.getAmount();
                dietInfo.food = foodMethod.queryByName(e.getName());
                dietInfos.add(dietInfo);
            }
        }
        if(dietInfos.size() >=1) {
            return dietInfos;
        }
        else {
            return null;
        }
    }
    public static Date setDateForm(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(date);
        try {
            Date newDate = sdf.parse(s);
            return newDate;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static boolean deleteScoreIfRecordEmpty(Date displayDate){
        if(getScoreByDate(displayDate)!=0){
            deleteScore(displayDate);
            return true;
        }
        return false;
    }
    public static int getScoreByDate(Date day){
        QueryBuilder qb = DaoManager.getInstance().getDaoSession().getDao(DietScore.class).queryBuilder();
        qb.where(DietScoreDao.Properties.Date.eq(day));
        DietScore score = (DietScore)qb.unique();
        if (score!=null){
            return score.getScore();
        }
        return 0;
    }
    public static void insertScore(int score,Date date){
        Date newDate = setDateForm(date);
        DietScore dietScore = new DietScore();
        dietScore.setDate(newDate);
        dietScore.setScore(score);
        dietRecordBaseDaoMethod.insertObject(dietScore);
    }
    public static void deleteScore(Date date){
        Date newDate = setDateForm(date);
        QueryBuilder qb = DaoManager.getInstance().getDaoSession().getDao(DietScore.class).queryBuilder();
        qb.where(DietScoreDao.Properties.Date.eq(newDate)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public static List<DietScore> getRecordsByMonth(Date firstDayOfMonth){
        Date newFirstDay = setDateForm(firstDayOfMonth);
        Date lastDay = lastDayOfMonth(firstDayOfMonth);
        Date newLastDay = setDateForm(lastDay);
        QueryBuilder qb = DaoManager.getInstance().getDaoSession().getDao(DietScore.class).queryBuilder();
        qb.where(DietScoreDao.Properties.Date.ge(newFirstDay),DietScoreDao.Properties.Date.le(newLastDay));
        return qb.list();
    }

    public static int calScore(List<DietInfo> dietInfoList){
        return 10086;
    }

    public static Date lastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return cal.getTime();
    }
    private static List<Date> getDateList(List<DietScore> list){
        List<Date> dateList = new ArrayList<>();
        if(list!=null){
            for(DietScore e :list){
                dateList.add(e.getDate());
            }
        }
        return dateList;
    }


}
