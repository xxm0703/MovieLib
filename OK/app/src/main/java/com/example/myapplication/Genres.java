package com.example.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Genres extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie_lib.db";

    public Genres(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE genres(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name TEXT UNIQUE NOT NULL" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS genres");
        onCreate(db);
    }

    public boolean add(Genre genre) {
        if (!exists(genre)) {
            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement("INSERT INTO genres(name) " +
                    "VALUES(?)");
            int id;

            stmt.bindString(1, genre.getName());
            id = (int) stmt.executeInsert();
            if (id != -1) {
                genre.setId(id);
                return true;
            }
        }
        return false;
    }

    public boolean delete(Genre genre) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM genres " +
                "WHERE ID = ?");

        stmt.bindLong(1, genre.getId());
        return stmt.executeUpdateDelete() > 0;
    }

    public Genre findById(int id) {
        String[] cols = new String[]{ String.valueOf(id) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM genres " +
                "WHERE ID = ?", cols);
        Genre genre = null;

        if (mCursor != null){
            if (mCursor.moveToFirst()) {
                String name = mCursor.getString(1);
                genre = new Genre(id, name);
            }
            mCursor.close();
        }
        return genre;
    }

    public Genre findByName(String name) {
        String[] cols = new String[]{ name };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM genres " +
                "WHERE name = ?", cols);
        Genre genre = null;

        if (mCursor != null){
            if (mCursor.moveToFirst()) {
                int id = mCursor.getInt(0);
                genre = new Genre(id, name);
            }
            mCursor.close();
        }
        return genre;
    }

    public List<Genre> extractGenres() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM genres", null);
        List<Genre> genres = null;

        if (mCursor != null){
            if (mCursor.moveToFirst()) {
                genres = new ArrayList<>(mCursor.getCount());
                do {
                    int id = mCursor.getInt(0);
                    String name = mCursor.getString(1);
                    genres.add(new Genre(id, name));
                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }
        return genres;
    }
    private boolean exists(Genre genre) {
        return findById(genre.getId()) != null || findByName(genre.getName()) != null;
    }

}