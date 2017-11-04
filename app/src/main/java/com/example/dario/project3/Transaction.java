package com.example.dario.project3;

/**
 * Created by dario on 5/9/17.
 */



public class Transaction {

    private long id;
    private String userName;
    private String pickUpDate;
    private String returnDate;
    private String bookTitle;
    private long reservationID;
    private double total;
    private String type;
    private String currentDate;

    public Transaction() {}

    public Transaction(long id, String username, String pickUpDate, String type)
    {
        this.id = id;
        this.userName = username;
        this.pickUpDate = pickUpDate;
        this.type = type;

        this.returnDate = "";
        this.total = 0.0;
        this.bookTitle = "";
        this.reservationID =0;
        this.currentDate = "";
    }

    public Transaction(long id, String userName, String pickUpDate,
                       String returnDate, String bookTitle, double total, String type, long reservationID, String currentDate)
    {
        this.id = id;
        this.userName = userName;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
        this.bookTitle = bookTitle;
        this.total = total;
        this.type = type;
        this.reservationID = reservationID;
        this.currentDate = currentDate;
    }

    public void setCurrentTime(String time) { this.currentDate = time;}

    public String getCurrentDate() {return this.currentDate;}

    public long getReservationID() {return reservationID;}

    public void setReservationID(long id) {this.reservationID = id;}

    public String getType(){return type;}

    public void setType(String type) { this.type = type;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
