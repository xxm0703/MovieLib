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
import com.example.myapplication.models.Genre;
import com.example.myapplication.models.Movie;

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
        super.onOpen(db);
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

    public boolean update(Movie movie, String name, Date releaseDate) {
        if (!exists(movie)) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("UPDATE movies " +
                "SET name = ?, release_date = ? " +
                "WHERE ID = ?;");
        stmt.bindString(1, name);
        stmt.bindString(2, releaseDate.toString());
        stmt.bindLong(3, movie.getId());
        return stmt.executeUpdateDelete() > 0;
    }

    public boolean delete(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM movies " +
                "WHERE id = ?");

        stmt.bindLong(1, movie.getId());
        return stmt.executeUpdateDelete() > 0;

        /*SQLiteDatabase db = this.getWritableDatabase();

        // Delete all associations with performing actors
        SQLiteStatement deleteActorsAssociationsStmt = db.compileStatement("DELETE FROM movies_actors " +
                "WHERE movie_id = ?;");
        deleteActorsAssociationsStmt.bindLong(1, movie.getId());
        boolean deleteActorsAssociationsResult = deleteActorsAssociationsStmt.executeUpdateDelete() > 0;

        // Delete all associations with performing actors
       // SQLiteStatement deleteGenresAssociationsStmt = db.compileStatement("DELETE FROM movies_genres " +
        //        "WHERE movie_id = ?;");
        //deleteGenresAssociationsStmt.bindLong(1, movie.getId());
        //boolean deleteGenresAssociationsResult = deleteGenresAssociationsStmt.executeUpdateDelete() > 0;

        SQLiteStatement deleteMovieStmt = db.compileStatement("DELETE FROM movies " +
                "WHERE ID = ?;");
        deleteMovieStmt .bindLong(1, movie.getId());
        boolean deleteMovieResult = deleteMovieStmt.executeUpdateDelete() > 0;

        return deleteActorsAssociationsResult && deleteMovieResult;*/
    }

    public Movie findById(int id) {
        String[] cols = new String[]{ String.valueOf(id) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies " +
                "WHERE ID = ?;", cols);
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

    public List<String> getActors(Movie movie) {
        String[] cols = new String[]{ String.valueOf(movie.getId()) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT a.id, a.name, a.age FROM movies m " +
                "INNER JOIN movies_actors ma " +
                "ON m.ID = ma.movie_id " +
                "INNER JOIN actors a " +
                "ON a.ID = ma.actor_id " +
                "WHERE m.ID = ?;", cols);
        List<String> actors = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int actorId = cursor.getInt(1);
                    String actorName = cursor.getString(2);
                    int actorAge = cursor.getInt(3);

                    actors.add(actorName);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return actors;
    }

    public List<String> getGenres(Movie movie) {
        String[] cols = new String[] { String.valueOf(movie.getId()) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT g.ID, g.name FROM genres g " +
                "INNER JOIN movies_genres mg " +
                "ON g.ID = mg.genre_id " +
                "INNER JOIN movies m " +
                "ON m.ID = mg.movie_id " +
                "WHERE m.ID = ?;", cols);
        List<String> genres = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                genres = new ArrayList<>(cursor.getCount());
                do {
                    int genreId = cursor.getInt(0);
                    String genreName = cursor.getString(1);

                    genres.add(genreName);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return genres;
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

    public ArrayList<String> extractMovieInformation() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM movies;", null);
        ArrayList<String> arrayList = null;

        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                arrayList = new ArrayList<>();
                do {
                    String name = mCursor.getString(1);
                    String date = mCursor.getString(2);
                    String temp = name.concat(", ");
                    String information = temp.concat(date);
                    arrayList.add(information);
                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }
        return arrayList;
    }

    private boolean exists(Movie movie) {
        return findById(movie.getId()) != null || findByName(movie.getName()) != null;
    }
}
