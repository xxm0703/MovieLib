package com.example.myapplication.dbinterface;

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

    public boolean addUser(User user) {
        if(!isEmailExists(user)) {
            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement("INSERT INTO users(email, password, name) " + "VALUES(?, ?, ?);");
            stmt.bindString(1, user.getEmail());
            stmt.bindString(2, user.getPassword());
            stmt.bindString(3, user.getName());
            return stmt.executeInsert() != -1;
        }
        return false;
    }

    public boolean isEmailExists(User user) {
        return findByEmail(user.getEmail()) != null;

    }

    public User findByEmail(String email) {
        String[] cols = new String[]{ email };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM users " + "WHERE email = ?;", cols);
        User user = null;
        if(mCursor != null) {
            if(mCursor.moveToFirst()) {
                String user_email = mCursor.getString(0);
                String pass = mCursor.getString(1);
                String name = mCursor.getString(2);
                user = new User(user_email, pass, name);
            }
            mCursor.close();
        }
        return user;
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
