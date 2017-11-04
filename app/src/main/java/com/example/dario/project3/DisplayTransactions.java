package com.example.dario.project3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dario on 5/14/17.
 */

public class DisplayTransactions extends Activity {

    private Button submit;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_logs);

        submit = (Button) findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent book = new Intent(getApplicationContext(),NewBook.class);
                startActivity(book);
                return;
            }
        });

        displayReservations();
    }

    public void displayReservations()
    {
        Database database = new Database(this);

        final ArrayList<Transaction> transactions = database.getTransactions();

        for(int i = 0; i < transactions.size(); i++)
        {
            /*
             Transaction(long id, String userName, String pickUpDate,
                       String returnDate, String bookTitle, double total, String type,
                        long reservationID, String currentDate)
             */

                /* Find Tablelayout defined in main.xml */
                TableLayout tl = (TableLayout) findViewById(R.id.table);

                /* Create a new row to be added. */
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                //adding textviews to tr
                TextView id = new TextView(this);
            TextView userName = new TextView(this);
            TextView pickUpDate = new TextView(this);
            TextView returnDate = new TextView(this);
            TextView bookTitle = new TextView(this);
            TextView total = new TextView(this);
            TextView type = new TextView(this);
                TextView reservationNumber = new TextView(this);
                TextView pickUpTime = new TextView(this);
                TextView returnTime = new TextView(this);

                id.setText(Long.toString(transactions.get(i).getId()) + "  ");
                userName.setText(transactions.get(i).getUserName() + " ");
                total.setText(Double.toString(transactions.get(i).getTotal()) + "  ");
                type.setText(transactions.get(i).getType() + "  ");
                reservationNumber.setText(Long.toString(transactions.get(i).getReservationID()) + "  ");
                pickUpTime.setText(transactions.get(i).getPickUpDate() + "  ");
                returnTime.setText(transactions.get(i).getReturnDate() + "  ");
                bookTitle.setText(transactions.get(i).getBookTitle() + "  ");


                //adding textviews to tr
                tr.addView(id);
                tr.addView(userName);
                tr.addView(total);
                tr.addView(type);
                tr.addView(reservationNumber);
                tr.addView(pickUpTime);
                tr.addView(returnTime);
                tr.addView(bookTitle);
                /* Add row to TableLayout. */
//tr.setBackgroundResource(R.drawable.sf_gradient_03);
                tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        }
    }
}
