package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class Genres extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movie_lib.db";
    public static final String TABLE_NAME = "genres";


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

        SQLiteStatement stmt = db.compileStatement("INSERT INTO genres(name) VALUES(?)");
        stmt.bindString(1, genre.getName());
        stmt.execute();
    }

    public void removeGenre(Genre genre) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM genres WHERE name = ?");
        stmt.bindString(1, genre.getName());
        stmt.execute();
    }

    public Genre findByName(String name) {
        String[] cols = new String[]{name};
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM genres WHERE name = ?", cols);

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

        ArrayList<Genre> genres = null;
        if (mCursor != null){
            if (mCursor.moveToFirst()) {
                genres = new ArrayList<>();
                do {
                    int column1 = mCursor.getInt(0);
                    String column2 = mCursor.getString(1);
                    genres.add(new Genre(column1, column2));
                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }

        return genres;
    }

    private boolean exists(Genre genre) {
        return findByName(genre.getName()) != null;
    }

}