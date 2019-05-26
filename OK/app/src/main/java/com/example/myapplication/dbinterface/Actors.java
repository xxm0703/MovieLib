package com.example.myapplication.dbinterface;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import com.example.myapplication.Config;
import com.example.myapplication.models.Actor;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Actors extends SQLiteOpenHelper {

    public Actors(Context context) {
        super(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS actors(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name TEXT UNIQUE NOT NULL," +
                "age INTEGER NOT NULL" +
                ");");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL(" DROP TABLE IF EXISTS actors;");
            onCreate(db);
        }
    }

    public boolean add(Actor actor) {
        if (!exists(actor)) {
            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement("INSERT INTO actors(name, age) " +
                    "VALUES(?, ?);");
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
                "WHERE ID = ?;");
        stmt.bindString(1, actor.getName());
        stmt.bindLong(2, actor.getAge());
        stmt.bindLong(3, actor.getId());
        return stmt.executeUpdateDelete() != 0;
    }

    public boolean delete(Actor actor) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM actors " +
                "WHERE ID = ?;");

        stmt.bindLong(1, actor.getId());
        return stmt.executeUpdateDelete() > 0;
    }

    public Actor findById(int id) {
        String[] cols = new String[]{ String.valueOf(id) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM actors " +
                "WHERE ID = ?;", cols);
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
                "WHERE name = ?;", cols);
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
        Cursor mCursor = db.rawQuery("SELECT * FROM actors;", null);
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
