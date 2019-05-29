package com.example.myapplication.dbinterface;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import com.example.myapplication.Config;
import com.example.myapplication.models.Genre;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Genres extends SQLiteOpenHelper {

    public Genres(Context context) {
        super(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS genres(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name TEXT UNIQUE NOT NULL" +
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
            db.execSQL(" DROP TABLE IF EXISTS genres;");
            onCreate(db);
        }
    }

    public boolean add(Genre genre) {
        if (!exists(genre)) {
            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement("INSERT INTO genres(name) " +
                    "VALUES(?);");
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
                "WHERE ID = ?;");

        stmt.bindLong(1, genre.getId());
        return stmt.executeUpdateDelete() > 0;
    }

    public Genre findById(int id) {
        String[] cols = new String[]{ String.valueOf(id) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM genres " +
                "WHERE ID = ?;", cols);
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
                "WHERE name = ?;", cols);
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

    public ArrayList<String> extractGenresInformation() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM genres;", null);
        ArrayList<String> all_genres = null;

        if (mCursor != null){
            if (mCursor.moveToFirst()) {
                all_genres = new ArrayList<>();
                do {
                    String name = mCursor.getString(1);
                    all_genres.add(name);
                } while (mCursor.moveToNext());
            }

            mCursor.close();
        }
        return all_genres;
    }

    private boolean exists(Genre genre) {
        return findById(genre.getId()) != null || findByName(genre.getName()) != null;
    }

}