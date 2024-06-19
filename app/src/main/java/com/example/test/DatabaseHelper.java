package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userDetails.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_DETAILS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_REGISTER = "registerno";
    private static final String KEY_DEPT = "dept";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_DETAILS + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT, " +
                    KEY_REGISTER + " TEXT, " +
                    KEY_DEPT + " TEXT); ";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILS);
        onCreate(db);
    }

    public int addDetails(String name, String registerno, String dept){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_REGISTER, registerno);
        values.put(KEY_DEPT, dept);
        db.insert(TABLE_DETAILS,null,values);
        db.close();
        return 1;
    }

    public void deleteDetails(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DETAILS,KEY_ID+ "= ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateDetails(int id, String name, String register, String dept){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(!name.isEmpty()){
            values.put(KEY_NAME, name);
        }
        if(!register.isEmpty()){
            values.put(KEY_REGISTER, register);
        }
        if(!dept.isEmpty()){
            values.put(KEY_DEPT, dept);
        }
        db.update(TABLE_DETAILS, values, KEY_ID + "= ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor viewDetails(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_DETAILS + " WHERE ID=?", new String[]{id});
        return res;
    }

    public Cursor viewAllDetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_DETAILS, null);
        return result;
    }
}
