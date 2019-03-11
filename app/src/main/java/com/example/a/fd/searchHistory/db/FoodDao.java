package com.example.a.fd.searchHistory.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.a.fd.model.Food;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FOOD".
*/
public class FoodDao extends AbstractDao<Food, Long> {

    public static final String TABLENAME = "FOOD";

    /**
     * Properties of entity Food.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Heat = new Property(1, int.class, "heat", false, "HEAT");
        public final static Property Protein = new Property(2, Double.class, "protein", false, "PROTEIN");
        public final static Property Fat = new Property(3, Double.class, "fat", false, "FAT");
        public final static Property Carbohydrate = new Property(4, Double.class, "carbohydrate", false, "CARBOHYDRATE");
        public final static Property ImageUrl = new Property(5, String.class, "imageUrl", false, "IMAGE_URL");
        public final static Property Evaluate = new Property(6, int.class, "evaluate", false, "EVALUATE");
        public final static Property Type = new Property(7, int.class, "type", false, "TYPE");
        public final static Property Name = new Property(8, String.class, "name", false, "NAME");
    }


    public FoodDao(DaoConfig config) {
        super(config);
    }
    
    public FoodDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FOOD\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"HEAT\" INTEGER NOT NULL ," + // 1: heat
                "\"PROTEIN\" REAL," + // 2: protein
                "\"FAT\" REAL," + // 3: fat
                "\"CARBOHYDRATE\" REAL," + // 4: carbohydrate
                "\"IMAGE_URL\" TEXT," + // 5: imageUrl
                "\"EVALUATE\" INTEGER NOT NULL ," + // 6: evaluate
                "\"TYPE\" INTEGER NOT NULL ," + // 7: type
                "\"NAME\" TEXT UNIQUE );"); // 8: name
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FOOD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Food entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getHeat());
 
        Double protein = entity.getProtein();
        if (protein != null) {
            stmt.bindDouble(3, protein);
        }
 
        Double fat = entity.getFat();
        if (fat != null) {
            stmt.bindDouble(4, fat);
        }
 
        Double carbohydrate = entity.getCarbohydrate();
        if (carbohydrate != null) {
            stmt.bindDouble(5, carbohydrate);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(6, imageUrl);
        }
        stmt.bindLong(7, entity.getEvaluate());
        stmt.bindLong(8, entity.getType());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(9, name);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Food entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getHeat());
 
        Double protein = entity.getProtein();
        if (protein != null) {
            stmt.bindDouble(3, protein);
        }
 
        Double fat = entity.getFat();
        if (fat != null) {
            stmt.bindDouble(4, fat);
        }
 
        Double carbohydrate = entity.getCarbohydrate();
        if (carbohydrate != null) {
            stmt.bindDouble(5, carbohydrate);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(6, imageUrl);
        }
        stmt.bindLong(7, entity.getEvaluate());
        stmt.bindLong(8, entity.getType());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(9, name);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Food readEntity(Cursor cursor, int offset) {
        Food entity = new Food( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // heat
            cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2), // protein
            cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3), // fat
            cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4), // carbohydrate
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // imageUrl
            cursor.getInt(offset + 6), // evaluate
            cursor.getInt(offset + 7), // type
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // name
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Food entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setHeat(cursor.getInt(offset + 1));
        entity.setProtein(cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2));
        entity.setFat(cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3));
        entity.setCarbohydrate(cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4));
        entity.setImageUrl(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setEvaluate(cursor.getInt(offset + 6));
        entity.setType(cursor.getInt(offset + 7));
        entity.setName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Food entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Food entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Food entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}