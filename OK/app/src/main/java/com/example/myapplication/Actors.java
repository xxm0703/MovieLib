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
public class Actors extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie_lib.db";

    public Actors(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE actors(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name TEXT NOT NULL," +
                "age INTEGER NOT NULL" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS actors");
        onCreate(db);
    }

    public boolean add(Actor actor) {
        if (!exists(actor)) {
            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement("INSERT INTO actor(name, age) " +
                    "VALUES(?, ?)");
            int id;

            stmt.bindString(1, actor.getName());
            stmt.bindLong(2, actor.getAge());
            id = (int) stmt.executeInsert();
            if (id != -1) {
                actor.setId(id);
                return true;
            }
        }
        return false;
    }

    public boolean update(Actor actor) {
        if (!exists(actor)) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("UPDATE actors " +
                "SET name = ?, age = ? " +
                "WHERE ID = ?");
        stmt.bindString(1, actor.getName());
        stmt.bindLong(2, actor.getAge());
        stmt.bindLong(3, actor.getId());
        return stmt.executeUpdateDelete() != 0;
    }

    public boolean delete(Actor actor) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM actors " +
                "WHERE ID = ?");

        stmt.bindLong(1, actor.getId());
        return stmt.executeUpdateDelete() > 0;
    }

    public Actor findById(int id) {
        String[] cols = new String[]{ String.valueOf(id) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM actors " +
                "WHERE ID = ?", cols);
        Actor actor = null;

        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                String name = mCursor.getString(1);
                int age = mCursor.getInt(2);
                actor = new Actor(id, name, age);
            }
            mCursor.close();
        }
        return actor;
    }

    public Actor findByName(String name) {
        String[] cols = new String[]{ name };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM actors " +
                "WHERE name = ?", cols);
        Actor actor = null;

        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                int id = mCursor.getInt(0);
                int age = mCursor.getInt(2);
                actor = new Actor(id, name, age);
            }
            mCursor.close();
        }
        return actor;
    }

    public List<Actor> extractActors() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM actors", null);
        List<Actor> actors = null;

        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                actors = new ArrayList<>(mCursor.getCount());
                do {
                    int id = mCursor.getInt(0);
                    String name = mCursor.getString(1);
                    int age = mCursor.getInt(2);
                    actors.add(new Actor(id, name, age));
                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }
        return actors;
    }

    private boolean exists(Actor actor) {
        return findById(actor.getId()) != null || findByName(actor.getName()) != null;
    }
}
