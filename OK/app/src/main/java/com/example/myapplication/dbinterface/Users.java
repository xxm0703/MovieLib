package com.example.myapplication.dbinterface;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.myapplication.Config;
import com.example.myapplication.models.User;

public class Users extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "users";
    public static final String COL_2 = "email";
    public static final String COL_3 = "password";
    public static final String COL_4 = "name";


    public Users(Context context) {
        super(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users(" +
                "email TEXT PRIMARY KEY NOT NULL, " +
                "password TEXT NOT NULL, " +
                "name TEXT" +
                ");");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        onCreate(db);
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL(" DROP TABLE IF EXISTS users;");
            onCreate(db);
        }
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
            User new_user = new User(cursor.getString(0), cursor.getString(1));
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

        return cursor != null && cursor.moveToFirst() && cursor.getCount() > 0;

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
