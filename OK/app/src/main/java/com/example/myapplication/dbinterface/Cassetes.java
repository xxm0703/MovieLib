package com.example.myapplication.dbinterface;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.myapplication.Config;
import com.example.myapplication.models.Cassete;
import com.example.myapplication.models.Movie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cassetes extends SQLiteOpenHelper {

    public Cassetes(Context context) {
        super(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS cassetes(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "movie_id INTEGER NOT NULL," +
                "borrow_date DATE," +
                "return_date DATE," +
                "borrower_id INTEGER," +
                "FOREIGN KEY (movie_id) REFERENCES movies(ID)," +
                "FOREIGN KEY (borrower_id) REFERENCES users(ID)" +
                ");");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        onCreate(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS cassetes;");
            onCreate(db);
        }
    }

    public boolean add(Cassete cassete) {
        if (!exists(cassete)) {
            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement(
                    "INSERT INTO cassetes(movie_id, borrow_date, return_date, borrower_id) " +
                            "VALUES(?, ?, ?, ?);");
            int id;

            stmt.bindLong(1, cassete.getMovieId());
            if (cassete.getBorrowDate() == null) {
                stmt.bindNull(2);
            } else {
                stmt.bindString(2, cassete.getBorrowDate().toString());
            }
            if (cassete.getReturnDate() == null) {
                stmt.bindNull(3);
            } else {
                stmt.bindString(3, cassete.getReturnDate().toString());
            }
            if (cassete.getBorrowerId() == null) {
                stmt.bindNull(4);
            } else {
                stmt.bindLong(4, cassete.getBorrowerId());
            }
            id = (int) stmt.executeInsert();
            if (id != -1) {
                cassete.setId(id);
                return true;
            }
        }
        return false;
    }

    public boolean update(Cassete cassete) {
        if (!exists(cassete)) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("UPDATE cassetes " +
                "SET movie_id = ?, borrow_date = ?, return_date = ?, borrower_id = ? " +
                "WHERE ID = ?;");

        stmt.bindLong(1, cassete.getMovieId());
        if (cassete.getBorrowDate() == null) {
            stmt.bindNull(2);
        } else {
            stmt.bindString(2, cassete.getBorrowDate().toString());
        }
        if (cassete.getReturnDate() == null) {
            stmt.bindNull(3);
        } else {
            stmt.bindString(3, cassete.getReturnDate().toString());
        }
        if (cassete.getBorrowerId() == null) {
            stmt.bindNull(4);
        } else {
            stmt.bindLong(4, cassete.getBorrowerId());
        }
        stmt.bindLong(5, cassete.getId());
        return stmt.executeUpdateDelete() > 0;
    }

    public boolean delete(Cassete cassete) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM cassetes " +
                "WHERE ID = ?;");

        stmt.bindLong(1, cassete.getId());
        return stmt.executeUpdateDelete() > 0;
    }

    public Cassete findById(int id) {
        String[] cols = new String[]{ String.valueOf(id) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cassetes " +
                "WHERE ID = ?;", cols);
        Cassete cassete = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int movieId = cursor.getInt(2);
                Date borrowDate = cursor.isNull(3) ? null : new Date(cursor.getString(3));
                Date returnDate = cursor.isNull(4) ? null : new Date(cursor.getString(4));
                Integer borrowerId = cursor.isNull(5) ? null : cursor.getInt(5);

                cassete = new Cassete(id, movieId, borrowDate, returnDate, borrowerId);
            }
            cursor.close();
        }
        return cassete;
    }

    public List<Cassete> findAllByMovieId(int movieId) {
        String[] cols = new String[]{ String.valueOf(movieId) };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cassetes " +
                "WHERE movie_id = ?;", cols);
        List<Cassete> cassetes = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                cassetes = new ArrayList<>(cursor.getCount());
                do {
                    int id = cursor.getInt(1);
                    Date borrowDate = cursor.isNull(3) ? null : new Date(cursor.getString(3));
                    Date returnDate = cursor.isNull(4) ? null : new Date(cursor.getString(4));
                    Integer borrowerId = cursor.isNull(5) ? null : cursor.getInt(5);

                    cassetes.add(new Cassete(id, movieId, borrowDate, returnDate, borrowerId));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return cassetes;
    }

    public List<Cassete> findAllByMovie(Movie movie) {
        return findAllByMovieId(movie.getId());
    }

    public List<Cassete> extractCassetes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cassetes;", null);
        List<Cassete> cassetes = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                cassetes = new ArrayList<>(cursor.getCount());
                do {
                    int id = cursor.getInt(1);
                    int movieId = cursor.getInt(2);
                    Date borrowDate = cursor.isNull(3) ? null : new Date(cursor.getString(3));
                    Date returnDate = cursor.isNull(4) ? null : new Date(cursor.getString(4));
                    Integer borrowerId = cursor.isNull(5) ? null : cursor.getInt(5);

                    cassetes.add(new Cassete(id, movieId, borrowDate, returnDate, borrowerId));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return cassetes;
    }

    private boolean exists(Cassete cassete) {
        return findById(cassete.getId()) != null;
    }
}
