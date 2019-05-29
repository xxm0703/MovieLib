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
import com.example.myapplication.models.Movie;
import com.example.myapplication.models.MoviesGenresEntry;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MoviesGenres extends SQLiteOpenHelper {


    public MoviesGenres(Context context) {
        super(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS movies_genres(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "movie_id INTEGER NOT NULL," +
                "genre_id INTEGER NOT NULL," +
                "PRIMARY KEY (movie_id, genre_id)," +
                "FOREIGN KEY (movie_id) REFERENCES movies(ID)," +
                "FOREIGN KEY (genre_id) REFERENCES genres(ID)" +
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
            db.execSQL("DROP TABLE IF EXISTS movies_genres;");
            onCreate(db);
        }
    }

    public boolean add(MoviesGenresEntry entry) {
        if (!exists(entry)) {
            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement("INSERT INTO movies_genres " +
                    "VALUES(?, ?);");
            int id;

            stmt.bindLong(1, entry.getMovieId());
            stmt.bindLong(2, entry.getGenreId());
            id = (int) stmt.executeInsert();
            if (id != -1) {
                entry.setId(id);
                return true;
            }
        }
        return false;
    }

    public boolean delete(MoviesGenresEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM movies_genres " +
                "WHERE ID = ?;");

        stmt.bindLong(1, entry.getId());
        return stmt.executeUpdateDelete() > 0;
    }

    public MoviesGenresEntry findById(int id) {
        String[] cols = new String[]{String.valueOf(id)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies_genres " +
                "WHERE ID = ?;", cols);
        MoviesGenresEntry entry = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int movieId = cursor.getInt(1);
                int genreId = cursor.getInt(2);

                entry = new MoviesGenresEntry(id, movieId, genreId);
            }
            cursor.close();
        }
        return entry;
    }

    public MoviesGenresEntry findByMovieIdAndGenreId(int movieId, int genreId) {
        String[] cols = new String[]{String.valueOf(movieId), String.valueOf(genreId)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies_genres " +
                "WHERE movie_id = ? AND genre_id = ?;", cols);
        MoviesGenresEntry entry = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(0);

                entry = new MoviesGenresEntry(id, movieId, genreId);
            }
            cursor.close();
        }
        return entry;
    }

    public MoviesGenresEntry findByMovieAndGenre(Movie movie, Genre genre) {
        return findByMovieIdAndGenreId(movie.getId(), genre.getId());
    }

    public List<MoviesGenresEntry> findAllByMovieId(int movieId) {
        String[] cols = new String[]{String.valueOf(movieId)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies_genres " +
                "WHERE movie_id = ?;", cols);
        List<MoviesGenresEntry> entries = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                entries = new ArrayList<>(cursor.getCount());
                do {
                    int id = cursor.getInt(0);
                    int genreId = cursor.getInt(2);

                    entries.add(new MoviesGenresEntry(id, movieId, genreId));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return entries;
    }

    public List<MoviesGenresEntry> findAllByMovie(Movie movie) {
        return findAllByMovieId(movie.getId());
    }

    public List<MoviesGenresEntry> findAllByGenreId(int genreId) {
        String[] cols = new String[]{String.valueOf(genreId)};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies_genres " +
                "WHERE genre_id = ?;", cols);
        List<MoviesGenresEntry> entries = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                entries = new ArrayList<>(cursor.getCount());
                do {
                    int id = cursor.getInt(0);
                    int movieId = cursor.getInt(1);

                    entries.add(new MoviesGenresEntry(id, movieId, genreId));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return entries;
    }

    public List<MoviesGenresEntry> findAllByGenre(Genre genre) {
        return findAllByGenreId(genre.getId());
    }

    private boolean exists(MoviesGenresEntry entry) {
        return findByMovieIdAndGenreId(entry.getMovieId(), entry.getGenreId()) != null;
    }
}
