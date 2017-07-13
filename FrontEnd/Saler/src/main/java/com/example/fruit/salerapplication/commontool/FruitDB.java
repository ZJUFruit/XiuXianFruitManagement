package com.example.fruit.salerapplication.commontool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.fruit.salerapplication.bean.FruitPreferenceBean;
import com.example.fruit.salerapplication.bean.FruitStorageBean;
import com.example.fruit.salerapplication.bean.SystemSettingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luxuhui on 2017/7/12.
 */

public class FruitDB {
    FruitPreferenceHelper fruitPreferenceHelper;
    FruitStorageHelper fruitStorageHelper;
    SystemSettingHelper systemSettingHelper;
    Context context;


    public FruitDB(Context context) {
        this.context = context;
        fruitPreferenceHelper = new FruitPreferenceHelper(context);
        fruitStorageHelper = new FruitStorageHelper(context);
        systemSettingHelper = new SystemSettingHelper(context);
    }

    public boolean createPreferenceTable() {
        SQLiteDatabase db = fruitPreferenceHelper.getWritableDatabase();

        fruitPreferenceHelper.onCreate(db);
        db.close();

        return true;
    }

    public boolean createStorageTable() {
        SQLiteDatabase db = fruitStorageHelper.getWritableDatabase();

        fruitStorageHelper.onCreate(db);
        db.close();

        return true;
    }


    public boolean createSysytemSettingTable() {
        SQLiteDatabase db = systemSettingHelper.getWritableDatabase();

        systemSettingHelper.onCreate(db);
        db.close();

        return true;
    }

    public boolean insertPerference(int typeid, int minnum, int maxnum, float minprice, float maxprice, int salerid, int autoorder) {
        SQLiteDatabase db = null;

        try {
            db = fruitPreferenceHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("typeid", typeid);
            contentValues.put("currentnum", 0);
            contentValues.put("minnum", minnum);
            contentValues.put("maxnum", maxnum);
            contentValues.put("minprice", minprice);
            contentValues.put("maxprice", maxprice);
            contentValues.put("salerid", salerid);
            contentValues.put("autoorder", autoorder);

            db.insertOrThrow(FruitPreferenceHelper.getTableName(), null, contentValues);

            db.setTransactionSuccessful();

            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "已存在该水果设置", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "发生数据库错误：插入水果设置失败", Toast.LENGTH_SHORT).show();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }

        }
        return false;
    }

    public boolean insertStorage(int fsid, int typeid, int salerid, int orderid, String date) {
        SQLiteDatabase db = null;

        try {
            db = fruitStorageHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("fsid", fsid);
            contentValues.put("typeid", typeid);
            contentValues.put("salerid", salerid);
            contentValues.put("orderid", orderid);
            contentValues.put("date", date);

            db.insertOrThrow(FruitStorageHelper.getTableName(), null, contentValues);

            db.setTransactionSuccessful();

            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "已存在该水果库存", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "发生数据库错误：插入水果库存失败", Toast.LENGTH_SHORT).show();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }

        }
        return false;
    }

    public boolean insertSetting(String key, String value){
        SQLiteDatabase db = null;

        try {
            db = fruitStorageHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("key", key);
            contentValues.put("value", value);

            db.insertOrThrow(SystemSettingHelper.getTableName(), null, contentValues);

            db.setTransactionSuccessful();

            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "已存在该用户配置", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "发生数据库错误：插入配置项失败", Toast.LENGTH_SHORT).show();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }

        }
        return false;

    }

    public boolean deletePerference(int typeid) {
        SQLiteDatabase db = null;

        try {
            db = fruitPreferenceHelper.getWritableDatabase();
            db.beginTransaction();

            String[] typeID = {String.valueOf(typeid)};
            db.delete(FruitPreferenceHelper.getTableName(), "typeid = ?", typeID);
            return true;
        } catch (Exception e) {
            Toast.makeText(context, "发生未知数据库错误", Toast.LENGTH_SHORT).show();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    public boolean deleteStorage(int fsid) {
        SQLiteDatabase db = null;

        try {
            db = fruitStorageHelper.getWritableDatabase();
            db.beginTransaction();

            String[] fsID = {String.valueOf(fsid)};
            db.delete(FruitStorageHelper.getTableName(), "fsid = ?", fsID);
            return true;
        } catch (Exception e) {
            Toast.makeText(context, "发生未知数据库错误：删除库存失败", Toast.LENGTH_SHORT).show();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }


    public boolean deleteSetting(String key) {
        SQLiteDatabase db = null;

        try {
            db = systemSettingHelper.getWritableDatabase();
            db.beginTransaction();

            String[] keyArgs = {key};

            db.delete(SystemSettingHelper.getTableName(), "key = ?", keyArgs);

        } catch (Exception e) {
            Toast.makeText(context, "发生未知数据库错误: 删除配置项失败", Toast.LENGTH_SHORT).show();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;

    }

    public List<FruitPreferenceBean> queryPerference() {
        SQLiteDatabase db = null;

        db = fruitPreferenceHelper.getReadableDatabase();

        List<FruitPreferenceBean> result = new ArrayList<>();
        Cursor cursor = db.query(FruitPreferenceHelper.getTableName(), null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            FruitPreferenceBean bean = new FruitPreferenceBean(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getFloat(3), cursor.getFloat(4), cursor.getInt(5), cursor.getInt(6));
            result.add(bean);
        }
        cursor.close();
        db.close();

        return result;

    }

    public List<FruitStorageBean> queryStorage() {
        SQLiteDatabase db = null;

        db = fruitStorageHelper.getReadableDatabase();

        List<FruitStorageBean> result = new ArrayList<>();
        Cursor cursor = db.query(FruitStorageHelper.getTableName(), null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            FruitStorageBean bean = new FruitStorageBean(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4));
            result.add(bean);
        }
        cursor.close();
        db.close();

        return result;

    }

    public List<SystemSettingBean> querySetting() {
        SQLiteDatabase db = null;

        db = systemSettingHelper.getReadableDatabase();

        List<SystemSettingBean> result = new ArrayList<>();
        Cursor cursor = db.query(SystemSettingHelper.getTableName(), null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            SystemSettingBean bean = new SystemSettingBean(cursor.getString(0), cursor.getString(1));
            result.add(bean);
        }
        cursor.close();
        db.close();

        return result;

    }
}
