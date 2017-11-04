package com.example.dario.project3;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends Activity {

    private Button createAccount;
    private Button placeHold;
    private Button cancelHold;
    private Button manageSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        createAccount = (Button) findViewById(R.id.createAccount);
        placeHold = (Button) findViewById(R.id.placeHold);
        cancelHold = (Button) findViewById(R.id.cancelHold);
        manageSystem = (Button) findViewById(R.id.manageSystem);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent createUser = new Intent(getApplicationContext(),createAccount.class);
                startActivity(createUser);
                return;

            }
        });

        placeHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent placeHold = new Intent(getApplicationContext(),PlaceHold.class);
                startActivity(placeHold);
                return;
            }
        });

        cancelHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();

                extras.putBoolean("cancelHold", true);
                Intent cancelHold = new Intent(getApplicationContext(), createAccount.class);
                cancelHold.putExtras(extras);
                startActivity(cancelHold);
                return;
            }
        });


        manageSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manageSystem = new Intent(getApplicationContext(),createAccount.class);

                Bundle extraInfo = new Bundle();

                extraInfo.putBoolean("admin", true);

                manageSystem.putExtras(extraInfo);

                startActivity(manageSystem);
                return;
            }
        });

        Database database = new Database(this);

        database.displayUserTable();
        database.displayTransactionTable();
    }
}
