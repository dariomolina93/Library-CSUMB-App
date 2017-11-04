package com.example.dario.project3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by dario on 5/9/17.
 */

public class Database {

    public static final String DATA_NAME = "Library Database";
    public static final int DB_VERSION = 1;

    //USER TABLE CONSTANTS
    public static final String USER_TABLE = "Users";
    public static final String ID = "id";
    public static final int USER_ID_COL = 0;
    public static final String USERNAME = "username";
    public static final int    USER_USERNAME_COL = 1;
    public static final String PASSWORD = "password";
    public static final int    USER_PASSWORD_COL = 2;

    //BOOK TABLE CONSTANTS
    public static final String BOOK_TABLE = "Books";
    public static final String BOOK_ID = "id";
    public static final int    BOOK_ID_COL = 0;
    public static final String USERRENTING = "userID";
    public static final int    BOOK_USERRENTING_COL = 1;
    public static final String AUTHOR = "author";
    public static final int    BOOK_AUTHOR_COL = 3;
    public static final String ISBN = "ISBN";
    public static final int    BOOK_ISBN_COL = 4;
    public static final String FEEPERHOUR = "feePerHour";
    public static final String BOOK_BOOKTITLE = "bookTitle";
    public static final int    BOOK_FEEPERHOUR_COL = 5;
    public static final int    BOOK_BOOKTITLE_COL = 2;

    //TRANSACTION TABLE CONSTANTS
    public static final String TRANSACTION_TABLE = "Transactions";
    public static final String TRANSACTION_ID = "id";
    public static final int    TRANSACTION_ID_COL = 0;
    public static final String PICKUPDATE = "pickUpDate";
    public static final int    TRANSACTION_PICKUPDATE_COL = 1;
    public static final String RETURNDATE = "returnDate";
    public static final int    TRANSACTION_RETURNDATE_COL = 2;
    public static final String BOOKTITLE = "bookTitle";
    public static final int    TRANSACTION_BOOKTITLE_COL = 3;
    public static final String TOTAL = "total";
    public static final int    TRANSACTION_TOTAL_COL = 4;
    public static final String TRANSACTION_USERNAME = "username";
    public static final int    TRANSACTION_USERNAME_COL = 5;
    public static final String TRANSACTION_TYPE = "type";
    public static final int    TRANSACTION_TYPE_COL = 6;
    public static final String TRANSACTION_RESERVATIONID = "reservationID";
    public static final int    TRANSACTION_RESERVATIONID_COL = 7;
    public static final String TRANSACTION_CURRENTTIME = "currentTime";
    public static final int    TRANSACTION_CURRENTTIME_COL = 8;

    //CREATE AND DROP TABLE STATEMENTS
    public static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USER_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USERNAME + " TEXT NOT NULL, "
            + PASSWORD + " TEXT NOT NULL);";

    public static final String CREATE_BOOK_TABLE =
            "CREATE TABLE " + BOOK_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USERRENTING + " TEXT, "
            + BOOK_BOOKTITLE + " TEXT NOT NULL, "
            + AUTHOR + " TEXT NOT NULL, "
            + ISBN + " TEXT NOT NULL, "
            + FEEPERHOUR + " REAL NOT NULL);";

    public static final String CREATE_TRANSACTION_TABLE =
            "CREATE TABLE " + TRANSACTION_TABLE + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PICKUPDATE + " TEXT, "
            + RETURNDATE + " TEXT, "
            + BOOKTITLE  + " TEXT , "
            + TOTAL + " REAL, "
            + TRANSACTION_USERNAME + " INTEGER NOT NULL,"
            + TRANSACTION_TYPE + " TEXT NOT NULL," +
                    TRANSACTION_RESERVATIONID + " INTEGER, " +
                    TRANSACTION_CURRENTTIME + " TEXT);";

    public static final String DROP_USER_TABLE =
            "DROP TABLE IF EXISTS " + USER_TABLE;

    public static final String DROP_BOOK_TABLE=
            "DROP TABLE IF EXISTS " + BOOK_TABLE;

    public static final String DROP_TRANSACTION_TABLE=
            "DROP TABLE IF EXISTS " + TRANSACTION_TABLE;

    private static class DBHelper extends SQLiteOpenHelper
    {
        public DBHelper(Context context, String name, CursorFactory factory, int version)
        {
            super(context, name, factory,version);
        }

        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_BOOK_TABLE);
            db.execSQL(CREATE_TRANSACTION_TABLE);

            db.execSQL("INSERT INTO Users(username,password) values('a@lice5','@csit100')," +
                    "('$brian7','123abc##'),('!chris12!','CHRIS12!!');");

            db.execSQL("INSERT INTO Books(bookTitle, author, ISBN, feePerHour) values " +
                    "('Hot Java','S. Narayanan', '123-ABC-101', 0.05)," +
                    "('Fun Java', 'Y.Byun', 'ABCDEF-09', 1.00)," +
                    "('Algorithm for Java', 'K.Alice','CDE-777-123', .25);");

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.d("Task list", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(Database.DROP_USER_TABLE);
            db.execSQL(Database.DROP_BOOK_TABLE);
            db.execSQL(Database.DROP_TRANSACTION_TABLE);
            onCreate(db);
        }

    }//end of DBHelper class

    //database and database helper objects
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public Database(Context context)
    {
        dbHelper = new DBHelper(context, DATA_NAME, null, DB_VERSION);
    }

    //private methods to read or write  into database
    private void openReadableDB() { db = dbHelper.getReadableDatabase();}

    private void openWritableDB() { db = dbHelper.getWritableDatabase();}

    private void closeCursor(Cursor cursor)
    {
        if (cursor != null)
            cursor.close();
    }


    public void closeDB()
    {
        if(db != null)
            db.close();
    }


    public long returnLastUserID()
    {
        openReadableDB();

        Cursor cursor = db.query(USER_TABLE,null,null,null,null,null,null);

        cursor.moveToLast();
        long id =  cursor.getInt(USER_ID_COL);

        closeCursor(cursor);

        closeDB();

        return id;
    }

    public long returnLastBookID()
    {
        openReadableDB();
        Cursor cursor = db.query(BOOK_TABLE,null,null,null,null,null,null);

        cursor.moveToLast();
        long id =  cursor.getInt(USER_ID_COL);

        closeCursor(cursor);

        closeDB();

        return id;
    }

    public void displayUserTable()
    {
        openReadableDB();
        Cursor cursor = db.query(USER_TABLE,null,null,null,null,null,null);

        Log.d(TAG, cursor.getColumnName(USER_ID_COL) + " " +
                cursor.getColumnName(USER_USERNAME_COL) + " " + cursor.getColumnName(USER_PASSWORD_COL));

        while(cursor.moveToNext())
        {
            Log.d(TAG, Integer.toString(cursor.getInt(USER_ID_COL)) + " " +
                    cursor.getString(USER_USERNAME_COL) + " " +
                    cursor.getString(USER_PASSWORD_COL));
        }

        closeCursor(cursor);
        closeDB();
        return;
    }

    public void displayTransactionTable()
    {
        openReadableDB();
        Cursor cursor = db.query(TRANSACTION_TABLE,null,null,null,null,null,null);

        Log.d(TAG, cursor.getColumnName(TRANSACTION_ID_COL) + " "+
                cursor.getColumnName(TRANSACTION_USERNAME_COL) + " " + cursor.getColumnName(TRANSACTION_TYPE_COL)
        + " " + cursor.getColumnName(TRANSACTION_PICKUPDATE_COL)+ " " + cursor.getColumnName(TRANSACTION_RETURNDATE_COL)
        + " " + cursor.getColumnName(TRANSACTION_BOOKTITLE_COL) + " " + cursor.getColumnName(TRANSACTION_TOTAL_COL) +
                cursor.getColumnName(TRANSACTION_RESERVATIONID_COL) + cursor.getColumnName(TRANSACTION_CURRENTTIME_COL));

        while(cursor.moveToNext())
        {
            Log.d(TAG, Integer.toString(cursor.getInt(TRANSACTION_ID_COL)) + " " + cursor.getString(TRANSACTION_USERNAME_COL)
                    + " " + cursor.getString(TRANSACTION_TYPE_COL) + " " + cursor.getString(TRANSACTION_PICKUPDATE_COL) + " "+
                    cursor.getString(TRANSACTION_RETURNDATE_COL) + " " + cursor.getString(TRANSACTION_BOOKTITLE_COL) + " " +
                    cursor.getString(TRANSACTION_TOTAL_COL) + cursor.getLong(TRANSACTION_RESERVATIONID_COL) + cursor.getString(TRANSACTION_CURRENTTIME_COL));
        }

        closeCursor(cursor);
        closeDB();
        return;
    }

    public long insertUser(User user)
    {
        ContentValues cv = new ContentValues();

        cv.put(ID, user.getId());
        cv.put(USERNAME, user.getUsername());
        cv.put(PASSWORD,user.getPassword());

        this.openWritableDB();

        long rowID = db.insert(USER_TABLE,null,cv);

        this.closeDB();

        return rowID;
    }

    public void insertBook(Book book)
    {
        ContentValues cv = new ContentValues();
        //long id, String userRenting, String bookTitle,
        // String author, String ISBN, double feePerHour)

        cv.put(BOOK_BOOKTITLE, book.getId());
        cv.put(USERRENTING, book.getUserRenting());
        cv.put(BOOKTITLE, book.getBookTitle());
        cv.put(AUTHOR, book.getAuthor());
        cv.put(ISBN,book.getISBN());
        cv.put(FEEPERHOUR, book.getFeePerHour());

        openWritableDB();

        db.insert(BOOK_TABLE,null,cv);

        closeDB();

        return;

    }

    public long returnLastTransactionID()
    {
        openReadableDB();

        Cursor cursor = db.query(TRANSACTION_TABLE,null,null,null,null,null,null);

        long id;
        Log.d("Inside function","returnLastTransactionID()");

        try {
            cursor.moveToLast();
            id = cursor.getLong(TRANSACTION_ID_COL);

            Log.d("Inside try:","id = " + Long.toString(id));

        }

        catch(Exception e)
        {

            Log.d("Inside catch:","id = 0");
            closeCursor(cursor);

            closeDB();
            return 0;
        }

        closeCursor(cursor);

        closeDB();

        return id;
    }

    public void insertTransaction(Transaction transaction)
    {
        openWritableDB();
        ContentValues cv = new ContentValues();

        cv.put(TRANSACTION_ID, transaction.getId());
        cv.put(TRANSACTION_USERNAME, transaction.getUserName());
        cv.put(TRANSACTION_TYPE, transaction.getType());
        cv.put(TOTAL, transaction.getTotal());
        cv.put(BOOKTITLE, transaction.getBookTitle());
        cv.put(PICKUPDATE, transaction.getPickUpDate());
        cv.put(RETURNDATE, transaction.getReturnDate());
        cv.put(TRANSACTION_RESERVATIONID, transaction.getReservationID());
        cv.put(TRANSACTION_CURRENTTIME, transaction.getCurrentDate());

        db.insert(TRANSACTION_TABLE,null,cv);

        closeDB();

        return;
    }

    //get user content from table

    public ArrayList<User> getUsers()
    {
        //list where user records will be stored in
        ArrayList<User> users = new ArrayList();

        //declare database is for reading(getting data)
        openReadableDB();

        Cursor cursor = db.query(USER_TABLE,null,null,null,null,null,null);

        while(cursor.moveToNext())
        {
            User user = new User();

            user.setId(cursor.getInt(USER_ID_COL));
            user.setPassword(cursor.getString(USER_PASSWORD_COL));
            user.setUsername(cursor.getString(USER_USERNAME_COL));

            users.add(user);
        }

        if(cursor != null)
            cursor.close();

        closeDB();

        return users;
    }

    public ArrayList<Book> getBooks()
    {
        ArrayList<Book> books = new ArrayList<>();

        openReadableDB();

        Cursor cursor = db.query(BOOK_TABLE,null,null,null,null,null,null);

        while(cursor.moveToNext())
        {
            Book book = new Book();

            book.setId(cursor.getLong(BOOK_ID_COL));
            book.setUserRenting(cursor.getString(BOOK_USERRENTING_COL));
            book.setBookTitle(cursor.getString(BOOK_BOOKTITLE_COL));
            book.setAuthor(cursor.getString(BOOK_AUTHOR_COL));
            book.setISBN(cursor.getString(BOOK_ISBN_COL));
            book.setFeePerHour(cursor.getDouble(BOOK_FEEPERHOUR_COL));

            books.add(book);

        }

        closeCursor(cursor);
        closeDB();

        return books;

    }

    public ArrayList<Transaction> getTransactions()
    {
        //list where user records will be stored in
        ArrayList<Transaction> transactions = new ArrayList();

        //declare database is for reading(getting data)
        openReadableDB();

        Cursor cursor = db.query(TRANSACTION_TABLE,null,null,null,null,null,null);

        if(cursor == null)
        {
            return null;
        }

        while(cursor.moveToNext())
        {
            Transaction transaction = new Transaction();

            transaction.setId(cursor.getInt(TRANSACTION_ID_COL));
            transaction.setBookTitle(cursor.getString(TRANSACTION_BOOKTITLE_COL));
            transaction.setPickUpDate(cursor.getString(TRANSACTION_PICKUPDATE_COL));
            transaction.setReturnDate(cursor.getString(TRANSACTION_RETURNDATE_COL));
            transaction.setTotal(cursor.getDouble(TRANSACTION_TOTAL_COL));
            transaction.setType(cursor.getString(TRANSACTION_TYPE_COL));
            transaction.setUserName(cursor.getString(TRANSACTION_USERNAME_COL));
            transaction.setReservationID(cursor.getInt(TRANSACTION_RESERVATIONID_COL));
            transactions.add(transaction);
        }

        if(cursor != null)
            cursor.close();

        closeDB();

        return transactions;

    }

    public void  deleteTransaction(String name)
    {
        String where = TRANSACTION_USERNAME + "=?";
        String[] whereArgs ={name};

        openWritableDB();

        db.delete(TRANSACTION_TABLE, where, whereArgs);
        closeDB();

        return;
    }

    public Transaction getBookFromTransaction(String name)
    {
        String where = BOOKTITLE + "=?";
        String[] whereArgs = {name};

        openReadableDB();

        Cursor cursor = db.query(TRANSACTION_TABLE,null, where, whereArgs, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }

        cursor.moveToFirst();

        /*
            public Transaction(long id, String userName, String pickUpDate,
                  String returnDate, String bookTitle, double total, String type, long reservationID, String currentDate)
         */

        Transaction transaction = new Transaction(cursor.getLong(TRANSACTION_ID_COL),cursor.getString(TRANSACTION_USERNAME_COL),
                cursor.getString(TRANSACTION_PICKUPDATE_COL),cursor.getString(TRANSACTION_RETURNDATE_COL),cursor.getString(TRANSACTION_BOOKTITLE_COL),
                cursor.getDouble(TRANSACTION_TOTAL_COL), cursor.getString(TRANSACTION_TYPE_COL), cursor.getLong(TRANSACTION_RESERVATIONID_COL),
                cursor.getString(TRANSACTION_CURRENTTIME_COL));

        closeCursor(cursor);
        closeDB();
        return transaction;
    }

    public User getUser(String name) {
        String where = USERNAME + "=?";
        String[] whereArgs = {name};

        openReadableDB();

        Cursor cursor = db.query(USER_TABLE, null, where, whereArgs, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }


        cursor.moveToFirst();

        User user = new User(cursor.getInt(USER_ID_COL), cursor.getString(USER_USERNAME_COL), cursor.getString(USER_PASSWORD_COL));


        closeCursor(cursor);

        this.closeDB();

        return user;
    }

    public Book getBook(String name)
    {
        String where = BOOK_BOOKTITLE + "=?";
        String[] whereArgs = {name};

        openReadableDB();

        Cursor cursor = db.query(BOOK_TABLE, null, where, whereArgs, null, null, null);
        cursor.moveToFirst();

        Book book = new Book(cursor.getInt(BOOK_ID_COL), cursor.getString(BOOK_USERRENTING_COL),
                cursor.getString(BOOK_BOOKTITLE_COL),cursor.getString(BOOK_AUTHOR_COL), cursor.getString(BOOK_ISBN_COL),
                cursor.getDouble(BOOK_FEEPERHOUR_COL));


        closeCursor(cursor);

        this.closeDB();

        return book;

    }




}
