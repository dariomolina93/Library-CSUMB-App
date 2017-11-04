

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

import java.util.ArrayList;

/**
 * Created by dario on 5/10/17.
 */



public class BooksJava extends Activity{

    private String pickUpTime, returnTime;
    private int totalHours;
    private ArrayList<Book> books;
    private Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books);

        Bundle extras = getIntent().getExtras();

        if(extras != null)
        {
            pickUpTime = extras.getString("pickUpTime");
            returnTime = extras.getString("returnTime");
            totalHours = extras.getInt("totalHours");
        }

        Log.d("BooksJava totalHours: ", Integer.toString(totalHours));



        displayBook();
    }


    public boolean checkAvailability(String date1, String date2)
    {

        String[] date1Split = date1.split("/");
        String[] date2Split = date2.split("/");

        date1Split[2] = date1Split[2].replaceAll("_","/");
        date1Split[2] = date1Split[2].replaceAll(":","/");

        String[] date1SplitContinued = date1Split[2].split("/");


        int monthDate1 = Integer.parseInt(date1Split[0]);
        int dayDate1 = Integer.parseInt(date1Split[1]);
        int yearDate1 = Integer.parseInt(date1SplitContinued[0]);
        int hourDate1 = Integer.parseInt(date1SplitContinued[1]);
        int minuteDate1 = Integer.parseInt(date1SplitContinued[2]);


        date2Split[2] = date2Split[2].replaceAll("_","/");
        date2Split[2] = date2Split[2].replaceAll(":","/");

        String[] date2SplitContinued = date2Split[2].split("/");

        int monthDate2 = Integer.parseInt(date2Split[0]);
        int dayDate2 = Integer.parseInt(date2Split[1]);
        int yearDate2 = Integer.parseInt(date2SplitContinued[0]);
        int hourDate2 = Integer.parseInt(date2SplitContinued[1]);
        int minuteDate2 = Integer.parseInt(date2SplitContinued[2]);

    /*
    System.out.println("monthDate1: " + monthDate1+ " Day: "+ dayDate1 + " Year: " + yearDate1 + " Hour: " + hourDate1 + " Minute: "+ minuteDate1);

    System.out.println("monthDate1: " + monthDate2+ " Day: "+ dayDate2 + " Year: " + yearDate2 + " Hour: " + hourDate2 + " Minute: "+ minuteDate2);
    */


        if(yearDate2 < yearDate1)
            return false;

        if(yearDate2 > yearDate1)
            return true;

        if(monthDate2 > monthDate1)
            return true;

        if(monthDate2 == monthDate1)
            if(dayDate2 > dayDate1)
                return true;

        if(monthDate2 == monthDate1)
            if(dayDate2 == dayDate1)
                if(hourDate2 == hourDate1)
                    if(minuteDate2 >= minuteDate1)
                        return true;

                    else
                        return false;

        if(monthDate2 == monthDate1)
            if(dayDate2 == dayDate1)
                if(hourDate2 >= hourDate1)
                    return true;


        return false;

    }


    public void displayBook()
    {
        Log.d("BooksJava.java", "Inside DisplayBook()");

        final Database database = new Database(getApplicationContext());

        final ArrayList<Transaction> transactions = database.getTransactions();
        books = database.getBooks();
        int number;

        buttons = new Button[books.size()];

        int counter = 0;

        if(transactions == null || transactions.size() == 0)
        {
            counter++;
            Log.d("Inside DisplayBook()", "Inside if statement");
            for(int i=0;i<books.size();i++)
            {
                /* Find Tablelayout defined in main.xml */
                TableLayout tl=(TableLayout)findViewById(R.id.table);

                /* Create a new row to be added. */
                TableRow tr=new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));

                /* Create a Button to be the row-content. */
                Button b=new Button(this);

                b.setId(i);
                b.setText(books.get(i).getBookTitle());
                b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
                Log.d("BooksJava bookID: ", Long.toString(books.get(i).getId()));
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double fee = totalHours * books.get(v.getId()).getFeePerHour();

                        //Log.d("BooksJava LIstener: ", Long.toString(books.get(v.getId()).getId()));

                        Intent login = new Intent(getApplicationContext(), createAccount.class);

                        Bundle extras = new Bundle();

                        extras.putString("pickUpTime", pickUpTime);
                        extras.putString("returnTime", returnTime);
                        extras.putDouble("fee", fee);
                        extras.putBoolean("reserveBook", true);
                        extras.putLong("reservationID", books.get(v.getId()).getId());
                        extras.putString("bookTitle", books.get(v.getId()).getBookTitle());


                        login.putExtras(extras);

                        startActivity(login);
                        return;

                    }
                });

                buttons[i] = b;
                /* Add Button to row. */
                tr.addView(b);
                /* Add row to TableLayout. */
//tr.setBackgroundResource(R.drawable.sf_gradient_03);
                tl.addView(tr,new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT));

            }

        }

        else
        {
            Log.d("Inside DisplayBook()", "Inside else statement");
            for(int i= 0; i < books.size(); i++)
            {

                String title = books.get(i).getBookTitle();

              Transaction transaction = database.getBookFromTransaction(title);

                if(transaction == null)
                {
                    counter++;
                    /* Find Tablelayout defined in main.xml */
                    TableLayout tl=(TableLayout)findViewById(R.id.table);

                /* Create a new row to be added. */
                    TableRow tr=new TableRow(this);
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));

                /* Create a Button to be the row-content. */
                    Button b=new Button(this);

                    b.setId(i);
                    b.setText(books.get(i).getBookTitle());
                    b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
                    Log.d("BooksJava bookID: ", Long.toString(books.get(i).getId()));
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            double fee = totalHours * books.get(v.getId()).getFeePerHour();

                            //Log.d("BooksJava LIstener: ", Long.toString(books.get(v.getId()).getId()));

                            Intent login = new Intent(getApplicationContext(), createAccount.class);

                            Bundle extras = new Bundle();

                            extras.putString("pickUpTime", pickUpTime);
                            extras.putString("returnTime", returnTime);
                            extras.putDouble("fee", fee);
                            extras.putBoolean("reserveBook", true);
                            extras.putLong("reservationID", books.get(v.getId()).getId());
                            extras.putString("bookTitle", books.get(v.getId()).getBookTitle());


                            login.putExtras(extras);

                            startActivity(login);
                            return;

                        }
                    });

                    buttons[i] = b;
                /* Add Button to row. */
                    tr.addView(b);
                /* Add row to TableLayout. */
//tr.setBackgroundResource(R.drawable.sf_gradient_03);
                    tl.addView(tr,new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT));
                }

                else
                {
                    if(checkAvailability(transaction.getReturnDate(), pickUpTime))
                    {
                        counter++;
                        /* Find Tablelayout defined in main.xml */
                        TableLayout tl=(TableLayout)findViewById(R.id.table);

                /* Create a new row to be added. */
                        TableRow tr=new TableRow(this);
                        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));

                /* Create a Button to be the row-content. */
                        Button b=new Button(this);

                        b.setId(i);
                        b.setText(transactions.get(i).getBookTitle());
                        b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
                        Log.d("BooksJava bookID: ", Long.toString(transactions.get(i).getId()));
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Book elseBook = database.getBook(transactions.get(v.getId()).getBookTitle());
                                double fee = totalHours * elseBook.getFeePerHour();

                                //Log.d("BooksJava LIstener: ", Long.toString(books.get(v.getId()).getId()));

                                Intent login = new Intent(getApplicationContext(), createAccount.class);

                                Bundle extras = new Bundle();

                                extras.putString("pickUpTime", pickUpTime);
                                extras.putString("returnTime", returnTime);
                                extras.putDouble("fee", fee);
                                extras.putBoolean("reserveBook", true);
                                extras.putLong("reservationID", transactions.get(v.getId()).getId());
                                extras.putString("bookTitle", elseBook.getBookTitle());


                                login.putExtras(extras);

                                startActivity(login);
                                return;

                            }
                        });

                        buttons[i] = b;
                /* Add Button to row. */
                        tr.addView(b);
                /* Add row to TableLayout. */
//tr.setBackgroundResource(R.drawable.sf_gradient_03);
                        tl.addView(tr,new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT));


                    }


                }

            }


        }

        if(counter == 0)
        {
            Intent main = new Intent(getApplicationContext(),NoBooks.class);
            startActivity(main);
            return;
        }
    }//end of function

}



