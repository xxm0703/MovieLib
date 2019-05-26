package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class Users extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movie_lib.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_2 = "email";
    public static final String COL_3 = "password";
    public static final String COL_4 = "name";


    public Users(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (email Text PRIMARY, password Text, name Text)", TABLE_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, user.email);
        values.put(COL_3, user.password);
        values.put(COL_4, user.name);
        db.insert(TABLE_NAME, null, values);
    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_2, COL_3, COL_4},
                COL_2 + "=?",
                new String[]{user.email},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            User new_user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            if (user.password.equalsIgnoreCase(new_user.password)) {
                return new_user;
            }
        }
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_2, COL_3, COL_4},
                COL_2 + "=?",
                new String[]{email},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            return true;
        }
        return false;

    }

    public boolean update_information(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("UPDATE users SET password=? WHERE email=?");
        stmt.bindString(1, password);
        stmt.bindString(2, email);
        return stmt.executeUpdateDelete() != 0;
    }

    public boolean delete_user(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM users WHERE email=?" );
        stmt.bindString(1, email);
        return stmt.executeUpdateDelete() != 0;
    }

}
