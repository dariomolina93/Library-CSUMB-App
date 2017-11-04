package com.example.dario.project3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by dario on 5/14/17.
 */

public class DisplayReservations extends Activity {

    private String userName;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_reservations);

        Bundle extra = getIntent().getExtras();

        if(extra != null)
        {
            userName = extra.getString("userName");
        }

        displayReservations();
    }

    public void displayReservations()
    {
        Database database = new Database(this);

        int counter = 0;

        final ArrayList<Transaction> transactions = database.getTransactions();

        for(int i = 0; i < transactions.size(); i++)
        {


            if(userName.equals(transactions.get(i).getUserName()))
            {

                counter++;
                                          /* Find Tablelayout defined in main.xml */
                TableLayout tl = (TableLayout) findViewById(R.id.table);

                /* Create a new row to be added. */
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                /* Create a Button to be the row-content. */
                Button b = new Button(this);

                b.setId(i);
                b.setText("Cancel");
                b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent login = new Intent(getApplicationContext(), ReservationCancel.class);

                        Bundle extras = new Bundle();

                        extras.putString("userName", transactions.get(v.getId()).getUserName());

                        login.putExtras(extras);

                        startActivity(login);
                        return;


                    }
                });

                //adding textviews to tr
                TextView reservationNumber = new TextView(this);
                TextView pickUpTime = new TextView(this);
                TextView returnTime = new TextView(this);
                TextView bookTitle = new TextView(this);

                reservationNumber.setText(Long.toString(transactions.get(i).getReservationID()));
                pickUpTime.setText(transactions.get(i).getPickUpDate());
                returnTime.setText(transactions.get(i).getReturnDate());
                bookTitle.setText(transactions.get(i).getBookTitle());

                //adding textviews to tr
                tr.addView(reservationNumber);
                tr.addView(pickUpTime);
                tr.addView(returnTime);
                tr.addView(bookTitle);

                /* Add Button to row. */
                tr.addView(b);
                /* Add row to TableLayout. */
//tr.setBackgroundResource(R.drawable.sf_gradient_03);
                tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            }

        }

        if(counter == 0)
        {
                                                          /* Find Tablelayout defined in main.xml */
                TableLayout tl = (TableLayout) findViewById(R.id.table);

                /* Create a new row to be added. */
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                /* Create a Button to be the row-content. */
                Button b = new Button(this);

                b.setId(0);
                b.setText("Okay");
                b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent login = new Intent(getApplicationContext(), MainActivity.class);

                        startActivity(login);
                        return;


                    }
                });

                //adding textviews to tr
                TextView reservationNumber = new TextView(this);


                reservationNumber.setText("No Reservations Made by User.");


                //adding textviews to tr

                /* Add Button to row. */
                tr.addView(reservationNumber);
                tr.addView(b);
                /* Add row to TableLayout. */
//tr.setBackgroundResource(R.drawable.sf_gradient_03);
                tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        }
    }

}
