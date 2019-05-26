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
public class MoviesActors extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie_lib.db";

    public MoviesActors(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE movies_actors(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "movie_id INTEGER NOT NULL," +
                "actor_id INTEGER NOT NULL," +
                "PRIMARY KEY (movie_id, actor_id)," +
                "FOREIGN KEY (movie_id) REFERENCES movies(ID)," +
                "FOREIGN KEY (actor_id) REFERENCES actors(ID)" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS movies_actors");
        onCreate(db);
    }

    public boolean add(MoviesActorsEntry entry) {
        if (!exists(entry)) {
            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement("INSERT INTO movies_actors " +
                    "VALUES(?, ?)");
            int id;

            stmt.bindLong(1, entry.getMovieId());
            stmt.bindLong(2, entry.getActorId());
            id = (int) stmt.executeInsert();
            if (id != -1) {
                entry.setId(id);
                return true;
            }
        }
        return false;
    }

    public boolean delete(MoviesActorsEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM movies_actors " +
                "WHERE ID = ?");

        stmt.bindLong(1, entry.getId());
        return stmt.executeUpdateDelete() > 0;
    }

    public MoviesActorsEntry findById(int id) {
        String[] cols = new String[]{ String.valueOf(id) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies_actors " +
                "WHERE ID = ?", cols);
        MoviesActorsEntry entry = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int movieId = cursor.getInt(1);
                int actorId = cursor.getInt(2);

                entry = new MoviesActorsEntry(id, movieId, actorId);
            }
            cursor.close();
        }
        return entry;
    }

    public MoviesActorsEntry findByMovieIdAndActorId(int movieId, int actorId) {
        String[] cols = new String[]{ String.valueOf(movieId), String.valueOf(actorId) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies_actors " +
                "WHERE movie_id = ? AND actor_id = ?", cols);
        MoviesActorsEntry entry = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(0);

                entry = new MoviesActorsEntry(id, movieId, actorId);
            }
            cursor.close();
        }
        return entry;
    }

    public MoviesActorsEntry findByMovieAndActor(Movie movie, Actor actor) {
        return findByMovieIdAndActorId(movie.getId(), actor.getId());
    }

    public List<MoviesActorsEntry> findAllByMovieId(int movieId) {
        String[] cols = new String[]{ String.valueOf(movieId) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies_actors " +
                "WHERE movie_id = ?", cols);
        List<MoviesActorsEntry> entries = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                entries = new ArrayList<>(cursor.getCount());
                do {
                    int id = cursor.getInt(0);
                    int actorId = cursor.getInt(2);

                    entries.add(new MoviesActorsEntry(id, movieId, actorId));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return entries;
    }

    public List<MoviesActorsEntry> findAllByMovie(Movie movie) {
        return findAllByMovieId(movie.getId());
    }

    public List<MoviesActorsEntry> findAllByActorId(int actorId) {
        String[] cols = new String[]{ String.valueOf(actorId) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies_actors " +
                "WHERE actor_id = ?", cols);
        List<MoviesActorsEntry> entries = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                entries = new ArrayList<>(cursor.getCount());
                do {
                    int id = cursor.getInt(0);
                    int movieId = cursor.getInt(1);

                    entries.add(new MoviesActorsEntry(id, movieId, actorId));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return entries;
    }

    public List<MoviesActorsEntry> findAllByActor(Actor actor) {
        return findAllByActorId(actor.getId());
    }

    private boolean exists(MoviesActorsEntry entry) {
        return findByMovieIdAndActorId(entry.getMovieId(), entry.getActorId()) != null;
    }
}
