package com.example.myapplication.models;

import java.util.Date;

public class Cassete {

    private int id;

    private int movieId;

    private Date borrowDate;

    private Date returnDate;

    private Integer borrowerId;

    public Cassete(int id, int movieId, Date borrowDate, Date returnDate, Integer borrowerId) {
        this.id = id;
        this.movieId = movieId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.borrowerId = borrowerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Integer borrowerId) {
        this.borrowerId = borrowerId;
    }
}
