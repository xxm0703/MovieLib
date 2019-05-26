package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class Actors extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movie_lib.db";
    public static final String TABLE_NAME = "actors";

    public Actors(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, name Text, age Integer)", TABLE_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addActor(Actor actor) {
        if (exists(actor))
            return;

        SQLiteDatabase db = this.getWritableDatabase();

        SQLiteStatement stmt = db.compileStatement("INSERT INTO actor(name, age) VALUES(?, ?)");
        stmt.bindString(0, actor.getName());
        stmt.bindLong(1, actor.getAge());
        stmt.execute();
    }

    public Actor findByName(String name) {
        String[] cols = new String[]{name};
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM actors WHERE name = ?", cols);

        Actor actor = null;

        if (mCursor != null){
            if (mCursor.moveToFirst()) {
                int id = mCursor.getInt(0);
                int age = mCursor.getInt(2);
                actor = new Actor(id, name, age);
            }
            mCursor.close();
        }

        return actor;
    }

    public boolean updateAge(Actor actor) {
        if (!exists(actor)){
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        SQLiteStatement stmt = db.compileStatement("UPDATE actors SET age=? WHERE ID=?");
        stmt.bindLong(0, actor.getAge() + 1);
        stmt.bindLong(1, actor.getId());
        return stmt.executeUpdateDelete() != 0;
    }

    public List<Actor> extractActors() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM actors", null);

        ArrayList<Actor> actors = null;
        if (mCursor != null){
            if (mCursor.moveToFirst()) {
                actors = new ArrayList<>();
                do {
                    int column1 = mCursor.getInt(0);
                    String column2 = mCursor.getString(1);
                    int column3 = mCursor.getInt(2);
                    actors.add(new Actor(column1, column2, column3));
                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }

        return actors;
    }

    private boolean exists(Actor actor) {
        return findByName(actor.getName()) != null;
    }

}
