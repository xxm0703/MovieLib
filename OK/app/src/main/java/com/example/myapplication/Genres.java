package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Genres extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movie_lib.db";
    public static final String TABLE_NAME = "genres";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "name";


    public Genres(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, name Text)", TABLE_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addGenre(Genre genre) {
        if (exists(genre))
            return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, genre.getName());
        db.insert(TABLE_NAME, null, values);
    }

    private boolean exists(Genre genre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_1, COL_2},
                COL_2 + "=?",
                new String[]{genre.getName()},
                null, null, null);

        return cursor != null && cursor.moveToFirst()&& cursor.getCount() > 0;
    }

}
