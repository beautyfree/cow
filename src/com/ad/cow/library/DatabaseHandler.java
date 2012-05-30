package com.ad.cow.library;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "android_api";
 
    // Tables name
    private static final String TABLE_LOGIN = "login";
    private static final String TABLE_DATA  = "data";
 
    // Tables Columns names
    private static final String KEY_LOGIN_ID = "id";
    private static final String KEY_LOGIN_NAME = "name";
    private static final String KEY_LOGIN_EMAIL = "email";
    private static final String KEY_LOGIN_UID = "uid";
    private static final String KEY_LOGIN_CREATED_AT = "created_at";
    
    private static final String KEY_DATA_ID = "id";
    private static final String KEY_DATA_LEVEL = "level";
    private static final String KEY_DATA_PERCENT = "percent";
    private static final String KEY_DATA_FEEDTIME = "feed_time";
    private static final String KEY_DATA_EXPTIME = "exp_time";
    private static final String KEY_DATA_EXP = "exp";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_LOGIN_ID + " INTEGER PRIMARY KEY,"
                + KEY_LOGIN_NAME + " TEXT,"
                + KEY_LOGIN_EMAIL + " TEXT UNIQUE,"
                + KEY_LOGIN_UID + " TEXT,"
                + KEY_LOGIN_CREATED_AT + " TEXT" + ")");
        
        db.execSQL("CREATE TABLE " + TABLE_DATA + "("
                + KEY_DATA_ID + " INTEGER PRIMARY KEY,"
                + KEY_DATA_LEVEL + " INTEGER,"
                + KEY_DATA_PERCENT + " REAL,"
                + KEY_DATA_FEEDTIME + " INTEGER,"
                + KEY_DATA_EXPTIME + " INTEGER,"
                + KEY_DATA_EXP + " REAL" + ")");
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
    	
        // Create tables again
        onCreate(db);
    }
 
    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_LOGIN_NAME, name); // Name
        values.put(KEY_LOGIN_EMAIL, email); // Email
        values.put(KEY_LOGIN_UID, uid); // UID
        values.put(KEY_LOGIN_CREATED_AT, created_at); // Created At
 
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
        
        addData();
    }
    
    private void addData() {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_DATA_LEVEL, 0); // Level
        values.put(KEY_DATA_PERCENT, 0.0); // Percent
        values.put(KEY_DATA_FEEDTIME, new Date().getTime()); // Feed time
        values.put(KEY_DATA_EXPTIME, new Date().getTime()); // Exp time 
        values.put(KEY_DATA_EXP, 0.0); // Exp
 
        // Inserting Row
        db.insert(TABLE_DATA, null, values);
        db.close(); // Closing database connection
    }
    
    public void saveUserData(HashMap<String, String> data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for(Entry<String, String> entry : data.entrySet()) {
            values.put(entry.getKey(), entry.getValue());
        }
        
        db.update(TABLE_DATA, values, null, null);
        db.close(); // Closing database connection
    }
 
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put(KEY_LOGIN_NAME, cursor.getString(1));
            user.put(KEY_LOGIN_EMAIL, cursor.getString(2));
            user.put(KEY_LOGIN_UID, cursor.getString(3));
            user.put(KEY_LOGIN_CREATED_AT, cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
    
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserData(){
        HashMap<String,String> data = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            data.put(KEY_DATA_LEVEL, cursor.getString(1)); 
            data.put(KEY_DATA_PERCENT, cursor.getString(2));
            data.put(KEY_DATA_FEEDTIME, cursor.getString(3));
            data.put(KEY_DATA_EXPTIME, cursor.getString(4)); 
            data.put(KEY_DATA_EXP, cursor.getString(5)); 
        }
        cursor.close();
        db.close();
        // return user
        return data;
    }
 
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
 
        // return row count
        return rowCount;
    }
 
    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGIN, null, null);
        db.delete(TABLE_DATA, null, null);
        db.close();
    }
 
}
