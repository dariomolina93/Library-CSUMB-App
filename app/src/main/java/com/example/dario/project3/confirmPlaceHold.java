package com.example.dario.project3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by dario on 5/11/17.
 */

public class confirmPlaceHold extends Activity {

    private String pickUpTime, returnTime;
    private String userName;
    private String bookTitle;
    private double fee;
    private boolean reserveBook = false;
    private long reservationID;

    private TextView textView;
    private Button yes, no;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation);

        Bundle extras = getIntent().getExtras();

        if(extras != null)
        {
            userName = extras.getString("userName");
            pickUpTime = extras.getString("pickUpTime");
            returnTime = extras.getString("returnTime");
            fee = extras.getDouble("fee");
            reserveBook = extras.getBoolean("reserveBook");
            reservationID = extras.getLong("reservationID");
            bookTitle = extras.getString("bookTitle");
        }


        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
        textView = (TextView) findViewById(R.id.confirm);

        textView.setText("UserName:" + userName + "\n" + "Pick up Date: " + pickUpTime + "\n"
                + "Return Date: " + returnTime + "\n" + "Book Title: " + bookTitle + "\n" + "Reservation Number: "
                + reservationID + "\n" + "Total: " + fee + "\n Is this correct?");

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                  public Transaction(long id, String userName, String pickUpDate,
                       String returnDate, String bookTitle, double total, String type)
    {
                 */

                Database database = new Database(getApplicationContext());

                long id = database.returnLastTransactionID();
                id++;

                String date = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());
                Transaction transaction = new Transaction(id,userName, pickUpTime,returnTime,
                        bookTitle, fee,"place hold", reservationID, date);

                database.insertTransaction(transaction);

                Intent menu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(menu);
                return;


            }
        });

    }

}
