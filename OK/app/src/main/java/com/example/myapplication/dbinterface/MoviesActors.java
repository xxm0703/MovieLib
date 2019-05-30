package com.example.myapplication.dbinterface;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import com.example.myapplication.Config;
import com.example.myapplication.models.MoviesActorsEntry;
import com.example.myapplication.models.Actor;
import com.example.myapplication.models.Movie;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MoviesActors extends SQLiteOpenHelper {

    public MoviesActors(Context context) {
        super(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS movies_actors(" +
                "movie_id INTEGER NOT NULL," +
                "actor_id INTEGER NOT NULL," +
                "PRIMARY KEY (movie_id, actor_id)," +
                "FOREIGN KEY (movie_id) REFERENCES movies(ID)," +
                "FOREIGN KEY (actor_id) REFERENCES actors(ID)" +
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
            db.execSQL("DROP TABLE IF EXISTS movies_actors;");
            onCreate(db);
        }
    }

    public boolean add(MoviesActorsEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("INSERT INTO movies_actors " +
                "VALUES(?, ?);");
        int id;

        stmt.bindLong(1, entry.getMovieId());
        stmt.bindLong(2, entry.getActorId());
        id = (int) stmt.executeInsert();
        if (id != -1) {
            return true;
        }
        return false;
    }
}
