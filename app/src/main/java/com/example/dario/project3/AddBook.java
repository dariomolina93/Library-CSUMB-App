package com.example.dario.project3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by dario on 5/14/17.
 */

public class AddBook extends Activity {

    private EditText title, author,ISBN, fee;
    private Button submit,done;
    private double money;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

        title = (EditText) findViewById(R.id.title);
        author =(EditText) findViewById(R.id.author);
        ISBN = (EditText) findViewById(R.id.ISBN);
        fee = (EditText) findViewById(R.id.fee);
        submit = (Button) findViewById(R.id.button);
        done = (Button) findViewById(R.id.done);

        done.setVisibility(View.GONE);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Database database = new Database(getApplicationContext());
                //long id, String userRenting, String bookTitle,
                // String author, String ISBN, double feePerHour)

                long id = database.returnLastBookID();
                id++;
                Book book = new Book(id,"", title.getText().toString(), author.getText().toString(),
                        ISBN.getText().toString(), money);

                database.insertBook(book);

                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);

                return;

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkUserInput())
                    if(checkBookInDatabase())
                    {
                        submit.setText("Want to Change Data? Change then press here.");
                        done.setVisibility(View.VISIBLE);
                        return;
                    }





            }
        });



    }

    private boolean checkUserInput()
    {
        int counter = 0;

        if(title.getText().toString().isEmpty())
        {
            title.setText("Field cannot be empty.");
            counter++;

        }

        if(author.getText().toString().isEmpty())
        {
            counter++;
            author.setText("Field cannot be empty.");
        }

        if(ISBN.getText().toString().isEmpty())
        {
            counter++;
            ISBN.setText("Field cannot be empty.");
        }

        if(fee.getText().toString().isEmpty() || !convertFeeToDouble())
        {
            counter++;
            fee.setText("Field cannot be empty.");
        }

        if(counter == 0)
        {
            return true;
        }

        else {
            Intent mainActivity = new Intent(getApplicationContext(),BookError.class);
            startActivity(mainActivity);
            return false;
        }
    }

    private boolean checkBookInDatabase()
    {
        Database database = new Database(this);

        ArrayList<Book> books = database.getBooks();

        for(int i = 0; i < books.size(); i++)
        {
            if(books.get(i).getISBN().equals(ISBN.getText().toString()))
            {
                Intent mainActivity = new Intent(getApplicationContext(),BookError.class);
                startActivity(mainActivity);
                return false;
            }

        }

        database.closeDB();

        return true;

    }

    private boolean convertFeeToDouble()
    {
        String Fee = fee.getText().toString();

        Fee = Fee.replace('$','0');

        try{
            money = Double.parseDouble(Fee);
            return true;
        }
        catch (Exception e)
        {
            Intent mainActivity = new Intent(getApplicationContext(),BookError.class);
            startActivity(mainActivity);
            return false;
        }
    }

}
