package com.example.dario.project3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TimePicker;

import java.util.Calendar;


public class PlaceHold extends Activity implements View.OnClickListener{


    Button btnDatePickerPickUp, btnTimePickerPickUp, btnDatePickerReturn, btnTimePickerReturn;
    EditText txtDatePickUp, txtTimePickUp, txtDateReturn, txtTimeReturn;
    private int mYearPickUp, mMonthPickUp, mDayPickUp, mHourPickUp, mMinutePickUp;
    private int mYearReturn, mMonthReturn, mDayReturn, mHourReturn, mMinuterReturn;
    private Button submit;
    int totalHours = 0;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placehold);


        btnDatePickerPickUp=(Button)findViewById(R.id.btn_datePickup);
        btnTimePickerPickUp=(Button)findViewById(R.id.btn_timePickUp);
        txtDatePickUp=(EditText)findViewById(R.id.in_datePickup);
        txtTimePickUp=(EditText)findViewById(R.id.in_timePickUp);

        btnDatePickerReturn = (Button) findViewById(R.id.btn_dateReturn);
        btnTimePickerReturn = (Button) findViewById(R.id.btn_timeReturn);
        txtDateReturn = (EditText) findViewById(R.id.in_dateReturn);
        txtTimeReturn = (EditText) findViewById(R.id.in_timeReturn);
        submit = (Button) findViewById(R.id.submit);


        btnDatePickerPickUp.setOnClickListener(this);
        btnTimePickerPickUp.setOnClickListener(this);
        btnDatePickerReturn.setOnClickListener(this);
        btnTimePickerReturn.setOnClickListener(this);


        submit.setOnClickListener(this);

    }//end of onCreate

    public void onClick(View v) {

        if(v == submit)
        {
            if(checkRentalDays())
            {
             Intent Books = new Intent(this, BooksJava.class);

                String pickUpTime = formatDate(mMonthPickUp,mDayPickUp,mYearPickUp,mHourPickUp,mMinutePickUp);
                String returnTime = formatDate(mMonthReturn, mDayReturn, mYearReturn, mHourReturn, mMinuterReturn);

                Bundle placeHold = new Bundle();
                placeHold.putInt("totalHours", totalHours);
                placeHold.putString("pickUpTime", pickUpTime);
                placeHold.putString("returnTime", returnTime);

                Log.d("PlaceHold totalHours: ", Integer.toString(totalHours));

                Books.putExtras(placeHold);
                startActivity(Books);
                return;
            }

        }

        if (v == btnDatePickerPickUp) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYearPickUp = c.get(Calendar.YEAR);
            mMonthPickUp = c.get(Calendar.MONTH);
            mDayPickUp = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            mYearPickUp = year;
                            mMonthPickUp = monthOfYear + 1;
                            mDayPickUp = dayOfMonth;

                            txtDatePickUp.setText((monthOfYear+1) + "-" + dayOfMonth + "-" + year);

                        }
                    }, mYearPickUp, mMonthPickUp, mDayPickUp);
            datePickerDialog.show();
        }

        if(v == btnDatePickerReturn)
        {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYearReturn = c.get(Calendar.YEAR);
            mMonthReturn = c.get(Calendar.MONTH);
            mDayReturn = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            mMonthReturn = monthOfYear + 1;
                            mYearReturn = year;
                            mDayReturn = dayOfMonth;

                            txtDateReturn.setText((monthOfYear + 1) + "- " + dayOfMonth + "-" + year);

                        }
                    }, mYearReturn, mMonthReturn, mDayReturn);
            datePickerDialog.show();


        }

        if(v == btnTimePickerReturn)
        {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHourReturn = c.get(Calendar.HOUR_OF_DAY);
            mMinuterReturn = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            mHourReturn = hourOfDay;
                            mMinuterReturn = minute;

                            txtTimeReturn.setText(hourOfDay + ":" + minute);
                        }
                    }, mHourReturn, mMinuterReturn, false);
            timePickerDialog.show();

        }

        if (v == btnTimePickerPickUp) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHourPickUp = c.get(Calendar.HOUR_OF_DAY);
            mMinutePickUp = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            mHourPickUp = hourOfDay;
                            mMinutePickUp = minute;

                            txtTimePickUp.setText(hourOfDay + ":" + minute);
                        }
                    }, mHourPickUp, mMinutePickUp, false);
            timePickerDialog.show();
        }
    }//end of onClick method

    public String formatDate(int month, int day, int year, int hour, int minute)
    {
        String format = Integer.toString(month) + "/" + Integer.toString(day) + "/"
                + Integer.toString(year) + "_" + Integer.toString(hour) + ":" + Integer.toString(minute);

        return format;

    }



    public boolean checkRentalDays()
    {
        /*
        Log.d("mMonthReturn: ", Integer.toString(mMonthReturn));
        Log.d("mMONTHPickUp: ", Integer.toString(mMonthPickUp));
        Log.d("mDayReturn: ", Integer.toString(mDayReturn));
        Log.d("mDayPickUp: ", Integer.toString(mDayPickUp));\
        */

        int monthDifference = mMonthReturn - mMonthPickUp;
        int dayDifference = mDayReturn - mDayPickUp;
        int timeDifference = mHourReturn - mHourPickUp;

        //int monthDifference = monthReturn - monthPickUp;
       // int dayDifference = dayPickUp - dayReturn;
        //int timeDifference = timeReturn - timePickUp;


        if(monthDifference == 0)
            if(dayDifference > 7)
            {
                Intent error = new Intent(getApplicationContext(),SevenDaysError.class);
                startActivity(error);
                return false;
            }

        if(monthDifference == 0)
            if(dayDifference == 0)
                if(timeDifference > 0)
                {
                    totalHours = ((24*dayDifference) + timeDifference);
                    return true;
                }

        if(monthDifference == 1)
            if(dayDifference > -24)
            {
                Intent error = new Intent(getApplicationContext(),SevenDaysError.class);
                startActivity(error);
                return false;

            }

        if(monthDifference == -1)
            if(dayDifference <= 24)
                if(timeDifference > 0)
                {
                    Intent error = new Intent(getApplicationContext(),SevenDaysError.class);
                    startActivity(error);
                    return false;

                }

        if(monthDifference >= 1)
        {
            Intent error = new Intent(getApplicationContext(),SevenDaysError.class);
            startActivity(error);
            return false;
        }

        if(monthDifference >= 0)
            if(dayDifference < 0)
            {
                Intent error = new Intent(getApplicationContext(),SevenDaysError.class);
                startActivity(error);
                return false;
            }

        totalHours = ((24*dayDifference) + timeDifference);

        return true;
    }

}// end of class

    /*
    private Spinner monthSpinnerPickUp;
    private Spinner daySpinnerPickUp;
    private Spinner monthSpinnerReturn;
    private Spinner daySpinnerReturn;
    private Spinner timeSpinnerPickUp;
    private Spinner timeSpinnerReturn;
    private RadioButton amPickUp;
    private RadioButton pmPickUp;
    private RadioButton amReturn;
    private RadioButton pmReturn;
    private Button submit;
    int totalHours;
    */

        /*
        monthSpinnerPickUp = (Spinner) findViewById(R.id.monthSpinnerPickUp);
        daySpinnerPickUp = (Spinner) findViewById(R.id.daySpinnerPickUp);
        monthSpinnerReturn = (Spinner) findViewById(R.id.monthSpinnerReturn);
        daySpinnerReturn = (Spinner) findViewById(R.id.daySpinnerReturn);
        timeSpinnerPickUp = (Spinner) findViewById(R.id.timeSpinnerPickUp);
        timeSpinnerReturn = (Spinner) findViewById(R.id.timeSpinnerReturn);
        amPickUp = (RadioButton) findViewById(R.id.amPickUp);
        amPickUp.setChecked(true);
        amReturn = (RadioButton) findViewById(R.id.amReturn);
        amReturn.setChecked(true);
        pmPickUp = (RadioButton) findViewById(R.id.pmPickUp);
        pmReturn = (RadioButton) findViewById(R.id.pmReturn);
        submit = (Button) findViewById(R.id.submit);


        //set array adapter to Spinners
        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(
                this,R.array.months,android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(
                this,R.array.days,android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(
                this,R.array.time,android.R.layout.simple_spinner_item);

        adapterMonth.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        adapterTime.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        adapterDay.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);


        monthSpinnerPickUp.setAdapter(adapterMonth);
        monthSpinnerReturn.setAdapter(adapterMonth);

        daySpinnerPickUp.setAdapter(adapterDay);
        daySpinnerReturn.setAdapter(adapterDay);

        timeSpinnerPickUp.setAdapter(adapterTime);
        timeSpinnerReturn.setAdapter(adapterTime);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkRentalDays())
                {
                    Database database = new Database(getApplicationContext());
                    ArrayList<Transaction> transactions = database.getTransactions();

                    DateFormat date = new SimpleDateFormat()




                }



            }
        });
        */





