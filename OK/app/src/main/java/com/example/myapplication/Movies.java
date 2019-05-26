package com.example.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Movies extends SQLiteOpenHelper {

    public Movies(Context context) {
        super(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS movies(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name TEXT UNIQUE NOT NULL," +
                "release_date DATE NOT NULL" +
                ");");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS movies;");
            onCreate(db);
        }
    }

    public boolean add(Movie movie) {
        if (!exists(movie)) {
            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement("INSERT INTO movies(name, release_date) " +
                    "VALUES(?, ?);");
            int id;

            stmt.bindString(1, movie.getName());
            stmt.bindString(2, movie.getReleaseDate().toString());
            id = (int) stmt.executeInsert();
            if (id != -1) {
                movie.setId(id);
                return true;
            }
        }
        return false;
    }

    public boolean update(Movie movie) {
        if (!exists(movie)) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("UPDATE movies " +
                "SET name = ?, release_date = ? " +
                "WHERE ID = ?;");
        stmt.bindString(1, movie.getName());
        stmt.bindString(2, movie.getReleaseDate().toString());
        stmt.bindLong(3, movie.getId());
        return stmt.executeUpdateDelete() > 0;
    }

    public boolean delete(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM movies " +
                "WHERE ID = ?;");

        stmt.bindLong(1, movie.getId());
        return stmt.executeUpdateDelete() > 0;
    }

    public Movie findById(int id) {
        String[] cols = new String[]{ String.valueOf(id) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies " +
                "WHERE ID = ?", cols);
        Movie movie = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                Date release_date = new Date(cursor.getString(2));
                movie = new Movie(id, name, release_date);
            }
            cursor.close();
        }
        return movie;
    }

    public Movie findByName(String name) {
        String[] cols = new String[]{ name };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies " +
                "WHERE name = ?;", cols);
        Movie movie = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(0);
                Date release_date = new Date(cursor.getString(2));
                movie = new Movie(id, name, release_date);
            }
            cursor.close();
        }
        return movie;
    }

    public List<Movie> extractMovies() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies;", null);
        List<Movie> movies = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                movies = new ArrayList<>(cursor.getCount());
                do {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    Date release_date = new Date(cursor.getString(2));

                    movies.add(new Movie(id, name, release_date));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return movies;
    }

    private boolean exists(Movie movie) {
        return findById(movie.getId()) != null || findByName(movie.getName()) != null;
    }
}
