package com.example.dario.project3;

/**
 * Created by dario on 5/9/17.
 */

public class Book {
    private long id;
    private String userRenting;
    private String bookTitle;
    private String author;
    private String ISBN;
    private double feePerHour;

    public Book(){};

    public Book(long id, String userRenting, String bookTitle, String author, String ISBN, double feePerHour)
    {
        this.id = id;
        this.userRenting = userRenting;
        this.bookTitle = bookTitle;
        this.author = author;
        this.ISBN = ISBN;
        this.feePerHour = feePerHour;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserRenting() {
        return userRenting;
    }

    public void setUserRenting(String userRenting) {
        this.userRenting = userRenting;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public double getFeePerHour() {
        return feePerHour;
    }

    public void setFeePerHour(double feePerHour) {
        this.feePerHour = feePerHour;
    }
}
