package com.example.dario.project3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by dario on 5/14/17.
 */

public class ReservationCancel extends Activity {

    private String userName;
    private Button submit;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_confirm_cancel);

        submit = (Button) findViewById(R.id.button);

        Bundle extras = getIntent().getExtras();

        if(extras != null)
        {
            userName = extras.getString("userName");
        }


        Log.d("ResevationCan", " userName= " + userName);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                    public Transaction(long id, String userName, String pickUpDate,
                       String returnDate, String bookTitle, double total, String type, long reservationID, String currentDate)
                 */
                Database database = new Database(getApplicationContext());

                ArrayList<Transaction> transactions = database.getTransactions();

                Transaction transaction = null;

                String date = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());


                for(int i =0 ; i < transactions.size(); i++)
                {
                    if(transactions.get(i).getUserName().equals(userName))
                    {
                         transaction = new Transaction(transactions.get(i).getId(),
                                transactions.get(i).getUserName(),transactions.get(i).getPickUpDate(), transactions.get(i).getReturnDate(),
                                transactions.get(i).getBookTitle(),transactions.get(i).getTotal(), "cancelation", transactions.get(i).getReservationID(),
                                date);

                        break;
                    }
                }

                if(transaction == null)
                {
                    Log.d("ReservationCancel:", " transaction == null");
                    return;
                }

                database.deleteTransaction(userName);

                Toast.makeText(getApplicationContext(),"Record Deleted", Toast.LENGTH_LONG).show();



                database.insertTransaction(transaction);

                Intent main = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(main);

                return;


            }
        });

    }


}
