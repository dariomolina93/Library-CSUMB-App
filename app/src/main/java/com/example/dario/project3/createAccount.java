package com.example.dario.project3;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class createAccount extends Activity {

    private EditText username;
    private EditText password;
    private Button submit;
    private int counter = 0;
    private int libCounter = 0;
    private int reserveCounter = 0;
    private int cancelCounter = 0;
    private  boolean success1 = false;

    private boolean admin = false;

    private String pickUpTime, returnTime;
    private String bookTitle;
    private double fee;
    private boolean reserveBook = false;
    private long reservationID;
    private boolean cancelHold = false;

    Toast toast;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Bundle extras = getIntent().getExtras();
        /*



                        extras.putLong("reservationId", books.get(number).getId());
                        extras.putString("bookTitle", books.get(number).getBookTitle());
        */


        if(extras != null)
        {
            pickUpTime = extras.getString("pickUpTime");
            returnTime = extras.getString("returnTime");
            fee = extras.getDouble("fee");
            reserveBook = extras.getBoolean("reserveBook");
            reservationID = extras.getLong("reservationID");
            bookTitle = extras.getString("bookTitle");
            cancelHold = extras.getBoolean("cancelHold");
            admin = extras.getBoolean("admin");


        }


        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Database database = new Database(getApplicationContext());
                String userName = username.getText().toString();
                String passWord = password.getText().toString();


                if(cancelHold) {
                    User user = database.getUser(userName);

                    if (user == null) {
                        if (cancelCounter == 0) {
                            toast = Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG);
                            toast.show();
                            cancelCounter++;
                            return;
                        }
                        else {
                            cancelCounter = 0;
                            Intent error = new Intent(getApplicationContext(), confirmError.class);
                            startActivity(error);
                            return;
                        }

                    }

                    if(!(user.getPassword().equals(passWord)))
                    {
                        if(cancelCounter == 0)
                        {
                            toast = Toast.makeText(getApplicationContext(),"Password Entered Incorrect", Toast.LENGTH_LONG);
                            toast.show();
                            reserveCounter++;
                            return;
                        }

                        else
                        {
                            cancelCounter = 0;
                            Intent error = new Intent(getApplicationContext(),confirmError.class);
                            startActivity(error);
                            return;
                        }
                    }

                    Bundle extras = new Bundle();
                    extras.putString("userName", userName);
                    Intent cancel = new Intent(getApplicationContext(), DisplayReservations.class);

                    cancel.putExtras(extras);
                    startActivity(cancel);
                    return;
                }

                if(reserveBook)
                {

                    User user = database.getUser(userName);

                    if(user == null)
                    {
                        if(reserveCounter == 0)
                        {
                            toast = Toast.makeText(getApplicationContext(),"User not found", Toast.LENGTH_LONG);
                            toast.show();
                            reserveCounter++;
                            return;
                        }

                        else
                        {
                            reserveCounter = 0;
                            Intent error = new Intent(getApplicationContext(),confirmError.class);
                            startActivity(error);
                            return;
                        }
                    }

                    if(!(user.getPassword().equals(passWord)))
                    {
                        if(reserveCounter == 0)
                        {
                            toast = Toast.makeText(getApplicationContext(),"Password Entered Incorrect", Toast.LENGTH_LONG);
                            toast.show();
                            reserveCounter++;
                            return;
                        }

                        else
                        {
                            reserveCounter = 0;
                            Intent error = new Intent(getApplicationContext(),confirmError.class);
                            startActivity(error);
                            return;
                        }
                    }

                    Bundle info = new Bundle();
                    info.putString("userName", userName);
                    info.putString("pickUpTime", pickUpTime);
                    info.putString("returnTime", returnTime);
                    info.putLong("reservationID", reservationID);
                    info.putString("bookTitle", bookTitle);
                    info.putDouble("fee", fee);

                    Intent confirm = new Intent(getApplicationContext(), confirmPlaceHold.class);
                    confirm.putExtras(info);

                    startActivity(confirm);
                    return;

                }

                if(admin)
                {
                    if(userName.equals("admin2"))
                        if(passWord.equals("!admin2"))
                        {
                            Intent extras = new Intent(getApplicationContext(), DisplayTransactions.class);
                            startActivity(extras);
                            return;
                        }

                    if(libCounter == 0)
                    {
                        toast = Toast.makeText(getApplicationContext(),"Invalid Information Entereed", Toast.LENGTH_LONG);
                        toast.show();
                        libCounter++;
                        return;
                    }

                    else
                    {
                        libCounter = 0;
                        Intent error = new Intent(getApplicationContext(),confirmError.class);
                        startActivity(error);
                        return;
                    }



                }


                if(validateUserInput())
                    if(handleDuplicateAccount())
                    {
                        User user = new User(userName, passWord);

                        long id = database.returnLastUserID();
                        id++;
                        user.setId(id);
                        database.insertUser(user);

                        database.displayUserTable();

                        toast = Toast.makeText(getApplicationContext(),"Account Created Success", Toast.LENGTH_LONG);
                        toast.show();


                        ArrayList<Transaction> transactions;


                        String date = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());

                        long TranID;
                        TranID = database.returnLastTransactionID();

                        Log.d("ID IS: ", Long.toString(TranID));

                        if(TranID == 0) {

                            TranID = 1;

                            Log.d("INSIDE IF STATEMENT ", "ID: "  + Long.toString(TranID));

                        }

                        else {
                            TranID++;

                            Log.d("INSIDE ELSE STATEMENT ", "ID: "  + Long.toString(TranID));
                        }


                        Transaction transaction = new Transaction(TranID,userName,date, "new account");

                        database.insertTransaction(transaction);

                        database.displayTransactionTable();

                        Intent main = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(main);
                        return;

                    }

                return;



            }//end of function
        });//end of listerner



    }


    private boolean handleDuplicateAccount()
    {
        Database database = new Database(this);
        ArrayList<User> users = database.getUsers();

        Log.d("HandDuplicate", "user size: " + users.size());

        for(int i = 0; i < users.size(); i++)
        {
            if(users.get(i).getUsername().equals(username.getText().toString()))
            {
                if(counter == 0)
                {
                    toast = Toast.makeText(getApplicationContext(), "Username is already taken!\nEnter information again", Toast.LENGTH_SHORT);
                    toast.show();
                    counter++;
                    return false;
                }

                else
                {
                    counter = 0;
                    Intent error = new Intent(getApplicationContext(),confirmError.class);
                    startActivity(error);
                    return false;
                }
            }

        }

        return true;

    }

    private boolean validateUserInput()
    {
        String userName = username.getText().toString();
        String passWord = password.getText().toString();

        boolean success = true;

        if(passWord.equals("!admin2"))
        {

            if(counter == 0) {
                toast = Toast.makeText(getApplicationContext(), "Password is reserved for librarian!\nEnter information again", Toast.LENGTH_SHORT);
                toast.show();
                counter++;
                return false;
            }

            else
            {
                counter = 0;
                Intent error = new Intent(getApplicationContext(),confirmError.class);
                startActivity(error);
                return false;
            }
        }

        if(!userName.contains("!") && !userName.contains("@") && !userName.contains("#") && !userName.contains("$"))
        {
            if(counter == 0)
            {
                toast = Toast.makeText(getApplicationContext(), "Username does not contain special char\nEnter information again", Toast.LENGTH_SHORT);
                toast.show();
                counter++;
                return false;
            }

            else
            {
                counter = 0;
                Intent error = new Intent(getApplicationContext(),confirmError.class);
                startActivity(error);
                return false;
            }
        }

        int letter = 0;
        for(int i = 0 ; i < userName.length(); i++)
        {
            if((userName.charAt(i) >= 65 && userName.charAt(i) <= 90) ||
                    (userName.charAt(i) >= 97 && userName.charAt(i) <= 122))
            {
                letter++;
            }
        }

            if (letter < 3) {
                if (counter == 0) {
                    toast = Toast.makeText(getApplicationContext(), "Username does not contain 3 letters\nEnter information again", Toast.LENGTH_SHORT);
                    toast.show();
                    counter++;
                    return false;
                }
                else {
                    counter = 0;
                    Intent error = new Intent(getApplicationContext(), confirmError.class);
                    startActivity(error);
                    return false;
                }

            }


        //******************************Password
            if (!passWord.contains("!") && !passWord.contains("@") &&
                    !passWord.contains("#") && !passWord.contains("$")) {
                if (counter == 0) {
                    toast = Toast.makeText(getApplicationContext(), "Password does not contain special char\nEnter information again", Toast.LENGTH_SHORT);
                    toast.show();
                    counter++;
                    return false;
                } else {
                    counter = 0;
                    Intent error = new Intent(getApplicationContext(), confirmError.class);
                    startActivity(error);
                    return false;
                }
            }


        letter = 0;
        for(int i = 0 ; i < passWord.length(); i++)
        {
            if((passWord.charAt(i) >= 65 && passWord.charAt(i) <= 90) ||
                    (passWord.charAt(i) >= 97 && passWord.charAt(i) <= 122))
            {
                letter++;
            }
        }

        if(letter < 3)
        {
            if(counter == 0)
            {
                toast = Toast.makeText(getApplicationContext(), "Password does not contain 3 letters\nEnter information again", Toast.LENGTH_SHORT);
                toast.show();
                counter++;
                return false;
            }

            else
            {
                counter = 0;
                Intent error = new Intent(getApplicationContext(),confirmError.class);
                startActivity(error);
                return false;
            }

        }

        return true;

    }


}
